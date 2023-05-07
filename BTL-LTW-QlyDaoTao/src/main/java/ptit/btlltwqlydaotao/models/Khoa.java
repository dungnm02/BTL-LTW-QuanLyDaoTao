package ptit.btlltwqlydaotao.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Khoa {
    @Id
    private int id;

    private String maKhoa;

    private String tenKhoa;

}
