import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Queen extends Piece {
    public Queen(int color, int xOffset, int yOffset, int width, int height, int col, int row) {
        super(color, xOffset, yOffset, width, height, col, row);

        try {
            if(color == 1) image = ImageIO.read(new File("Piece Images\\W_Queen.png"));
            else image = ImageIO.read(new File("Piece Images\\B_Queen.png"));
        } catch(IOException e) {}
    }
}
