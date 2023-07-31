package org.example.simplecrud.factories;

import org.example.simplecrud.dao.*;
import org.example.simplecrud.entity.*;

public class DaoFactory {
    public static <T> Dao createDao(Class<T> clazz) {
        if (clazz == Product.class) {
            return new ProductDao();
        } else if (clazz == User.class) {
            return new UserDao();
        } else if (clazz == DetailInformation.class) {
            return new DetailInformationDao();
        } else if (clazz == Cart.class) {
            return new CartDao();
        } else if (clazz == Supplier.class) {
            return new SupplierDao();
        } else if (clazz == Order.class) {
            return new OrderDao();
        } else if (clazz == Review.class) {
            return new RevievsDao();
        } else if (clazz == Category.class) {
            return new CategoryDao();
        } else if (clazz == Tag.class) {
            return new TagDao();
        } else if (clazz == Promotion.class) {
            return new PromotionDao();
        } else if (clazz == Payment.class) {
            return new PaymentDao();
        } else if (clazz == WishList.class) {
            return new WishListDao();
        } else {
            throw new IllegalArgumentException("Unknown DAO class for entity " + clazz.getName());
        }
    }
}
