package ptit.btlltwqlydaotao.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.btlltwqlydaotao.models.SinhVien;
import ptit.btlltwqlydaotao.services.KhoaService;
import ptit.btlltwqlydaotao.services.SinhVienService;

import java.util.List;

@Controller
@RequestMapping(value = "/qly/sinhvien")
public class QlySinhVienController {

    private final SinhVienService sinhVienService;
    private final KhoaService khoaService;

    public QlySinhVienController(SinhVienService sinhVienService, KhoaService khoaService) {
        this.sinhVienService = sinhVienService;
        this.khoaService = khoaService;
    }

    @GetMapping("")
    public String index(Model model, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "keyword", required = false) String keyword) {
        List<SinhVien> dsSinhVien = sinhVienService.searchSinhVien(type, keyword);
        model.addAttribute("dsSinhVien", dsSinhVien);
        return "qly_sinhvien";
    }

    @GetMapping("/them")
    public String themSinhVienGet(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        model.addAttribute("dsKhoa", khoaService.findAll());
        return "qly_sinhvien_them";
    }

    @PostMapping("/them")
    public String themSinhVienPost(@Valid @ModelAttribute SinhVien sinhVien, BindingResult result) {
        if (result.hasErrors()) {
            return "qly_sinhvien_them";
        } else {
            sinhVienService.createSinhVien(sinhVien);
            return "redirect:/qly/sinhvien";
        }
    }

    @GetMapping("/sua/{id}")
    public String showSuaSinhVien(@PathVariable("id") int id, Model model) {
        SinhVien sinhVien;
        if (model.containsAttribute("sinhVien")) {
            sinhVien = (SinhVien) model.getAttribute("sinhVien");
        } else {
            sinhVien = sinhVienService.findSinhVienById(id);
        }

        model.addAttribute(sinhVien);
        model.addAttribute("dsKhoa", khoaService.findAll());

        return "qly_sinhvien_sua";
    }

    @PostMapping("/sua/{id}")
    public String submitSuaSinhVien(@Valid @ModelAttribute("sinhVien") SinhVien sinhVien, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sinhVien", result);
            redirectAttributes.addFlashAttribute("sinhVien", sinhVien);
            return "redirect:/qly/sinhvien/sua/" + sinhVien.getId();
        } else {
            sinhVienService.updateSinhVien(sinhVien);
            return "redirect:/qly/sinhvien";
        }
    }

    @GetMapping("/xoa/{id}")
    public String showXoaSinhVien(@PathVariable("id") int id, Model model) {
        SinhVien sinhVien = sinhVienService.findSinhVienById(id);
        model.addAttribute(sinhVien);
        return "qly_sinhvien_xoa";
    }

    @PostMapping("/xoa/{id}")
    public String submitXoaSinhVien(@PathVariable("id") int id) {
        sinhVienService.deleteSinhVien(id);
        return "redirect:/qly/sinhvien";
    }

}
