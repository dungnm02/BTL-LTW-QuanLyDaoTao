package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.Khoa;
import ptit.btlltwqlydaotao.repositories.KhoaRepository;

import java.util.List;

@Service
public class KhoaService {
    KhoaRepository khoaRepository;

    KhoaService(KhoaRepository khoaRepository) {
        this.khoaRepository = khoaRepository;
    }

    public List<Khoa> findAll() {
        return khoaRepository.findAll();
    }
}
