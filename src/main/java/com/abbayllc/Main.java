package com.abbayllc;

import com.abbayllc.entities.Product;
import com.abbayllc.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Map<String,String> props = new HashMap<>();
        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","none");



        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(),props);
        EntityManager manager = entityManagerFactory.createEntityManager();

        try {

            manager.getTransaction().begin();

            /*SELECT, UPDATE, DELETE we can perform these querys in out jpql but not insert

            String jpql ="SElECT p FROM Product p where p.price >1500";
             we can extend the above query even to be paramterized like as follows



            List<Product> products  = query.getResultList();

            for( Product p : products){
                System.out.println(p);
            }

             */
//            String jpql = "select p from Product  p where p.price > :price and  p.name LIKE :name";
//
//
//            TypedQuery<Product>  query =  manager.createQuery(jpql, Product.class);
//            query.setParameter("price",1500);
//            query.setParameter("name","%M%");

// finding average value
//            String jpql = "select avg(p.price) from Product p";
//            TypedQuery<Double> query = manager.createQuery(jpql,Double.class);
//            Double averagePrice = query.getSingleResult();
//
//            System.out.println("Average price : " + averagePrice);

//
//            String jpqlCount = "Select Count(p) from Product p";
//
//            TypedQuery<Long> count = manager.createQuery(jpqlCount,Long.class);
//
//            Long numofProducts = count.getSingleResult();
//
//            System.out.println(numofProducts);


            //selecting specific attribute
            String jpql3 = """
                           select  p.name,avg(p.price) 
                           from Product p group by p.name
                           """;
            TypedQuery<Object[]> q  =manager.createQuery(jpql3, Object[].class);

            q.getResultList().forEach(objects -> {
                System.out.println(objects[0] +" " + objects[1] );
            });




            manager.getTransaction().commit();

        }finally {
            manager.close();
        }


    }
}