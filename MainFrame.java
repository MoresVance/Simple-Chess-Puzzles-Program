import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;


public class MainFrame extends JFrame implements ActionListener{
    private Board board = new Board();
    private JLabel turn, fenString, puzzleNum, puzzleStatus;
    private JButton generateRandomPuzzle, getFenString, getSolution, trackSolution, settings;
    private HashMap<Integer, FEN> fenHashMap;
    private FEN currentFen;
    private int c = 0;

    public MainFrame(HashMap<Integer, FEN> fenHashMap) throws IOException{
        this.fenHashMap = fenHashMap;
        setTitle("1001 Winning Chess Sacrifices and Combinations");
        setSize(900, 800);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        File logoFile = new File("App_logo.png");
        BufferedImage logo = ImageIO.read(logoFile);
        if(logo != null){
            setIconImage(logo); 
            System.out.println("Successfully loaded in program icon!");
        } else{ System.out.println("URL is null. Failed to load program icon."); }

        turn = new JLabel("Whose Turn");
        fenString = new JLabel("FEN String");
        puzzleNum = new JLabel(" Puzzle Number: ");
        generateRandomPuzzle = new JButton("Generate Random Puzzle");
        getFenString = new JButton("Copy FEN to Clipboard");
        getSolution = new JButton("Get Solution");
        trackSolution = new JButton("Track Solution");
        settings = new JButton("Settings");
        puzzleStatus = new JLabel("Puzzle Status");

        generateRandomPuzzle.addActionListener(this);
        getFenString.addActionListener(this);
        getSolution.addActionListener(this);
        trackSolution.addActionListener(this);
        settings.addActionListener(this);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 50, 0, 50);
        add(puzzleNum, c);
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(20, 50, 20, 50);
        add(turn, c);
        c.gridx = 1;
        c.gridy = 1;
        c.ipady = 500;
        add(new JPanel(), c);
        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = 50;
        c.ipady = 15;
        c.insets = new Insets(20, 50, 0, 50);
        add(generateRandomPuzzle, c);
        c.gridx = 1;
        c.gridy = 3;
        c.ipadx = 50;
        c.ipady = 15;
        c.insets = new Insets(20, 50, 0, 50);
        add(getSolution, c);
        c.gridx = 2;
        c.gridy = 3;
        c.ipadx = 50;
        c.ipady = 15;
        c.insets = new Insets(20, 50, 0, 50);
        add(getFenString, c);
        c.gridx = 0;
        c.gridy = 4;
        c.ipadx = 112;
        c.ipady = 15;
        add(trackSolution, c);
        c.gridx = 2;
        c.gridy = 4;
        c.ipadx = 132;
        c.ipady = 15;
        add(settings, c);
        c.gridx = 1;
        c.gridy = 4;
        c.ipadx = 0;
        c.insets = new Insets(20, 50, 10, 50);
        add(puzzleStatus, c);

        setVisible(true);


    }

    public void drawPosition(FEN fen, Graphics g, int xOffset, int yOffset, int width, int height){
        String[] fenRows = fen.getFenString().split("/");
        int col, count;
        for(int row = 0; row < 8; row++){
            col = 0;
            count = 0;
            while(true){
                if(col > 7 || count > fenRows[row].length()){
                    break;
                }
                char pieceChar = fenRows[row].charAt(count);
                if(Character.isDigit(pieceChar)){
                    col += Character.getNumericValue(pieceChar);
                } else {
                    int color = Character.isUpperCase(pieceChar) ? 1 : 0;
                    switch (Character.toLowerCase(pieceChar)) {
                        case 'p':
                            new Pawn(color, xOffset, yOffset, width, height, col, row).draw(g, width, height);
                            break;
                        case 'r':
                            new Rook(color, xOffset, yOffset, width, height, col, row).draw(g, width, height);
                            break;
                        case 'n':
                            new Knight(color, xOffset, yOffset, width, height, col, row).draw(g, width, height);
                            break;
                        case 'b':
                            new Bishop(color, xOffset, yOffset, width, height, col, row).draw(g, width, height);
                            break;
                        case 'q':
                            new Queen(color, xOffset, yOffset, width, height, col, row).draw(g, width, height);
                            break;
                        case 'k':
                            new King(color, xOffset, yOffset, width, height, col, row).draw(g, width, height);
                            break;
                    }
                    col++;
                }
                count++;
            }
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == generateRandomPuzzle){
            while(true){
                int rand = (int)(Math.random() * 315) + 1;
                if(fenHashMap.get(rand).ifSolved() != 1){
                    currentFen = fenHashMap.get(rand);
                    break;
                } 
            }
            
            currentFen.printDesc();
            fenString.setText(currentFen.getFullFENString());
            turn.setText(currentFen.whoseTurn());
            puzzleNum.setText("Puzzle Number: " + currentFen.getNum());
            puzzleStatus.setText(currentFen.getStatus());
            
            revalidate();
            repaint();
        }

        if(e.getSource() == getFenString){
            String myString = fenString.getText();
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
        if(e.getSource() == getSolution){
            JOptionPane.showMessageDialog(this, currentFen.getSolution(), "Solution for Puzzle " + currentFen.getNum(), JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getSource() == trackSolution){
            String[] options = {"Solved", "Partially Solved", "Not Solved"};
            String selectedOption = JOptionPane.showInputDialog(this, "Puzzle " + currentFen.getNum(), "Track your Solution!", JOptionPane.PLAIN_MESSAGE, null, options, options[0]).toString();
            if(selectedOption.equals("Solved")){
                currentFen.setIfSolved(1);
            } else if(selectedOption.equals("Partially Solved")){
                currentFen.setIfSolved(0.5);
            } else if(selectedOption.equals("Not Solved")){
                currentFen.setIfSolved(0);
            }
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = 65, height = 65, xOffset = (getWidth() - width*8)/2, yOffset = (getHeight() - height*8)/2 - 30;
        board.draw(g, xOffset, yOffset, width, height, new Color(0xfcf1c2), new Color(0xb0875f));
        
        if(currentFen != null){
            drawPosition(currentFen, g, xOffset, yOffset, width, height);
        }
        
    }
}
