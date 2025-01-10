package com.vivekempire.TodoList.dto.resp;

public class MssgRespDTO {

    private String mssg;
    private boolean status;
    private String access_token;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getMssg() {
        return mssg;
    }

    public void setMssg(String mssg) {
        this.mssg = mssg;
    }

    public MssgRespDTO(String mssg,boolean status) {
        this.mssg = mssg;
        this.status=status;
    }
    public MssgRespDTO(String mssg,boolean status,String access_token) {
        this.mssg = mssg;
        this.status=status;
        this.access_token=access_token;
    }
}
