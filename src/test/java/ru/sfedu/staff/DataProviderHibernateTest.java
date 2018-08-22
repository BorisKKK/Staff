/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.staff;

import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.classes.Adress;
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
public class DataProviderHibernateTest {
    
    private static Teacher teacher;
    private static Lessons lesson;
    private static PaidLessons paidLesson;
    private static Pupil pupil;
    private static Events event;
    private static Contract contract;
    
    private static Logger log = Logger.getLogger(StaffClient.class);
    
    public DataProviderHibernateTest() {
    }
    
    public static Adress createAdress(){
        Random random = new Random();
        int num1 = random.nextInt(7);
        String streetArr[] = {"Советская", "Садовая", "Социалистическая", "Красноармейская", "Ильюшина", "Искра", "Карла-Маркса", "Шевченко"};
        int num2 = random.nextInt(7);
        String homeArr[] = {"1","2","3","4","5","6","7","8"};
        int num3 = random.nextInt(7);
        String roomArr[] = {"11","12","13","14","15","16","17","18"};
        Adress a = new Adress(streetArr[num1], homeArr[num2], roomArr[num3]);
        return a;
    }
    
    public static String createName(){
        Random random = new Random();
        int num = random.nextInt(7);
        String nameArr[] = {"Генадий", "Андрей", "Игорь", "Егор", "Олег", "Тимур", "Игнат", "Василий"};
        return nameArr[num];
    }
    
    public static String createSurname(){
        Random random = new Random();
        int num = random.nextInt(7);
        String surnameArr[] = {"Карпатов", "Тихонов", "Пенкин", "Петров", "Некрасов", "Цветной", "Мильчаков", "Кандратьев"};
        return surnameArr[num];
    }
    
    public static String createMiddleName(){
        Random random = new Random();
        int num = random.nextInt(7);
        String surnameArr[] = {"Владимирович", "Иванович", "Аркадьевич", "Петрович", "Генадьевич", "Эдуардович", "Андреевич", "Васильевич"};
        return surnameArr[num];
    }
    
    public static Teacher createTeacher(){
        Adress a = createAdress();
        Random random = new Random();
        int num = random.nextInt(7);
        String specializationArr[] = {"История", "Биология", "Прикладная информатика", "Информационные системы", "Математика", "География", "Электромеханик", "Электропривод и автоматика"};
        Teacher t = new Teacher();
        t.setName(createName());
        t.setDateOfBirth("22.12.2001");
        //t.setAge(17);
        t.setPaspN("343434");
        t.setPaspS("2334");
        //t.setSex("м");
        t.setMiddleName(createMiddleName());
        t.setSurname(createSurname());
        t.setTel("84566543443");
        t.setAdress(a);
        t.setDegree("test");
        t.setCategory("test");
        t.setSpecialization(specializationArr[num]);
        //t.setExperience("test");
        return t;
    }
    
    public static Pupil createPupil(){
        Adress a = createAdress();
        Pupil p = new Pupil();
        p.setSchool(1);
        p.setName(createName());
        p.setDateOfBirth("22.12.2001");
        //p.setAge(17);
        p.setPaspN("343434");
        p.setPaspS("2334");
        //p.setSex("м");
        p.setSurname(createSurname());
        p.setMiddleName(createMiddleName());
        p.setTel("84566543443");
        p.setAdress(a);
        return p;
    }
    
    public static Lessons createLesson(Teacher t){
        Random random = new Random();
        int num = random.nextInt(1);
        String directionArr[] = {"Техническая направленность", "Туристско-краеведческая направленность"};
        Lessons l = new Lessons();
        l.setCountOfgroups(0);
        l.setDirection(directionArr[num]);
        l.setAud(0);
        num = random.nextInt(3);
        String titlArr[] = {"Автомоделирование", "Судомоделирование", "test", "Робототехника"};
        l.setTitle(titlArr[num]);
        l.setTeacher(t);
        return l;
    }
    
    
    public static PaidLessons createPaidLesson(Teacher t){
        Random random = new Random();
        int num = random.nextInt(1);
        String directionArr[] = {"Художественная направленность", "Социально-педагогическая направленность"};
        PaidLessons l = new PaidLessons();
        l.setCountOfgroups(0);
        l.setDirection(directionArr[num]);
        l.setAud(0);
        num = random.nextInt(3);
        String titlArr[] = {"Радиоспорт", "test1", "Юный краевед", "Ракетное моделирование"};
        l.setTitle(titlArr[num]);
        l.setCoast(1000);
        l.setTeacher(t);
        return l;
    }
    
   
    
    public static Contract createContract(Pupil p, Lessons l){
        Contract c = new Contract();
        c.setGroupNumber(1);
        c.setPupil(p);
        c.setLesson(l);
        c.setTermOfImplementation(5);
        return c;
    }
    
