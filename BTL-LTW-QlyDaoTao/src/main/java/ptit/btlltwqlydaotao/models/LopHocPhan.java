package ptit.btlltwqlydaotao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LopHocPhan {
    @Id
    @GeneratedValue
    @Column(length = 20)
    private int id;
    @NotEmpty(message = "Tên lớp học phần không được bỏ trống")
    private String tenLopHocPhan;
    @NotEmpty(message = "Ngày trong tuần không được bỏ trống")
    private String ngayTrongTuan;
    @NotEmpty(message = "Kíp trong ngày không được bỏ trống")
    private String kipTrongNgay;
    @NotEmpty(message = "Phòng học không được bỏ trống")
    private String phongHoc;
    @ManyToOne
    private HocKi hocKi;

    @ManyToOne
    private GiangVienMonHoc giangVienMonHoc;

}
