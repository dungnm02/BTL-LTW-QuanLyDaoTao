package ptit.btlltwqlydaotao.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.GiangVienMonHoc;
import ptit.btlltwqlydaotao.models.MonHoc;
import ptit.btlltwqlydaotao.repositories.GiangVienMonHocRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiangVienMonHocService {
    private final GiangVienMonHocRepository giangVienMonHocRepository;
    private final MonHocService mocHocService;
    private final GiangVienService giangVienService;

    public GiangVienMonHocService(GiangVienMonHocRepository giangVienMonHocRepository, MonHocService mocHocService, GiangVienService giangVienService) {
        this.mocHocService = mocHocService;
        this.giangVienService = giangVienService;
        this.giangVienMonHocRepository = giangVienMonHocRepository;
    }

    public List<GiangVienMonHoc> findAllByGiangVien(GiangVien giangVien) {
        return giangVienMonHocRepository.findAllByGiangVien(giangVien);
    }

    public List<GiangVienMonHoc> findAllByMonHoc(MonHoc monHoc) {
        return giangVienMonHocRepository.findAllByMonHoc(monHoc);
    }

    public ArrayList<GiangVien> getAllGiangVienDayMonHoc(MonHoc monHoc) {
        ArrayList<GiangVien> dsGiangVien = new ArrayList<>();
        List<GiangVienMonHoc> dsgiangVienMonHoc = findAllByMonHoc(monHoc);
        for (GiangVienMonHoc giangVienMonHoc : dsgiangVienMonHoc) {
            dsGiangVien.add(giangVienMonHoc.getGiangVien());
        }
        return dsGiangVien;
    }

    public List<GiangVien> getAllGiangVienChuaDayMonHoc(MonHoc monHoc) {
        List<GiangVien> dsGiangVienDayMonHoc = getAllGiangVienDayMonHoc(monHoc);
        List<GiangVien> dsGiangVienKhoa = giangVienService.findAllGiangVienByKhoa(monHoc.getKhoa());
        List<GiangVien> dsGiangVienChuaDayMonHoc = new ArrayList<>(dsGiangVienKhoa);
        dsGiangVienChuaDayMonHoc.removeAll(dsGiangVienDayMonHoc);
        return dsGiangVienChuaDayMonHoc;
    }

    public void addGiangVienDayMonHoc(Integer idMonHoc, List<Integer> dsIdGiangVienDuocChon) {
        for (Integer idGiangVienDuocChon : dsIdGiangVienDuocChon) {
            GiangVienMonHoc giangVienMonHoc = new GiangVienMonHoc();
            giangVienMonHoc.setGiangVien(giangVienService.findGiangVienById(idGiangVienDuocChon));
            giangVienMonHoc.setMonHoc(mocHocService.findMonHocById(idMonHoc));
            giangVienMonHocRepository.save(giangVienMonHoc);
        }
    }

    public void deleteAllByMonHocId(Integer monHocId) {
        List<GiangVienMonHoc> dsGiangVienMonHoc = findAllByMonHoc(mocHocService.findMonHocById(monHocId));
        giangVienMonHocRepository.deleteAll(dsGiangVienMonHoc);
    }

    public void deleteAllByGiangVienId(Integer giangVienId) {
        List<GiangVienMonHoc> dsGiangVienMonHoc = findAllByGiangVien(giangVienService.findGiangVienById(giangVienId));
        giangVienMonHocRepository.deleteAll(dsGiangVienMonHoc);
    }

    @Transactional
    public void deleteGiangVienDayMonHoc(int idMonHoc, List<Integer> dsIdGiangVienDuocChon) {
        MonHoc monHoc = mocHocService.findMonHocById(idMonHoc);
        for (Integer idGiangVienDuocChon : dsIdGiangVienDuocChon) {
            giangVienMonHocRepository.deleteAllByGiangVienAndMonHoc(giangVienService.findGiangVienById(idGiangVienDuocChon), monHoc);
        }
    }
}