    public static Events createEvent(Pupil p){
        Events ev = new Events();
        ev.setDate_of_event("22.22.14");
        Random random = new Random();
        int num = random.nextInt(4);
        String arrRes[] = {"1 место", "2 место", "3 место", "диплом", "сертификат"};
        ev.setResult(arrRes[num]);
        num = random.nextInt(4);
        String arrTit[] = {"Конвент 2014", "Шаг в будущее", "НТТМ 2014", "Бизнес Дона 2014", "городская выставка юных инноваторов"};
        ev.setTitle(arrTit[num]);
        ev.setPupil(p);
        return ev;
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testA() {
        DataProviderHibernate dp = new DataProviderHibernate();
        teacher = createTeacher();
        lesson = createLesson(teacher);
        pupil = createPupil();
        paidLesson = createPaidLesson(teacher);
        contract = createContract(pupil, paidLesson);
        event = createEvent(pupil);
        
        
           // ------------------------------------------------------------Тесты для добавления     
        Result r1 = dp.saveRecord(teacher, EnumOfEntities.TEACHER);
        Result r2 = dp.saveRecord(lesson, EnumOfEntities.LESSONS);
        Result r3 = dp.saveRecord(paidLesson, EnumOfEntities.LESSONS);
        Result r4 = dp.saveRecord(pupil, EnumOfEntities.PUPIL);
        Result r5 = dp.saveRecord(event, EnumOfEntities.EVENTS);
        Result r6 = dp.saveRecord(contract, EnumOfEntities.CONTRACT);
        
        Assert.assertEquals(r1.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r2.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r3.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r4.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r5.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r6.getStatus(),EnumOfStatuses.SUCCESS);
        
     
    }
    
    @Test
    public void testB() {
        
        //-----------------------------------------------------------Тест поиска
        DataProviderHibernate dp = new DataProviderHibernate();
        log.info(teacher);
        Result r1 = dp.getTeacherByName(teacher.getName(), teacher.getSurname(), teacher.getMiddleName());
        Result r2 = dp.getLessonByTitle(lesson.getTitle());
        Result r3 = dp.getLessonByTitle(paidLesson.getTitle());
        Result r4 = dp.getPupilByName(pupil.getName(), pupil.getSurname(), pupil.getMiddleName());
        Result r5 = dp.getEventByTitleAndPupil(event.getTitle(), pupil.getId());
        Result r6 = dp.getContractByTitleAndPupil(paidLesson.getLesson_id(), pupil.getId());
        
        Assert.assertEquals(r1.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r2.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r3.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r4.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r5.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r6.getStatus(),EnumOfStatuses.SUCCESS);
    }
    
    
    @Test
    public void testC() {
        
        //-----------------------------------------------------------Тест обновления
        DataProviderHibernate dp = new DataProviderHibernate();
        int idT = teacher.getId();
        int idP = pupil.getId();
        int idL = lesson.getLesson_id();
        int idPL = paidLesson.getLesson_id();
        int idE = event.getEvent_id();
        int idC= contract.getContract_id();
        
        teacher = createTeacher();
        lesson = createLesson(teacher);
        pupil = createPupil();
        paidLesson = createPaidLesson(teacher);
        contract = createContract(pupil,paidLesson);
        event = createEvent(pupil);
        
        teacher.setId(idT);
        pupil.setId(idP);
        lesson.setLesson_id(idL);
        paidLesson.setLesson_id(idPL);
        event.setEvent_id(idE); 
        contract.setContract_id(idC);
        
        Result r1 = dp.updateTeacher(teacher);
        Result r2 = dp.updatePupil(pupil);
        Result r3 = dp.updateLesson(paidLesson);
        Result r4 = dp.updatePupil(pupil);
        Result r5 = dp.updateEvent(event);
        Result r6 = dp.updateContract(contract);
        
        Assert.assertEquals(r1.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r2.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r3.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r4.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r5.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r6.getStatus(),EnumOfStatuses.SUCCESS);
    }
    
    
    @Test
    public void testD() {
        
        //-----------------------------------------------------------Тест удаления
        DataProviderHibernate dp = new DataProviderHibernate();
        Result r4 = dp.deleteRecord(paidLesson.getLesson_id(), EnumOfEntities.LESSONS);
        Result r3 = dp.deleteRecord(lesson.getLesson_id(), EnumOfEntities.LESSONS);
        Result r1 = dp.deleteRecord(teacher.getId(), EnumOfEntities.TEACHER);
        Result r2 = dp.deleteRecord(pupil.getId(), EnumOfEntities.PUPIL);
        Result r5 = dp.deleteRecord(event.getEvent_id(), EnumOfEntities.EVENTS);
        Result r6 = dp.deleteRecord(contract.getContract_id(), EnumOfEntities.CONTRACT);
        
        Assert.assertEquals(r1.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r2.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r3.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r4.getStatus(),EnumOfStatuses.SUCCESS);
        Assert.assertEquals(r5.getStatus(),EnumOfStatuses.ERROR);
        Assert.assertEquals(r6.getStatus(),EnumOfStatuses.ERROR);
    }
    
}
