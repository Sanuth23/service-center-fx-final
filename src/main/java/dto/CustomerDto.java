package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDto {
    private String id;
    private String name;
    private String contactNumber;
    private String email;
}
