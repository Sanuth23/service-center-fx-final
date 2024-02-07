package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {
    @Id
    private String code;
    private String name;
    private String category;
    private double serviceFee;
}
