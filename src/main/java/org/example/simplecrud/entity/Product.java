package org.example.simplecrud.entity;

import lombok.*;
import org.example.simplecrud.entity.enums.Metal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product implements Comparable{
    private int id;
    private String name;
    private Metal metal;
    private int cost;
    private String image;
    private Supplier supplier;

    @Override
    public int compareTo(Object o) {
        Product product = (Product) o;
        return Integer.compare(this.getId(),product.getId());
    }
}
