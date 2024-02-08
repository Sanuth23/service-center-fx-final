package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartDto {
    private String partId;
    private String partName;
    private double unitPrice;
}
