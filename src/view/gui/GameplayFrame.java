package view.gui;

import model.questions.Category;
import model.questions.Difficulty;
import javax.swing.*;
import java.awt.*;

/**
 * Abstract class that contains several default implementations for methods of the {@code GUI} abstract class.
 * @author Tasos Papadopoulos
 * @author Thodwrhs Myridis
 * @version 12.1.2021
 */
public abstract class GameplayFrame extends GUI {
    protected JLabel gamemode;
    protected JLabel questionTextLabel;
    protected JLabel categoryLabel;
    protected JLabel roundLabel;
    protected boolean hasTimer;
    protected int count = 10000;
    protected final Timer timer;
    protected JLabel timerLabel;
    protected JLabel difficultyLabel;
    protected JLabel questionsImageLabel;

    /**
     * Default constructor. Creates general gameplay frame.
     */
    public GameplayFrame() /*throws HeadlessException*/ {
        super();
        gamemode = UtilGUI.getLabelInstance("");
        questionTextLabel = UtilGUI.getLabelInstance("");
        categoryLabel = UtilGUI.getLabelInstance("");
        roundLabel = UtilGUI.getLabelInstance("");
        hasTimer = false;
        timerLabel = UtilGUI.getLabelInstance("");
        difficultyLabel = UtilGUI.getLabelInstance("");
        questionsImageLabel = UtilGUI.getLabelInstance("");
        timer =  new Timer(100, e -> {
            if(count<=0) {
                ((Timer) e.getSource()).stop();
            } else {
                count -= 100;
            }
            if(hasTimer)
                GameplayFrame.this.timerLabel.setText((count/1000.0)+" seconds");
            else
                GameplayFrame.this.timerLabel.setText("");
        });
    }

    /**
     * @see UI
     */
    @Override
    public void updateGamemode(String gamemodeName) {
        this.gamemode.setText(gamemodeName);
    }

    /**
     * @see UI
     */
    @Override
    public void updateQuestion(String question) {
        this.questionTextLabel.setText("<html>"+question+"</html>");
    }

    /**
     * @see UI
     */
    @Override
    public void updateRoundId(String id) {
        roundLabel.setText("Round : "+id);
    }

    /**
     * @see UI
     */
    @Override
    public void setHasTimer(boolean b) { this.hasTimer = b; }

    /**
     * @see UI
     */
    @Override
    public void restartCount() {
        this.count = 10000;
    }

    /**
     * @see UI
     */
    @Override
    public void stopTimer() {
        this.timer.stop();
    }

    /**
     * @see UI
     */
    @Override
    public void startTimer() { this.timer.start(); }

    /**
     * @see UI
     */
    @Override
    public void updateDifficulty(Difficulty difficulty) {
        this.difficultyLabel.setText(difficulty.toString());
    }

    /**
     * @see UI
     */
    @Override
    public void updateQuestionsImage(ImageIcon imageIcon) {
        if(imageIcon==null) {
            this.questionsImageLabel.setVisible(false);
            return;
        }

        Image resizedImage = imageIcon.getImage().getScaledInstance(UtilGUI.getScreenWidth()*550/1368,
                UtilGUI.getScreenHeight()*300/768,
                    java.awt.Image.SCALE_DEFAULT
        );
        this.questionsImageLabel.setIcon(new ImageIcon(resizedImage));
        this.questionsImageLabel.setVisible(true);
    }
}
