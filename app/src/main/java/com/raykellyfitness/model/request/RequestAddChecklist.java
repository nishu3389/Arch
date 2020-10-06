package com.raykellyfitness.model.request;

public class RequestAddChecklist {

    public RequestAddChecklist() {
    }

    public RequestAddChecklist(int id) {
        Id = id;
    }

    /**
     * TypeOfChecklist : 1
     * Name : My Checklist 2
     * MoveInDate : 30/Jul/2020
     * MoveOutDate : 02/Aug/2020
     * TypeOfMove : 1
     */




    private int TypeOfChecklist = 1;
    private String ChecklistName ;
    private String MoveInDate;
    private String MoveOutDate;
    private int MovingType = 0;
    private int Id = 0;

    public String getChecklistName() {
        return ChecklistName;
    }

    public void setChecklistName(String checklistName) {
        ChecklistName = checklistName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTypeOfChecklist() {
        return TypeOfChecklist;
    }

    public void setTypeOfChecklist(int TypeOfChecklist) {
        this.TypeOfChecklist = TypeOfChecklist;
    }


    public String getMoveInDate() {
        return MoveInDate;
    }

    public void setMoveInDate(String MoveInDate) {
        this.MoveInDate = MoveInDate;
    }

    public String getMoveOutDate() {
        return MoveOutDate;
    }

    public void setMoveOutDate(String MoveOutDate) {
        this.MoveOutDate = MoveOutDate;
    }

    public int getTypeOfMove() {
        return MovingType;
    }

    public void setTypeOfMove(int TypeOfMove) {
        this.MovingType = TypeOfMove;
    }
}
