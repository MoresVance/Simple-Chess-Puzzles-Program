import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;


public class MainFrame extends JFrame implements ActionListener{
    private Board board = new Board();
    private JLabel turn, fenString, puzzleNum;
    private JButton generateRandomPuzzle, getFenString, getSolution;
    private HashMap<Integer, FEN> fenHashMap;
    private FEN currentFen;

    public MainFrame(HashMap<Integer, FEN> fenHashMap){
        this.fenHashMap = fenHashMap;
        setTitle("1001 Winning Chess Sacrifices and Combinations");
        setSize(900, 800);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        turn = new JLabel("Whose Turn");
        fenString = new JLabel("FEN String");
        puzzleNum = new JLabel(" Puzzle Number: ");
        generateRandomPuzzle = new JButton("Generate Random Puzzle");
        getFenString = new JButton("Copy FEN to Clipboard");
        getSolution = new JButton("Get Solution");

        generateRandomPuzzle.addActionListener(this);
        getFenString.addActionListener(this);
        getSolution.addActionListener(this);
        
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
        c.ipady = 20;
        c.insets = new Insets(20, 50, 0, 50);
        add(generateRandomPuzzle, c);
        c.gridx = 1;
        c.gridy = 3;
        c.ipadx = 50;
        c.ipady = 20;
        c.insets = new Insets(20, 50, 0, 50);
        add(getSolution, c);
        c.gridx = 2;
        c.gridy = 3;
        c.ipadx = 50;
        c.ipady = 20;
        c.insets = new Insets(20, 50, 0, 50);
        add(getFenString, c);

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
            int rand = (int)(Math.random() * 315) + 1;
            currentFen = fenHashMap.get(rand);
            currentFen.printDesc();
            fenString.setText(currentFen.getFullFENString());
            turn.setText(currentFen.whoseTurn());
            puzzleNum.setText("Puzzle Number: " + currentFen.getNum());
            
            
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
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = 65, height = 65, xOffset = (getWidth() - width*8)/2, yOffset = (getHeight() - height*8)/2;
        board.draw(g, xOffset, yOffset, width, height, new Color(0xfcf1c2), new Color(0xb0875f));
        
        if(currentFen != null){
            drawPosition(currentFen, g, xOffset, yOffset, width, height);
        }
        
    }
}
