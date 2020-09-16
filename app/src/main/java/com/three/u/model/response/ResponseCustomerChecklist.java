package com.three.u.model.response;

import java.io.Serializable;

public class ResponseCustomerChecklist implements Serializable {

    public ResponseCustomerChecklist() {
    }

    public ResponseCustomerChecklist(int checklistType) {
        this.checklistType = checklistType;
    }

    private int id;
    private int checklistType;
    private boolean isPrimaryUser = true;
    private int movingType;
    private String checklistName;
    private String movingDate;
    private String moveOutDate = "";
    private double completionPercentage;

    public boolean isPrimaryUser() {
        return isPrimaryUser;
    }

    public void setPrimaryUser(boolean primaryUser) {
        isPrimaryUser = primaryUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChecklistType() {
        return checklistType;
    }

    public void setChecklistType(int checklistType) {
        this.checklistType = checklistType;
    }

    public int getMovingType() {
        return movingType;
    }

    public void setMovingType(int movingType) {
        this.movingType = movingType;
    }

    public String getChecklistName() {
        return checklistName;
    }

    public void setChecklistName(String checklistName) {
        this.checklistName = checklistName;
    }

    public String getMovingDate() {
        return movingDate;
    }

    public void setMovingDate(String movingDate) {
        this.movingDate = movingDate;
    }

    public String getMoveOutDate() {
        return moveOutDate;
    }

    public void setMoveOutDate(String moveOutDate) {
        this.moveOutDate = moveOutDate;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}
