package fit.hcp.imoocsecskill.web;

import fit.hcp.imoocsecskill.bean.SeckillProduct;
import fit.hcp.imoocsecskill.dto.SeckillResult;
import fit.hcp.imoocsecskill.dto.SeckillResultForClient;
import fit.hcp.imoocsecskill.dto.SeckillUrlExporter;
import fit.hcp.imoocsecskill.enums.SeckillStateEnum;
import fit.hcp.imoocsecskill.exception.RepeatSeckillException;
import fit.hcp.imoocsecskill.exception.SeckillCloseException;
import fit.hcp.imoocsecskill.service.SeckillService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Date: 2022/01/27/5:20 PM
 */
@Slf4j
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    SeckillService seckillService;

    /**
     * 指向列表页
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<SeckillProduct> allSeckillProduct = seckillService.getAllSeckillProduct();
        model.addAttribute("productList", allSeckillProduct);
        return "list"; // classpath:/templates//list.html
    }

    /**
     * 指向详情页
     *
     * @param seckillId
     * @param model
     * @return
     */
    @GetMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null)
            return "redirect:/seckill/list";
        SeckillProduct seckillProduct = seckillService.getSeckillProductById(seckillId);
        if (seckillProduct == null)
            return "redirect:/seckill/list";
        model.addAttribute("seckillProduct", seckillProduct);
        return "detail"; // classpath:/templates//detail.html
    }


    /**
     * 返回服务器时间
     *
     * @return
     */
    @GetMapping("/time/now")
    @ResponseBody
    public SeckillResultForClient<Long> currentTime() {
        Date date = new Date();
        return new SeckillResultForClient<Long>(true, date.getTime());
    }

    /**
     * 根据商品 ID，暴露秒杀接口
     *
     * @param seckillId
     * @return
     */
    @PostMapping("/{seckillId}/exposer")
    @ResponseBody
    public SeckillResultForClient<SeckillUrlExporter> exposer(@PathVariable("seckillId") Long seckillId) {
        try {
            SeckillUrlExporter urlExporter = seckillService.exportSeckillUrl(seckillId);
            return new SeckillResultForClient<SeckillUrlExporter>(true, urlExporter);
        } catch (Exception e) {
            return new SeckillResultForClient<SeckillUrlExporter>(false, e.getMessage());
        }
    }


    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param md5
     * @param userPhone
     * @return
     */
    @PostMapping("/{seckillId}/{md5}/execution")
    @ResponseBody
    public SeckillResultForClient<SeckillResult> execute(
            @PathVariable("seckillId") Long seckillId,
            @PathVariable("md5") String md5,
            @CookieValue(value = "userPhone", required = false) String userPhone) {
        // cookie 中没有 userPhone 信息
        if (userPhone == null)
            return new SeckillResultForClient<SeckillResult>(false, "未注册");

        try {
            // 执行秒杀
            // SeckillResult seckillResult = seckillService.executeSeckill(seckillId, userPhone, md5);
            SeckillResult seckillResult = seckillService.executeSeckillProcedure(seckillId, userPhone, md5); // 存储过程
            return new SeckillResultForClient<SeckillResult>(true, seckillResult);
        } catch (SeckillCloseException e) {
            // 秒杀活动已经关闭
            SeckillResult seckillResult = new SeckillResult(seckillId, SeckillStateEnum.END);
            return new SeckillResultForClient<SeckillResult>(true, seckillResult);
        } catch (RepeatSeckillException e) {
            // 重复秒杀
            SeckillResult seckillResult = new SeckillResult(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResultForClient<SeckillResult>(true, seckillResult);
        } catch (Exception e) {
            // 其它错误
            log.error(e.getMessage(), e);
            SeckillResult seckillResult = new SeckillResult(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResultForClient<SeckillResult>(true, seckillResult);
        }
    }

}
