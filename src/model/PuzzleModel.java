/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

/**
 *
 * @author ttn21
 */
public class PuzzleModel {

    private Tile[][] tiles; //a 2D array represents for Puzzle
    private Tile emptyTile;
    private int squareSize;

    public PuzzleModel() {
    }

    public PuzzleModel(int squareSize) {
        this.squareSize = squareSize;
        tiles = new Tile[squareSize][squareSize];
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile getEmptyTile() {
        return emptyTile;
    }

    public void setEmptyTile(Tile emptyTile) {
        this.emptyTile = emptyTile;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public void setSquareSize(int squareSize) {
        this.squareSize = squareSize;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * This method reset Block
     */
    /**
     * This method generate a solved puzzle
     */
    public void generateBlock() {
        for (int row = 0; row < squareSize; row++) {
            for (int col = 0; col < squareSize; col++) {
                tiles[row][col] = new Tile(row, col, "" + (row * squareSize + col + 1));
            } //end for col
        } //end for row
        tiles[squareSize - 1][squareSize - 1].setValue(""); //last tile is empty tile
        emptyTile = new Tile(squareSize - 1, squareSize - 1, "");
    } //end method

    /**
     * Shuffle new generated solved puzzle
     */
    public void shuffleBlock() {
        for (int row = 0; row < squareSize; row++) {
            for (int col = 0; col < squareSize; col++) {
                int newRow = new Random().nextInt(squareSize);
                int newCol = new Random().nextInt(squareSize);
                exchangeTile(row, col, newRow, newCol);
            } //end for col
        } //end for row
    } //end method

    /**
     *
     * @return whether this puzzle can be solved or not
     */
    public boolean isSolvable() {
        int[] tempTile = new int[squareSize * squareSize];
        int index = 0;

        //construc an 1D array contains all tile
        for (int row = 0; row < squareSize; row++) {
            for (int col = 0; col < squareSize; col++) {
                if (tiles[row][col].isEmptyTile()) {
                    tempTile[index++] = 0;
                } else {
                    tempTile[index++] = Integer.parseInt(tiles[row][col].getValue());
                }
            } //end for col
        } //end for row

        //calculate number of inversion
        int inversion = 0;
        for (int i = 0; i < tempTile.length; i++) {
            for (int j = i; j < tempTile.length; j++) {
                if (tempTile[i] > tempTile[j] && tempTile[j] != 0) {
                    inversion++;
                } //end if
            } //end for
        } //end for

        //Case 1: Square size is odd
        if (squareSize % 2 != 0) {
            
            //if the inversions number is even, then it is solvable
            return inversion % 2 == 0;
        } //end if
     
        //Case 2: Square size is even
        //If the sum of inversions and number of row of empty tile
        //is even, then it is solvable
        //plus one because emptyTile.getRow() count from 0
        return (inversion + emptyTile.getRow() + 1) % 2 == 0;
    } //end method

    /**
     *
     * @param firstRow row of first tile
     * @param firstCol column of first tile
     * @param secondRow row of second tile
     * @param secondCol column of second tile Exchange two tile
     */
    public void exchangeTile
        (int firstRow, int firstCol, int secondRow, int secondCol) {
        //first, update emptyTile row and col before switching if neccessary
        if (this.getTile(firstRow, firstCol).isEmptyTile()) {
            emptyTile.setRow(secondRow);
            emptyTile.setCol(secondCol);
        } else if (this.getTile(secondRow, secondCol).isEmptyTile()) {
            emptyTile.setRow(firstRow);
            emptyTile.setCol(firstCol);
        }

        Tile tmp = tiles[firstRow][firstCol];
        tiles[firstRow][firstCol] = tiles[secondRow][secondCol];
        tiles[secondRow][secondCol] = tmp;
    } //end method

    /**
     * @return if current puzzle is solved or not
     */
    public boolean isSolved() {
        for (int row = 0; row < squareSize; row++) {
            for (int col = 0; col < squareSize; col++) {
                if (!tiles[row][col].isOnRightPosition(row, col)) {
                    return false;
                    //if any tile is on wrong position
                    //immediately return false
                } //end if 
            } //end for
        } //end for
        return true;
    } //end method

    /**
     * @param row Row of Tile to be changed
     * @param col Col of Tile to be changed
     * @return whether tile is moved or not
     */
    public boolean moveTile(int row, int col) {
        if (isChangable(row, col, 1, 0) || isChangable(row, col, -1, 0)
                || isChangable(row, col, 0, 1) || isChangable(row, col, 0, -1)) {
            return true;
        } //end if
        return false;
    } //end method

    /**
     * @param row Row of current tile
     * @param col Col of current tile
     * @param rowChange Row shift
     * @param colChange Col shift
     * @return whether neighbor tile is empty and in a block or not. if true,
     * exchange these title
     */
    public boolean isChangable(int row, int col, int rowChange, int colChange) {
        int neighborRow = row + rowChange;
        int neighborCol = col + colChange;
        if (isValidTile(neighborRow, neighborCol) && tiles[neighborRow][neighborCol].isEmptyTile()) {
            exchangeTile(row, col, neighborRow, neighborCol);
            return true;
        } //end if
        return false;
    } //end method

    /**
     * @param row row of tile to check
     * @param col col of tile to check
     * @return Whether a tile row and col is appropriate or not
     */
    public boolean isValidTile(int row, int col) {
        return row >= 0 && row < squareSize && col >= 0 && col < squareSize;
    } //end method

} //end class

