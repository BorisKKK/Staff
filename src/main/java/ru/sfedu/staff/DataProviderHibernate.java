/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.staff;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import ru.sfedu.staff.utils.HibernateUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.classes.Contract;
import ru.sfedu.classes.Events;
import ru.sfedu.classes.Lessons;
import ru.sfedu.classes.PaidLessons;
import ru.sfedu.classes.Pupil;
import ru.sfedu.classes.Teacher;

/**
 *
 * @author BORYAN
 */
public class DataProviderHibernate {
    public static Logger log;
    public SessionFactory sessionFactory;
  
   
    public Session createSession(){
        sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }
    
  
    
    public <T> Result saveRecord(T bean, EnumOfEntities typeOfEntity) {
           switch (typeOfEntity) {
            case TEACHER:
                    return addTeacher((Teacher) bean);
            case PUPIL:
                    return addPupil((Pupil) bean);
            case EVENTS:
                    return addEvents((Events) bean);
            case LESSONS:
                    return addLessons((Lessons) bean);
            case CONTRACT:
                    return addContract((Contract)bean);
        }
        return null;
    }
    
    
    
    
    public Result addTeacher(Teacher t){
        Session session = createSession();
        Query query = session.createQuery("from ru.sfedu.classes.Teacher where name = :userName AND surname = :surName AND middleName = :middleName");
        query.setParameter("userName", t.getName());
        query.setParameter("surName", t.getSurname());
        query.setParameter("middleName", t.getMiddleName());
        if (query.list().isEmpty()) {
            session.beginTransaction();
            session.saveOrUpdate(t);
            session.getTransaction().commit();
        } 
        else return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("The record was successfully found."));
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Teacher adedd."));
   }
   
   
    public Result addPupil(Pupil p){
        Session session = createSession();
        Query query = session.createQuery("from ru.sfedu.classes.Pupil where name = :userName AND surname = :surName AND middleName = :middleName");
        query.setParameter("userName", p.getName());      /// поменять на getPassport в итоге 
        query.setParameter("surName", p.getSurname());
        query.setParameter("middleName", p.getMiddleName());
        if (query.list().isEmpty()) {
            session.beginTransaction();
            session.save(p);
            session.getTransaction().commit();
        } 
        else return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("The record was successfully found."));
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Pupil adedd."));
       
       
   }
   
   
    public Result addLessons(Lessons l){
        Session session = createSession();
        Query query = session.createQuery("from ru.sfedu.classes.Lessons where title = :TitleName");
        query.setParameter("TitleName", l.getTitle()); /// поменять на getTitle в итоге        
        if (query.list().isEmpty()) {
            session.beginTransaction();
            session.save(l);
            session.getTransaction().commit();
        } 
        else return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Занятие с таким названием уже есть в базе."));
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Lesson adedd."));
       
       
   }
   
   
    public Result addEvents(Events e){
        Session session = createSession();
        Query query = session.createQuery("from ru.sfedu.classes.Events e INNER JOIN e.pupil p where title = :TitleName AND p.id = :pupilId");
        query.setParameter("TitleName", e.getTitle());    
        query.setParameter("pupilId", e.getPupil().getId()); 
        if (query.list().isEmpty()) {
            session.beginTransaction();
            session.save(e);
            session.getTransaction().commit();
        } 
        else return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Запись об участии этого обучающегося в данном конкурсе уже есть в базе."));
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Event adedd."));
       
       
   }
    
    
    public Result addContract(Contract c){
        Session session = createSession();
        Query query = session.createQuery("from ru.sfedu.classes.Contract e INNER JOIN e.pupil p INNER JOIN e.lesson l where p.id = :pupilId AND l.lesson_id = :lessonId");
        query.setParameter("pupilId", c.getPupil().getId());   
        query.setParameter("lessonId", c.getLesson().getLesson_id());  
        if (query.list().isEmpty()) {
            session.beginTransaction();
            session.save(c);
            session.getTransaction().commit();
        } 
        else return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Ученик уже имеет контракт связанный с этим занятием"));
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Contract adedd."));
       
       
   }
    
    
    
    public Result deleteRecord(int id, EnumOfEntities typeOfEntity){
        Session session = createSession();
        Query query;
        switch(typeOfEntity){
            case TEACHER:
                session.beginTransaction();
                Teacher t = (Teacher) session.get(Teacher.class, id);
                List lessons = new ArrayList<Lessons>();
                if(t == null){
                    return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Нет педагога с таким id"));
                }
                query = session.createQuery("from ru.sfedu.classes.Lessons l where l.teacher.id = :id");
                //query = session.createQuery("select count(distinct pupil.id) from ru.sfedu.classes.PaidLessons l INNER JOIN l.cont cont INNER JOIN cont.pupil pupil");
                //query = session.createQuery("select p from ru.sfedu.classes.Pupil p INNER JOIN p.cont cont INNER JOIN cont.lesson l where l.lesson_id = :id");
                //query = session.createQuery("delete ru.sfedu.classes.Lessons l where l.teacher.id = :id");
                query.setParameter("id", id);
                lessons = (List<Lessons>) query.list();
                if (lessons.isEmpty()) {
                    session.delete(t);
                    session.getTransaction().commit();
                    session.close();     
                } 
                else {
                    List listOfLessons = new ArrayList<String>();
                    lessons.stream().forEach(b -> {
                        Lessons bean = new Lessons();
                        bean = (Lessons) b;
                        listOfLessons.add(bean.getTitle());
                    });
                    
                    return new Result(EnumOfStatuses.ERROR, lessons, Arrays.asList("Данный педагог ведет занятие - " + listOfLessons + ". Обновите данные перед удалением педагога"));
                }
                //query.executeUpdate();
                
            break;
            case PUPIL:
                session.beginTransaction();
                Pupil p = (Pupil) session.get(Pupil.class, id);
                if(p == null){
                    return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Нет ученика с таким id"));
                }
                query = session.createQuery("delete ru.sfedu.classes.Contract c where c.pupil.id = :id");
                query.setParameter("id", id);
                query.executeUpdate();
                session.delete(p);
                session.getTransaction().commit();
                session.close();
            break;
            case LESSONS:
                session.beginTransaction();
                Lessons l = (Lessons) session.get(Lessons.class, id);
                if(l == null){
                    return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Нет занятия с таким id"));
                }
                query = session.createQuery("delete ru.sfedu.classes.Contract c where c.lesson.lesson_id = :id");
                query.setParameter("id", id);
                query.executeUpdate();
                session.delete(l);
                session.getTransaction().commit();
                session.close();
            break;
            case EVENTS:
                session.beginTransaction();
                Events e = (Events) session.get(Events.class, id);
                if(e == null){
                    return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Нет конкурса с таким id"));
                }
                session.delete(e);
                session.getTransaction().commit();
                session.close();
            break;
            case CONTRACT:
                session.beginTransaction();
                Contract c = (Contract) session.get(Contract.class, id);
                if(c == null){
                    return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Нет контракта с таким id"));
                }
                session.delete(c);
                session.getTransaction().commit();
                session.close();
            break;
        }
        
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Record successfully deleted."));
    }
    
    
    
    
    
    public Result updateTeacher(Teacher t){
        Session session = createSession();
        session.beginTransaction();
        session.saveOrUpdate(t);
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Информация о педогоге с id - " + t.getId() + " обновлена"));
    }
    
    
     public Result updatePupil(Pupil p){
        Session session = createSession();
        session.beginTransaction();
        session.saveOrUpdate(p);
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Информация о ученике с id - " + p.getId() + " обновлена"));
    }
     
     
     public Result updateEvent(Events ev){
        Session session = createSession();
        session.beginTransaction();
        session.saveOrUpdate(ev);
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Информация о конкурсе с id - " + ev.getEvent_id() + " обновлена"));
    }
     
     
     public Result updateContract(Contract c){
        Session session = createSession();
        session.beginTransaction();
        session.saveOrUpdate(c);
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Информация о контракте с id - " + c.getContract_id() + " обновлена"));
    }
     
     
     public Result updateLesson(Lessons l){
        Session session = createSession();
        session.beginTransaction();
        int oldId = l.getLesson_id();
        session.delete(l);
        session.save(l);
        int newId = l.getLesson_id();
        Query query = session.createQuery("update ru.sfedu.classes.Contract set LESSON_ID = :newLessonId where LESSON_ID = :oldLessonId");
        query.setParameter("newLessonId", newId);
        query.setParameter("oldLessonId", oldId);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        
        
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Информация о занятии обновлена"));
    }
    
    
    
    public Result getLessonsByTeacher(int id){
        //session = HibernateUtil.getSessionFactory().getCurrentSession();
       // session.beginTransaction();
       Session session = createSession();
       List l = new ArrayList<Lessons>();
       session.beginTransaction();
       Teacher t = (Teacher) session.get(Teacher.class, id);
       if(t == null){
           return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Нет педагога с таким id"));
       }
       Query query = session.createQuery(
          " select l "
              + " from ru.sfedu.classes.Lessons l INNER JOIN l.teacher teacher"
              + " where teacher.id = :teacherId "
      );
       query.setParameter("teacherId", t.getId());
//       if (((org.hibernate.query.Query) query).list().isEmpty()) {
//           return new Result(EnumOfStatuses.ERROR, l,null);
//       }
       l = (List<Lessons>) query.list();
       session.getTransaction().commit();
       session.close();
       return new Result(EnumOfStatuses.SUCCESS, l, Arrays.asList("Lessons by teacher found."));
    }
    
    public Teacher getTeacherById(int id){
        Session session = createSession();
        session.beginTransaction();
        Teacher t = session.get(Teacher.class, id);
        session.getTransaction().commit();
        session.close();
        return t;
    }
    
    public Pupil getPupilById(int id){
        Session session = createSession();
        session.beginTransaction();
        Pupil p = session.get(Pupil.class, id);
        session.getTransaction().commit();
        session.close();
        return p;
    }
    
    public Lessons getLessonById(int id){
        Session session = createSession();
        session.beginTransaction();
        Lessons l = session.get(Lessons.class, id);
        session.getTransaction().commit();
        session.close();
        return l;
    }
    
    public Result getTeacherByName(String name, String surname, String middleName){
        Session session = createSession();
        Query query;
        List teacher = new ArrayList<Teacher>();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Teacher where name = :Name AND surname = :Surname AND middleName = :middleName");
        query.setParameter("Name", name);
        query.setParameter("Surname", surname);
        query.setParameter("middleName", middleName);
        teacher = (List<Teacher>) query.list();
        if (teacher.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Педагог отсутствует в базе"));
        } 
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, teacher, Arrays.asList("Teacher by name found."));
        }
    
    
    
    public Result getPupilByName(String name, String surname, String middleName){
        Session session = createSession();
        Query query;
        List pupil = new ArrayList<Pupil>();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Pupil where name = :Name AND surname = :Surname AND middleName = :middleName");
        query.setParameter("Name", name);
        query.setParameter("Surname", surname);
        query.setParameter("middleName", middleName);
        pupil = (List<Pupil>) query.list();
        if (pupil.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Ученик отсутствует в базе"));
        } 
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, pupil, Arrays.asList("Pupil by name found."));
        }
    
    
    public int getPupilIdByName(String name, String surname, String middleName){
        Session session = createSession();
        Query query;
        List pupil = new ArrayList<Pupil>();
        Pupil p = new Pupil();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Pupil where name = :Name AND surname = :Surname AND middleName = :middleName");
        query.setParameter("Name", name);
        query.setParameter("Surname", surname);
        query.setParameter("middleName", middleName);
        pupil = (List<Pupil>) query.list();
        p = (Pupil) pupil.get(0);
        
        int id = p.getId();
        
        session.getTransaction().commit();
        session.close();
        return id;
        }
    
    
    
    public int getLessonIdTitle(String title){
        Session session = createSession();
        Query query;
        List lesson = new ArrayList<Lessons>();
        Lessons l = new Lessons();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Lessons where title = :title");
        query.setParameter("title", title);
        
        lesson = (List<Lessons>) query.list();
        l = (Lessons) lesson.get(0);
        
        int id = l.getLesson_id();
        
        session.getTransaction().commit();
        session.close();
        return id;
        }
    
    
    
    public Result getLessonByTitle(String title){
        Session session = createSession();
        Query query;
        //Lessons lesson = new Lessons();
        List lessons = new ArrayList<Lessons>();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Lessons where title = :Title");
        query.setParameter("Title", title);
        lessons = (List<Lessons>)query.list();
        if (lessons.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, lessons, Arrays.asList("There is no lessons by this title."));
        } 
        //l.stream().forEach(b -> {Lessons lesson = new Lessons(); lesson = (Lessons) b;});
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, lessons, Arrays.asList("Lesson by title found."));
        }
    
    
    
    public Result getEventByTitleAndPupil(String title, int idPupil){
        Session session = createSession();
        Query query;
        //Lessons lesson = new Lessons();
        List event = new ArrayList<Events>();
        session.beginTransaction();
        //query = session.createQuery("select e from ru.sfedu.classes.Events e INNER JOIN e.pupil pupil where pupil.id = :id");  
        query = session.createQuery("select e from ru.sfedu.classes.Events e INNER JOIN e.pupil pupil where title = :Title AND pupil.id = :id");
        query.setParameter("Title", title);
        query.setParameter("id", idPupil);
        event = (List<Events>)query.list();
        if (event.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, event, Arrays.asList("There is no events by this pupil and title."));
        } 
        //l.stream().forEach(b -> {Lessons lesson = new Lessons(); lesson = (Lessons) b;});
      
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, event,  Arrays.asList("Events by this pupil and title found."));
        }
    
    
    public Result getContractByTitleAndPupil(int idLesson, int idPupil){
        Session session = createSession();
        Query query;
        List cont = new ArrayList<Contract>();
        session.beginTransaction();
        //query = session.createQuery("select e from ru.sfedu.classes.Events e INNER JOIN e.pupil pupil where pupil.id = :id");  
        query = session.createQuery("select c from ru.sfedu.classes.Contract c INNER JOIN c.pupil pupil INNER JOIN c.lesson lesson where lesson.lesson_id = :idLesson AND pupil.id = :idPupil");
        query.setParameter("idLesson", idLesson);
        query.setParameter("idPupil", idPupil);
        cont = (List<Contract>)query.list();
        if (cont.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, cont, Arrays.asList("There is no contract by this pupil and lesson."));
        } 
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, cont, Arrays.asList("Contract by pupil and lesson found."));
        }
    
    
    public Result addEvent(int idOfPupil, Events event){
        Query query;
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Pupil where id = :pupilId");
        query.setParameter("pupilId", idOfPupil);         
        if (((org.hibernate.query.Query) query).list().isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("There is no pupil with this id."));
        } 
        Pupil pupil = session.get(Pupil.class, idOfPupil);
        session.getTransaction().commit();
        session.close();
        event.setPupil(pupil);
        saveRecord(event, EnumOfEntities.EVENTS);
        return new Result(EnumOfStatuses.SUCCESS, null, Arrays.asList("Event added."));  
    }
    
    
    public Result getCountOfPupilInPaid(){
        Query query;
        int count;
        List<PaidLessons> paidLessons = new ArrayList<PaidLessons>();
        Session session = createSession();
        session.beginTransaction();
//        query = session.createQuery("from ru.sfedu.classes.Lessons where DTYPE = :type");
//        query.setParameter("type", "paid");     
//        paidLessons = query.list();
//        paidLessons.stream().forEach(b -> {
//                b.getCont();
//                    query = session.createQuery("from ru.sfedu.classes.Lessons where DTYPE = :type");
//                });
        //query = session.createQuery("select count(distinct pupil.id) from ru.sfedu.classes.PaidLessons l INNER JOIN l.cont cont INNER JOIN cont.pupil pupil");
        //query = session.createSQLQuery("select count(distinct pupils.id) from lessons l INNER JOIN contract cont INNER JOIN pupils pupils where DTYPE = 'paid'");
        query = session.createSQLQuery("select count(distinct pupils.id) from lessons l INNER JOIN contract cont ON l.lesson_id = cont.LESSON_ID INNER JOIN pupils pupils ON cont.PUPIL_ID = pupils.id where DTYPE = :type");
        query.setParameter("type", "paid");
        List r = query.list();
        session.getTransaction().commit();
        session.close();
        Result res = new Result(EnumOfStatuses.SUCCESS, r, Arrays.asList("Count of pupils in paid lessons found.")); 
        return res;
    }
    
    public Result getCountOfPupilInFree(){
        Query query;
        //int count;
        List<PaidLessons> paidLessons = new ArrayList<PaidLessons>();
        Session session = createSession();
        session.beginTransaction();
        query = session.createSQLQuery("select count(distinct pupils.id) from lessons l INNER JOIN contract cont ON l.lesson_id = cont.LESSON_ID INNER JOIN pupils pupils ON cont.PUPIL_ID = pupils.id where DTYPE = :type");
        query.setParameter("type", "free");
        List result = query.list();
        session.getTransaction().commit();
        session.close();
        Result res = new Result(EnumOfStatuses.SUCCESS, result, Arrays.asList("Count of pupils in free lessons found.")); 
        return res;
    }
    
    public Result getAllPupilsByLesson(int lessId){
        Query query;
        List pupils = new ArrayList<Pupil>();
        Session session = createSession();
        session.beginTransaction();
        Lessons lessonCheck = session.get(Lessons.class,lessId);
        if(lessonCheck == null){
            return new Result(EnumOfStatuses.ERROR, null, Arrays.asList("Нет занятия с таким id"));
        }
        query = session.createSQLQuery("select * from pupils p INNER JOIN contract cont ON p.id = cont.PUPIL_ID INNER JOIN lessons l ON cont.LESSON_ID = l.lesson_id where l.lesson_id = :id");
        query.setParameter("id", lessId);
        pupils = (List<Pupil>) query.list();
        if (pupils.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, pupils, Arrays.asList("На занятие еще никто не записан"));
        } 
        session.getTransaction().commit();
        session.close();
        Result r = new Result(EnumOfStatuses.SUCCESS, pupils, Arrays.asList("Pupils by lesson found."));
        return r;
    }
    
    
    public Result getAllEventsByPupil(int pupilId){
        Query query;
        List events = new ArrayList<Events>();
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("select e from ru.sfedu.classes.Events e INNER JOIN e.pupil pupil where pupil.id = :id");  
        query.setParameter("id", pupilId);
        events = (List<Events>) query.list();
        if (events.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, events, Arrays.asList("Ученик не участвовал ни в одном конкурсе"));
        } 
        session.getTransaction().commit();
        session.close();
        Result r = new Result(EnumOfStatuses.SUCCESS, events, Arrays.asList("Events by pupil found."));
        return r;

    }
    
    
        public Result getAllContractsByPupil(int pupilId){
        Query query;
        List cont = new ArrayList<Contract>();
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("select c from ru.sfedu.classes.Contract c INNER JOIN c.pupil pupil where pupil.id = :id");  
        query.setParameter("id", pupilId);
        cont = (List<Contract>) query.list();
        if (cont.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, cont, Arrays.asList("У ученика еще нет контрактов"));
        } 
        session.getTransaction().commit();
        session.close();
        Result r = new Result(EnumOfStatuses.SUCCESS, cont, Arrays.asList("Contracts by pupil found."));
        return r;
        
       
        
    }
    


       public Result getAllTeachers(){
        Query query;
        List<Teacher> teachers;
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Teacher");
        teachers = (List<Teacher>)query.list();
        if (teachers.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, teachers, Arrays.asList("There is no teachers."));
        } 
        session.getTransaction().commit();
        session.close();
        Result res = new Result(EnumOfStatuses.SUCCESS, teachers, Arrays.asList("Teachers found."));
        return res;
    }
       
       public Result getAllLessons(){
        Query query;
        List<Lessons> lessons;
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Lessons");
        lessons = (List<Lessons>)query.list();
        if (lessons.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, lessons, Arrays.asList("There is no lessons."));
        } 
        session.getTransaction().commit();
        session.close();
        Result res = new Result(EnumOfStatuses.SUCCESS, lessons, Arrays.asList("Lessons found."));
        return res;
    }
       
       
       public Result getAllPupils(){
        Query query;
        List<Pupil> pupils;
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Pupil");
        pupils = (List<Pupil>)query.list();
        if (pupils.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, pupils, Arrays.asList("There is no pupils."));
        } 
        session.getTransaction().commit();
        session.close();
        Result res = new Result(EnumOfStatuses.SUCCESS, pupils,  Arrays.asList("Pupils found."));
        return res;
    }
       
       public Result getAllEvents(){
        Query query;
        List<Events> events;
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("select distinct(title) from ru.sfedu.classes.Events");
        events = (List<Events>)query.list();
        if (events.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, events, Arrays.asList("There is no events."));
        } 
        session.getTransaction().commit();
        session.close();
        Result res = new Result(EnumOfStatuses.SUCCESS, events, Arrays.asList("Events found."));
        return res;
    }
       
       
       public Result getAllContracts(){
        Query query;
        List<Contract> contracts;
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("from ru.sfedu.classes.Contract");
        contracts = (List<Contract>)query.list();
        if (contracts.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, contracts, Arrays.asList("There is no contracts."));
        } 
        session.getTransaction().commit();
        session.close(); 
        Result res = new Result(EnumOfStatuses.SUCCESS, contracts, Arrays.asList("Contracts found."));
        return res;
    }
       
       public Result getCountOfTeachers(){
        Query query;
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("select count(*) from ru.sfedu.classes.Teacher");
        List result = query.list();
        if (result.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, result, Arrays.asList("There is no teachers."));
        } 
        session.getTransaction().commit();
        session.close();
        //sessionFactory.close();
        return new Result(EnumOfStatuses.SUCCESS,result,null);
    }
       
       
        public Result getCountOfLessons(){
        Query query;
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("select count(*) from ru.sfedu.classes.Lessons");
        List result = query.list();
        if (result.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, result, Arrays.asList("There is no lessons."));
        } 
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS,result,null);
    }
        
        
        public Result getCountOfPupils(){
        Query query;
        Session session = createSession();
        session.beginTransaction();
        query = session.createQuery("select count(*) from ru.sfedu.classes.Pupil");
        List result = query.list();
        if (result.isEmpty()) {
            return new Result(EnumOfStatuses.ERROR, result, Arrays.asList("There is no pupils."));
        } 
        session.getTransaction().commit();
        session.close();
        return new Result(EnumOfStatuses.SUCCESS, result, null);
    }
    
    
    
    
        
        
}
    
    
   
 
    
    
    
    
    
    
    

