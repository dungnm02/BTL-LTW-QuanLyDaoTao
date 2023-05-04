package ptit.btlltwqlydaotao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.services.HocKiService;

@Controller
@RequestMapping("/qly/hocki")
public class QlyHocKiController {

    private HocKiService hocKiService;
    public QlyHocKiController(HocKiService hocKiService) {
        this.hocKiService = hocKiService;
    }
    @GetMapping("")
    public String index() {
        return "qlyhocki";
    }

    @GetMapping("/them")
    public String showThemHocKi(Model model) {
        model.addAttribute("hocKi", new HocKi());
        return "qlyhockithem";
    }

    @PostMapping("/them")
    public String submitThemHocKi(@ModelAttribute("hocKi") HocKi hocKi) {
        return "redirect:/qly/hocki";
    }

    @GetMapping("/sua/{id}")
    public String showSuaHocKi(@PathVariable("id") int id , Model model) {
        model.addAttribute("hocKi", hocKiService.findHocKiById(id));
        return "qlyhockisua";
    }

    @PostMapping("/sua/{id}")
    public String submitSuaHocKi(@PathVariable("id") int id, @ModelAttribute("hocKi") HocKi hocKi) {
        return "redirect:/qly/hocki";
    }

    @GetMapping("/xoa/{id}")
    public String showSXoaHocKi(@PathVariable("id") int id, Model model) {
        model.addAttribute("hocKi", hocKiService.findHocKiById(id));
        return "redirect:/qly/hocki";
    }

    @PostMapping("/xoa/{id}")
    public String submitXoaHocKi(@PathVariable("id") int id) {
        hocKiService.deleteHocKiById(id);
        return "redirect:/qly/hocki";
    }


}
