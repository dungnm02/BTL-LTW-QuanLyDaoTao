package ptit.btlltwqlydaotao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.services.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/qly/hocki/{idHocKi}/lophocphan")
public class QlyLopHocPhanController {

    private final LopHocPhanService lopHocPhanService;
    private final SinhVienService sinhVienService;
    private final HocKiService hocKiService;
    private final KhoaService khoaService;
    private final MonHocService monHocService;
    private final GiangVienMonHocService giangVienMonHocService;
    private final KetQuaHocPhanService ketQuaHocPhanService;

    public QlyLopHocPhanController(LopHocPhanService lopHocPhanService, SinhVienService sinhVienService, HocKiService hocKiService, KhoaService khoaService, MonHocService monHocService, GiangVienMonHocService giangVienMonHocService, KetQuaHocPhanService ketQuaHocPhanService) {
        this.lopHocPhanService = lopHocPhanService;
        this.sinhVienService = sinhVienService;
        this.hocKiService = hocKiService;
        this.khoaService = khoaService;
        this.monHocService = monHocService;
        this.giangVienMonHocService = giangVienMonHocService;
        this.ketQuaHocPhanService = ketQuaHocPhanService;
    }

    @GetMapping("")
    public String index(Model model, @PathVariable("idHocKi") int idHocKi) {
        HocKi hocKi = hocKiService.findById(idHocKi);
        model.addAttribute("hocKi", hocKi);
        model.addAttribute("dsLopHocPhan", lopHocPhanService.findByHocKi(hocKi));

        return "qly_hocki_lophocphan";
    }

