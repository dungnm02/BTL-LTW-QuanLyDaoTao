package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.SinhVien;
import ptit.btlltwqlydaotao.repositories.SinhVienRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class SinhVienService {
    SinhVienRepository sinhVienRepository;

    public SinhVienService(SinhVienRepository sinhVienRepository) {
        this.sinhVienRepository = sinhVienRepository;
    }

    public Iterable<SinhVien> findAllSinhVien() {
        return sinhVienRepository.findAll();
    }

    public SinhVien findSinhVienById(int id) {
        Optional<SinhVien> sinhVien = sinhVienRepository.findById(id);
        return sinhVien.get();
    }

    public void addSinhVien(SinhVien sinhVien) {
        sinhVien.setMaSV(generateMaSV());
        sinhVien.setUsername(sinhVien.getMaSV());
        sinhVien.setPassword(generatePassword(sinhVien.getMaSV(), sinhVien.getNgaySinh()));
        sinhVienRepository.save(sinhVien);
    }

    public void updateSinhVien(SinhVien sinhVien, int id) {
        SinhVien oldSinhVien = findSinhVienById(id);
        oldSinhVien.setHoTen(sinhVien.getHoTen());
        oldSinhVien.setNgaySinh(sinhVien.getNgaySinh());
        oldSinhVien.setCccd(sinhVien.getCccd());
        oldSinhVien.setEmail(sinhVien.getEmail());
        oldSinhVien.setSdt(sinhVien.getSdt());
        sinhVienRepository.save(oldSinhVien);
    }

    public void deleteSinhVien(int id) {
        sinhVienRepository.deleteById(id);
    }

    private String generatePassword(String maSV, Date ngaySinh) {
        String[] arr = ngaySinh.toString().split("-");
        String password = maSV;
        for (String s : arr) {
            password += s;
        }
        return password;
    }

    private String generateMaSV() {
        return String.format("SV%04d", sinhVienRepository.count() + 1);
    }

}
