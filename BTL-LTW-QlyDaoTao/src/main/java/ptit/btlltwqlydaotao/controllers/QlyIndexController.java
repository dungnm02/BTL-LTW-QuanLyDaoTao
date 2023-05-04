package ptit.btlltwqlydaotao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/qly")
public class QlyIndexController {
    @GetMapping("")
    public String index() {
        return "qlyindex";
    }
}
