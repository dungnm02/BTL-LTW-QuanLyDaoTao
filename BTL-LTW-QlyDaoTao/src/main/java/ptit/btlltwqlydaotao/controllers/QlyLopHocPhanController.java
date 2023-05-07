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

@Controller
@RequestMapping("/qly/hocki/{idHocKi}/lophocphan")
public class QlyLopHocPhanController {

    private final LopHocPhanService lopHocPhanService;
    private final HocKiService hocKiService;
    private final KhoaService khoaService;
    private final MonHocService monHocService;
    private final GiangVienMonHocService giangVienMonHocService;
    private final KetQuaHocPhanService ketQuaHocPhanService;

    public QlyLopHocPhanController(LopHocPhanService lopHocPhanService, HocKiService hocKiService, KhoaService khoaService, MonHocService monHocService, GiangVienMonHocService giangVienMonHocService, KetQuaHocPhanService ketQuaHocPhanService) {
        this.lopHocPhanService = lopHocPhanService;
        this.hocKiService = hocKiService;
        this.khoaService = khoaService;
        this.monHocService = monHocService;
        this.giangVienMonHocService = giangVienMonHocService;
        this.ketQuaHocPhanService = ketQuaHocPhanService;
    }

    @GetMapping("")
    public String index(Model model, @PathVariable("idHocKi") int idHocKi) {
        HocKi hocKi = hocKiService.findHocKiById(idHocKi);
        model.addAttribute("hocKi", hocKiService.findHocKiById(idHocKi));
        model.addAttribute("dsLopHocPhan", lopHocPhanService.findAllByHocKi(hocKi));

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
        model.addAttribute("dsNgayTrongTuan", new ArrayList<String>(Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6")));
        model.addAttribute("dsKipTrongNgay", new ArrayList<String>(Arrays.asList("Kíp 1", "Kíp 2", "Kíp 3", "Kíp 4", "Kíp 5", "Kíp 6")));
        model.addAttribute("dsPhongHoc", new ArrayList<String>(Arrays.asList("101", "102", "103", "201", "202", "203")));

        return "qly_hocki_lophocphan_them";
    }

    @PostMapping("/them")
    public String submitThem(Model model, @PathVariable("idHocKi") int idHocKi, @ModelAttribute("lopHocPhan") LopHocPhan lopHocPhan, RedirectAttributes redirectAttributes) {
        lopHocPhan.setHocKi(hocKiService.findHocKiById(idHocKi));
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
        model.addAttribute("hocKi", hocKiService.findHocKiById(idHocKi));
        model.addAttribute("lopHocPhan", lopHocPhan);
        model.addAttribute("dsNgayTrongTuan", new ArrayList<String>(Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6")));
        model.addAttribute("dsKipTrongNgay", new ArrayList<String>(Arrays.asList("Kíp 1", "Kíp 2", "Kíp 3", "Kíp 4", "Kíp 5", "Kíp 6")));
        model.addAttribute("dsPhongHoc", new ArrayList<String>(Arrays.asList("101", "102", "103", "201", "202", "203")));
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
        model.addAttribute("hocKi", hocKiService.findHocKiById(idHocKi));
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
        model.addAttribute("hocKi", hocKiService.findHocKiById(idHocKi));
        model.addAttribute("dsKetQuaHocPhan", ketQuaHocPhanService.findByLopHocPhan(lopHocPhan));
        return "qly_hocki_lophocphan_sinhvien";
    }

//    @GetMapping("/sinhvien/{idLopHocPhan}/them")
//    public String showThemSinhVien(@PathVariable("idLopHocPhan") int idLopHocPhan, Model model) {
//
//
//
//    }

}
