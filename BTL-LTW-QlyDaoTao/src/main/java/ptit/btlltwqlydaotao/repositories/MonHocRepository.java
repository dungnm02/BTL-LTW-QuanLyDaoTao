package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.models.MonHoc;

import java.util.List;

@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, Integer> {
    List<MonHoc> findAllByKhoa(Khoa khoa);
}
