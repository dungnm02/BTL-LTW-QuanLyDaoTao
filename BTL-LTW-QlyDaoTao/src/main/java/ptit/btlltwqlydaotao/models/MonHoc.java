package ptit.btlltwqlydaotao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonHoc {
    @Id
    @GeneratedValue
    @Column(length = 20)
    private int id;
    @NotEmpty(message = "Tên môn học không được bỏ trống")
    private String tenMonHoc;
    @NotNull
    private String ghiChu;
    @ManyToOne
    private Khoa khoa;
}
