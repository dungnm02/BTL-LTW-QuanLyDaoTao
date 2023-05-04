package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.HocKi;

@Repository
public interface HocKiRepository extends CrudRepository<HocKi, Integer> {
}
