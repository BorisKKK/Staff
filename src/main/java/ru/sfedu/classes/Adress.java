/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.classes;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author BORYAN
 */
@Embeddable
public class Adress {
    //@Column(name = "street")
    private String street;
    //@Column(name = "house")
    private String house;
    //@Column(name = "room")
    private String room;
    
    public Adress() {
    }

    public Adress(String street, String house, String room) {
        this.street = street;
        this.house = house;
        this.room = room;
    }
    
    
    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getRoom() {
        return room;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Adress{" + "street= " + street + ", house= " + house + ", room= " + room + '}';
    }
    
}
