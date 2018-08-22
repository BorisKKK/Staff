/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.classes;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author BORYAN
 */
@Entity(name = "events")
public class Events implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int event_id;
    private String title;
    private String date_of_event;
    private String result;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PUPIL_ID", nullable = false)
    private Pupil pupil;

    public Events() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_of_event() {
        return date_of_event;
    }

    public void setDate_of_event(String date_of_event) {
        this.date_of_event = date_of_event;
    }

    public String getResult() {
        return result;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }
    
    @Override
    public String toString() {
        return "Events{" + "event_id=" + event_id + ", title=" + title + ", date_of_event=" + date_of_event + ", result=" + result + '}';
    }
}
