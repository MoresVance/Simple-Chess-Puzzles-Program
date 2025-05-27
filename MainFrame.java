import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;



public class MainFrame extends JFrame{
    private CardLayout cLayout;
    private JPanel cardPanel;

    private MainPanel mainPanel;
    private SettingsPanel settingsPanel;

    public MainFrame(HashMap<Integer, FEN> fenHashMap) throws IOException{
        setTitle("1001 Winning Chess Sacrifices and Combinations");
        setSize(900, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        File logoFile = new File("App_logo.png");
        BufferedImage logo = ImageIO.read(logoFile);
        if(logo != null){
            setIconImage(logo); 
            System.out.println("Successfully loaded in program icon!");
        } else{ System.out.println("URL is null. Failed to load program icon."); }

        cLayout = new CardLayout();
        cardPanel = new JPanel(cLayout);

        settingsPanel = new SettingsPanel(cLayout, cardPanel);
        mainPanel = new MainPanel(fenHashMap, cLayout, cardPanel, settingsPanel);
        
        cardPanel.add(mainPanel, "Main");
        cardPanel.add(settingsPanel, "Settings");

        add(cardPanel);
        
        setVisible(true);


    }

    
}
