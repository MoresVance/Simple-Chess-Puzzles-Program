import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Piece {
    public BufferedImage image;
    private int color, row, col, x, y;
    private boolean isAlive = true;

    public Piece(int color, int xOffset, int yOffset, int width, int height, int col, int row) {
        this.color = color;
        this.col = col;
        this.row = row;
        x = xOffset + col * width;
        y = yOffset + row * height;
    }

    public void draw(Graphics g, int width, int height) {
        if(isAlive){g.drawImage(image.getScaledInstance(width*4, height*4, Image.SCALE_SMOOTH), x, y, width, height, null);}
    }
    
    // Getters and setters
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
    
}
