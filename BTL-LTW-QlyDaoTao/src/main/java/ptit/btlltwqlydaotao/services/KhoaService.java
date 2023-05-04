package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.repositories.KhoaRepository;

@Service
public class KhoaService {
    KhoaRepository khoaRepository;

    KhoaService(KhoaRepository khoaRepository) {
        this.khoaRepository = khoaRepository;
    }

    public Iterable<Khoa> findAllKhoa() {
        return khoaRepository.findAll();
    }
}
