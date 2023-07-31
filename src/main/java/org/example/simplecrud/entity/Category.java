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
public class Category implements Comparable {
    int id;
    String category;
    List<Product> products;

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    @Override
    public int compareTo(Object o) {
        Category category1 = (Category) o;
        return Integer.compare(this.getId(), category1.getId());
    }

    public static class CategoryBuilder {
        private int id;
        private String category;
        private List<Product> products;

        CategoryBuilder() {
        }

        public CategoryBuilder id(int id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder category(String category) {
            this.category = category;
            return this;
        }

        public CategoryBuilder products(List<Product> products) {
            this.products = products;
            return this;
        }

        public Category build() {
            return new Category(this.id, this.category, this.products);
        }

        public String toString() {
            return "Category.CategoryBuilder(id=" + this.id + ", category=" + this.category + ", products=" + this.products + ")";
        }
    }
}
