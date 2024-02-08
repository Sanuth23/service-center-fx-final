package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private String userId;
    private String name;
    private String contactNumber;
    private String jobRole;
    private String username;
    private String password;
}
