package org.example.simplecrud.entity;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Promotion implements Comparable{
    int id;
    LocalDate startDate;
    LocalDate endDate;
    Product product;
    double discount;

    @Override
    public int compareTo(Object o) {
        Promotion promotion = (Promotion) o;
        return Integer.compare(this.getId(),promotion.getId());
    }
}
