package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.models.LopHocPhan;
import ptit.btlltwqlydaotao.models.MonHoc;

import java.util.List;

@Repository
public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, Integer> {

    List<LopHocPhan> findAllByHocKi(HocKi hocKi);

    List<LopHocPhan> findAllByHocKiAndGiangVienMonHoc_GiangVien(HocKi hocKi, GiangVien giangVien);

    List<LopHocPhan> findTopByHocKiAndGiangVienMonHoc_MonHocOrderByIdDesc(HocKi hocKi, MonHoc monHoc);
}
