/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.staff;

/**
 *
 * @author BORYAN
 */
public enum StatusResponse {
    SUCCESS ("Success"),
    ERROR ("Error");
  
    private String status;       

    private StatusResponse(String status) {
        this.status = status;
    }

    public static StatusResponse getSUCCESS() {
        return SUCCESS;
    }

    public static StatusResponse getERROR() {
        return ERROR;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
}