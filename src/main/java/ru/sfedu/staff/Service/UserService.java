/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.staff.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ru.sfedu.classes.Adress;
import ru.sfedu.classes.Contract;
import ru.sfedu.classes.Events;
import ru.sfedu.classes.Lessons;
import ru.sfedu.classes.PaidLessons;
import ru.sfedu.classes.Pupil;
import ru.sfedu.classes.Teacher;
import ru.sfedu.staff.DataProviderHibernate;
import ru.sfedu.staff.Result;
import ru.sfedu.staff.EnumOfEntities;
import ru.sfedu.staff.EnumOfStatuses;
import ru.sfedu.staff.StandardResponse;

/**
 *
 * @author BORYAN
 */
public class UserService {
    private DataProviderHibernate dp = new DataProviderHibernate();
    private Gson gson = new GsonBuilder().create();
    
    
    public Result createTeacher(String json){
        Adress a = gson.fromJson(json, Adress.class);
        Teacher teacher = gson.fromJson(json, Teacher.class);
//        Adress a = new Adress();
//        
//        a.setHouse("sdf");
//        a.setRoom("sdf");
//        a.setStreet("dfsd");
        teacher.setAdress(a);
        Result r = dp.saveRecord(teacher, EnumOfEntities.TEACHER);
        return r;
    }
    
    
    public Result createPupil(String json){
        Adress a = gson.fromJson(json, Adress.class);
        Pupil pupil = gson.fromJson(json, Pupil.class);
        pupil.setAdress(a);
        Result r = dp.saveRecord(pupil, EnumOfEntities.PUPIL);
        return r;
    }
    
    
    public Result createEvent(String json){
        
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int id = gson.fromJson(jsonObject.get("p_id"), Integer.class);
        Pupil p = dp.getPupilById(id);
        Events event = gson.fromJson(json, Events.class);
        event.setPupil(p);
        Result r = dp.saveRecord(event, EnumOfEntities.EVENTS);
        return r;
    }
    
    public Result createContract(String json){
        
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int pupil_id = gson.fromJson(jsonObject.get("p_id"), Integer.class);
        int lesson_id = gson.fromJson(jsonObject.get("l_id"), Integer.class);
        Pupil p = dp.getPupilById(pupil_id);
        Lessons l = dp.getLessonById(lesson_id);
        Contract contract = gson.fromJson(json, Contract.class);
        contract.setPupil(p);
        contract.setLesson(l);
        Result r = dp.saveRecord(contract, EnumOfEntities.CONTRACT);
        return r;
    }
    
    
    public Result createLesson(String json){
        
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int id = gson.fromJson(jsonObject.get("t_id"), Integer.class);
        String type = gson.fromJson(jsonObject.get("type"), String.class);
        Teacher t = dp.getTeacherById(id);
        Lessons lesson = null;
        switch(type){
            case "free":
                lesson = gson.fromJson(json, Lessons.class);
                break;
            case "paid":
                lesson = gson.fromJson(json, PaidLessons.class);
                break;
        }
        lesson.setTeacher(t);
        Result r = dp.saveRecord(lesson, EnumOfEntities.LESSONS);
        return r;
    }
    
    public Result updateLesson(String json){
        
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int id = gson.fromJson(jsonObject.get("t_id"), Integer.class);
        String type = gson.fromJson(jsonObject.get("type"), String.class);
        Teacher t = dp.getTeacherById(id);
        Lessons lesson = null;
        switch(type){
            case "free":
                lesson = gson.fromJson(json, Lessons.class);
                break;
            case "paid":
                lesson = gson.fromJson(json, PaidLessons.class);
                break;
        }
        lesson.setTeacher(t);
        Result r = dp.updateLesson(lesson);
        return r;
    }
    
    
    
    public Result updateEvent(String json){
        
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int id = gson.fromJson(jsonObject.get("p_id"), Integer.class);
        Pupil p = dp.getPupilById(id);
        Events event = gson.fromJson(json, Events.class);
        event.setPupil(p);
        Result r = dp.updateEvent(event);
        return r;
    }
    
    
    public Result updateContract(String json){
        
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int idPupil = gson.fromJson(jsonObject.get("p_id"), Integer.class);
        int idLesson = gson.fromJson(jsonObject.get("l_id"), Integer.class);
        Pupil p = dp.getPupilById(idPupil);
        Lessons l = dp.getLessonById(idLesson);
        Contract cont = gson.fromJson(json, Contract.class);
        cont.setPupil(p);
        cont.setLesson(l);
        Result r = dp.updateContract(cont);
        return r;
    }
    
    
    public Result getPupilsByLesson(String json){
        
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        //int id = gson.fromJson(jsonObject.get("idOfLesson"), Integer.class);
        
        int idToDelete = gson.fromJson(jsonObject.get("idToDelete"), Integer.class);
        
        Result r = dp.getAllPupilsByLesson(idToDelete);
        return r;
    }
    
    
    public Result getTeacher(){
       // Collection<Teacher> t = dp.getAllTeachers();
       Result r = dp.getAllTeachers();
       //String s = gson.toJson(r);
//        List<String> l = new ArrayList<>();
//        t.stream().forEach(b -> {
//            //System.out.println(b.toString());
//            l.add(b.toString());
//                    });
        //StandardResponse r = new StandardResponse(EnumOfStatuses.SUCCESS, gson.toJson(l));
        //System.out.println(gson.toJson(l));
        return r;
    }
    
    public Result getLessons(){
        Result r = dp.getAllLessons();
        return r;
    }
    
