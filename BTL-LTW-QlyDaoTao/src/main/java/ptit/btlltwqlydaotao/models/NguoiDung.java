package ptit.btlltwqlydaotao.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class NguoiDung {
    @Id
    @GeneratedValue
    @Column(length = 20)
    private Integer id;
    @Column(length = 50, nullable = false)
    private String username;
    @Column(length = 50, nullable = false)
    private String password;

    @Column(nullable = false)
    @NotEmpty(message = "Họ tên không được để trống")
    private String hoTen;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate ngaySinh;
    @Column(length = 12, nullable = false)
    @NotEmpty(message = "Căn cước công dân không được để trống")
    @Size(min = 12, max = 12, message = "Căn cước công dân phải đủ 12 số")
    private String cccd;
    @Column(nullable = false)
    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
    @Column(columnDefinition = "CHAR(10)", nullable = false)
    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Số điện thoại không hợp lệ")
    private String sdt;


}
