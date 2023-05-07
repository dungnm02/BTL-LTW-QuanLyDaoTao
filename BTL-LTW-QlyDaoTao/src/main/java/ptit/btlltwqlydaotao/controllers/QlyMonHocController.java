package ptit.btlltwqlydaotao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.models.MonHoc;
import ptit.btlltwqlydaotao.services.GiangVienMonHocService;
import ptit.btlltwqlydaotao.services.KhoaService;
import ptit.btlltwqlydaotao.services.MonHocService;

import java.util.List;

@Controller
@RequestMapping(value = "/qly/monhoc")
public class QlyMonHocController {

    private final MonHocService monHocService;

    private final KhoaService khoaService;

    private final GiangVienMonHocService giangVienMonHocService;

    public QlyMonHocController(MonHocService monHocService, KhoaService khoaService, GiangVienMonHocService giangVienMonHocService) {
        this.monHocService = monHocService;
        this.khoaService = khoaService;
        this.giangVienMonHocService = giangVienMonHocService;
    }

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(value = "khoaId", required = false) Integer khoaId,
                        @RequestParam(value = "keyword", required = false) String keyword) {

        List<Khoa> dsKhoa = khoaService.findAll();
        model.addAttribute("dsKhoa", dsKhoa);

        List<MonHoc> dsMonHoc = monHocService.searchMonHoc(khoaId, keyword);
        model.addAttribute("dsMonHoc", dsMonHoc);

        return "qly_monhoc";
    }

    @GetMapping("/them")
    public String showThem(Model model) {
        model.addAttribute("dsKhoa", khoaService.findAll());
        model.addAttribute("message", model.getAttribute("message"));
        if (!model.containsAttribute("monHoc")) {
            model.addAttribute("monHoc", new MonHoc());
        } else {
            model.addAttribute("monHoc", model.getAttribute("monHoc"));
        }
        return "qly_monhoc_them";
    }

    @PostMapping("/them")
    public String submitThem(@ModelAttribute("monHoc") MonHoc monHoc, RedirectAttributes redirectAttributes) {
        try {
            monHocService.createMonHoc(monHoc);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("monHoc", monHoc);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/qly/monhoc/them";
        }
        return "redirect:/qly/monhoc";
    }

    @GetMapping("/chongiangvien/{id}")
    public String showChonGiangVien(@PathVariable("id") int id, Model model) {
        MonHoc monHoc = monHocService.findMonHocById(id);
        model.addAttribute("monHoc", monHoc);
        model.addAttribute("dsGiangVienDayMonHoc", giangVienMonHocService.getAllGiangVienDayMonHoc(monHoc));
        model.addAttribute("dsGiangVienChuaDayMonHoc", giangVienMonHocService.getAllGiangVienChuaDayMonHoc(monHoc));
        return "qly_monhoc_themgiangvien";
    }


    @PostMapping("/chongiangvien/{id}")
    public String submitChonGiangVien(@PathVariable("id") int id, @RequestParam("dsIdGiangVienDuocChon") List<Integer> dsIdGiangVienDuocChon) {
        giangVienMonHocService.addGiangVienDayMonHoc(id, dsIdGiangVienDuocChon);
        return "redirect:/qly/monhoc";
    }

    @GetMapping("/xoagiangvien/{id}")
    public String showXoaGiangVien(@PathVariable("id") int id, Model model) {
        MonHoc monHoc = monHocService.findMonHocById(id);
        model.addAttribute("monHoc", monHoc);
        model.addAttribute("dsGiangVienDayMonHoc", giangVienMonHocService.getAllGiangVienDayMonHoc(monHoc));
        return "qly_monhoc_xoagiangvien";
    }

    @PostMapping("/xoagiangvien/{id}")
    public String submitXoaGiangVien(@PathVariable("id") int id, @RequestParam("dsIdGiangVienDuocChon") List<Integer> dsIdGiangVienDuocChon) {
        giangVienMonHocService.deleteGiangVienDayMonHoc(id, dsIdGiangVienDuocChon);
        return "redirect:/qly/monhoc";
    }


    @GetMapping("/sua/{id}")
    public String showSua(@PathVariable("id") int id, Model model) {
        model.addAttribute("message", model.getAttribute("message"));
        model.addAttribute("monHoc", monHocService.findMonHocById(id));
        return "qly_monhoc_sua";
    }

    @PostMapping("/sua/{id}")
    public String submitSua(@ModelAttribute("monHoc") MonHoc monHoc, RedirectAttributes redirectAttributes) {
        try {
            monHocService.updateMonHoc(monHoc);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/qly/monhoc/sua/" + monHoc.getId();
        }
        return "redirect:/qly/monhoc";
    }

    @GetMapping("/xoa/{id}")
    public String showXoa(@PathVariable("id") int monHocId, Model model) {
        model.addAttribute("monHoc", monHocService.findMonHocById(monHocId));
        return "qly_monhoc_xoa";
    }

    @PostMapping("/xoa/{id}")
    public String submitXoa(@PathVariable("id") int monHocId) {
        giangVienMonHocService.deleteAllByMonHocId(monHocId);
        monHocService.deleteMonHoc(monHocId);
        return "redirect:/qly/monhoc";
    }
}
