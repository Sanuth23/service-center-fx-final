package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartTm extends RecursiveTreeObject<PartTm> {
    private String partId;
    private String partName;
    private double unitPrice;
    private JFXButton btn;
}
