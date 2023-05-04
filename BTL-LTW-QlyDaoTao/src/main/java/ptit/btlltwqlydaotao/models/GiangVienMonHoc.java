package ptit.btlltwqlydaotao.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiangVienMonHoc {
    @Id
    @GeneratedValue
    @Column(length = 20)
    private int id;
    @ManyToOne
    private GiangVien giangVien;
    @ManyToOne
    private MonHoc monHoc;
}
