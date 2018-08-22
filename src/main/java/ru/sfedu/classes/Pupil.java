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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author BORYAN
 */
@Entity(name = "pupils")
public class Pupil extends Person  implements Serializable{
    private int school;
    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "pupil", 
               cascade = CascadeType.ALL)
    private transient Set<Contract> cont = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER,
               mappedBy = "pupil", 
               cascade = CascadeType.ALL)
    private transient Set<Events> events = new HashSet<>();
    
    public Pupil() {
    }

    public Set<Contract> getCont() {
        return cont;
    }

    public void setCont(Set<Contract> cont) {
        this.cont = cont;
    }
    public Set<Events> getEvents() {
        return events;
    }

    public void setEvents(Set<Events> events) {
        this.events = events;
    }

    public void setSchool(int school) {
        this.school = school;
    }


    public int getSchool() {
        return school;
    }

    @Override
    public String toString(){
        return "id - " + getId()
                + " school - " + getSchool()
                + " name - " + getName()
                + " surname - " + getSurname()
                + " middleName - " + getMiddleName()
                + " telephone  - " + getTel()
                + " date_of_birth - " + getDateOfBirth()
                + " paspN - " + getPaspN()
                + " paspS - " + getPaspS()
                + " adress - " + getAdress();
        
    }


    
    
    
    
    
    
    
}
