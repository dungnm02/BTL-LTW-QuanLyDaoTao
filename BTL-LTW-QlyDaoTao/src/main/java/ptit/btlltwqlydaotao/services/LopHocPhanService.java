package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.repositories.LopHocPhanRepository;

import java.util.List;

@Service
public class LopHocPhanService {
    private final MonHocService monHocService;
    private final LopHocPhanRepository lopHocPhanRepository;

    public LopHocPhanService(MonHocService monHocService, LopHocPhanRepository lopHocPhanRepository) {
        this.monHocService = monHocService;
        this.lopHocPhanRepository = lopHocPhanRepository;
    }

    public List<LopHocPhan> findAllLopHocPhan() {
        return lopHocPhanRepository.findAll();
    }

    public List<LopHocPhan> findAllByHocKi(HocKi hocKi) {
        return lopHocPhanRepository.findAllByHocKi(hocKi);
    }

    public LopHocPhan findById(int id) {
        return lopHocPhanRepository.findById(id).orElse(null);
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


}
