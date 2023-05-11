package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.models.KetQuaHocPhan;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.models.SinhVien;

import java.util.List;

@Repository
public interface KetQuaHocPhanRepository extends JpaRepository<KetQuaHocPhan, Integer> {
    List<KetQuaHocPhan> findBySinhVien(SinhVien sinhVien);

    List<KetQuaHocPhan> findByLopHocPhan(LopHocPhan lopHocPhan);

    KetQuaHocPhan findByLopHocPhan_IdAndSinhVien_Id(int idLopHocPhan, int idSinhVien);

    List<KetQuaHocPhan> findBySinhVienAndLopHocPhan_HocKi(SinhVien sinhVien, HocKi hocKi);

    void deleteByLopHocPhan_Id(int lopHocPhanId);

    void deleteBySinhVien_Id(int sinhVienId);
}
