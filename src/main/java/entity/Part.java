package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Part {
    @Id
    private String partId;
    private String partName;
    private double unitPrice;

    @OneToMany(mappedBy = "part")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Part(String partId, String partName, double unitPrice) {
        this.partId = partId;
        this.partName = partName;
        this.unitPrice = unitPrice;
    }
}
