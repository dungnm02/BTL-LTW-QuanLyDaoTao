package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.QuanLy;

@Repository
public interface QuanLyRepository extends JpaRepository<QuanLy, Integer> {
    QuanLy findByUsername(String username);
}
