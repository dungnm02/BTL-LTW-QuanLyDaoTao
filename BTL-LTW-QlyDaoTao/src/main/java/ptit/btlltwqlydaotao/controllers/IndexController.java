package ptit.btlltwqlydaotao.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @RequestMapping("/login/success")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("QUANLY")) {
            return "redirect:/qly";
        } else if (request.isUserInRole("GIANGVIEN")) {
            return "redirect:/gvien";
        } else {
            return "redirect:/svien";
        }
    }
}
