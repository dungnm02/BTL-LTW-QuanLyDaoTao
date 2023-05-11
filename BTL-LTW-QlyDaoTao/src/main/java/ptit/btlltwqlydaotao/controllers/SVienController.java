package ptit.btlltwqlydaotao.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.models.KetQuaHocPhan;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.models.SinhVien;
import ptit.btlltwqlydaotao.services.HocKiService;
import ptit.btlltwqlydaotao.services.KetQuaHocPhanService;
import ptit.btlltwqlydaotao.services.LopHocPhanService;
import ptit.btlltwqlydaotao.services.SinhVienService;

import java.util.*;

@Controller
@RequestMapping(value = "/svien")
@SessionAttributes("sinhVien")
public class SVienController {

    private final HocKiService hocKiService;

    private final SinhVienService sinhVienService;

    private final LopHocPhanService lopHocPhanService;

    private final KetQuaHocPhanService ketQuaHocPhanService;

    public SVienController(HocKiService hocKiService, SinhVienService sinhVienService, LopHocPhanService lopHocPhanService, KetQuaHocPhanService ketQuaHocPhanService) {
        this.hocKiService = hocKiService;
        this.sinhVienService = sinhVienService;
        this.lopHocPhanService = lopHocPhanService;
        this.ketQuaHocPhanService = ketQuaHocPhanService;
    }

    @ModelAttribute(name = "sinhVien")
    public SinhVien sinhVien() {
        return (SinhVien) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("")
    public String index() {
        return "svien_index";
    }

    @GetMapping("/lichhoc")
    public String showLichHoc(Model model) {
        SinhVien sinhVien = (SinhVien) model.getAttribute("sinhVien");
        HocKi hocKi = hocKiService.findHocKiDangHoatDong();
        model.addAttribute("thoiKhoaBieu", sinhVienService.getThoiKhoaBieu(sinhVien, hocKi));
        return "svien_lichhoc";
    }

    @GetMapping("/ketqua")
    public String showKetQua(Model model) {
        //Lấy sinh viên hiện tại
        SinhVien sinhVien = (SinhVien) model.getAttribute("sinhVien");

        //Lấy danh sách kết quả học phần của sinh viên
        List<KetQuaHocPhan> dsKetQuaHocPhan = ketQuaHocPhanService.findBySinhVien(sinhVien);
        //Sắp xếp các kết quả học phần theo thứ tự tăng dần của thời gian bắt đầu học kì
        dsKetQuaHocPhan.sort(Comparator.comparing(o -> o.getLopHocPhan().getHocKi().getNgayBatDau()));

        //Lấy các học kì sinh viên này học:
        Set<HocKi> dsHocKiSet = new HashSet<>();
        for (KetQuaHocPhan ketQuaHocPhan : dsKetQuaHocPhan) {
            dsHocKiSet.add(ketQuaHocPhan.getLopHocPhan().getHocKi());
        }
        List<HocKi> dsHocKi = new ArrayList<>(dsHocKiSet);

        model.addAttribute("dsHocKi", dsHocKi);
        model.addAttribute("dsKetQuaHocPhan", dsKetQuaHocPhan);
        return "svien_ketqua";
    }


    @GetMapping("/dangky")
    public String showDangKy(Model model) {
        //Tìm học kì đang mở đăng ký
        model.addAttribute("dsHocKi", hocKiService.findHocKiDangMoDangKy());
        return "svien_dangky";
    }

    @GetMapping("/dangky/hocki/{idHocKi}")
    public String showDangKyHocKi(@PathVariable("idHocKi") int idHocki, Model model) {
        //Lấy thông báo nếu có
        model.addAttribute("message", model.getAttribute("message"));

        HocKi hocKi = hocKiService.findById(idHocki);
        model.addAttribute("hocKi", hocKi);

        //Lấy các lớp đã đăng kí của sinh viên trong học kì này
        SinhVien sinhVien = (SinhVien) model.getAttribute("sinhVien");
        List<KetQuaHocPhan> dsKetQuaHocPhan = ketQuaHocPhanService.findBySinhVienAndHocKi(sinhVien, hocKi);
        model.addAttribute("dsKetQuaHocPhan", dsKetQuaHocPhan);

        //Lấy các môn học cùng khoa với sinh viên được mở lớp học kì này
        model.addAttribute("dsMonHoc", hocKiService.findMonHocByHocKiAndKhoa(hocKi, sinhVien().getKhoa()));
        //Lấy tất cả lớp học phần trong học kì này
        model.addAttribute("dsLopHocPhan", lopHocPhanService.findByHocKi(hocKi));
        return "svien_dangky_hocki";
    }

    @PostMapping("/dangky/hocki/{idHocKi}")
    public String submitDangKyHocKi(@PathVariable("idHocKi") int idHocKi,
                                    @RequestParam("idLopHocPhanDuocChon") int idLopHocPhanDuocChon,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        SinhVien sinhVien = (SinhVien) model.getAttribute("sinhVien");
        HocKi hocKi = hocKiService.findById(idHocKi);
        LopHocPhan lopHocPhan = lopHocPhanService.findById(idLopHocPhanDuocChon);
        try {
            sinhVienService.dangKyLopHocPhan(sinhVien, hocKi, lopHocPhan);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/svien/dangky/hocki/" + idHocKi;
        }
        redirectAttributes.addFlashAttribute("message", "Đăng ký thành công " + lopHocPhan.getGiangVienMonHoc().getMonHoc().getTenMonHoc() + " - " + lopHocPhan.getMaLopHocPhan());
        return "redirect:/svien/dangky/hocki/" + idHocKi;
    }

    @GetMapping("/doimatkhau")
    public String showDoiMatKhau(Model model) {
        model.addAttribute("message", model.getAttribute("message"));
        return "svien_doimatkhau";
    }

    @PostMapping("/doimatkhau")
    public String submitDoiMatKhau(@RequestParam("matKhauCu") String matKhauCu,
                                   @RequestParam("matKhauMoi") String matKhauMoi,
                                   @RequestParam("xacNhanMatKhauMoi") String xacNhanMatKhauMoi,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        SinhVien sinhVien = (SinhVien) model.getAttribute("sinhVien");
        try {
            sinhVienService.doiMatKhau(sinhVien, matKhauCu, matKhauMoi, xacNhanMatKhauMoi);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/svien/doimatkhau";
        }
        redirectAttributes.addFlashAttribute("message", "Đổi mật khẩu thành công");
        return "redirect:/svien/doimatkhau";
    }
}
