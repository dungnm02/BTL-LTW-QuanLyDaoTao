package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.Khoa;

@Repository
public interface KhoaRepository extends CrudRepository<Khoa, Integer> {
}
