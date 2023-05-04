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

    public Iterable<MonHoc> findAllMonHoc() {
        return monHocRepository.findAll();
    }

    public List<MonHoc> findAllByKhoa(Khoa khoa) {
        return monHocRepository.findAllByKhoa(khoa);
    }

    public MonHoc findMonHocById(int id) {
        return monHocRepository.findById(id).orElse(null);
    }

    public void addMonHoc(MonHoc monHoc) {
        monHocRepository.save(monHoc);
    }

    public void updateMonHoc(MonHoc monHoc, int id) {
        MonHoc oldMonHoc = findMonHocById(id);
        oldMonHoc.setTenMonHoc(monHoc.getTenMonHoc());
        oldMonHoc.setGhiChu(monHoc.getGhiChu());
        monHocRepository.save(oldMonHoc);
    }

    public void deleteMonHoc(int id) {
        monHocRepository.deleteById(id);
    }

}
