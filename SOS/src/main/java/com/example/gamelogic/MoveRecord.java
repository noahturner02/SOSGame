package com.example.gamelogic;

public class MoveRecord extends Coordinate {
    // Exactly like the coordinate, but contains the piece. Object for recording a move
    SelectedPiece piece;
    public MoveRecord(int x, int y, SelectedPiece piece) {
        super(x, y);
        this.piece = piece;
    }

    public SelectedPiece getPiece() {
        return piece;
    }
}
