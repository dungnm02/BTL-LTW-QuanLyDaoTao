package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.models.MonHoc;

import java.util.List;

@Repository
public interface MonHocRepository extends CrudRepository<MonHoc, Integer> {
    List<MonHoc> findAllByKhoa(Khoa khoa);
}
