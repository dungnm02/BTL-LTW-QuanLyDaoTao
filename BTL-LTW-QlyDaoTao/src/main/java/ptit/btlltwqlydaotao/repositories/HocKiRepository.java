package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.HocKi;

import java.util.List;

@Repository
public interface HocKiRepository extends JpaRepository<HocKi, Integer> {
    List<HocKi> findByMoDangKy(boolean moDangKy);
}
