package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class PlaceOrder {
    @Id
    private String orderId;
    private Date date;
    private String issue;

    @ManyToOne()
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name = "itemCode")
    private Item item;

    @OneToMany(mappedBy = "orders")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public PlaceOrder(String orderId, Date date, String issue) {
        this.orderId = orderId;
        this.date = date;
        this.issue = issue;
    }
}
