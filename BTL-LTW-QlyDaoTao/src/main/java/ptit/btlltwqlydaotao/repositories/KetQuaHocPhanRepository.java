package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.KetQuaHocPhan;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.models.SinhVien;

import java.util.List;
@Repository
public interface KetQuaHocPhanRepository extends JpaRepository<KetQuaHocPhan, Integer> {
    List<KetQuaHocPhan> findBySinhVien(SinhVien sinhVien);

    List<KetQuaHocPhan> findByLopHocPhan(LopHocPhan lopHocPhan);
}
