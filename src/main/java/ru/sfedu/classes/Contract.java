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
@Entity(name = "contract")
public class Contract implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contract_id;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PUPIL_ID", nullable = false)
    private Pupil pupil;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="LESSON_ID", nullable = false)
    private Lessons lesson;
    private int termOfImplementation;
    private int groupNumber;

    public int getContract_id() {
        return contract_id;
    }

    public Contract() {
    }

    public Pupil getPupil() {
        return pupil;
    }

    public Lessons getLesson() {
        return lesson;
    }

    public int getTermOfImplementation() {
        return termOfImplementation;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }

    public void setLesson(Lessons lesson) {
        this.lesson = lesson;
    }

    public void setTermOfImplementation(int term) {
        this.termOfImplementation = term;
    }

    public void setGroupNumber(int group) {
        this.groupNumber = group;
    }
    
    @Override
    public String toString() {
        return "Contract{" + "pupil=" + pupil + ", lesson=" + lesson + ", term=" + termOfImplementation + ", group=" + groupNumber + '}';
    }
}
