package Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Game extends JFrame implements KeyListener, ActionListener{

    private Panel theGame = new Panel();
    private final JLabel score = new JLabel("Score: 0");
    private final JButton reset = new JButton("New Game");
    private final JLabel best = new JLabel("Best: 0");

    private int bestScore = 0;

    public Game(){
        super("2048");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        score.setBounds(100,50,300,50);
        score.setFont(score.getFont().deriveFont(20f));
        add(score);

        best.setBounds(100,25,300,50);
        best.setFont(best.getFont().deriveFont(20f));
        add(best);

        reset.setBounds(400,20,100,25);
        reset.setFocusable(false);
        reset.addActionListener(this);

        add(theGame);

        addKeyListener(this);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }

    public void endGame(){
        score.setText("Game Over! Final Score: "+theGame.getScore());
        removeKeyListener(this);
        add(reset);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource() == reset);
        if (e.getSource() == reset){
            remove(theGame);
            theGame = new Panel();
            add(theGame);
            score.setText("Score: "+theGame.getScore());
            addKeyListener(this);
            remove(reset);

            repaint();
        }
    }

    /**
     * Called whenever a key is released
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37 -> theGame.move(1);
            case 38 -> theGame.move(2);
            case 39 -> theGame.move(3);
            case 40 -> theGame.move(4);
        }
        score.setText("Score: "+theGame.getScore());
        if (theGame.getScore() > bestScore){
            bestScore = theGame.getScore();
            best.setText("Best: "+bestScore);
        }
        if (theGame.moved()){
            theGame.generateTile();
            theGame.resetMoved();
            if(theGame.gameOver()){
                endGame();
            }
        }
        //theGame.printBoard();
    }

    public void keyTyped(KeyEvent e) { }
    public void keyPressed(KeyEvent e) {}

}
