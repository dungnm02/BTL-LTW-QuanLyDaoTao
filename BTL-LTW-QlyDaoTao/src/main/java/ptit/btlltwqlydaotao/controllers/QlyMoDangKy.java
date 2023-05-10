package ptit.btlltwqlydaotao.controllers;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ptit.btlltwqlydaotao.models.HocKi;
import ptit.btlltwqlydaotao.services.HocKiService;
import ptit.btlltwqlydaotao.ultilities.ScheduledTasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/qly/modotdangky")
public class QlyMoDangKy {

    private final TaskScheduler taskScheduler;

    private final HocKiService hocKiService;

    private final ScheduledTasks scheduledTasks;

    public QlyMoDangKy(TaskScheduler taskScheduler, HocKiService hocKiService, ScheduledTasks scheduledTasks) {
        this.taskScheduler = taskScheduler;
        this.hocKiService = hocKiService;
        this.scheduledTasks = scheduledTasks;
    }

    @GetMapping("")
    public String showMoDangKy(Model model) {
        //Mở đăng ký cho học kì tiếp theo (chưa bắt đầu) và chưa mở đăng kí
        model.addAttribute("message", model.getAttribute("message"));
        model.addAttribute("dsHocKi", hocKiService.findHocKiChuaBatDau());
        return "qly_modangky";
    }

    @PostMapping("")
    public String submitMoDangKy(@RequestParam("batDau") String batDau,
                                 @RequestParam("idHocKi") int idHocKi,
                                 RedirectAttributes redirectAttributes) throws ParseException {
        HocKi hocKi = hocKiService.findById(idHocKi);
        //Mở đăng ký cho học kì tiếp theo (chưa bắt đầu)
        //Chỉ mở đăng ký trong một ngày
        Date moDangKy = new SimpleDateFormat("yyyy-MM-dd").parse(batDau);
        Date dongDangKy = new Date(moDangKy.getTime() + 24 * 60 * 60 * 1000);
        //Test: mở đăng ký trong 5 phút
        //Date moDangKy = new Date(); // Thời gian hiện tại
        //Date dongDangKy = new Date(date1.getTime() + (60 * 1000)); // Thời gian sau 5 phút

        if (!hocKiService.checkMoDangKi(hocKi, dongDangKy)) {
            redirectAttributes.addFlashAttribute("message", "Thời gian không hợp lệ");
            return "redirect:/qly/modotdangky";
        } else {
            taskScheduler.schedule(() -> scheduledTasks.moDangKy(hocKi), moDangKy);
            taskScheduler.schedule(() -> scheduledTasks.dongDangKy(hocKi), dongDangKy);
            redirectAttributes.addFlashAttribute("message", "Mở đăng ký thành công");
            return "redirect:/qly/modotdangky";
        }

    }

}
