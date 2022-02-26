package fit.hcp.imoocsecskill.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Date: 2022/02/07/10:58 AM
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(){
        return "forward:/seckill/list";
    }
}
