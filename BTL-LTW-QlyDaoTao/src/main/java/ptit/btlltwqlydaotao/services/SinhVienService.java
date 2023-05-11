package ptit.btlltwqlydaotao.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.*;
import ptit.btlltwqlydaotao.repositories.SinhVienRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SinhVienService {
    private final SinhVienRepository sinhVienRepository;
    private final KetQuaHocPhanService ketQuaHocPhanService;

    public SinhVienService(SinhVienRepository sinhVienRepository, KetQuaHocPhanService ketQuaHocPhanService) {
        this.sinhVienRepository = sinhVienRepository;
        this.ketQuaHocPhanService = ketQuaHocPhanService;
    }

    public List<SinhVien> findByKhoa(Khoa khoa) {
        return sinhVienRepository.findByKhoa(khoa);
    }

    public SinhVien findById(int id) {
        Optional<SinhVien> sinhVien = sinhVienRepository.findById(id);
        return sinhVien.orElse(null);
    }

    public List<SinhVien> searchSinhVien(String type, String keyword) {
        if (type != null && keyword != null && !keyword.isBlank()) {
            //Nếu người dùng nhập từ khóa, trả về danh sách theo kiểu tìm kiếm
            if (type.equals("maSinhVien")) {
                return sinhVienRepository.findByMaSinhVienContainingIgnoreCase(keyword);
            } else {
                return sinhVienRepository.findByHoTenContainingIgnoreCase(keyword);
            }
        } else {
            //Nếu người dùng không nhập từ khóa, trả về toàn bộ danh sách
            return sinhVienRepository.findAll();
        }
    }

    public void createSinhVien(SinhVien sinhVien) {
        sinhVien.setMaSinhVien(generateMaSinhVien(sinhVien.getKhoa()));
        sinhVien.setUsername(sinhVien.getMaSinhVien());
        sinhVien.setPassword(generatePassword(sinhVien.getMaSinhVien(), sinhVien.getNgaySinh()));
        sinhVienRepository.save(sinhVien);
    }

    public void updateSinhVien(SinhVien sinhVien) {
        sinhVienRepository.save(sinhVien);
    }

    @Transactional
    public void deleteById(int sinhVienId) {
        //Xóa tất cả kết quả học phần của sinh viên
        ketQuaHocPhanService.deleteBySinhVienId(sinhVienId);
        //Xóa sinh viên
        sinhVienRepository.deleteById(sinhVienId);
    }

    private String generatePassword(String maSV, LocalDate ngaySinh) {
        String[] arr = ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).split("/");
        StringBuilder password = new StringBuilder(maSV);
        for (String s : arr) {
            password.append(s);
        }
        return password.toString();
    }

    private String generateMaSinhVien(Khoa khoa) {
        //Lấy danh sách sinh viên theo khoa sắp xêp theo Id giảm dần
        List<SinhVien> dsSinhVien = sinhVienRepository.findTopByKhoaOrderByIdDesc(khoa);
        //Nếu danh sách rỗng, trả về mã SV đầu tiên của khoa
        if (dsSinhVien.size() == 0) {
            return "SV" + khoa.getMaKhoa() + "001";
        } else {
            //Nếu danh sách không rỗng, lấy mã SV cuối cùng của khoa, tăng số thứ tự lên 1
            String maSV = dsSinhVien.get(0).getMaSinhVien();
            int number = Integer.parseInt(maSV.substring(2 + khoa.getMaKhoa().length()));
            number++;
            return "SV" + khoa.getMaKhoa() + String.format("%03d", number);
        }
    }

    public void dangKyLopHocPhan(SinhVien sinhVien, HocKi hocKi, LopHocPhan lopHocPhan) {
        MonHoc monHoc = lopHocPhan.getGiangVienMonHoc().getMonHoc();
        //Kiểm tra trùng lịch học
        if (sinhVienTrungLich(sinhVien, hocKi, lopHocPhan)) {
            throw new RuntimeException("Lớp học phần trùng lịch học");
        }

        int idKetQuaHocPhan = kiemTraLopHocPhanCuaMonHoc(sinhVien, hocKi, monHoc);
        //Hủy đăng ký cũ trong học kì của môn học này
        if (idKetQuaHocPhan != 0) {
            ketQuaHocPhanService.deleteById(idKetQuaHocPhan);
        }
        KetQuaHocPhan ketQuaHocPhan = new KetQuaHocPhan();
        ketQuaHocPhan.setSinhVien(sinhVien);
        ketQuaHocPhan.setLopHocPhan(lopHocPhan);
        ketQuaHocPhanService.createKetQuaHocPhan(ketQuaHocPhan);
    }

    public int kiemTraLopHocPhanCuaMonHoc(SinhVien sinhVien, HocKi hocKi, MonHoc monHoc) {
        //Kiểm tra xem sinh viên này đã đăng ký lớp học phần của môn học này chưa
        KetQuaHocPhan ketQuaHocPhan = ketQuaHocPhanService.findBySinhVienAndHocKiAndMonHoc(sinhVien, hocKi, monHoc);
        if (ketQuaHocPhan != null) {
            //Nếu đúng trả về id của kết quả học phần
            return ketQuaHocPhan.getId();
        } else {
            return 0;
        }
    }

    public boolean sinhVienTrungLich(SinhVien sinhVien, HocKi hocKi, LopHocPhan lopHocPhan) {
        List<KetQuaHocPhan> dsKetQuaHocPhan = ketQuaHocPhanService.findBySinhVienAndHocKi(sinhVien, hocKi);
        for (KetQuaHocPhan ketQuaHocPhan : dsKetQuaHocPhan) {
            if (ketQuaHocPhan.getLopHocPhan().getNgayTrongTuan().equals(lopHocPhan.getNgayTrongTuan()) &&
                    ketQuaHocPhan.getLopHocPhan().getKipTrongNgay().equals(lopHocPhan.getKipTrongNgay())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ArrayList<String>> getThoiKhoaBieu(SinhVien sinhVien, HocKi hocKi) {
        //Khởi tạo một bảng thời khóa biểu (5 ngày và 6 kíp)
        ArrayList<ArrayList<String>> thoiKhoaBieu = new ArrayList<>(new ArrayList<>());
        for (int i = 0; i < 6; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                temp.add("");
            }
            thoiKhoaBieu.add(temp);
        }

        if (hocKi != null) {
            //Tìm các kết quả học phần trong học kì hiện tại của sinh viên này
            List<KetQuaHocPhan> dsLopHocPhan = ketQuaHocPhanService.findBySinhVienAndHocKi(sinhVien, hocKi);
            //Lấy thông tin buổi học từ lớp học phần trong kết quả học phần của sinh viên
            for (KetQuaHocPhan ketQuaHocPhan : dsLopHocPhan) {
                LopHocPhan lopHocPhan = ketQuaHocPhan.getLopHocPhan();
                String thongTinBuoiHoc = "Môn học: " + lopHocPhan.getGiangVienMonHoc().getMonHoc().getTenMonHoc() + "</br>" + "Phòng: " + lopHocPhan.getPhongHoc();
                //Đưa các thông tin buổi học vào ô tương ứng của bảng thời khóa biểu
                thoiKhoaBieu.get(Integer.parseInt(lopHocPhan.getKipTrongNgay()) - 1).set(Integer.parseInt(lopHocPhan.getNgayTrongTuan()) - 2, thongTinBuoiHoc);
            }
        }
        return thoiKhoaBieu;
    }

    public void doiMatKhau(SinhVien sinhVien, String matKhauCu, String matKhauMoi, String xacNhanMatKhauMoi) {
        //Kiểm tra mật khẩu cũ có khớp với mật khẩu trong CSDL không
        if (matKhauCu.equals(sinhVien.getPassword())) {
            //Kiểm tra mật khẩu mới có để trống không
            if (matKhauMoi.isBlank()) {
                throw new RuntimeException("Mật khẩu mới không được để trống");
            }
            //Kiểm tra mật khẩu mới có ít nhất 8 ký tự không
            if (matKhauMoi.length() < 8) {
                throw new RuntimeException("Mật khẩu mới phải có ít nhất 8 ký tự");
            }
            //Kiểm tra mật khẩu mới và xác nhận mật khẩu mới có khớp nhau không
            if (matKhauMoi.equals(xacNhanMatKhauMoi)) {
                sinhVien.setPassword(matKhauMoi);
                sinhVienRepository.save(sinhVien);
            } else {
                throw new RuntimeException("Xác nhận mật khẩu mới không khớp");
            }
        } else {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }
    }


}
