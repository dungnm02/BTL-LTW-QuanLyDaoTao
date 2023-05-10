package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.models.MonHoc;
import ptit.btlltwqlydaotao.repositories.HocKiRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class HocKiService {
    private final HocKiRepository hocKiRepository;

    private final LopHocPhanService lopHocPhanService;


    public HocKiService(HocKiRepository hocKiRepository, LopHocPhanService lopHocPhanService) {
        this.hocKiRepository = hocKiRepository;
        this.lopHocPhanService = lopHocPhanService;
    }

    public List<HocKi> findAllHocKi() {
        return hocKiRepository.findAll();
    }

    public HocKi findById(int id) {
        return hocKiRepository.findById(id).orElse(null);
    }

    public HocKi findHocKiDangHoatDong() {
        List<HocKi> dsHocKi = findAllHocKi();
        for (HocKi hocKi : dsHocKi) {
            if (LocalDate.now().isAfter(hocKi.getNgayBatDau()) && LocalDate.now().isBefore(hocKi.getNgayKetThuc())) {
                return hocKi;
            }
        }
        return null;
    }

    public void deleteHocKiById(int id) {
        hocKiRepository.deleteById(id);
    }

    public void addHocKi(HocKi hocKi) {
        if (hocKiBitrung(hocKi)) {
            throw new RuntimeException("Thời gian bị trùng với học kì khác");
        } else {
            hocKi.setMaHocKi(generateMaHocKi(hocKi.getNgayBatDau(), hocKi.getNgayKetThuc()));
            hocKi.setMoDangKy(false);
            hocKiRepository.save(hocKi);
        }
    }

    public void updateHocKi(HocKi hocKi) {
        if (hocKiBitrung(hocKi)) {
            throw new RuntimeException("Thời gian bị trùng với học kì khác");
        } else {
            hocKi.setMaHocKi(generateMaHocKi(hocKi.getNgayBatDau(), hocKi.getNgayKetThuc()));
            hocKiRepository.save(hocKi);
        }
    }

    public List<HocKi> findHocKiChuaBatDau() {
        //Lấy ra các học kì chưa bắt đầu và chưa mở đăng ký
        List<HocKi> dsHocKi = findAllHocKi();
        List<HocKi> dsHocKiChuaBatDau = new ArrayList<>();
        LocalDate ngayHienTai = LocalDate.now();
        for (HocKi hocKi : dsHocKi) {
            if (hocKi.getNgayBatDau().isAfter(ngayHienTai) && !hocKi.isMoDangKy()) {
                dsHocKiChuaBatDau.add(hocKi);
            }
        }
        return dsHocKiChuaBatDau;
    }

    public List<HocKi> findHocKiDangMoDangKy() {
        return hocKiRepository.findByMoDangKy(true);
    }

    public List<MonHoc> findMonHocByHocKiAndKhoa(HocKi hocKi, Khoa khoa) {
        //Tìm tất cả lớp học phần trong học kì và khoa
        List<LopHocPhan> dsLopHocPhan = lopHocPhanService.findByHocKiAndKhoa(hocKi, khoa);
        //Lấy tất cả môn học trong học kì và khoa
        Set<MonHoc> dsMonHoc = new HashSet<>();
        for (LopHocPhan lopHocPhan : dsLopHocPhan) {
            dsMonHoc.add(lopHocPhan.getGiangVienMonHoc().getMonHoc());
        }
        return new ArrayList<>(dsMonHoc);
    }

    public List<LocalDate> getThuHaiTrongSauThang() {
        //Lấy ra các thứ hai trong vòng 6 tháng tiếp theo
        //Để demo thì hiện tại sẽ lấy trong vòng 12 tháng
        List<LocalDate> dsThuHai = new ArrayList<>();
        LocalDate ngayHienTai = LocalDate.now();
        LocalDate ngayKetThuc = ngayHienTai.plusMonths(12);

        while (ngayHienTai.isBefore(ngayKetThuc)) {
            if (ngayHienTai.getDayOfWeek().getValue() == 1) {
                dsThuHai.add(ngayHienTai);
            }
            ngayHienTai = ngayHienTai.plusDays(1);
        }
        return dsThuHai;
    }

    public boolean checkMoDangKi(HocKi hocKi, Date dongDangKi) {
        //Kiểm tra xem ngày đóng đăng ký trước học kì và ngày mở đăng kí có sau ngày hiện tại không
        LocalDate dongDangKiLD = LocalDate.ofInstant(dongDangKi.toInstant(), ZoneId.systemDefault());
        return dongDangKiLD.isBefore(hocKi.getNgayBatDau()) && dongDangKiLD.isAfter(LocalDate.now().plusDays(1));
    }

    private boolean hocKiBitrung(HocKi h) {
        //Kiểm tra học kì h có bị trùng thời gian với học kì khác nó hay không
        List<HocKi> dsHocKi = findAllHocKi();
        for (HocKi hocKi : dsHocKi) {
            if (h.getId() != hocKi.getId() && h.getNgayBatDau().isBefore(hocKi.getNgayKetThuc()) && h.getNgayKetThuc().isAfter(hocKi.getNgayBatDau())) {
                return true;
            }
        }
        return false;
    }

    private String generateMaHocKi(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        //Tạo mã học kì theo định dạng: <năm bắt đầu>-<tháng bắt đầu>-<năm kết thúc>-<tháng kết thúc>
        return ngayBatDau.getYear() + "-" + ngayBatDau.getMonthValue() + "-" + ngayKetThuc.getYear() + "-" + ngayKetThuc.getMonthValue();
    }


}
