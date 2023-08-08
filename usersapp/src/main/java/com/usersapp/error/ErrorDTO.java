package com.usersapp.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public class ErrorDTO {

    private Date stamp;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String applicationName;

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public ErrorDTO stamp(Date stamp) {
        this.stamp = stamp;
        return this;
    }

    public ErrorDTO applicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public ErrorDTO message(String message) {
        this.message = message;
        return this;
    }
}
