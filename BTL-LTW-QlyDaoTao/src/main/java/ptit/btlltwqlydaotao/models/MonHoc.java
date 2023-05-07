package ptit.btlltwqlydaotao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @Column(length = 20, nullable = false, unique = true)
    private String maMonHoc;
    @NotEmpty(message = "Tên môn học không được bỏ trống")
    private String tenMonHoc;
    @ManyToOne
    private Khoa khoa;
}
