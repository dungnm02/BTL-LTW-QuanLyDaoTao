package ptit.btlltwqlydaotao.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinhVien extends NguoiDung {
    @Column(length = 20, nullable = false, unique = true)
    private String maSinhVien;
    @ManyToOne
    private Khoa khoa;
}
