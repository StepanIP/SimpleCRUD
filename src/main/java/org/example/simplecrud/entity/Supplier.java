package org.example.simplecrud.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Supplier implements Comparable{
    private int id;
    private String companyName;
    private String contactPerson;
    private String address;
    private String email;

    @Override
    public int compareTo(Object o) {
        Supplier supplier = (Supplier) o;
        return Integer.compare(this.getId(),supplier.getId());
    }
}