    @GetMapping("/them")
    public String showThem(Model model, @PathVariable("idHocKi") int idHocKi) {
        LopHocPhan lopHocPhan;
        if (model.containsAttribute("lopHocPhan")) {
            lopHocPhan = (LopHocPhan) model.getAttribute("lopHocPhan");
        } else {
            lopHocPhan = new LopHocPhan();
        }

        model.addAttribute("message", model.getAttribute("message"));
        model.addAttribute("lopHocPhan", lopHocPhan);
        model.addAttribute("idHocKi", idHocKi);
        model.addAttribute("dsKhoa", khoaService.findAll());
        model.addAttribute("dsMonHoc", monHocService.findAll());
        model.addAttribute("dsGiangVienMonHoc", giangVienMonHocService.findAll());
        model.addAttribute("dsNgayTrongTuan", new ArrayList<>(Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6")));
        model.addAttribute("dsKipTrongNgay", new ArrayList<>(Arrays.asList("Kíp 1", "Kíp 2", "Kíp 3", "Kíp 4", "Kíp 5", "Kíp 6")));
        model.addAttribute("dsPhongHoc", new ArrayList<>(Arrays.asList("101", "102", "103", "201", "202", "203")));

        return "qly_hocki_lophocphan_them";
    }

    @PostMapping("/them")
    public String submitThem(@PathVariable("idHocKi") int idHocKi, @ModelAttribute("lopHocPhan") LopHocPhan lopHocPhan, RedirectAttributes redirectAttributes) {
        lopHocPhan.setHocKi(hocKiService.findById(idHocKi));
        try {
            lopHocPhanService.createLopHocPhan(lopHocPhan);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("lopHocPhan", lopHocPhan);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/qly/hocki/" + idHocKi + "/lophocphan/them";
        }
        return "redirect:/qly/hocki/" + idHocKi + "/lophocphan";
    }

    @GetMapping("/sua/{idLopHocPhan}")
    public String showSua(@PathVariable("idLopHocPhan") int idLopHocPhan, @PathVariable("idHocKi") int idHocKi, Model model) {
        LopHocPhan lopHocPhan;
        if (model.containsAttribute("lopHocPhan")) {
            lopHocPhan = (LopHocPhan) model.getAttribute("lopHocPhan");
        } else {
            lopHocPhan = lopHocPhanService.findById(idLopHocPhan);
        }
        model.addAttribute("message", model.getAttribute("message"));
        model.addAttribute("hocKi", hocKiService.findById(idHocKi));
        model.addAttribute("lopHocPhan", lopHocPhan);
        model.addAttribute("dsNgayTrongTuan", new ArrayList<>(Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6")));
        model.addAttribute("dsKipTrongNgay", new ArrayList<>(Arrays.asList("Kíp 1", "Kíp 2", "Kíp 3", "Kíp 4", "Kíp 5", "Kíp 6")));
        model.addAttribute("dsPhongHoc", new ArrayList<>(Arrays.asList("101", "102", "103", "201", "202", "203")));
        return "qly_hocki_lophocphan_sua";
    }

    @PostMapping("/sua/{idLopHocPhan}")
    public String submitSua(@PathVariable("idHocKi") int idHocKi, @ModelAttribute("lopHocPhan") LopHocPhan lopHocPhan, RedirectAttributes redirectAttributes) {
        try {
            lopHocPhanService.updateLopHocPhan(lopHocPhan);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("lopHocPhan", lopHocPhan);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/qly/hocki/" + idHocKi + "/lophocphan/sua/" + lopHocPhan.getId();
        }
        return "redirect:/qly/hocki/" + idHocKi + "/lophocphan";
    }

    @GetMapping("/xoa/{idLopHocPhan}")
    public String showXoa(@PathVariable("idLopHocPhan") int idLopHocPhan, @PathVariable("idHocKi") int idHocKi, Model model) {
        model.addAttribute("lopHocPhan", lopHocPhanService.findById(idLopHocPhan));
        model.addAttribute("hocKi", hocKiService.findById(idHocKi));
        return "qly_hocki_lophocphan_xoa";
    }

    @PostMapping("/xoa/{idLopHocPhan}")
    public String showXoa(@PathVariable("idLopHocPhan") int idLopHocPhan, @PathVariable("idHocKi") int idHocKi) {
        lopHocPhanService.deleteLopHocPhan(idLopHocPhan);
        return "redirect:/qly/hocki/" + idHocKi + "/lophocphan";
    }

    @GetMapping("/sinhvien/{idLopHocPhan}")
    public String showSinhVien(@PathVariable("idLopHocPhan") int idLopHocPhan, @PathVariable("idHocKi") int idHocKi, Model model) {
        LopHocPhan lopHocPhan = lopHocPhanService.findById(idLopHocPhan);
        model.addAttribute("lopHocPhan", lopHocPhan);
        model.addAttribute("hocKi", hocKiService.findById(idHocKi));
        model.addAttribute("dsKetQuaHocPhan", ketQuaHocPhanService.findByLopHocPhan(lopHocPhan));
        return "qly_hocki_lophocphan_sinhvien";
    }

    @GetMapping("/sinhvien/{idLopHocPhan}/them")
    public String showThemSinhVien(@PathVariable("idLopHocPhan") int idLopHocPhan,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   Model model) {
        LopHocPhan lopHocPhan = lopHocPhanService.findById(idLopHocPhan);
        model.addAttribute("dsSinhVienKhongTrongLop", lopHocPhanService.searchSinhVien(lopHocPhan, type, keyword));
        model.addAttribute("lopHocPhan", lopHocPhan);
        model.addAttribute("hocKi", lopHocPhan.getHocKi());
        return "qly_hocki_lophocphan_sinhvien_them";
    }

    @PostMapping("/sinhvien/{idLopHocPhan}/them")
    public String submitThemSinhVien(@PathVariable("idLopHocPhan") int idLopHocPhan,
                                     @RequestParam("dsIdSinhVienDuocChon") List<Integer> dsIdSinhVienDuocChon) {
        lopHocPhanService.addSinhVien(idLopHocPhan, dsIdSinhVienDuocChon);
        return "redirect:/qly/hocki/" + lopHocPhanService.findById(idLopHocPhan).getHocKi().getId() + "/lophocphan/sinhvien/" + idLopHocPhan;
    }

    @GetMapping("/sinhvien/{idLopHocPhan}/xoa/{idSinhVien}")
    public String showXoaSinhVien(@PathVariable("idLopHocPhan") int idLopHocPhan,
                                  @PathVariable("idSinhVien") int idSinhVien,
                                  Model model) {
        LopHocPhan lopHocPhan = lopHocPhanService.findById(idLopHocPhan);
        model.addAttribute("sinhVien", sinhVienService.findById(idSinhVien));
        model.addAttribute("lopHocPhan", lopHocPhan);
        model.addAttribute("hocKi", lopHocPhan.getHocKi());
        return "qly_hocki_lophocphan_sinhvien_xoa";
    }

    @PostMapping("/sinhvien/{idLopHocPhan}/xoa/{idSinhVien}")
    public String submitXoaSinhVien(@PathVariable("idLopHocPhan") int idLopHocPhan,
                                    @PathVariable("idSinhVien") int idSinhVien) {
        lopHocPhanService.deleteSinhVien(idLopHocPhan, idSinhVien);
        return "redirect:/qly/hocki/" + lopHocPhanService.findById(idLopHocPhan).getHocKi().getId() + "/lophocphan/sinhvien/" + idLopHocPhan;
    }


}
