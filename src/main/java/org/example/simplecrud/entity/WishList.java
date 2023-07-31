package org.example.simplecrud.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WishList implements Comparable{
    int id;
    Product product;
    User user;

    @Override
    public int compareTo(Object o) {
        WishList wishList = (WishList) o;
        return Integer.compare(this.getId(),wishList.getId());
    }
}
