package ptit.btlltwqlydaotao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ptit.btlltwqlydaotao.models.GiangVien;
import ptit.btlltwqlydaotao.models.QuanLy;
import ptit.btlltwqlydaotao.models.SinhVien;
import ptit.btlltwqlydaotao.repositories.GiangVienRepository;
import ptit.btlltwqlydaotao.repositories.QuanLyRepository;
import ptit.btlltwqlydaotao.repositories.SinhVienRepository;


public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private GiangVienRepository giangVienRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private QuanLyRepository quanLyRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GiangVien giangVien = giangVienRepository.findByUsername(username);
        if (giangVien != null) {
            return giangVien;
        }

        SinhVien sinhVien = sinhVienRepository.findByUsername(username);
        if (sinhVien != null) {
            return sinhVien;
        }
        QuanLy quanLy = quanLyRepository.findByUsername(username);
        if (quanLy != null) {
            return quanLy;
        }

        throw new UsernameNotFoundException("Người dùng '" + username + "' không tồn tại");
    }
}

