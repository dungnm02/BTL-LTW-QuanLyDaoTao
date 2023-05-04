package ptit.btlltwqlydaotao.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KetQuaHocPhan {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private float diemCC;
    @NotNull
    private float diemKT1;
    @NotNull
    private float diemKT2;
    @NotNull
    private float diemThi;
    @ManyToOne
    private SinhVien sinhVien;
    @ManyToOne
    private LopHocPhan lopHocPhan;
}
