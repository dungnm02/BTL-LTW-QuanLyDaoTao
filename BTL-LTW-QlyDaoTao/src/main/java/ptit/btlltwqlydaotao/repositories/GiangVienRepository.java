package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.Khoa;

import java.util.List;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, Integer> {
    List<GiangVien> findAllByKhoa(Khoa khoa);

}
