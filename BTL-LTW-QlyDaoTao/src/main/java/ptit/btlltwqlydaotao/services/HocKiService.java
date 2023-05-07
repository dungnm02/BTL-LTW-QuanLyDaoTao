package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.repositories.HocKiRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HocKiService {
    HocKiRepository hocKiRepository;

    public HocKiService(HocKiRepository hocKiRepository) {
        this.hocKiRepository = hocKiRepository;
    }

    public List<HocKi> findAllHocKi() {
        return hocKiRepository.findAll();
    }

    public HocKi findHocKiById(int id) {
        return hocKiRepository.findById(id).orElse(null);
    }

    public void deleteHocKiById(int id) {
        hocKiRepository.deleteById(id);
    }

    public void addHocKi(HocKi hocKi) {
        if (hocKiBitrung(hocKi)) {
            throw new RuntimeException("Thời gian bị trùng với học kì khác");
        } else {
            hocKi.setMaHocKi(generateMaHocKi(hocKi.getNgayBatDau(), hocKi.getNgayKetThuc()));
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
        //Lấy ra các học kì chưa bắt đầu
        List<HocKi> dsHocKi = findAllHocKi();
        List<HocKi> dsHocKiChuaBatDau = new ArrayList<>();
        LocalDate ngayHienTai = LocalDate.now();
        for (HocKi hocKi : dsHocKi) {
            if (hocKi.getNgayBatDau().isAfter(ngayHienTai)) {
                dsHocKiChuaBatDau.add(hocKi);
            }
        }
        return dsHocKiChuaBatDau;
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
        return ngayBatDau.getYear() + "-" + ngayBatDau.getMonthValue() + "-" + ngayKetThuc.getYear() + "-" + ngayKetThuc.getMonthValue();
    }


}
