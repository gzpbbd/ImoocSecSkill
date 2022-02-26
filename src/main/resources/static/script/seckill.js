// 放交互逻辑的 js 代码
// js 模块化
let seckill = {
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer'
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    handleSeckill: function (seckillId, node) {
        // 获取秒杀地址，控制现实逻辑，执行秒杀、
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>') // 放置按钮
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            // 在回调函数中，执行交互流程
            if (result && result['success']) {
                let exposer = result['data']
                console.log('exposer: ' + JSON.stringify(exposer))
                if (exposer['exposed']) {
                    // 开启秒杀
                    // 1. 获取秒杀地址
                    let md5 = exposer['md5'];
                    let killUrl = seckill.URL.execution(seckillId, md5);
                    console.log('killUrl: ' + killUrl);
                    // 2. 绑定一次点击事件
                    $('#killBtn').one('click', function () {
                        // 执行秒杀请求
                        // 1. 先禁用按钮
                        $(this).addClass('disabled');
                        // 2. 发送秒杀请求执行秒杀
                        $.post(killUrl, {}, function (result) {
                            // post 请求失败
                            if (!result) {
                                console.log('failed to post for killing');
                                return;
                            }
                            if (result['success']) {
                                // 秒杀成功
                                let killResult = result['data'];
                                let state = killResult['state'];
                                let stateInfo = killResult['stateInfo'];
                                console.log('killResult: ' + JSON.stringify(killResult))
                                // 3. 显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>')
                            } else {
                                // 秒杀失败
                                console.log('failed to post for killing: result: ' + JSON.stringify(result))
                            }
                        })
                    })
                    node.show();
                } else {
                    // 未开启秒杀
                    let now = exposer['nowTimeStamp'];
                    let start = exposer['startTimeStamp'];
                    let end = exposer['endTimeStamp'];
                    seckill.countdown(seckillId, now, start, end);
                }


            } else {
                console.log('failed to post for exposer: result: ' + result)
            }
        })
    },
    // 验证手机号是否有效
    validatePhone: function (phone) {
        return phone && phone.length == 11 && !isNaN(phone);
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        let seckillBox = $('#seckill-box')
        // 时间判断
        if (nowTime > endTime) {
            // 秒杀结束
            console.log('秒杀结束')
            seckillBox.html('秒杀结束！')
        } else if (nowTime < startTime) {
            // 秒杀还未开始
            console.log('秒杀还未开始')
            let killTime = new Date(startTime + 1000)
            seckillBox.countdown(killTime, function (event) {
                // 时间格式
                let format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒')
                seckillBox.html(format)
            }).on('finish.countdown', function () {
                // 时间到达后的回调事件
                // 获取秒杀地址，控制现实逻辑，执行秒杀
                seckill.handleSeckill(seckillId, seckillBox)
            })
        } else {
            // 秒杀开始
            console.log('秒杀进行中')
            seckill.handleSeckill(seckillId, seckillBox)
        }


    },
    detail: {
        // 详情页初始化
        init: function (params) {
            // 手机验证的登陆，计时交互
            // 规划交互流程
            // 在 cookie 中查找手机号
            let killPhone = $.cookie('userPhone');
            console.log("cookie killPhone" + killPhone);
            // 验证手机号
            if (!seckill.validatePhone(killPhone)) {
                // 绑定手机号
                let killPhoneModal = $('#killPhoneModal');
                // 显示弹出层
                killPhoneModal.modal({
                    show: true,// 显示弹出层
                    backdrop: 'static',// 禁止位置关闭
                    keyboard: false // 关闭键盘事件
                });
                // 输入手机号后的点击事件
                $('#killPhoneBtn').click(function () {
                    let inputPhone = $('#killPhoneKey').val();
                    console.log("inputPhone " + inputPhone);
                    if (seckill.validatePhone(inputPhone)) {
                        // 把电话号码写入 cookie
                        $.cookie('userPhone', inputPhone, {expires: 7, path: '/seckill'});
                        // 刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }

            // 已经登陆
            // 秒杀倒计时
            let seckillId = params['seckillId'];
            let startTime = params['startTime'];
            let endTime = params['endTime'];
            $.get(seckill.URL.now(), {}, function (result) {
                    if (result && result['success']) {
                        let nowTime = result['data']
                        // 时间判断
                        seckill.countdown(seckillId, nowTime, startTime, endTime)
                    } else {
                        console.log('result:' + result)
                    }
                }
            );

        }
    }
}