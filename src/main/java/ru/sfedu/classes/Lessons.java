/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.classes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author BORYAN
 */
@Entity(name = "lessons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue( "free" )
public class Lessons implements Serializable{

//    @GeneratedValue(generator = "lessonKeyGenerator")
//    @org.hibernate.annotations.GenericGenerator(
//        name = "lessonKeyGenerator",
//        strategy = "foreign",
//        parameters = 
//            @org.hibernate.annotations.Parameter(
//            name = "property", value = "teacher"
//        )
//    )
    //@Column(insertable = false, updatable=false, nullable=false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lesson_id;
    //@Embedded
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TEACH_ID", nullable = false)
    private Teacher teacher;
    private String title;
    private int aud;
    private String direction;
    private int countOfgroups;
    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "lesson", 
               cascade = CascadeType.ALL)
    private transient Set<Contract> cont = new HashSet<>();

    public Lessons() {
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getLesson_id() {
        return lesson_id;
    }

    public Set<Contract> getCont() {
        return cont;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }

    public void setAud(int room) {
        this.aud = room;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setCountOfgroups(int countOfgroups) {
        this.countOfgroups = countOfgroups;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getTitle() {
        return title;
    }

    public int getAud() {
        return aud;
    }

    public String getDirection() {
        return direction;
    }

    public int getCountOfgroups() {
        return countOfgroups;
    }
    
    @Override
    public String toString() {
        return "Lessons{" + "lesson_id=" + lesson_id + " title=" + title + ", aud=" + aud + ", direction=" + direction + ", countOfgroups=" + countOfgroups + '}';
    }
    
}
