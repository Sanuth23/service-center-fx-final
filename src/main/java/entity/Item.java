package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {
    @Id
    private String code;
    private String name;
    private String category;
    private double serviceFee;

    @OneToMany(mappedBy = "item")
    private List<PlaceOrder> orders = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Item(String code, String name, String category, double serviceFee) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.serviceFee = serviceFee;
    }
}
