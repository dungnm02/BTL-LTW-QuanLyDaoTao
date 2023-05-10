package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.repositories.GiangVienRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class GiangVienService {
    private final GiangVienRepository giangVienRepository;


    public GiangVienService(GiangVienRepository giangVienRepository) {
        this.giangVienRepository = giangVienRepository;
    }

    public List<GiangVien> findAll() {
        return giangVienRepository.findAll();
    }

    public List<GiangVien> findGiangVienByKhoa(Khoa khoa) {
        return giangVienRepository.findByKhoa(khoa);
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

    public GiangVien findById(int id) {
        Optional<GiangVien> giangVien = giangVienRepository.findById(id);
        return giangVien.orElse(null);
    }


    public void createGiangVien(GiangVien giangVien) {
        giangVien.setMaGiangVien(generateMaGiangVien(giangVien.getKhoa()));
        giangVien.setUsername(giangVien.getMaGiangVien());
        giangVien.setPassword(generatePassword(giangVien.getMaGiangVien(), giangVien.getNgaySinh()));
        giangVienRepository.save(giangVien);
    }

    public void updateGiangVien(GiangVien giangVien) {
        giangVienRepository.save(giangVien);
    }

    public void deleteGiangVien(int id) {
        giangVienRepository.deleteById(id);
    }

    private String generatePassword(String maGV, LocalDate ngaySinh) {
        String[] arr = ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).split("/");
        StringBuilder password = new StringBuilder(maGV);
        for (String s : arr) {
            password.append(s);
        }
        return password.toString();
    }

    private String generateMaGiangVien(Khoa khoa) {
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
