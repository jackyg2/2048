package Game;

import javax.swing.*;
import java.awt.*;


public class Panel extends JPanel {

    private final Tile[][] grid = new Tile[4][4];
    private boolean movement = false;
    private int score = 0;

    /**
     * Constructor.
     * Initially creates two random tiles on the board.
     */
    public Panel(){
        generateTile();
        generateTile();
        setBounds(100,100,400,400);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(null);
    }

    /**
     * This method will generate a tile randomly on the board on an empty square.
     */
    public void generateTile(){
        int number = (int)(Math.random() * 16);
        while(grid[number/4][number%4] != null){
            number = (int)(Math.random() * 16);
        }
        int value = (Math.random() > 0.8) ? 4 : 2;
        grid[number/4][number%4] = new Tile(value);
        grid[number/4][number%4].setBounds((number%4)*100,(number/4)*100,100,100);
        add(grid[number/4][number%4]);
        repaint();
    }

    /**
     * This function checks whether the game has ended due to there being no legal moves
     * @return true if the game is over; false otherwise
     */
    public boolean gameOver(){
        Panel copy = new Panel();
        for (int i = 0; i<4; i++){
            for (int n = 0; n<4; n++){
                copy.grid[i][n] = (grid[i][n] == null) ? null : new Tile(grid[i][n].getValue());
            }
        }

        for (int i = 1; i<5; i++) {
            copy.move(i);
        }
        return !copy.movement;
    }

    /**
     * @return the score
     */
    public int getScore(){ return score; }

    /**
     * @return if a movement was made
     */
    public boolean moved(){
        return movement;
    }

    /**
     * Sets movement back to false
     */
    public void resetMoved(){ movement = false; }

    /**
     * Paints the game
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        super.paint(g2);
        for (int i = 0; i<= 4; i++){
            g2.drawLine(i*100,0,i*100,400);
            g2.drawLine(0,i*100,400,i*100);
        }
    }

    /**
     * Moves the board the direction the player inputted
     * @param direction 1:Left, 2:Up, 3:Right, 4:Down
     */
    public void move(int direction){
        for (int outer = 0; outer<4; outer++){
            moveTiles(direction,outer,(direction <3)?0:3);
            int increment = (direction < 3)? 1: -1;
            for (int inner = (direction < 3)? 0: 3; inner != ((direction < 3)? 3: 0); inner+=increment){
                int row = (direction % 2 == 0) ? inner : outer;
                int column = (direction % 2 == 0) ? outer : inner;
                int nextRow = (direction % 2 == 0) ? row+increment : row;
                int nextColumn = (direction % 2 == 0) ? column : column+increment;
                if (grid[row][column] != null && grid[nextRow][nextColumn] != null &&
                        grid[row][column].getValue() == grid[nextRow][nextColumn].getValue()) {
                    grid[row][column].upgrade();
                    score+=grid[row][column].getValue();
                    remove(grid[nextRow][nextColumn]);
                    grid[nextRow][nextColumn] = null;
                    moveTiles(direction, outer, inner+increment);
                    movement = true;
                }
            }
        }
    }

