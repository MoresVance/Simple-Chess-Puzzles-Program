
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class King extends Piece {

    public King(int color, int xOffset, int yOffset, int width, int height, int col, int row) {
        super(color, xOffset, yOffset, width, height, col, row);

        try {
            if(color == 1) image = ImageIO.read(new File("Piece Images\\W_King.png"));
            else image = ImageIO.read(new File("Piece Images\\B_King.png"));
        } catch(IOException e) {}
    }
   
}