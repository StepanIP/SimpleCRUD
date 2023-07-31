package org.example.simplecrud.entity;

import lombok.*;
import org.example.simplecrud.entity.enums.PeymentMethod;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Payment implements Comparable{
    int id;
    PeymentMethod peymentMethod;
    double amount;
    Order order;

    @Override
    public int compareTo(Object o) {
        Payment payment = (Payment) o;
        return Integer.compare(this.getId(),payment.getId());
    }
}