    public Result getTeacherCount(){
        Result r = dp.getCountOfTeachers();
        return r;
    }
    
    public Result getLessonsCount(){
        Result r = dp.getCountOfLessons();
        return r;
    }
    
    public Result getPupilsCount(){          
        Result r = dp.getCountOfPupils();
        return r;
    }
    
    
    public Result getPupils(){          
        Result r = dp.getAllPupils();
        return r;
    }
    
    public Result getEvents(){          
        Result r = dp.getAllEvents();
        return r;
    }
    
    public Result getContracts(){          
        Result r = dp.getAllContracts();
        return r;
    }
    
    public Result getPupilsCountFree(){
        Result r = dp.getCountOfPupilInFree();
        return r;
    }
    
    public Result getPupilsCountPaid(){
        Result r = dp.getCountOfPupilInPaid();
        return r;
    }
    
    
    
    public Result getTeacherByName(String json){          
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String name = gson.fromJson(jsonObject.get("name"), String.class);
        String surname = gson.fromJson(jsonObject.get("surname"), String.class);
        String middleName = gson.fromJson(jsonObject.get("middleName"), String.class);
        Result r = dp.getTeacherByName(name,surname, middleName);
        return r;
    }
    
    public Result getLessonByTitle(String json){          
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String title = gson.fromJson(jsonObject.get("title"), String.class);
        Result r = dp.getLessonByTitle(title);
        return r;
    }
    
    
    public Result getEventByTitlePupil(String json){          
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        List pupil = new ArrayList<Pupil>();
        String title = gson.fromJson(jsonObject.get("title"), String.class);
        String name = gson.fromJson(jsonObject.get("name"), String.class);
        String surname = gson.fromJson(jsonObject.get("surname"), String.class);
        String middleName = gson.fromJson(jsonObject.get("middleName"), String.class);
        int idPupil = dp.getPupilIdByName(name, surname,middleName);
        
        Result r = dp.getEventByTitleAndPupil(title, idPupil);
        return r;
    }
    
    
      public Result getContractByTitlePupil(String json){          
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        List pupil = new ArrayList<Pupil>();
        String title = gson.fromJson(jsonObject.get("title"), String.class);
        String name = gson.fromJson(jsonObject.get("name"), String.class);
        String surname = gson.fromJson(jsonObject.get("surname"), String.class);
        String middleName = gson.fromJson(jsonObject.get("middleName"), String.class);
        int idPupil = dp.getPupilIdByName(name, surname,middleName);
        int idLesson = dp.getLessonIdTitle(title);
     
        Result r = dp.getContractByTitleAndPupil(idLesson, idPupil);
        return r;
    }
    
    
     public Result getPupilByName(String json){          
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String name = gson.fromJson(jsonObject.get("name"), String.class);
        String surname = gson.fromJson(jsonObject.get("surname"), String.class);
        String middleName = gson.fromJson(jsonObject.get("middleName"), String.class);
        
        Result r = dp.getPupilByName(name,surname,middleName);
        return r;
    }
    
    public Result delTeacher(String json){          
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int idToDelete = gson.fromJson(jsonObject.get("idToDelete"), Integer.class);
        Result r = dp.deleteRecord(idToDelete, EnumOfEntities.TEACHER);
        return r;
    }
    
    
    public Result delPupil(String json){
       JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
       int idToDelete = gson.fromJson(jsonObject.get("idToDelete"), Integer.class);
       Result r = dp.deleteRecord(idToDelete, EnumOfEntities.PUPIL);
       return r; 
    }
    
    
     public Result delLesson(String json){
       JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
       int idToDelete = gson.fromJson(jsonObject.get("idToDelete"), Integer.class);
       Result r = dp.deleteRecord(idToDelete, EnumOfEntities.LESSONS);
       return r; 
    }
     
     
     public Result delContract(String json){
       JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
       int idToDelete = gson.fromJson(jsonObject.get("idToDelete"), Integer.class);
       Result r = dp.deleteRecord(idToDelete, EnumOfEntities.CONTRACT);
       return r; 
    }
     
     
     public Result delEvent(String json){
       JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
       int idToDelete = gson.fromJson(jsonObject.get("idToDelete"), Integer.class);
       Result r = dp.deleteRecord(idToDelete, EnumOfEntities.EVENTS);
       return r; 
    }
    
    public Result getLessonsOfTeacher(String json){      
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int idOfTeacher = gson.fromJson(jsonObject.get("idOfTeacher"), Integer.class);
        Result r = dp.getLessonsByTeacher(idOfTeacher);
        return r;
    }
    
     public Result getEventsOfPupil(String json){      
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int idToFind = gson.fromJson(jsonObject.get("idToFind"), Integer.class);
        Result r = dp.getAllEventsByPupil(idToFind);
        return r;
    }
     
     
     public Result getContractsOfPupil(String json){      
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        int idToFind = gson.fromJson(jsonObject.get("idToFind"), Integer.class);
        Result r = dp.getAllContractsByPupil(idToFind);
        return r;
    }
    
    public Result updateTeacher(String json){ 
        Adress a = gson.fromJson(json, Adress.class);
        Teacher teacher = gson.fromJson(json, Teacher.class);
        teacher.setAdress(a);
        Result r = dp.updateTeacher(teacher);
        return r;
    }
    
        public Result updatePupil(String json){ 
        Adress a = gson.fromJson(json, Adress.class);
        Pupil pupil = gson.fromJson(json, Pupil.class);
        pupil.setAdress(a);
        Result r = dp.updatePupil(pupil);
        return r;
    }
        
        
}
