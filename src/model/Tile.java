/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ttn21
 */
public class Tile {
    private int row;
    private int col;
    private String value;

    public Tile(int row, int col, String value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public String getValue() {
        return value;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public boolean isEmptyTile() {
        return this.value.isEmpty();
    }
    
    public boolean isOnRightPosition(int row, int col) {
        return this.row == row && this.col == col;
    }

}
