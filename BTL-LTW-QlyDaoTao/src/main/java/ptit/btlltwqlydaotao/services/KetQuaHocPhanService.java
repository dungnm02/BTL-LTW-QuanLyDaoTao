package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.KetQuaHocPhan;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.models.SinhVien;
import ptit.btlltwqlydaotao.repositories.KetQuaHocPhanRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class KetQuaHocPhanService {
    private final KetQuaHocPhanRepository ketQuaHocPhanRepository;

    public KetQuaHocPhanService(KetQuaHocPhanRepository ketQuaHocPhanRepository) {
        this.ketQuaHocPhanRepository = ketQuaHocPhanRepository;
    }

    public List<KetQuaHocPhan> findBySinhVien(SinhVien sinhVien) {
        return ketQuaHocPhanRepository.findBySinhVien(sinhVien);
    }

    public List<KetQuaHocPhan> findByLopHocPhan(LopHocPhan lopHocPhan) {
        return ketQuaHocPhanRepository.findByLopHocPhan(lopHocPhan);
    }

    public List<SinhVien> findSinhVienByLopHocPhan(LopHocPhan lopHocPhan) {
        List<SinhVien> dsSinhVien = new ArrayList<>();
        for (KetQuaHocPhan ketQuaHocPhan : findByLopHocPhan(lopHocPhan)) {
            dsSinhVien.add(ketQuaHocPhan.getSinhVien());
        }
        return dsSinhVien;
    }
}
