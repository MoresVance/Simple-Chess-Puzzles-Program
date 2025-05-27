import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MainPanel extends JPanel implements ActionListener{
    private Board board = new Board();
    private JLabel turn, fenString, puzzleNum, puzzleStatus;
    private JButton generateRandomPuzzle, getFenString, getSolution, trackSolution, settings;
    private HashMap<Integer, FEN> fenHashMap;
    private FEN currentFen;
    private JPanel cardPanel;
    private CardLayout cLayout;
    private SettingsPanel settingsPanel;
    private int numSolved = 0, numPartialSolved = 0, numUnsolved = 0;

    public MainPanel(HashMap<Integer, FEN> fenHashMap, CardLayout cLayout, JPanel cardPanel, SettingsPanel settingsPanel) throws IOException{
        this.fenHashMap = fenHashMap;
        this.cLayout = cLayout;
        this.cardPanel = cardPanel;
        this.settingsPanel = settingsPanel;

        fenHashMap.forEach((k, v) -> {
            if(v.ifSolved() == 1){
                numSolved++;
            } else if(v.ifSolved() == 0.5){
                numPartialSolved++;
            } else if(v.ifSolved() == 0){
                numUnsolved++;
            }
        });

        setLayout(new GridBagLayout());
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
                numSolved++;
            } else if(selectedOption.equals("Partially Solved")){
                currentFen.setIfSolved(0.5);
                numPartialSolved++;
            } else if(selectedOption.equals("Not Solved")){
                currentFen.setIfSolved(0);
                numUnsolved++;
            }
        }
        if(e.getSource() == settings){
            settingsPanel.setProgress(numSolved, numPartialSolved, numUnsolved);
            cLayout.show(cardPanel, "Settings");
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
