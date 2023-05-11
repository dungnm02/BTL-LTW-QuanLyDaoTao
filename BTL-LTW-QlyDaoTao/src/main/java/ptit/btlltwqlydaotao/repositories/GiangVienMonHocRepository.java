package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.GiangVienMonHoc;

import java.util.List;

@Repository
public interface GiangVienMonHocRepository extends JpaRepository<GiangVienMonHoc, Integer> {

    List<GiangVienMonHoc> findByMonHoc_Id(Integer monHocId);

    List<GiangVienMonHoc> findByGiangVien_Id(Integer giangVienId);

    GiangVienMonHoc findByGiangVien_IdAndMonHoc_Id(Integer giangVienId, Integer monHocId);
}
