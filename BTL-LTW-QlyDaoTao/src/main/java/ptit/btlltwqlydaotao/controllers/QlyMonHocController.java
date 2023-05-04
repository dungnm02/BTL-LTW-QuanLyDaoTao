package ptit.btlltwqlydaotao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ptit.btlltwqlydaotao.models.MonHoc;
import ptit.btlltwqlydaotao.services.GiangVienMonHocService;
import ptit.btlltwqlydaotao.services.GiangVienService;
import ptit.btlltwqlydaotao.services.KhoaService;
import ptit.btlltwqlydaotao.services.MonHocService;

import java.util.List;

@Controller
@RequestMapping(value = "/qly/monhoc")
public class QlyMonHocController {

    private final MonHocService monHocService;

    private final KhoaService khoaService;

    private final GiangVienMonHocService giangVienMonHocService;

    private final GiangVienService giangVienService;

    public QlyMonHocController(MonHocService monHocService, KhoaService khoaService, GiangVienMonHocService giangVienMonHocService, GiangVienService giangVienService) {
        this.monHocService = monHocService;
        this.khoaService = khoaService;
        this.giangVienMonHocService = giangVienMonHocService;
        this.giangVienService = giangVienService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("dsMonHoc", monHocService.findAllMonHoc());
        return "qlymonhoc";
    }

    @GetMapping("/them")
    public String showThem(Model model) {
        model.addAttribute("monHoc", new MonHoc());
        model.addAttribute("dsKhoa", khoaService.findAllKhoa());
        return "qlymonhocthem";
    }

    @PostMapping("/them")
    public String submitThem(@ModelAttribute("monHoc") MonHoc monHoc) {
        monHocService.addMonHoc(monHoc);
        return "redirect:/qly/monhoc";
    }

    @GetMapping("/chongiangvien/{id}")
    public String showChonGiangVien(@PathVariable("id") int id, Model model) {
        MonHoc monHoc = monHocService.findMonHocById(id);
        model.addAttribute("monHoc", monHoc);
        model.addAttribute("dsGiangVienDayMonHoc", giangVienMonHocService.getAllGiangVienDayMonHoc(monHoc));
        model.addAttribute("dsGiangVienChuaDayMonHoc", giangVienMonHocService.getAllGiangVienChuaDayMonHoc(monHoc));
        return "qlymonhocthemgiangvien";
    }


    @PostMapping("/chongiangvien/{id}")
    public String submitChonGiangVien(@PathVariable("id") int id, @RequestParam("dsIdGiangVienDuocChon") List<Integer> dsIdGiangVienDuocChon) {
        giangVienMonHocService.addGiangVienDayMonHoc(id, dsIdGiangVienDuocChon);
        return "redirect:/qly/monhoc";
    }


    @GetMapping("/sua/{id}")
    public String showSua(@PathVariable("id") int id, Model model) {
        model.addAttribute("monHoc", monHocService.findMonHocById(id));
        return "qlymonhocsua";
    }

    @PostMapping("/sua/{id}")
    public String submitSua(@PathVariable("id") int id, @ModelAttribute("monHoc") MonHoc monHoc) {
        monHocService.updateMonHoc(monHoc, id);
        return "redirect:/qly/monhoc/";
    }

    @GetMapping("/xoa/{id}")
    public String showXoa(@PathVariable("id") int id, Model model) {
        model.addAttribute("monHoc", monHocService.findMonHocById(id));
        return "qlymonhocxoa";
    }

    @PostMapping("/xoa/{id}")
    public String submitXoa(@PathVariable("id") int id) {
        monHocService.deleteMonHoc(id);
        return "redirect:/qly/monhoc";
    }
}
