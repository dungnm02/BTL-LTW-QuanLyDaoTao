package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.models.MonHoc;
import ptit.btlltwqlydaotao.repositories.MonHocRepository;

import java.util.List;

@Service
public class MonHocService {
    private final MonHocRepository monHocRepository;

    MonHocService(MonHocRepository monHocRepository) {
        this.monHocRepository = monHocRepository;
    }

    public MonHoc findMonHocById(int id) {
        return monHocRepository.findById(id).orElse(null);
    }

    public List<MonHoc> findAll() {
        return monHocRepository.findAll();
    }


    public List<MonHoc> searchMonHoc(Integer khoaId, String tenMonHoc) {
        khoaId = (khoaId == null) ? 0 : khoaId;
        if (khoaId == 0 && tenMonHoc == null) {
            return monHocRepository.findAll();
        } else if (khoaId == 0) {
            return monHocRepository.findByTenMonHocContainingIgnoreCase(tenMonHoc);
        } else if (tenMonHoc == null) {
            return monHocRepository.findByKhoa_Id(khoaId);
        } else {
            return monHocRepository.findByKhoa_IdAndTenMonHocContainingIgnoreCase(khoaId, tenMonHoc);
        }
    }

    public void createMonHoc(MonHoc monHoc) {
        if (monHocRepository.findByKhoa_IdAndTenMonHocContainingIgnoreCase(monHoc.getKhoa().getId(), monHoc.getTenMonHoc()).size() > 0) {
            throw new RuntimeException("Môn học có tên như thế này tồn tại");
        } else {
            monHoc.setMaMonHoc(generateMaMonHoc(monHoc.getKhoa()));
            monHocRepository.save(monHoc);
        }
    }

    public void updateMonHoc(MonHoc monHoc) {
        if (monHocRepository.findByTenMonHoc(monHoc.getTenMonHoc()) != null) {
            throw new RuntimeException("Môn học có tên như thế này tồn tại");
        } else {
            monHocRepository.save(monHoc);
        }
    }

    public void deleteMonHoc(int id) {
        monHocRepository.deleteById(id);
    }

    private String generateMaMonHoc(Khoa khoa) {
        //Lấy danh sách môn học theo khoa sắp xêp theo Id giảm dần
        List<MonHoc> dsMonHoc = monHocRepository.findTopByKhoaOrderByIdDesc(khoa);
        //Nếu danh sách rỗng, trả về mã SV đầu tiên của khoa
        if (dsMonHoc.size() == 0) {
            return "MH" + khoa.getMaKhoa() + "001";
        } else {
            //Nếu danh sách không rỗng, lấy mã SV cuối cùng của khoa, tăng số thứ tự lên 1
            String maMonHoc = dsMonHoc.get(0).getMaMonHoc();
            int number = Integer.parseInt(maMonHoc.substring(2 + khoa.getMaKhoa().length()));
            number++;
            return "MH" + khoa.getMaKhoa() + String.format("%03d", number);
        }
    }
}
