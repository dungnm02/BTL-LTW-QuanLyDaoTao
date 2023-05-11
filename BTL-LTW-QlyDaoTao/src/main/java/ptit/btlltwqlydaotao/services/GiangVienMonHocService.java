package ptit.btlltwqlydaotao.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.GiangVienMonHoc;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.models.MonHoc;
import ptit.btlltwqlydaotao.repositories.GiangVienMonHocRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiangVienMonHocService {
    private final GiangVienMonHocRepository giangVienMonHocRepository;
    private final MonHocService mocHocService;
    private final GiangVienService giangVienService;
    private final LopHocPhanService lopHocPhanService;

    public GiangVienMonHocService(GiangVienMonHocRepository giangVienMonHocRepository, MonHocService mocHocService, GiangVienService giangVienService, LopHocPhanService lopHocPhanService) {
        this.mocHocService = mocHocService;
        this.giangVienService = giangVienService;
        this.giangVienMonHocRepository = giangVienMonHocRepository;
        this.lopHocPhanService = lopHocPhanService;
    }

    public List<GiangVienMonHoc> findAll() {
        return giangVienMonHocRepository.findAll();
    }

    public List<GiangVien> getGiangVienDayMonHoc(int monHocId) {
        //Tìm tất cả giảng viên dạy môn học này
        List<GiangVien> dsGiangVien = new ArrayList<>();
        List<GiangVienMonHoc> dsgiangVienMonHoc = giangVienMonHocRepository.findByMonHoc_Id(monHocId);
        for (GiangVienMonHoc giangVienMonHoc : dsgiangVienMonHoc) {
            dsGiangVien.add(giangVienMonHoc.getGiangVien());
        }
        return dsGiangVien;
    }

    public List<GiangVien> getGiangVienKhongDayMonHoc(int monHocId) {
        //Lấy khoa của môn học này
        Khoa khoa = mocHocService.findById(monHocId).getKhoa();

        //Tìm tất cả giảng viên dạy môn học này
        List<GiangVien> dsGiangVienDayMonHoc = getGiangVienDayMonHoc(monHocId);

        //Tìm tất cả giảng viên trong khoa
        List<GiangVien> dsGiangVienKhoa = giangVienService.findByKhoaId(khoa.getId());

        //Lọc lấy tất cả giảng viên thuộc khoa của môn học này mà không dạy môn học này
        List<GiangVien> dsGiangVienKhongDayMonHoc = new ArrayList<>(dsGiangVienKhoa);
        dsGiangVienKhongDayMonHoc.removeAll(dsGiangVienDayMonHoc);

        return dsGiangVienKhongDayMonHoc;
    }


    public void deleteGiangVienMonHoc(GiangVienMonHoc giangVienMonHoc) {
        //Xóa tất cả lớp học phần của giảng viên môn học này
        lopHocPhanService.deleteByGiangVienMonHocId(giangVienMonHoc.getId());
        giangVienMonHocRepository.deleteById(giangVienMonHoc.getId());
    }

    public void addGiangVienDayMonHoc(int idMonHoc, List<Integer> dsIdGiangVienDuocChon) {
        MonHoc monHoc = mocHocService.findById(idMonHoc);
        for (Integer idGiangVienDuocChon : dsIdGiangVienDuocChon) {
            GiangVienMonHoc giangVienMonHoc = new GiangVienMonHoc();
            giangVienMonHoc.setGiangVien(giangVienService.findById(idGiangVienDuocChon));
            giangVienMonHoc.setMonHoc(monHoc);
            giangVienMonHocRepository.save(giangVienMonHoc);
        }
    }

    @Transactional
    public void deleteByMonHocId(Integer monHocId) {
        //Tìm các gvmh theo id môn học
        List<GiangVienMonHoc> dsGiangVienMonHoc = giangVienMonHocRepository.findByMonHoc_Id(monHocId);
        for (GiangVienMonHoc giangVienMonHoc : dsGiangVienMonHoc) {
            deleteGiangVienMonHoc(giangVienMonHoc);
        }
    }

    @Transactional
    public void deleteByGiangVienId(Integer giangVienId) {
        //Tìm các gvmh theo id giảng viên
        List<GiangVienMonHoc> dsGiangVienMonHoc = giangVienMonHocRepository.findByGiangVien_Id(giangVienId);
        for (GiangVienMonHoc giangVienMonHoc : dsGiangVienMonHoc) {
            deleteGiangVienMonHoc(giangVienMonHoc);
        }
    }

    @Transactional
    public void deleteGiangVienDayMonHoc(Integer monHocId, List<Integer> dsGiangVienId) {
        for (Integer giangVienId : dsGiangVienId) {
            deleteGiangVienMonHoc(giangVienMonHocRepository.findByGiangVien_IdAndMonHoc_Id(giangVienId, monHocId));
        }
    }

}
