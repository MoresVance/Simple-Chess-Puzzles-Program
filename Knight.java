import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

public class Knight extends Piece {
    public Knight(int color, int xOffset, int yOffset, int width, int height, int col, int row) {
        super(color, xOffset, yOffset, width, height, col, row);

        try {
            if(color == 1) image = ImageIO.read(new File("Piece Images\\W_Knight.png"));
            else image = ImageIO.read(new File("Piece Images\\B_Knight.png"));
        } catch(IOException e) {}
    }
    
}
