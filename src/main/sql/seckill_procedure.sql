drop procedure if exists seckill.execute_seckill;
-- out_result: 1 秒杀成功, 0 秒杀关闭, -1 重复秒杀, -2 内部错误
create procedure seckill.execute_seckill(in in_seckill_id bigint, in in_user_phone varchar(255), in in_kill_time timestamp, out out_result int)
  begin
    declare rows int default -3;
    start transaction;
    insert ignore into success_killed_record(seckill_id, user_phone, state) values (in_seckill_id, in_user_phone, 0);
    select row_count() into rows;
    -- 重复秒杀
    if (rows=0) then
      rollback;
      set out_result=-1;
    -- 内部错误
    elseif (rows<0) then
      rollback;
      set out_result=-2;
    -- 插入记录成功
    else
      update seckill_product set number=number-1 where seckill_id=in_seckill_id and start_time<=in_kill_time and end_time>=in_kill_time and number>0;
      select row_count() into rows;
      -- 减库存失败
      if (rows=0) then
        rollback;
        set out_result=0;
      -- 内部错误
      elseif (rows<0) then
        rollback;
        set out_result=-2;
      -- 减库存成功
      else
        commit;
        set out_result=1;
      end if;
    end if;
  end;

-- 清空秒杀记录，查看数据
set @seckill_id=1000;
delete from success_killed_record where seckill_id=@seckill_id;
select * from success_killed_record where seckill_id=@seckill_id;
select * from seckill_product where seckill_id=@seckill_id;

-- 调用存储过程，查看结果
call seckill.execute_seckill(@seckill_id, '12345678901', now(), @out_result);
select * from success_killed_record where seckill_id=@seckill_id;
select * from seckill_product where seckill_id=@seckill_id;
select @out_result;