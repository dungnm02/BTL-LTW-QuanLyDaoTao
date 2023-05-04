package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.repositories.GiangVienRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GiangVienService {
    private GiangVienRepository giangVienRepository;


    public GiangVienService(GiangVienRepository giangVienRepository) {
        this.giangVienRepository = giangVienRepository;
    }

    public Iterable<GiangVien> findAllGiangVien() {
        return giangVienRepository.findAll();
    }

    public List<GiangVien> findAllGiangVienByKhoa(Khoa khoa) {
        return giangVienRepository.findAllByKhoa(khoa);
    }
    public GiangVien findGiangVienById(int id) {
        Optional<GiangVien> giangVien = giangVienRepository.findById(id);
        return giangVien.get();
    }

    public void addGiangVien(GiangVien giangVien) {
        giangVien.setMaGV(generateMaGV());
        giangVien.setUsername(giangVien.getMaGV());
        giangVien.setPassword(generatePassword(giangVien.getMaGV(), giangVien.getNgaySinh()));
        giangVienRepository.save(giangVien);
    }

    public void updateGiangVien(GiangVien giangVien, int id) {
        GiangVien oldGiangVien = findGiangVienById(id);
        oldGiangVien.setHoTen(giangVien.getHoTen());
        oldGiangVien.setNgaySinh(giangVien.getNgaySinh());
        oldGiangVien.setCccd(giangVien.getCccd());
        oldGiangVien.setEmail(giangVien.getEmail());
        oldGiangVien.setSdt(giangVien.getSdt());
        giangVienRepository.save(oldGiangVien);
    }

    public void deleteGiangVien(int id) {
        giangVienRepository.deleteById(id);
    }

    private String generatePassword(String maGV, Date ngaySinh) {
        String[] arr = ngaySinh.toString().split("-");
        String password = maGV;
        for (String s : arr) {
            password += s;
        }
        return password;
    }

    private String generateMaGV() {
        return String.format("GV%04d", giangVienRepository.count() + 1);
    }

}
