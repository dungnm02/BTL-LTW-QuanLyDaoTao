package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.GiangVienMonHoc;
import ptit.btlltwqlydaotao.models.MonHoc;

import java.util.List;

@Repository
public interface GiangVienMonHocRepository extends CrudRepository<GiangVienMonHoc, Integer> {
    List<GiangVienMonHoc> findAllByMonHoc(MonHoc monHoc);
    List<GiangVienMonHoc> findAllByGiangVien(GiangVien giangVien);
}
