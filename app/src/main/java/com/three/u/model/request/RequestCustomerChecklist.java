package com.three.u.model.request;

public class RequestCustomerChecklist {

    public RequestCustomerChecklist(int typeOfChecklist) {
        TypeOfChecklist = typeOfChecklist;
    }

    private int TypeOfChecklist = 1;

    public int getTypeOfChecklist() {
        return TypeOfChecklist;
    }

    public void setTypeOfChecklist(int TypeOfChecklist) {
        this.TypeOfChecklist = TypeOfChecklist;
    }
}
