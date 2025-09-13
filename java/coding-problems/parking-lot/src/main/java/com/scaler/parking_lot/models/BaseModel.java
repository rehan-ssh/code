package com.scaler.parking_lot.models;

import java.util.Date;

public class BaseModel {
    private long id;
    private Date createdAt;
    private Date updatedAt;


    public long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setId(long id) {
        this.id = id;
    }
}
