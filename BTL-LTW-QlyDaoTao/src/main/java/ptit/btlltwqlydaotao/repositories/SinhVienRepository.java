package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.SinhVien;

@Repository
public interface SinhVienRepository extends CrudRepository<SinhVien, Integer> {

}
