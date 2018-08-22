/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.staff;

import java.util.List;
import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;

/**
 *
 * @author BORYAN
 */
public class Result <T> {
    private EnumOfStatuses status;
    private List<T> data;
    private List<String> message;
    private static Logger log = Logger.getLogger(StaffClient.class);
    
    public Result() {
        
    }
    
    public Result(EnumOfStatuses status, List<T> data, List<String> messages) {
        this.status = status;
        this.data = data;
        this.message = messages;
        log.info(message);
    }

    
    public EnumOfStatuses getStatus() {
        return status;
    }

    public void setStatus(EnumOfStatuses status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<String> getMessages() {
        return message;
    }

    public void setMessages(List<String> messages) {
        this.message = messages;
    }
    
    @Override
    public String toString() {
        return "Result{" + "status= " + status + ", data= " + data + ", message= " + message + '}';
    }
}
