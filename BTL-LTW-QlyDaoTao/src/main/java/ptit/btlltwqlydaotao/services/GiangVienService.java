package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.repositories.GiangVienRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GiangVienService {
    private final GiangVienRepository giangVienRepository;

    public GiangVienService(GiangVienRepository giangVienRepository) {
        this.giangVienRepository = giangVienRepository;
    }

    public List<GiangVien> findByKhoaId(int khoaId) {
        return giangVienRepository.findByKhoa_Id(khoaId);
    }

    public List<GiangVien> searchGiangVien(String type, String keyword) {
        if (type != null && keyword != null && !keyword.isBlank()) {
            //Nếu người dùng nhập từ khóa, trả về danh sách theo kiểu tìm kiếm
            if (type.equals("maGiangVien")) {
                return giangVienRepository.findByMaGiangVienContainingIgnoreCase(keyword);
            } else {
                return giangVienRepository.findByHoTenContainingIgnoreCase(keyword);
            }
        } else {
            //Nếu người dùng không nhập từ khóa, trả về toàn bộ danh sách
            return giangVienRepository.findAll();
        }
    }

    public GiangVien findById(int giangVienId) {
        return giangVienRepository.findById(giangVienId).orElse(null);
    }

    public void createGiangVien(GiangVien giangVien) {
        //Tạo các thông tin cần thiết cho giảng viên
        giangVien.setMaGiangVien(generateMaGiangVien(giangVien.getKhoa()));
        giangVien.setUsername(giangVien.getMaGiangVien());
        giangVien.setPassword(generatePassword(giangVien.getMaGiangVien(), giangVien.getNgaySinh()));
        giangVienRepository.save(giangVien);
    }

    public void save(GiangVien giangVien) {
        giangVienRepository.save(giangVien);
    }

    public void deleteById(int giangVienId) {
        giangVienRepository.deleteById(giangVienId);
    }

    public void doiMatKhau(GiangVien giangVien, String matKhauCu, String matKhauMoi, String xacNhanMatKhauMoi) {
        //Kiểm tra mật khẩu cũ có khớp với mật khẩu trong CSDL không
        if (matKhauCu.equals(giangVien.getPassword())) {
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
                giangVien.setPassword(matKhauMoi);
                giangVienRepository.save(giangVien);
            } else {
                throw new RuntimeException("Xác nhận mật khẩu mới không khớp");
            }
        } else {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }
    }

    private String generatePassword(String maGV, LocalDate ngaySinh) {
        //Tạo mật khẩu mặc định là mã giảng viên + ngày sinh
        String[] arr = ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).split("/");
        StringBuilder password = new StringBuilder(maGV);
        for (String s : arr) {
            password.append(s);
        }
        return password.toString();
    }

    private String generateMaGiangVien(Khoa khoa) {
        //Tạo mã giảng viên theo quy tắc GV + mã khoa + số thứ tự trong khoa
        //Lấy danh sách giảng viên theo khoa sắp xêp theo Id giảm dần
        List<GiangVien> dsGiangVien = giangVienRepository.findTopByKhoaOrderByIdDesc(khoa);
        //Nếu danh sách rỗng, trả về mã GV đầu tiên của khoa
        if (dsGiangVien.size() == 0) {
            return "GV" + khoa.getMaKhoa() + "001";
        } else {
            //Nếu danh sách không rỗng, lấy mã GV cuối cùng của khoa, tăng số thứ tự lên 1
            String maGV = dsGiangVien.get(0).getMaGiangVien();
            int number = Integer.parseInt(maGV.substring(2 + khoa.getMaKhoa().length()));
            number++;
            return "GV" + khoa.getMaKhoa() + String.format("%03d", number);
        }
    }

}
