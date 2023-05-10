package ptit.btlltwqlydaotao.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocKi {
    @Id
    @GeneratedValue
    @Column(length = 20)
    private int id;
    @Column(length = 20, nullable = false, unique = true)
    private String maHocKi;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate ngayBatDau;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate ngayKetThuc;
    private boolean moDangKy;
}
