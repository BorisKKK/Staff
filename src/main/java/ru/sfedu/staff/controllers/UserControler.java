/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.staff.controllers;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import ru.sfedu.staff.Result;
import ru.sfedu.staff.Service.UserService;
import static spark.Spark.*;
import static ru.sfedu.staff.utils.JsonUtil.*;
import ru.sfedu.staff.EnumOfStatuses;
import ru.sfedu.staff.StandardResponse;
import ru.sfedu.staff.StatusResponse;
/**
 *
 * @author BORYAN
 */
public class UserControler {

    private Gson gson = new GsonBuilder().create();
    
    
    
    public UserControler(final UserService userService) {
        
        
        
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "post");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });
        
        
        get("/hello", (req, res) -> "Hello World");
        
        post("/teacher", (req, res) -> {
            Result r = userService.createTeacher(req.body());
            return r;
        }, json());
        
        post("/lesson", (req, res) -> {
            return userService.createLesson(req.body());    
        }, json());
        
        post("/pupil/add", (req, res) -> {
            return userService.createPupil(req.body());
        }, json());
        
        post("/event/add", (req, res) -> {
            return userService.createEvent(req.body());
        }, json());
       
        post("/contract/add", (req, res) -> {
            return userService.createContract(req.body());
        }, json());
        
        post("/teacher/name", (req, res) -> {
            return userService.getTeacherByName(req.body());
        }, json());
        
        
        post("/pupil/name", (req, res) -> {
            return userService.getPupilByName(req.body());
        }, json());
        
        
        post("/lesson/title", (req, res) -> {
            return userService.getLessonByTitle(req.body());
        }, json());
        
        post("/event/title", (req, res) -> {           
            return userService.getEventByTitlePupil(req.body());
        }, json());
        
        post("/contract/title", (req, res) -> {           
            return userService.getContractByTitlePupil(req.body());
        }, json());
        
        post("/events/pupil", (req, res) -> {
            return userService.getEventsOfPupil(req.body());
        }, json());
        
        post("/contracts/pupil", (req, res) -> {
            return userService.getContractsOfPupil(req.body());
        }, json());
        
        post("/teacher/delete", (req, res) -> {
            return userService.delTeacher(req.body());
        }, json());
        
        post("/pupil/delete", (req, res) -> {
            return userService.delPupil(req.body());
        }, json());
        
        post("/event/delete", (req, res) -> {
            return userService.delEvent(req.body());
        }, json());
        
        post("/lesson/delete", (req, res) -> {
            return userService.delLesson(req.body());
        }, json());
        
        
        post("/contract/delete", (req, res) -> {
            return userService.delContract(req.body());
        }, json());
        
        
        post("/teacher/update", (req, res) -> {
            return userService.updateTeacher(req.body());
        }, json());
        
        post("/pupil/update", (req, res) -> {
            return userService.updatePupil(req.body());
        }, json());
        
        post("/lesson/update", (req, res) -> {
            return userService.updateLesson(req.body());
        }, json());
        
        post("/event/update", (req, res) -> {
            return userService.updateEvent(req.body());
        }, json());
        
        post("/contract/update", (req, res) -> {
            return userService.updateContract(req.body());
        }, json());
        
        post("/pupils/lesson", (req, res) -> {
            return userService.getPupilsByLesson(req.body());
        }, json());
        
//        post("/pupil", (req, res) -> {
//            return userService.createPupil(req.body());
//        }, json());
        
        post("/lessons/teacher", (req, res) -> {
        res.type("application/json");
        //System.out.println("\n\n\n\n"+userService.getTeacher());
          return userService.getLessonsOfTeacher(req.body());
            }, json()); 
        
        
        get("/contracts", (request, response) -> {
        response.type("application/json");
        //System.out.println("\n\n\n\n"+userService.getTeacher());
          return userService.getContracts();
            }, json()); 
        
        get("/events", (request, response) -> {
        response.type("application/json");
        //System.out.println("\n\n\n\n"+userService.getTeacher());
          return userService.getEvents();
            }, json()); 
        
        get("/pupils", (request, response) -> {
        response.type("application/json");
        //System.out.println("\n\n\n\n"+userService.getTeacher());
          return userService.getPupils();
            }, json()); 
        
        get("/teachers", (request, response) -> {
        response.type("application/json");
//        return gson.toJson(
//           new StandardResponse(EnumOfStatuses.SUCCESS, new Gson()
//        .toJsonTree(userService.getTeacher())));
            System.out.println("\n\n\n\n"+gson.toJson(userService.getTeacher()));
          return userService.getTeacher();
            }, json()); 
        
        
        get("/lessons", (request, response) -> {
        response.type("application/json");
        //System.out.println("\n\n\n\n"+userService.getLessons());
          return userService.getLessons();
            }, json()); 
        
        
        
        get("/teachers/count", (request, response) -> {
        response.type("application/json");
        System.out.println("\n\n\n\n"+userService.getTeacherCount());
          return userService.getTeacherCount();
            }, json()); 
        
        
        get("/lessons/count", (request, response) -> {
        response.type("application/json");
        System.out.println("\n\n\n\n"+userService.getLessonsCount());
          return userService.getLessonsCount();
            }, json()); 
        
        get("/pupils/count", (request, response) -> {
        response.type("application/json");
        System.out.println("\n\n\n\n"+gson.toJson(userService.getPupilsCount()));
          return userService.getPupilsCount();
            }, json()); 
        
        get("/pupils/count/free", (request, response) -> {
        response.type("application/json");
        //System.out.println("\n\n\n\n"+gson.toJson(userService.getPupilsCount()));
          return userService.getPupilsCountFree();
            }, json());
        
        get("/pupils/count/paid", (request, response) -> {
        response.type("application/json");
        System.out.println("\n\n\n\n"+userService.getPupilsCountPaid());
          return userService.getPupilsCountPaid();
            }, json());
    }
    
}
