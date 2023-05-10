package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.*;

import java.util.List;

@Repository
public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, Integer> {

    List<LopHocPhan> findAllByHocKi(HocKi hocKi);

    List<LopHocPhan> findAllByHocKiAndGiangVienMonHoc_GiangVien(HocKi hocKi, GiangVien giangVien);

    List<LopHocPhan> findTopByHocKiAndGiangVienMonHoc_MonHocOrderByIdDesc(HocKi hocKi, MonHoc monHoc);

    List<LopHocPhan> findByHocKiAndGiangVienMonHoc_MonHoc_Khoa(HocKi hocKi, Khoa khoa);

    List<LopHocPhan> findAllByGiangVienMonHoc_GiangVien(GiangVien giangVien);

    List<LopHocPhan> findByGiangVienMonHoc(GiangVienMonHoc giangVienMonHoc);
}
