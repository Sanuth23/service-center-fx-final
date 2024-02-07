package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemDto {
    private String code;
    private String name;
    private String category;
    private double serviceFee;
}
