package org.example.simplecrud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart implements Comparable {
    private int id;
    private float totalCost;
    private int totalAmount;
    private List<Product> products;

    public static CartBuilder builder() {
        return new CartBuilder();
    }

    @Override
    public int compareTo(Object o) {
        Cart cart = (Cart) o;
        return Integer.compare(this.getId(), cart.getId());
    }

    public static class CartBuilder {
        private int id;
        private float totalCost;
        private int totalAmount;
        private List<Product> products;

        CartBuilder() {
        }

        public CartBuilder id(int id) {
            this.id = id;
            return this;
        }

        public CartBuilder totalCost(float totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public CartBuilder totalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public CartBuilder products(List<Product> products) {
            this.products = products;
            return this;
        }

        public Cart build() {
            return new Cart(this.id, this.totalCost, this.totalAmount, this.products);
        }

        public String toString() {
            return "Cart.CartBuilder(id=" + this.id + ", totalCost=" + this.totalCost + ", totalAmount=" + this.totalAmount + ", products=" + this.products + ")";
        }
    }
}
