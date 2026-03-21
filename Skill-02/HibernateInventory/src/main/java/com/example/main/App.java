package com.example.main;
import com.example.dao.ProductDAO;
import com.example.entity.Product;
public class App {
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
       Product p1 = new Product("Laptop", "Gaming Laptop", 75000, 10);
        Product p2 = new Product("Mouse", "Wireless Mouse", 1200, 50);
       dao.saveProduct(p1);
        dao.saveProduct(p2);

        dao.updateProduct(1, 72000, 8);
        dao.deleteProduct(2);
    }
}

