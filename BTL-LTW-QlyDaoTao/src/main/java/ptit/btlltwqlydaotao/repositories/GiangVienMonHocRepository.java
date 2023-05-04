package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.GiangVienMonHoc;
import ptit.btlltwqlydaotao.models.MonHoc;

import java.util.List;

@Repository
public interface GiangVienMonHocRepository extends JpaRepository<GiangVienMonHoc, Integer> {
    List<GiangVienMonHoc> findAllByMonHoc(MonHoc monHoc);

    List<GiangVienMonHoc> findAllByGiangVien(GiangVien giangVien);

    void deleteAllByGiangVienAndMonHoc(GiangVien giangVien, MonHoc monHoc);
}
