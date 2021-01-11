package view.gui;

import controller.FrontController;
import resources.utilResources.Image;
import javax.swing.*;
import java.awt.*;

/**
 * This class represents the frame where we can choose between single player or two players gameplay.
 * @author Thodwrhs Myridis
 * @author Tasos Papadopoulos
 */
public class PlayFrame extends GUI {
    private final IntroFrame introFrame;
    private JButton onePlayerButton;
    private JButton twoPlayersButton;
    private JButton backButton;
    private final JLabel backgroundImageLabel;

    /**
     * Default constructor.
     * @param introFrame
     */
    public PlayFrame(IntroFrame introFrame){
        this.introFrame=introFrame;
        UtilGUI.setUpJFrameProperties(frame);
        backgroundImageLabel = UtilGUI.setUpBackGround(frame, Image.PLAY_PAGE_BACKGROUND_IMG);
        this.setUpButtonsPanel();
        this.setUpButtonListeners();
        FrontController.getInstance().setView(this);
        this.setVisible(true);
        this.introFrame.setVisible(false);
    }

    /**
     * This method creates buttons panel (one player - two players).
     */
    private void setUpButtonsPanel() {
        JPanel buttonsPanel =new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(1,2,(int)(0.338*UtilGUI.getScreenWidth()),0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder((int)(0.625*UtilGUI.getScreenHeight()),(int)(0.140*UtilGUI.getScreenWidth()),
                (int)(0.106*UtilGUI.getScreenHeight()),(int)(0.098*UtilGUI.getScreenWidth())));

        onePlayerButton= UtilGUI.getButtonInstance("One Player");
        twoPlayersButton= UtilGUI.getButtonInstance("Two Players");

        buttonsPanel.add(twoPlayersButton);
        buttonsPanel.add(onePlayerButton);

        JPanel backPanel=new JPanel();
        backPanel.setLayout(new BorderLayout());
        backPanel.setOpaque(false);

        backButton= UtilGUI.getButtonInstance("Back");
        backButton.setPreferredSize(new Dimension((int)(0.091*UtilGUI.getScreenWidth()),(int)(0.037*UtilGUI.getScreenHeight())));
        backPanel.add(backButton,BorderLayout.LINE_END);
        backPanel.setBorder(BorderFactory.createEmptyBorder(0,0,(int)(0.013*UtilGUI.getScreenHeight()),(int)(0.007*UtilGUI.getScreenWidth())));

        JPanel playPanel=new JPanel();
        playPanel.setLayout(new BorderLayout());
        playPanel.setOpaque(false);

        JPanel labelPanel=new JPanel();
        labelPanel.setLayout(new GridLayout(1,1));
        labelPanel.setBorder(BorderFactory.createEmptyBorder((int)(0.074*UtilGUI.getScreenHeight()),(int)(0.270*UtilGUI.getScreenWidth()),
                0,(int)(0.260*UtilGUI.getScreenWidth())));
        labelPanel.setOpaque(false);

        JLabel gamemodesLabel=UtilGUI.getLabelInstance("Select gamemode",30);
        labelPanel.add(gamemodesLabel);

        playPanel.add(labelPanel,BorderLayout.PAGE_START);
        playPanel.add(backPanel,BorderLayout.PAGE_END);
        playPanel.add(buttonsPanel,BorderLayout.CENTER);

        backgroundImageLabel.add(playPanel);
    }

    /**
     * This method sets all button listeners.
     */
    private void setUpButtonListeners(){
        onePlayerButton.addActionListener(e -> {
            new OnePlayerSelectionFrame(PlayFrame.this);
            PlayFrame.this.setVisible(false);
        });

        twoPlayersButton.addActionListener(e -> {
            new TwoPlayersSelectionFrame(PlayFrame.this);
            PlayFrame.this.setVisible(false);
        });

        backButton.addActionListener(e -> {
            introFrame.setVisible(true);
            PlayFrame.this.setVisible(false);
        });
    }
}