    /**
    public void moveAllLeft() {
        for (int row = 0; row<4; row++) { // checks each row
            //moveTilesLeft(row, 0);
            moveTiles(1, row, 0);
            for (int column = 0; column < 3; column++) { // checks each column
                if (grid[row][column] != null && grid[row][column+1] != null &&
                        grid[row][column].getValue() == grid[row][column + 1].getValue()) {
                    grid[row][column].upgrade();
                    remove(grid[row][column+1].getImage());
                    grid[row][column+1] = null;
                    moveTilesLeft(row,column+1);
                    //moveTiles(1, row, column+1);
                    movement = true;
                }
            }
        }
    }


    public void moveAllUp(){
        for (int column = 0; column<4; column++) { // checks each column
            //moveTilesUp(column, 0);
            moveTiles(2, column, 0);
            for (int row = 0; row < 3; row++) { // checks each column
                if (grid[row][column] != null && grid[row+1][column] != null &&
                        grid[row][column].getValue() == grid[row+1][column].getValue()) {
                    grid[row][column].upgrade();
                    remove(grid[row+1][column].getImage());
                    grid[row+1][column] = null;
                    //moveTilesUp(column,row+1);
                    moveTiles(2, column, row+1);
                    movement = true;
                }
            }
        }
    }

    public void moveAllRight(){
        for (int row = 0; row<4; row++) { // checks each row
            //moveTilesRight(row, 3);
            moveTiles(3, row, 3);
            for (int column = 3; column > 0; column--) { // checks each column
                if (grid[row][column] != null && grid[row][column-1] != null &&
                        grid[row][column].getValue() == grid[row][column-1].getValue()) {
                    grid[row][column].upgrade();
                    remove(grid[row][column-1].getImage());
                    grid[row][column-1] = null;
                    //moveTilesRight(row,column-1);
                    moveTiles(3, row, column-1);
                    movement = true;
                }
            }
        }
    }

    public void moveAllDown(){
        for (int column = 0; column<4; column++) { // checks each column
            moveTiles(4,column, 3);
            //moveTilesDown(column,3);
            for (int row = 3; row > 0; row--) { // checks each row
                if (grid[row][column] != null && grid[row-1][column] != null &&
                        grid[row][column].getValue() == grid[row-1][column].getValue()) {
                    grid[row][column].upgrade();
                    remove(grid[row-1][column].getImage());
                    grid[row-1][column] = null;
                    moveTiles(4,column,row-1);
                    //moveTilesDown(column,row-1);
                    movement = true;
                }
            }
        }
    }




    public void moveTilesLeft(int row, int columnStart){
        for (int column = columnStart; column < 3; column++) { // checks each column
            if (grid[row][column] == null){ // if column is null
                for (int t = column+1; t<4; t++) { // check the following column if they have a tile
                    if (grid[row][t] != null){ // if there is a tile, move it to the null column.
                        System.out.println("Moving ("+row+", "+t+") to ("+row+", "+column+")");
                        grid[row][column] = grid[row][t];
                        grid[row][t] = null;
                        grid[row][column].getImage().setBounds(column*100,row*100,100,100);
                        movement = true;
                        break;
                    }
                }
            }
        }
    }

    public void moveTilesUp(int column, int rowStart){
        for (int row = rowStart; row < 3; row++) { // checks each column
            if (grid[row][column] == null){ // if column is null
                for (int t = row+1; t<4; t++) { // check the following column if they have a tile
                    if (grid[t][column] != null){ // if there is a tile, move it to the null column.
                        grid[row][column] = grid[t][column];
                        grid[t][column] = null;
                        grid[row][column].getImage().setBounds(column*100,row*100,100,100);
                        movement = true;
                        break;
                    }
                }
            }
        }
    }

    public void moveTilesDown(int column, int rowStart){
        for (int row = rowStart; row > 0; row--) { // checks each column
            if (grid[row][column] == null){ // if column is null
                for (int t = row-1; t>-1; t--) { // check the following column if they have a tile
                    if (grid[t][column] != null){ // if there is a tile, move it to the null column.
                        grid[row][column] = grid[t][column];
                        grid[t][column] = null;
                        grid[row][column].getImage().setBounds(column*100,row*100,100,100);
                        movement = true;
                        break;
                    }
                }
            }
        }
    }

    public void moveTilesRight(int row, int columnStart){
        for (int column = columnStart; column > 0; column--) { // checks each column
            if (grid[row][column] == null){ // if column is null
                for (int t = column-1; t>-1; t--) { // check the following column if they have a tile
                    if (grid[row][t] != null){ // if there is a tile, move it to the null column.
                        grid[row][column] = grid[row][t];
                        grid[row][t] = null;
                        grid[row][column].getImage().setBounds(column*100,row*100,100,100);
                        movement = true;
                        break;
                    }
                }
            }
        }
    }*/


    /**
     *
     * @param direction The direction that the tiles are being moved. 1:Left, 2:Up, 3:Right, 4:Down
     * @param constantIndex the index of the row/column that is being moved
     * @param startIndex the starting index of the movement
     */
    public void moveTiles(int direction, int constantIndex, int startIndex){
        int increment = (direction < 3) ? 1 : -1;
        // checkIndex < 3 for up and left, 0,1,2
        // checkIndex > 0 for down and right, 3,2,1
        for (int checkIndex = startIndex; checkIndex != ((direction < 3) ? 3 : 0); checkIndex+=increment) { // checks each row/file
            // if going left/right, the row is constant
            int row = (direction % 2 == 0) ? checkIndex : constantIndex;
            // if going up/down, the column is constant
            int column = (direction % 2 == 0) ? constantIndex : checkIndex;

            if (grid[row][column] == null){ // if tile is null
                for (int t = checkIndex+increment; t>-1 && t<4; t+=increment) { // check the following tile if they have a tile
                    int checkRow = (direction % 2 == 0) ? t : row;
                    int checkColumn = (direction % 2 == 0) ? column : t;
                    if (grid[checkRow][checkColumn] != null){ // if there is a tile, move it to the null position.
                        grid[row][column] = grid[checkRow][checkColumn];
                        grid[checkRow][checkColumn] = null;
                        grid[row][column].setBounds(column*100,row*100,100,100);
                        movement = true;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Prints the board to the console.
     */
    public void printBoard(){
        for (Tile[] row : grid){
            for (Tile index : row){
                if (index != null) {
                    System.out.print(index.getValue() + "          ");
                }else{
                    System.out.print(0 + "          ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
