package ptit.btlltwqlydaotao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/svien")
public class SvienIndexController {
    @GetMapping("")
    public String index() {
        return "svienindex";
    }
}
