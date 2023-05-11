package ptit.btlltwqlydaotao.ultilities;

import org.springframework.stereotype.Component;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.services.HocKiService;

import java.time.LocalDate;

@Component
public class ScheduledTasks {
    private final HocKiService hocKiService;

    public ScheduledTasks(HocKiService hocKiService) {
        this.hocKiService = hocKiService;
    }

    public void moDangKy(HocKi hocKi) {
        System.out.println(hocKi);
        System.out.println("Mở đăng ký" + LocalDate.now());
        hocKi.setMoDangKy(true);
        hocKiService.updateHocKi(hocKi);
    }

    public void dongDangKy(HocKi hocKi) {
        System.out.println("Đóng đăng ký" + LocalDate.now());
        hocKi.setMoDangKy(false);
        hocKiService.updateHocKi(hocKi);
    }
}
