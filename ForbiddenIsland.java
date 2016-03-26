import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.ArrayList;

// A cell of land.
class Cell {
    static final int CELL_SIZE = 20;
    
    double height;
    int x, y;
    Cell left, top, right, bottom;
    boolean isFlooded, hasPart;
    
    Cell(double height, int x, int y) {
        this.height = height;
        this.x = x;
        this.y = y;
        this.isFlooded = false;
        this.hasPart = false;
    }
    
    // EFFECT: Sets the flooded state of this cell to the given boolean.
    void setFlooded(boolean isFlooded) {
        this.isFlooded = isFlooded;
    }
    
    // compares the height of this cell to the height of that cell.
    //  Returns: 0  if height of this and that are equal.
    //           <0 if height of this is less than height of that.
    //           >0 if height of this is greater than height of that.
    double compareHeight(Cell that) {
        return this.height - that.height;
    }
    
    // Draws this cell onto the given background.
    WorldImage drawOnto(WorldImage background) {
        WorldImage cell;
        if (this.isFlooded) {
            cell = new RectangleImage(Cell.CELL_SIZE, Cell.CELL_SIZE, OutlineMode.SOLID,
                    new Color(0x0000FF));
        }
        else {
            cell = new RectangleImage(Cell.CELL_SIZE, Cell.CELL_SIZE, OutlineMode.SOLID,
                    new Color(0x00FF00));
        }
        return new OverlayImage(cell, background);
    }
}


// A cell in the ocean.
class OceanCell extends Cell {
    
    OceanCell(int x, int y) {
        super(0, x, y);
        this.isFlooded = true;
    }
    
    @Override
    // EFFECT: Sets the flooded state of this ocean cell, which is always true.
    void setFlooded(boolean isFlooded) {}
    
    @Override
    // compares the height of this ocean cell to the height of that cell.
    //  Returns: the height of that cell, which is
    //           0  if height of that is even with ocean level.
    //           <0 if height of that is below ocean level.
    //           >0 if height of that is above ocean level.
    double compareHeight(Cell that) {
        return that.height;
    }
    
    @Override
    // Draws this ocean cell onto the given background.
    WorldImage drawOnto(WorldImage background) {
        return new OverlayImage(new RectangleImage(Cell.CELL_SIZE, Cell.CELL_SIZE,
                OutlineMode.SOLID, Color.BLUE), background);
    }
}



// A board representing an island.
class Board {
    ArrayList<ArrayList<Cell>> cells;
    
    Board() {
        this.makeMountainBoard();
    }
    
    void makeMountainBoard() {
        ArrayList<ArrayList<Double>> hieghts = new ArrayList<ArrayList<Double>>();
        double middle = ForbiddenIsland.ISLAND_SIZE / 2.0;
        
        for (int i = 0; i < ForbiddenIsland.ISLAND_SIZE; i++) {
            hieghts.add(new ArrayList<Double>());
            for (int j = 0; j < ForbiddenIsland.ISLAND_SIZE; j++) {
                
            }
        }
    }
    
    ArrayList<ArrayList<Cell>> doubleListToCellList(ArrayList<ArrayList<Double>> heights) {
        ArrayList<ArrayList<Cell>> result = new ArrayList<ArrayList<Cell>>();
        
        for (int i = 0; i < ForbiddenIsland.ISLAND_SIZE; i++) {
            result.add(new ArrayList<Cell>());
            for (int j = 0; j < ForbiddenIsland.ISLAND_SIZE; j++) {
                result.get(i).add(new Cell(heights.get(i).get(j), i, j));
            }
        }
        
        return result;
    }
    
    void updateBoard(int waterHeight) {
        for (ArrayList<Cell> row : cells) {
            for (Cell cell : row) {
                cell.update(waterHeight);
            }
        }
    }
}


class ForbiddenIsland {
    static final int ISLAND_SIZE = 64;
}