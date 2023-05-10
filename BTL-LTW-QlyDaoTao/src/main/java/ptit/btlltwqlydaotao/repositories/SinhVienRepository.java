package ptit.btlltwqlydaotao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.models.SinhVien;

import java.util.List;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, Integer> {

    List<SinhVien> findByMaSinhVienContainingIgnoreCase(String maSV);

    List<SinhVien> findByHoTenContainingIgnoreCase(String hoTen);

    List<SinhVien> findTopByKhoaOrderByIdDesc(Khoa khoa);

    List<SinhVien> findByKhoa(Khoa khoa);

    SinhVien findByUsername(String username);
}
