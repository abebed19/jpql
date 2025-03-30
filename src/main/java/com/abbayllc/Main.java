package com.abbayllc;

import com.abbayllc.entities.Product;
import com.abbayllc.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            //SELECT, UPDATE, DELETE we can perform these querys in out jpql but not insert

            //String jpql ="SElECT p FROM Product p where p.price >1500";
            // we can extend the above query even to be paramterized like as follows
            String jpql = "select p from Product  p where p.price > :price and  p.name LIKE :name";


            TypedQuery<Product>  query =  manager.createQuery(jpql, Product.class);
            query.setParameter("price",1500);
            query.setParameter("name","%m%");


            List<Product> products  = query.getResultList();

            for( Product p : products){
                System.out.println(p);
            }


            manager.getTransaction().commit();

        }finally {
            manager.close();
        }


    }
}