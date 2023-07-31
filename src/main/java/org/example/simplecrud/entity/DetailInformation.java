package org.example.simplecrud.entity;

import lombok.*;
import org.example.simplecrud.entity.enums.Country;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetailInformation implements Comparable{
    private int id;
    private int age;
    private Country country;
    private String deliveryAddress;
    private Cart cart;

    @Override
    public int compareTo(Object o) {
        DetailInformation detailInformation = (DetailInformation) o;
        return Integer.compare(this.getId(),detailInformation.getId());
    }
}
