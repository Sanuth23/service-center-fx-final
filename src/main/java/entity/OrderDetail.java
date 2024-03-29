package entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class OrderDetail {
    @EmbeddedId
    private OrderDetailsKey detailsId;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "orderId")
    private PlaceOrder orders;

    @ManyToOne
    @MapsId("code")
    @JoinColumn(name = "itemCode")
    private Item item;

    @ManyToOne()
    @JoinColumn(name = "partId")
    private Part part;

    private int partQty;
    private double partAmount;

}
