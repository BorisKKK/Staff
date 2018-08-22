/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.classes;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author BORYAN
 */
@Entity(name = "paid_lessons")
@DiscriminatorValue( "paid" )
public class PaidLessons extends Lessons implements Serializable{
    private int coast;
    //@ManyToOne(fetch=FetchType.LAZY)
    //@JoinColumn(name="TEACH_ID", nullable = false)
    //private Teacher teacher;
    
    public PaidLessons() {
    }

    public int getCoast() {
        return coast;
    }


    public void setCoast(int coast) {
        this.coast = coast; 
    }
    
    @Override
    public String toString() {
        return "lesson_id - " + getLesson_id()
                + " title - " + getTitle()
                + " aud - " + getAud()
                + " direction - " + getDirection()
                + " countOfgroups  - " + getCountOfgroups()
                + " coast - " + getCoast();
              
        
    }
    
}
