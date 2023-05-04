package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.Khoa;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa, Integer> {
}
