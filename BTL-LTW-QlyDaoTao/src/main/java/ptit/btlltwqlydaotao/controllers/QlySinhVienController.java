package ptit.btlltwqlydaotao.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptit.btlltwqlydaotao.models.SinhVien;
import ptit.btlltwqlydaotao.services.KhoaService;
import ptit.btlltwqlydaotao.services.SinhVienService;

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
    public String index(Model model) {
        model.addAttribute("dsSinhVien", sinhVienService.findAllSinhVien());
        return "qlysinhvien";
    }

    @GetMapping("/them")
    public String themSinhVienGet(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        model.addAttribute("dsKhoa", khoaService.findAllKhoa());
        return "qlysinhvienthem";
    }

    @PostMapping("/them")
    public String themSinhVienPost(@Valid @ModelAttribute SinhVien sinhVien, BindingResult result) {
        if (result.hasErrors()) {
            return "qlysinhvienthem";
        } else {
            sinhVienService.addSinhVien(sinhVien);
            return "redirect:/qly/sinhvien";
        }
    }

    @GetMapping("/sua/{id}")
    public String showSuaSinhVien(@PathVariable("id") int id, Model model) {
        SinhVien sinhVien = sinhVienService.findSinhVienById(id);
        model.addAttribute(sinhVien);
        model.addAttribute("dsKhoa", khoaService.findAllKhoa());
        return "qlysinhviensua";
    }

    @PostMapping("/sua/{id}")
    public String submitSuaSinhVien(@PathVariable("id") int id, @ModelAttribute("sinhVien") SinhVien sinhVien) {
        sinhVienService.updateSinhVien(sinhVien, id);
        return "redirect:/qly/sinhvien";
    }

    @GetMapping("/xoa/{id}")
    public String showXoaSinhVien(@PathVariable("id") int id, Model model) {
        SinhVien sinhVien = sinhVienService.findSinhVienById(id);
        model.addAttribute(sinhVien);
        return "qlysinhvienxoa";
    }

    @PostMapping("/xoa/{id}")
    public String submitXoaSinhVien(@PathVariable("id") int id) {
        sinhVienService.deleteSinhVien(id);
        return "redirect:/qly/sinhvien";
    }

}
