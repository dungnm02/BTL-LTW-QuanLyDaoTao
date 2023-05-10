package ptit.btlltwqlydaotao.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.services.GiangVienMonHocService;
import ptit.btlltwqlydaotao.services.GiangVienService;
import ptit.btlltwqlydaotao.services.KhoaService;

import java.util.List;

@Controller
@RequestMapping(value = "/qly/giangvien")
public class QlyGiangVienController {
    private final GiangVienService giangVienService;
    private final GiangVienMonHocService giangVienMonHocService;
    private final KhoaService khoaService;

    public QlyGiangVienController(GiangVienService giangVienService, GiangVienMonHocService giangVienMonHocService, KhoaService khoaService) {
        //Dependency Injection
        this.giangVienService = giangVienService;
        this.giangVienMonHocService = giangVienMonHocService;
        this.khoaService = khoaService;
    }

    @GetMapping("")
    public String index(Model model, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "keyword", required = false) String keyword) {
        List<GiangVien> dsGiangVien = giangVienService.searchGiangVien(type, keyword);
        model.addAttribute("dsGiangVien", dsGiangVien);
        return "qly_giangvien";
    }

    @GetMapping("/them")
    public String showThemGiangVien(Model model) {
        GiangVien giangVien;
        if (model.containsAttribute("giangVien")) {
            giangVien = (GiangVien) model.getAttribute("giangVien");
        } else {
            giangVien = new GiangVien();
        }

        model.addAttribute("giangVien", giangVien);
        model.addAttribute("dsKhoa", khoaService.findAll());

        return "qly_giangvien_them";
    }

    @PostMapping("/them")
    public String submitThemGiangVien(@Valid @ModelAttribute GiangVien giangVien, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.giangVien", result);
            redirectAttributes.addFlashAttribute("giangVien", giangVien);
            return "redirect:/qly/giangvien/them";
        } else {
            giangVienService.createGiangVien(giangVien);
            return "redirect:/qly/giangvien";
        }
    }

    @GetMapping("/sua/{id}")
    public String showSuaGiangVien(@PathVariable("id") int id, Model model) {
        GiangVien giangVien;
        if (model.containsAttribute("giangVien")) {
            giangVien = (GiangVien) model.getAttribute("giangVien");
        } else {
            giangVien = giangVienService.findById(id);
        }

        model.addAttribute(giangVien);
        model.addAttribute("dsKhoa", khoaService.findAll());

        return "qly_giangvien_sua";
    }

    @PostMapping("/sua/{id}")
    public String submitSuaGiangVien(@Valid @ModelAttribute("giangVien") GiangVien giangVien, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.giangVien", result);
            redirectAttributes.addFlashAttribute("giangVien", giangVien);
            return "redirect:/qly/giangvien/sua/" + giangVien.getId();
        } else {
            giangVienService.updateGiangVien(giangVien);
            return "redirect:/qly/giangvien";
        }
    }

    @GetMapping("/xoa/{id}")
    public String showXoaGiangVien(@PathVariable("id") int id, Model model) {
        GiangVien giangVien = giangVienService.findById(id);
        model.addAttribute(giangVien);
        return "qly_giangvien_xoa";
    }

    @PostMapping("/xoa/{id}")
    public String submitXoaGiangVien(@PathVariable("id") int id) {
        giangVienMonHocService.deleteAllByGiangVienId(id);
        giangVienService.deleteGiangVien(id);
        return "redirect:/qly/giangvien";
    }
}
