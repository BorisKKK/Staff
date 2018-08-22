/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.staff;

import com.google.gson.JsonElement;

/**
 *
 * @author BORYAN
 */
public class StandardResponse {
    
    private EnumOfStatuses status;
    private String message;
    private JsonElement data;
    
    
    public StandardResponse(EnumOfStatuses status) {
        
    }
    public StandardResponse(EnumOfStatuses status, String message) {
       
    }
    public StandardResponse(EnumOfStatuses status, JsonElement data) {
        
    }

    public void setStatus(EnumOfStatuses statatus) {
        this.status = statatus;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public EnumOfStatuses getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public JsonElement getData() {
        return data;
    }
}
