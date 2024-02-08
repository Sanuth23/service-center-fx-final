package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserTm extends RecursiveTreeObject<UserTm> {
    private String userId;
    private String name;
    private String contactNumber;
    private String jobRole;
    private String username;
    private String password;
    private JFXButton btn;
}
