package ptit.btlltwqlydaotao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.models.KetQuaHocPhan;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.services.HocKiService;
import ptit.btlltwqlydaotao.services.KetQuaHocPhanService;
import ptit.btlltwqlydaotao.services.LopHocPhanService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/gvien")
@SessionAttributes("giangVien")
public class GvienController {
    private final HocKiService hocKiService;
    private final LopHocPhanService lopHocPhanService;

    private final KetQuaHocPhanService ketQuaHocPhanService;

    public GvienController(HocKiService hocKiService, LopHocPhanService lopHocPhanService, KetQuaHocPhanService ketQuaHocPhanService) {
        this.hocKiService = hocKiService;
        this.lopHocPhanService = lopHocPhanService;
        this.ketQuaHocPhanService = ketQuaHocPhanService;
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
        List<KetQuaHocPhan> dsKetQuaHocPhan = new ArrayList<KetQuaHocPhan>();
        for (LopHocPhan lopHocPhan : dsLopHocPhan) {
            dsKetQuaHocPhan.addAll(ketQuaHocPhanService.findByLopHocPhan(lopHocPhan));
        }

        model.addAttribute("hocKi", hocKiDangHoatDong);
        model.addAttribute("dsKetQuaHocPhan", new ArrayList<>(dsKetQuaHocPhan));
        model.addAttribute("dsLopHocPhan", dsLopHocPhan);
        return "gvien_nhapdiem";
    }
}


