package org.example.simplecrud.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Review implements Comparable{
    int id;
    User user;
    Product product;
    String text;
    int rate;

    @Override
    public int compareTo(Object o) {
        Review review = (Review) o;
        return Integer.compare(this.getId(),review.getId());
    }
}
