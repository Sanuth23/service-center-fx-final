package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailTm extends RecursiveTreeObject<OrderDetailTm> {
    private String orderId;
    private String date;
    private String custId;
    private String custName;
    private JFXButton btn;
}
