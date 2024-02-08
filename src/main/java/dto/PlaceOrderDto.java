package dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlaceOrderDto {

    private String orderId;
    private String custId;
    private String itemCode;
    private String date;
    private String issue;
    private double total;
    private String status;
    private List<OrderDetailDto> list;
}
