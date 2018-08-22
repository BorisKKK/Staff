/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.staff;

//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.classes.Adress;
import ru.sfedu.classes.Lessons;
import ru.sfedu.classes.Pupil;
import ru.sfedu.classes.Teacher;
import ru.sfedu.staff.Service.UserService;
import ru.sfedu.staff.controllers.UserControler;
import static spark.Spark.*;


/**
 *
 * @author BORYAN
 */
public class StaffClient {
    
    private static Logger log = Logger.getLogger(StaffClient.class);
 
    
   
    
    public void logBasicSystemInfo() {
        log.info("Launching the application...");
        log.info(
        "Operating System: " + System.getProperty("os.name") + " "
        + System.getProperty("os.version")
        );
        log.info("JRE: " + System.getProperty("java.version"));
        log.info("Java Launched From: " + System.getProperty("java.home"));
        log.info("Class Path: " + System.getProperty("java.class.path"));
        log.info("Library Path: " + System.getProperty("java.library.path"));
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("User Working Directory: " + System.getProperty("user.dir"));
        log.info("Test INFO logging.");
    }
    
   
    
    StaffClient(){
        log.debug("<Your constructor name>[0]: starting application.........");
    }
    

    
    public static void main(String[] args) {
        port(8080);
        //get("/hello", (req, res) -> "Hello World");
        
        new UserControler(new UserService());
    }
  
}
