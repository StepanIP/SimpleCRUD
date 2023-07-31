package org.example.simplecrud.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Tag implements Comparable{
    int id;
    String tag;
    List<Product> products;

    @Override
    public int compareTo(Object o) {
        Tag wishList = (Tag) o;
        return Integer.compare(this.getId(),wishList.getId());
    }
}
