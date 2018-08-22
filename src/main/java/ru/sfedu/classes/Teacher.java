/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.classes;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.Hibernate;
    

/**
 *
 * @author BORYAN
 */

//@Embeddable
@Entity(name = "teachers")
public class Teacher extends Person implements Serializable {
  
    private String degree;
    
    private String category;
    
    private String specialization;
   // @ElementCollection
   // @CollectionTable(
   // name="lessons",
   // joinColumns=@JoinColumn(name="TEACH_ID")
   // )
    //@Exclude
    //@Expose(serialize = false)
   
    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "teacher",    
               cascade = CascadeType.ALL)
    private transient Set<Lessons> less = new HashSet<>();
    
    public Teacher() {
    }

    

    public Set<Lessons> getLess() {
        return null;
    }
    
    

    public void setDegree(String degree) {
        this.degree = degree;
    }


    public String getDegree() {
        return degree;
    }

    public String getCategory() {
        return category;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public void setLess(Set<Lessons> less) {
        //this.less = less;
    }

//    @Override
//    public String toString() {
//        return "Teacher{" + "degree=" + degree + ", experience=" + experience + ", category=" + category + ", specialization=" + specialization + "}";//, less=" + less + '}';
//    }
    
//   @Override
 //   public String toString(){
   // ---
//        String les = "";
//        Iterator<Lessons> it = less.iterator();
//        les += it.next().toString();
//        while(it.hasNext()){
//            les += ", " + it.next().toString();      
//        }
            
        //if((les != null) && (less.size() > 0)){
             //for (int i = 0; i < less.size(); i++) {
                //if (i > 0)
                    //les += ",";
                //les += less.get(i).toString();
            //}
        //}
    
    
    //---------
    @Override
    public String toString(){
        return "id - " + getId()
                + " name - " + getName()
                + " surname - " + getSurname()
                + " middleName - " + getMiddleName()
                + " telephone  - " + getTel()
                + " date_of_birth - " + getDateOfBirth()
                + " paspN - " + getPaspN()
                + " paspS - " + getPaspS()
                + " degree - " + getDegree()
                + " category - " + getCategory()
                + " specialization - " + getSpecialization();
                
        
    }
    
}
