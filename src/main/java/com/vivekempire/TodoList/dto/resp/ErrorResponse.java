package com.vivekempire.TodoList.dto.resp;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorResponse {
    private String messg;
    private HttpStatus status;
    private Date timestamp;

    public ErrorResponse(String messg, HttpStatus status, Date timestamp) {
        this.messg = messg;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessg() {
        return messg;
    }

    public void setMessg(String messg) {
        this.messg = messg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
