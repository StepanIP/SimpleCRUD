package org.example.simplecrud.entity;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order implements Comparable{
    int id;
    String state;
    LocalDate dateOrdered;
    LocalDate dateShifted;
    Product product;
    User user;

    @Override
    public int compareTo(Object o) {
        Order order = (Order) o;
        return Integer.compare(this.getId(),order.getId());
    }
}
