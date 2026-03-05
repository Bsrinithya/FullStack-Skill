
package com.example.demo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.example.entity.Product;
import com.example.util.HibernateUtil;

public class HQLDemo {

    public static void main(String[] args) {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try {

            String hql = "FROM Product p ORDER BY p.price ASC";
            Query<Product> query = session.createQuery(hql, Product.class);

            List<Product> products = query.list();

            System.out.println("Products sorted by price:");
            for(Product p : products){
                System.out.println(p);
            }

        } finally {
            session.close();
            factory.close();
        }
    }
}
