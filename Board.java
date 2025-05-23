import java.awt.*;

public class Board {
    public void draw(Graphics g, int x, int y, int width, int height, Color lightColor, Color darkColor) {
        boolean isBright = true;

        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(isBright) {
                    g.setColor(lightColor);
                    g.fillRect(x + col * width,y + row * height, width, height);

                    isBright = false;
                } else {
                    g.setColor(darkColor);
                    g.fillRect(x + col * width, y + row * height, width, height);

                    isBright = true;
                }
            }

            if(isBright) isBright = false;
            else isBright = true;
        }
    }
    
}