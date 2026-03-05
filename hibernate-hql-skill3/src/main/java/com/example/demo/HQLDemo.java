
package com.example.demo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.example.entity.Product;
import com.example.util.HibernateUtil;
import com.example.loader.ProductDataLoader;

public class HQLDemo {

    public static void main(String[] args){

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try{

            // Run once to insert sample data
            // ProductDataLoader.loadSampleProducts(session);

            sortProductsByPriceAscending(session);
            sortProductsByPriceDescending(session);
            sortProductsByQuantityDescending(session);

            getFirstThreeProducts(session);
            getNextThreeProducts(session);

            countTotalProducts(session);
            countProductsInStock(session);
            countProductsByDescription(session);
            findMinMaxPrice(session);

            groupProductsByDescription(session);

            filterProductsByPriceRange(session,20,100);

            findProductsStartingWith(session,"D");
            findProductsEndingWith(session,"p");
            findProductsContaining(session,"Desk");
            findProductsByNameLength(session,5);

        }finally{
            session.close();
            factory.close();
        }
    }

    public static void sortProductsByPriceAscending(Session session){
        Query<Product> q=session.createQuery("FROM Product p ORDER BY p.price ASC",Product.class);
        printProducts("Price ASC",q.list());
    }

    public static void sortProductsByPriceDescending(Session session){
        Query<Product> q=session.createQuery("FROM Product p ORDER BY p.price DESC",Product.class);
        printProducts("Price DESC",q.list());
    }

    public static void sortProductsByQuantityDescending(Session session){
        Query<Product> q=session.createQuery("FROM Product p ORDER BY p.quantity DESC",Product.class);
        printProducts("Quantity DESC",q.list());
    }

    public static void getFirstThreeProducts(Session session){
        Query<Product> q=session.createQuery("FROM Product",Product.class);
        q.setFirstResult(0);
        q.setMaxResults(3);
        printProducts("First 3 Products",q.list());
    }

    public static void getNextThreeProducts(Session session){
        Query<Product> q=session.createQuery("FROM Product",Product.class);
        q.setFirstResult(3);
        q.setMaxResults(3);
        printProducts("Next 3 Products",q.list());
    }

    public static void countTotalProducts(Session session){
        Long count=session.createQuery("SELECT COUNT(p) FROM Product p",Long.class).uniqueResult();
        System.out.println("Total Products: "+count);
    }

    public static void countProductsInStock(Session session){
        Long count=session.createQuery("SELECT COUNT(p) FROM Product p WHERE p.quantity>0",Long.class).uniqueResult();
        System.out.println("Products In Stock: "+count);
    }

    public static void countProductsByDescription(Session session){
        List<Object[]> list=session.createQuery("SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description",Object[].class).list();
        for(Object[] r:list){
            System.out.println(r[0]+" : "+r[1]);
        }
    }

    public static void findMinMaxPrice(Session session){
        Object[] r=session.createQuery("SELECT MIN(p.price), MAX(p.price) FROM Product p",Object[].class).uniqueResult();
        System.out.println("Min Price: "+r[0]);
        System.out.println("Max Price: "+r[1]);
    }

    public static void groupProductsByDescription(Session session){
        List<Object[]> list=session.createQuery("SELECT p.description,p.name,p.price FROM Product p ORDER BY p.description",Object[].class).list();
        for(Object[] r:list){
            System.out.println(r[0]+" - "+r[1]+" - "+r[2]);
        }
    }

    public static void filterProductsByPriceRange(Session session,double min,double max){
        Query<Product> q=session.createQuery("FROM Product p WHERE p.price BETWEEN :min AND :max",Product.class);
        q.setParameter("min",min);
        q.setParameter("max",max);
        printProducts("Price Range",q.list());
    }

    public static void findProductsStartingWith(Session session,String prefix){
        Query<Product> q=session.createQuery("FROM Product p WHERE p.name LIKE :pattern",Product.class);
        q.setParameter("pattern",prefix+"%");
        printProducts("Starts With "+prefix,q.list());
    }

    public static void findProductsEndingWith(Session session,String suffix){
        Query<Product> q=session.createQuery("FROM Product p WHERE p.name LIKE :pattern",Product.class);
        q.setParameter("pattern","%"+suffix);
        printProducts("Ends With "+suffix,q.list());
    }

    public static void findProductsContaining(Session session,String text){
        Query<Product> q=session.createQuery("FROM Product p WHERE p.name LIKE :pattern",Product.class);
        q.setParameter("pattern","%"+text+"%");
        printProducts("Contains "+text,q.list());
    }

    public static void findProductsByNameLength(Session session,int len){
        Query<Product> q=session.createQuery("FROM Product p WHERE LENGTH(p.name)=:l",Product.class);
        q.setParameter("l",len);
        printProducts("Name Length "+len,q.list());
    }

    private static void printProducts(String title,List<Product> list){
        System.out.println("\n=== "+title+" ===");
        for(Product p:list) System.out.println(p);
    }
}
