package ptit.btlltwqlydaotao.services;

import org.springframework.stereotype.Service;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.repositories.HocKiRepository;

@Service
public class HocKiService {
    HocKiRepository hocKiRepository;

    public HocKiService (HocKiRepository hocKiRepository) {
        this.hocKiRepository = hocKiRepository;
    }

    public HocKi findHocKiById(int id) {
        return hocKiRepository.findById(id).orElse(null);
    }

    public void deleteHocKiById(int id) {
        hocKiRepository.deleteById(id);
    }
}
