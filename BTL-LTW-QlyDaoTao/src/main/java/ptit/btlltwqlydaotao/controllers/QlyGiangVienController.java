package ptit.btlltwqlydaotao.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.services.GiangVienService;
import ptit.btlltwqlydaotao.services.KhoaService;

@Controller
@RequestMapping(value = "/qly/giangvien")
public class QlyGiangVienController {
    private final GiangVienService giangVienService;
    private final KhoaService khoaService;

    public QlyGiangVienController(GiangVienService giangVienService, KhoaService khoaService) {
        this.giangVienService = giangVienService;
        this.khoaService = khoaService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("dsGiangVien", giangVienService.findAllGiangVien());
        return "qlygiangvien";
    }

    @GetMapping("/them")
    public String themGiangVienGet(Model model) {
        model.addAttribute("giangVien", new GiangVien());
        model.addAttribute("dsKhoa", khoaService.findAllKhoa());
        return "qlygiangvienthem";
    }

    @PostMapping("/them")
    public String themGiangVienPost(@Valid @ModelAttribute GiangVien giangVien, BindingResult result) {
        if (result.hasErrors()) {
            return "qlygiangvienthem";
        } else {
            giangVienService.addGiangVien(giangVien);
            return "redirect:/qly/giangvien";
        }
    }

    @GetMapping("/sua/{id}")
    public String showSuaGiangVien(@PathVariable("id") int id, Model model) {
        GiangVien giangVien = giangVienService.findGiangVienById(id);
        model.addAttribute(giangVien);
        model.addAttribute("dsKhoa", khoaService.findAllKhoa());
        return "qlygiangviensua";
    }

    @PostMapping("/sua/{id}")
    public String submitSuaGiangVien(@PathVariable("id") int id, @ModelAttribute("giangVien") GiangVien giangVien) {
        giangVienService.updateGiangVien(giangVien, id);
        return "redirect:/qly/giangvien";
    }

    @GetMapping("/xoa/{id}")
    public String showXoaGiangVien(@PathVariable("id") int id, Model model) {
        GiangVien giangVien = giangVienService.findGiangVienById(id);
        model.addAttribute(giangVien);
        return "qlygiangvienxoa";
    }

    @PostMapping("/xoa/{id}")
    public String submitXoaGiangVien(@PathVariable("id") int id) {
        giangVienService.deleteGiangVien(id);
        return "redirect:/qly/giangvien";
    }
}
