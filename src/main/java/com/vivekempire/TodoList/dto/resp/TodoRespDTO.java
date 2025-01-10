package com.vivekempire.TodoList.dto.resp;

public class TodoRespDTO {
    private  String mssg;
    private boolean status;

    public String getMssg() {
        return mssg;
    }

    public void setMssg(String mssg) {
        this.mssg = mssg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TodoRespDTO(String mssg, boolean status) {
        this.mssg = mssg;
        this.status = status;
    }
}
