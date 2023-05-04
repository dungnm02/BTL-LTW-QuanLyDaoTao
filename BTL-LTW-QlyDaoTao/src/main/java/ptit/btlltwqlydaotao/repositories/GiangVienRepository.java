package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.models.MonHoc;

import java.util.List;

@Repository
public interface GiangVienRepository extends CrudRepository<GiangVien, Integer> {
    List<GiangVien> findAllByKhoa(Khoa khoa);

}
