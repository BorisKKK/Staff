/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.staff.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.classes.Adress;
import ru.sfedu.classes.Contract;
import ru.sfedu.classes.Events;
import ru.sfedu.classes.Lessons;
import ru.sfedu.classes.PaidLessons;
import ru.sfedu.classes.Person;
import ru.sfedu.classes.Pupil;
import ru.sfedu.classes.Teacher;

/**
 *
 * @author BORYAN
 */
public class HibernateUtil {
    
private static SessionFactory sessionFactory;
    
 public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            //-- loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(Pupil.class);// Аннотированная сущность
            metadataSources.addAnnotatedClass(Adress.class);// Аннотированная сущность
            metadataSources.addAnnotatedClass(Person.class);// Аннотированная сущность
            metadataSources.addAnnotatedClass(Teacher.class);// Аннотированная сущность
            metadataSources.addAnnotatedClass(Lessons.class);// Аннотированная сущность
            metadataSources.addAnnotatedClass(Contract.class);// Аннотированная сущность
            metadataSources.addAnnotatedClass(PaidLessons.class);// Аннотированная сущность
            metadataSources.addAnnotatedClass(Events.class);// Аннотированная сущность
          
            //---------------metadataSources.addResource("named-queries.hbm.xml");// Именованные запросы
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
            //sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }
    
}
