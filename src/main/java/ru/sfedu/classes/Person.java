/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.classes;

import com.google.gson.annotations.Expose;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author BORYAN
 */
@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false, nullable=false)
    
    private int id;
    
    private String name;
    
    private String surname;
    
    private String middleName;
    
    private String dateOfBirth;
    
    private String tel;
    
    private String paspS;
    
    private String paspN;
    
    @Embedded
    private Adress adress;

    public Person() {
    }

    public Person(int id, String name, String surname, String sex, String dateOfBirth, int age, String tel, String paspS, String paspN, Adress adress) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.tel = tel;
        this.paspS = paspS;
        this.paspN = paspN;
        this.adress = adress;
    }
    
  

    public Adress getAdress() {
        return adress;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }



    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    
     public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
     

    public String getMiddleName() {
        return middleName;
    }


    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getTel() {
        return tel;
    }

    public String getPaspS() {
        return paspS;
    }

    public String getPaspN() {
        return paspN;
    }

    public void setPaspS(String paspS) {
        this.paspS = paspS;
    }

    public void setPaspN(String paspN) {
        this.paspN = paspN;
    }
    
    
}
