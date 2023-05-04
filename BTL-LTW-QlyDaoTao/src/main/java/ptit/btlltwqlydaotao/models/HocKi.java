package ptit.btlltwqlydaotao.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocKi {
    @Id
    @GeneratedValue
    @Column(length = 20)
    private int id;

    @NotEmpty(message = "Tên học kì không được để trống")
    @Column(length = 50)
    private String tenHocKi;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private Date ngayBatDau;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date ngayKetThuc;
}
