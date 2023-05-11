package ptit.btlltwqlydaotao.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.models.KetQuaHocPhan;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.services.GiangVienService;
import ptit.btlltwqlydaotao.services.HocKiService;
import ptit.btlltwqlydaotao.services.KetQuaHocPhanService;
import ptit.btlltwqlydaotao.services.LopHocPhanService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/gvien")
@SessionAttributes("giangVien")
public class GvienController {
    private final HocKiService hocKiService;
    private final LopHocPhanService lopHocPhanService;
    private final KetQuaHocPhanService ketQuaHocPhanService;
    private final GiangVienService giangVienService;

    public GvienController(HocKiService hocKiService, LopHocPhanService lopHocPhanService, KetQuaHocPhanService ketQuaHocPhanService, GiangVienService giangVienService) {
        this.hocKiService = hocKiService;
        this.lopHocPhanService = lopHocPhanService;
        this.ketQuaHocPhanService = ketQuaHocPhanService;
        this.giangVienService = giangVienService;
    }

    @ModelAttribute(name = "giangVien")
    public GiangVien giangVien() {
        return (GiangVien) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("")
    public String index() {
        return "gvien_index";
    }

    @GetMapping("/nhapdiem")
    public String showNhapDiem(Model model) {
        GiangVien giangVien = (GiangVien) model.getAttribute("giangVien");
        //Lấy học kì đang hoạt động
        HocKi hocKiDangHoatDong = hocKiService.findHocKiDangHoatDong();
        //Lấy các lớp học phần trong học kì đó mà giảng viên dạy
        List<LopHocPhan> dsLopHocPhan = lopHocPhanService.findByHocKiAndGiangVien(hocKiDangHoatDong, giangVien);
        //Lấy kết quả học phần các sinh viên trong lớp học phần đó
        List<KetQuaHocPhan> dsKetQuaHocPhan = new ArrayList<>();
        for (LopHocPhan lopHocPhan : dsLopHocPhan) {
            dsKetQuaHocPhan.addAll(ketQuaHocPhanService.findByLopHocPhan(lopHocPhan));
        }

        model.addAttribute("hocKi", hocKiDangHoatDong);
        model.addAttribute("dsKetQuaHocPhan", new ArrayList<>(dsKetQuaHocPhan));
        model.addAttribute("dsLopHocPhan", dsLopHocPhan);
        return "gvien_nhapdiem";
    }

    @GetMapping("/nhapdiem/sinhvien")
    public String showNhapDiemSinhVien(Model model,
                                       @RequestParam(value = "idKetQuaHocPhanDuocChon", required = false) Integer idKetQuaHocPhanDuocChon) {
        if (idKetQuaHocPhanDuocChon == null) {
            return "redirect:/gvien/nhapdiem";
        }
        KetQuaHocPhan ketQuaHocPhan = ketQuaHocPhanService.findById(idKetQuaHocPhanDuocChon);
        model.addAttribute("ketQuaHocPhan", ketQuaHocPhan);
        return "gvien_nhapdiem_sinhvien";
    }

    @PostMapping("/nhapdiem/sinhvien")
    public String submitNhapDiemSinhVien(@ModelAttribute("ketQuaHocPhan") KetQuaHocPhan ketQuaHocPhan) {
        ketQuaHocPhanService.createKetQuaHocPhan(ketQuaHocPhan);
        return "redirect:/gvien/nhapdiem";
    }

    @GetMapping("/lichday")
    public String showLichDay(Model model) {
        //Lấy giảng viên đang đăng nhập
        GiangVien giangVien = (GiangVien) model.getAttribute("giangVien");
        //Lấy các lớp học phần mà giảng viên dạy
        List<LopHocPhan> dsLopHocPhan = lopHocPhanService.findByGiangVien(giangVien);
        //Lấy các học kì mà giảng viên dạy
        Set<HocKi> dsHocKi = new HashSet<>();
        for (LopHocPhan lopHocPhan : dsLopHocPhan) {
            dsHocKi.add(lopHocPhan.getHocKi());
        }
        model.addAttribute("dsHocKi", new ArrayList<>(dsHocKi));
        model.addAttribute("dsLopHocPhan", dsLopHocPhan);
        return "gvien_lichday";
    }

    @GetMapping("/doimatkhau")
    public String showDoiMatKhau(Model model) {
        model.addAttribute("message", model.getAttribute("message"));
        return "gvien_doimatkhau";
    }

    @PostMapping("/doimatkhau")
    public String submitDoiMatKhau(@RequestParam("matKhauCu") String matKhauCu,
                                   @RequestParam("matKhauMoi") String matKhauMoi,
                                   @RequestParam("xacNhanMatKhauMoi") String xacNhanMatKhauMoi,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        GiangVien giangVien = (GiangVien) model.getAttribute("giangVien");
        try {
            giangVienService.doiMatKhau(giangVien, matKhauCu, matKhauMoi, xacNhanMatKhauMoi);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/gvien/doimatkhau";
        }
        redirectAttributes.addFlashAttribute("message", "Đổi mật khẩu thành công");
        return "redirect:/gvien/doimatkhau";
    }
}


