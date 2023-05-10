package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.*;
import ptit.btlltwqlydaotao.repositories.LopHocPhanRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LopHocPhanService {
    private final LopHocPhanRepository lopHocPhanRepository;
    private final KetQuaHocPhanService ketQuaHocPhanService;
    private final SinhVienService sinhVienService;

    public LopHocPhanService(LopHocPhanRepository lopHocPhanRepository, KetQuaHocPhanService ketQuaHocPhanService, SinhVienService sinhVienService) {
        this.lopHocPhanRepository = lopHocPhanRepository;
        this.ketQuaHocPhanService = ketQuaHocPhanService;
        this.sinhVienService = sinhVienService;
    }

    public List<LopHocPhan> findByHocKi(HocKi hocKi) {
        return lopHocPhanRepository.findAllByHocKi(hocKi);
    }

    public List<LopHocPhan> findByHocKiAndKhoa(HocKi hocKi, Khoa khoa) {
        return lopHocPhanRepository.findByHocKiAndGiangVienMonHoc_MonHoc_Khoa(hocKi, khoa);
    }

    public List<LopHocPhan> findByHocKiAndGiangVien(HocKi hocKi, GiangVien giangVien) {
        return lopHocPhanRepository.findAllByHocKiAndGiangVienMonHoc_GiangVien(hocKi, giangVien);
    }

    public List<LopHocPhan> findByGiangVien(GiangVien giangVien) {
        return lopHocPhanRepository.findAllByGiangVienMonHoc_GiangVien(giangVien);
    }

    public LopHocPhan findById(int lopHocPhanId) {
        return lopHocPhanRepository.findById(lopHocPhanId).orElse(null);
    }

    public void deleteById(int lopHocPhanId) {
        //Xóa tất cả kết quả học phần của lớp học phần
        List<KetQuaHocPhan> dsKetQuaHocPhan = ketQuaHocPhanService.findByLopHocPhan(findById(lopHocPhanId));
        for (KetQuaHocPhan ketQuaHocPhan : dsKetQuaHocPhan) {
            ketQuaHocPhanService.deleteById(ketQuaHocPhan.getId());
        }
        //Xóa lớp học phần
        lopHocPhanRepository.deleteById(lopHocPhanId);
    }

    public List<SinhVien> findAllSinhVien(LopHocPhan lopHocPhan) {
        List<KetQuaHocPhan> dsKetQuaHocPhan = ketQuaHocPhanService.findByLopHocPhan(lopHocPhan);
        List<SinhVien> dsSinhVien = new ArrayList<>();
        for (KetQuaHocPhan ketQuaHocPhan : dsKetQuaHocPhan) {
            dsSinhVien.add(ketQuaHocPhan.getSinhVien());
        }
        return dsSinhVien;
    }

    public List<SinhVien> searchSinhVien(LopHocPhan lopHocPhan, String type, String keyword) {
        List<SinhVien> dsSinhVienTrongKhoa = sinhVienService.findByKhoa(lopHocPhan.getGiangVienMonHoc().getMonHoc().getKhoa());
        List<SinhVien> dsSinhVienTrongLop = findAllSinhVien(lopHocPhan);
        List<SinhVien> dsSinhVienKhongTrongLop = new ArrayList<>(dsSinhVienTrongKhoa);
        List<SinhVien> dsSinhVienTimDuoc = sinhVienService.searchSinhVien(type, keyword);
        dsSinhVienKhongTrongLop.removeAll(dsSinhVienTrongLop);
        dsSinhVienKhongTrongLop.retainAll(dsSinhVienTimDuoc);
        return dsSinhVienKhongTrongLop;
    }

    public void createLopHocPhan(LopHocPhan lopHocPhan) {
        if (phongHocTrungLich(lopHocPhan)) {
            throw new RuntimeException("Phòng học bị trùng lịch");
        } else if (giangVienTrungLich(lopHocPhan)) {
            throw new RuntimeException("Giảng viên bị trùng lịch");
        } else {
            lopHocPhan.setMaLopHocPhan(generateMaLopHocPhan(lopHocPhan));
            lopHocPhanRepository.save(lopHocPhan);
        }
    }

    public void updateLopHocPhan(LopHocPhan lopHocPhan) {
        if (phongHocTrungLich(lopHocPhan)) {
            throw new RuntimeException("Phòng học bị trùng lịch");
        } else if (giangVienTrungLich(lopHocPhan)) {
            throw new RuntimeException("Giảng viên bị trùng lịch");
        } else {
            lopHocPhanRepository.save(lopHocPhan);
        }
    }

    public void deleteLopHocPhan(int idLopHocPhan) {
        lopHocPhanRepository.deleteById(idLopHocPhan);
    }

    public void addSinhVien(int idLopHocPhan, List<Integer> dsIdSinhVienDuocChon) {
        LopHocPhan lopHocPhan = findById(idLopHocPhan);
        for (Integer idSinhVien : dsIdSinhVienDuocChon) {
            SinhVien sinhVien = sinhVienService.findById(idSinhVien);
            KetQuaHocPhan ketQuaHocPhan = new KetQuaHocPhan();
            ketQuaHocPhan.setSinhVien(sinhVien);
            ketQuaHocPhan.setLopHocPhan(lopHocPhan);
            ketQuaHocPhanService.createKetQuaHocPhan(ketQuaHocPhan);
        }
    }

    public void deleteSinhVien(int idLophocPhan, int idSinhVien) {
        KetQuaHocPhan ketQuaHocPhan = ketQuaHocPhanService.findByLopHocPhan_IdAndSinhVien_Id(idLophocPhan, idSinhVien);
        ketQuaHocPhanService.deleteById(ketQuaHocPhan.getId());
    }

    private String generateMaLopHocPhan(LopHocPhan lhp) {
        //Lấy ra danh sách lớp học phần của môn học trong học kì này.\
        List<LopHocPhan> dsLopHocPhan = lopHocPhanRepository.findTopByHocKiAndGiangVienMonHoc_MonHocOrderByIdDesc(lhp.getHocKi(), lhp.getGiangVienMonHoc().getMonHoc());
        //Nếu danh sách rỗng, trả về mã GV đầu tiên của khoa
        if (dsLopHocPhan.size() == 0) {
            return lhp.getHocKi().getMaHocKi() + "-" + lhp.getGiangVienMonHoc().getMonHoc().getMaMonHoc() + "-001";
        } else {
            //Nếu danh sách không rỗng, lấy mã GV cuối cùng của khoa, tăng số thứ tự lên 1
            String maLopHocPhan = dsLopHocPhan.get(0).getMaLopHocPhan();
            int number = Integer.parseInt(maLopHocPhan.substring(maLopHocPhan.length() - 3));
            number++;
            return lhp.getHocKi().getMaHocKi() + "-" + lhp.getGiangVienMonHoc().getMonHoc().getMaMonHoc() + "-" + String.format("%03d", number);
        }

    }

    private boolean phongHocTrungLich(LopHocPhan lhp) {
        //Kiểm tra xem phòng học có bị trùng lịch với lớp khác cùng học kì hay không
        List<LopHocPhan> dsLopHocPhan = lopHocPhanRepository.findAllByHocKi(lhp.getHocKi());
        for (LopHocPhan lopHocPhan : dsLopHocPhan) {
            if (lopHocPhan.getPhongHoc().equals(lhp.getPhongHoc()) && lopHocPhan.getNgayTrongTuan().equals(lhp.getNgayTrongTuan()) && lopHocPhan.getKipTrongNgay().equals(lhp.getKipTrongNgay())) {
                return true;
            }
        }
        return false;
    }

    private boolean giangVienTrungLich(LopHocPhan lhp) {
        //Kiểm tra xem giảng viên có bị trùng lịch hay không
        List<LopHocPhan> dsLopHocPhan = lopHocPhanRepository.findAllByHocKiAndGiangVienMonHoc_GiangVien(lhp.getHocKi(), lhp.getGiangVienMonHoc().getGiangVien());
        for (LopHocPhan lopHocPhan : dsLopHocPhan) {
            if (lopHocPhan.getNgayTrongTuan().equals(lhp.getNgayTrongTuan()) && lopHocPhan.getKipTrongNgay().equals(lhp.getKipTrongNgay())) {
                return true;
            }
        }
        return false;
    }


    public List<LopHocPhan> findByGiangVienMonHoc(GiangVienMonHoc giangVienMonHoc) {
        return lopHocPhanRepository.findByGiangVienMonHoc(giangVienMonHoc);
    }
}
