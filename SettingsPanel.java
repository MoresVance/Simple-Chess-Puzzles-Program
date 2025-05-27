import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel implements ActionListener{
    private CardLayout cLayout;
    private JPanel cardPanel;

    private JButton setPuzzleTheme, resetProgress, back;
    private JLabel numSolved, numPartialSolved, numUnsolved, progress;

    public SettingsPanel(CardLayout cLayout, JPanel cardPanel){
        this.cLayout = cLayout;
        this.cardPanel = cardPanel;    

        setLayout(new GridBagLayout());
        setPuzzleTheme = new JButton("Puzzle Themes");
        resetProgress = new JButton("Reset Progress");
        back = new JButton ("Back");
        numSolved = new JLabel("Solved Puzzles: ");
        numPartialSolved = new JLabel("Partially Solved Puzzles: ");
        numUnsolved = new JLabel("Unsolved Puzzles: ");
        progress = new JLabel("Progress: ");

        setPuzzleTheme.addActionListener(this);
        resetProgress.addActionListener(this);
        back.addActionListener(this);

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 20, 20, 20);
        c.ipadx = 20;
        add(progress, c);
        c.gridx = 0;
        c.gridy = 1;
        add(numSolved, c);
        c.gridx = 1;
        c.gridy = 1;
        add(numPartialSolved, c);
        c.gridx = 2;
        c.gridy = 1;
        add(numUnsolved, c);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 90;
        c.ipady = 20;
        c.insets = new Insets(80, 0, 20, 0);
        add(setPuzzleTheme, c);
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(20, 0, 20, 0);
        add(resetProgress, c);
        c.gridx = 1;
        c.gridy = 4;
        c.ipadx = 155;
        add(back, c);
        
    }

    public void setProgress(int numSolved, int numPartialSolved, int numUnsolved){
        this.numSolved.setText("Solved Puzzles: " + numSolved);
        this.numPartialSolved.setText("Partially Solved Puzzles: " + numPartialSolved);
        this.numUnsolved.setText("Unsolved Puzzles: " + (numUnsolved + 687));
        double percentage = (float) numSolved/1002;
        System.out.println(percentage);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);

        progress.setText("Progress: " + df.format(percentage) + "%");

        revalidate();
        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            cLayout.show(cardPanel, "Main");
        }
    }
}
