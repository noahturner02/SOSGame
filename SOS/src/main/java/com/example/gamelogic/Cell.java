package com.example.gamelogic;

public class Cell {
    cellStatus status;
    Cell(cellStatus status) {
        this.status = status;
    }
    public cellStatus getStatus() {
        return status;
    }
}
