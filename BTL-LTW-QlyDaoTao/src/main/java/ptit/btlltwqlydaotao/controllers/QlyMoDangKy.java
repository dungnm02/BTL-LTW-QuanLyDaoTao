package ptit.btlltwqlydaotao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.services.HocKiService;

import java.util.List;

@Controller
@RequestMapping(value = "/qly/modotdangky")
public class QlyMoDangKy {

    private final HocKiService hocKiService;

    public QlyMoDangKy(HocKiService hocKiService) {
        this.hocKiService = hocKiService;
    }

    @GetMapping("")
    public String showMoDangKy(Model model) {
        //Mở đăng ký cho học kì tiếp theo (chưa bắt đầu)
        List<HocKi> dsHocKi = hocKiService.findHocKiChuaBatDau();
        model.addAttribute("dsHocKi", hocKiService.findHocKiChuaBatDau());
        return "qly_modangky";
    }

    @PostMapping("")
    public String submitMoDangKy(@RequestParam("batDau") String batDau) {
        System.out.println(batDau);
        return "redirect:/qly/modotdangky";
    }
}
