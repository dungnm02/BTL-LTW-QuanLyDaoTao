package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.*;
import ptit.btlltwqlydaotao.repositories.KetQuaHocPhanRepository;

import java.util.List;

@Service
public class KetQuaHocPhanService {
    private final KetQuaHocPhanRepository ketQuaHocPhanRepository;

    public KetQuaHocPhanService(KetQuaHocPhanRepository ketQuaHocPhanRepository) {
        this.ketQuaHocPhanRepository = ketQuaHocPhanRepository;
    }

    public KetQuaHocPhan findById(Integer id) {
        return ketQuaHocPhanRepository.findById(id).orElse(null);
    }

    public List<KetQuaHocPhan> findBySinhVien(SinhVien sinhVien) {
        return ketQuaHocPhanRepository.findBySinhVien(sinhVien);
    }

    public KetQuaHocPhan findBySinhVienAndHocKiAndMonHoc(SinhVien sinhVien, HocKi hocKi, MonHoc monHoc) {
        for (KetQuaHocPhan ketQuaHocPhan : findBySinhVien(sinhVien)) {
            if (ketQuaHocPhan.getLopHocPhan().getHocKi() == hocKi &&
                    ketQuaHocPhan.getLopHocPhan().getGiangVienMonHoc().getMonHoc() == monHoc) {
                return ketQuaHocPhan;
            }
        }
        return null;
    }

    public List<KetQuaHocPhan> findBySinhVienAndHocKi(SinhVien sinhVien, HocKi hocKi) {
        return ketQuaHocPhanRepository.findBySinhVienAndLopHocPhan_HocKi(sinhVien, hocKi);
    }

    public List<KetQuaHocPhan> findByLopHocPhan(LopHocPhan lopHocPhan) {
        return ketQuaHocPhanRepository.findByLopHocPhan(lopHocPhan);
    }

    public KetQuaHocPhan findByLopHocPhan_IdAndSinhVien_Id(int idLopHocPhan, int idSinhVien) {
        return ketQuaHocPhanRepository.findByLopHocPhan_IdAndSinhVien_Id(idLopHocPhan, idSinhVien);
    }

    public void createKetQuaHocPhan(KetQuaHocPhan ketQuaHocPhan) {
        ketQuaHocPhanRepository.save(ketQuaHocPhan);
    }

    public void deleteById(int id) {
        ketQuaHocPhanRepository.deleteById(id);
    }

}
