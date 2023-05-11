package ptit.btlltwqlydaotao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.services.HocKiService;

@Controller
@RequestMapping("/qly/hocki")
public class QlyHocKiController {

    private final HocKiService hocKiService;

    public QlyHocKiController(HocKiService hocKiService) {
        this.hocKiService = hocKiService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("dsHocKi", hocKiService.findAllHocKi());
        return "qly_hocki";
    }

    @GetMapping("/them")
    public String showThemHocKi(Model model) {
        HocKi hocKi;
        if (model.containsAttribute("hocKi")) {
            hocKi = (HocKi) model.getAttribute("hocKi");
        } else {
            hocKi = new HocKi();
        }

        model.addAttribute("message", model.getAttribute("message"));
        model.addAttribute("hocKi", hocKi);
        model.addAttribute("dsThuHai", hocKiService.getThuHaiTrongSauThang());

        return "qly_hocki_them";
    }

    @PostMapping("/them")
    public String submitThemHocKi(@ModelAttribute("hocKi") HocKi hocKi, RedirectAttributes redirectAttributes) {
        try {
            hocKiService.createHocKi(hocKi);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("hocKi", hocKi);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/qly/hocki/them";
        }
        return "redirect:/qly/hocki";
    }

    @GetMapping("/sua/{id}")
    public String showSuaHocKi(@PathVariable("id") int id, Model model) {
        model.addAttribute("hocKi", hocKiService.findById(id));
        model.addAttribute("dsThuHai", hocKiService.getThuHaiTrongSauThang());
        return "qly_hocki_sua";
    }

    @PostMapping("/sua/{id}")
    public String submitSuaHocKi(@PathVariable("id") int id, @ModelAttribute("hocKi") HocKi hocKi, RedirectAttributes redirectAttributes) {
        try {
            hocKiService.updateHocKi(hocKi);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("hocKi", hocKi);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/qly/hocki/sua/" + id;
        }
        System.out.println(hocKi);
        return "redirect:/qly/hocki";
    }

    @GetMapping("/xoa/{id}")
    public String showSXoaHocKi(@PathVariable("id") int id, Model model) {
        model.addAttribute("hocKi", hocKiService.findById(id));
        return "qly_hocki_xoa";
    }

    @PostMapping("/xoa/{id}")
    public String submitXoaHocKi(@PathVariable("id") int id) {
        hocKiService.deleteHocKiById(id);
        return "redirect:/qly/hocki";
    }


}
