package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTm extends RecursiveTreeObject<CustomerTm> {
    private String id;
    private String name;
    private String contactNumber;
    private String email;
    private JFXButton btn;
}
