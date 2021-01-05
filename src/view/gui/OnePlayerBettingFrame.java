package view.gui;

import controller.FrontController;
import controller.requests.SetBetAmountRequest;
import model.player.Player;
import model.questions.Category;
import resources.images.Image;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class OnePlayerBettingFrame extends JFrame implements GUI{
    private static final OnePlayerBettingFrame instance = new OnePlayerBettingFrame();
    private JLabel backgroundImageLabel;
    private JPanel bettingPanel;
    private JLabel bettingLabel;
    private JPanel labelsPanel;
    private JPanel centerPanel;
    private JPanel buttonsPanel;
    private JLabel amountAvailableLabel;
    private JLabel bettingPhaseLabel;
    private JPanel bettingPhasePanel;
    private JButton bettingAmountButton1;
    private JButton bettingAmountButton2;
    private JButton bettingAmountButton3;
    private JButton bettingAmountButton4;
    private JLabel categoryLabel;
    private JLabel scoreLabel;

    public static OnePlayerBettingFrame getInstance() {
        return instance;
    }

    private OnePlayerBettingFrame(){
        UtilGUI.setUpJFrameProperties(this);
        backgroundImageLabel = UtilGUI.setUpBackGround(this, Image.ONE_PLAYER_BETTING_PAGE_BACKGROUND_IMG);
        this.setComponentsPanel();
        this.setUpButtonListeners();
        //this.setVisible(true);
    }

    private void setComponentsPanel() {
        bettingPanel=new JPanel();
        bettingPanel.setLayout(new BorderLayout());
        bettingPanel.setOpaque(false);

        bettingPhasePanel=new JPanel();
        bettingPhasePanel.setOpaque(false);
        bettingPhasePanel.setLayout(new BorderLayout());
        bettingPhasePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        bettingPhaseLabel=UtilGUI.getLabelInstance("             High Stakes");

        bettingLabel=UtilGUI.getLabelInstance("Select your betting amount");


        scoreLabel = new JLabel("Score : "+FrontController.getInstance().getModel().getScore(0));
        scoreLabel.setFont(UtilGUI.getCustomFont());
        scoreLabel.setForeground(Color.WHITE);
        categoryLabel = new JLabel("Category : ");
        categoryLabel.setFont(UtilGUI.getCustomFont());
        categoryLabel.setForeground(Color.WHITE);

        labelsPanel=new JPanel();
        labelsPanel.setOpaque(false);
        labelsPanel.setLayout(new BoxLayout(labelsPanel,BoxLayout.Y_AXIS));
        labelsPanel.setBorder(BorderFactory.createEmptyBorder((int)(0.006*UtilGUI.getScreenHeight()),(int)(0.416*UtilGUI.getScreenWidth()),
                0,0));

        labelsPanel.add(bettingPhaseLabel);
        labelsPanel.add(Box.createRigidArea(new Dimension(0,(int)(0.064*UtilGUI.getScreenHeight()))));
        labelsPanel.add(bettingLabel);
        labelsPanel.add(scoreLabel);
        labelsPanel.add(categoryLabel);

        bettingPhasePanel.add(labelsPanel,BorderLayout.CENTER);

        centerPanel=new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder((int)(0.138*UtilGUI.getScreenHeight()),0,
                0,(int)(0.114*UtilGUI.getScreenWidth())));
        centerPanel.setOpaque(false);
        buttonsPanel=new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.X_AXIS));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0,(int)(0.104*UtilGUI.getScreenWidth()),
                (int)(0.490*UtilGUI.getScreenHeight()),0));
        buttonsPanel.setOpaque(false);
        bettingAmountButton1= UtilGUI.getButtonInstance("250");
        bettingAmountButton2= UtilGUI.getButtonInstance("500");
        bettingAmountButton3= UtilGUI.getButtonInstance("750");
        bettingAmountButton4= UtilGUI.getButtonInstance("1000");

        amountAvailableLabel=UtilGUI.getLabelInstance("Amounts Available");

        bettingAmountButton1.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
        bettingAmountButton2.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
        bettingAmountButton3.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
        bettingAmountButton4.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
        bettingAmountButton1.setPreferredSize(new Dimension((int)(0.052*UtilGUI.getScreenWidth()),(int)(0.055*UtilGUI.getScreenHeight())));
        bettingAmountButton2.setPreferredSize(new Dimension((int)(0.052*UtilGUI.getScreenWidth()),(int)(0.055*UtilGUI.getScreenHeight())));
        bettingAmountButton3.setPreferredSize(new Dimension((int)(0.052*UtilGUI.getScreenWidth()),(int)(0.055*UtilGUI.getScreenHeight())));
        bettingAmountButton4.setPreferredSize(new Dimension((int)(0.052*UtilGUI.getScreenWidth()),(int)(0.055*UtilGUI.getScreenHeight())));
        buttonsPanel.add(bettingAmountButton1);
        buttonsPanel.add(Box.createRigidArea(new Dimension((int)(0.026*UtilGUI.getScreenWidth()),0)));
        buttonsPanel.add(bettingAmountButton2);
        buttonsPanel.add(Box.createRigidArea(new Dimension((int)(0.026*UtilGUI.getScreenWidth()),0)));
        buttonsPanel.add(bettingAmountButton3);
        buttonsPanel.add(Box.createRigidArea(new Dimension((int)(0.026*UtilGUI.getScreenWidth()),0)));
        buttonsPanel.add(bettingAmountButton4);

        centerPanel.add(amountAvailableLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,(int)(0.120*UtilGUI.getScreenHeight()))));
        centerPanel.add(buttonsPanel);

        bettingPanel.add(centerPanel,BorderLayout.CENTER);
        bettingPanel.add(bettingPhasePanel,BorderLayout.PAGE_START);
        backgroundImageLabel.add(bettingPanel);
    }
    // TODO SET ACTION COMMANDS IN ALL BUTTONS
    private void setUpButtonListeners() {
        ActionListener listener = e -> {
            List<Integer> betsSelected = new ArrayList<>();
            betsSelected.add(Integer.parseInt(e.getActionCommand()));
            FrontController.getInstance().dispatchRequest(new SetBetAmountRequest(betsSelected));
            OnePlayerFrame.getInstance().setVisible(true);
            OnePlayerBettingFrame.this.dispose();
        };

        this.bettingAmountButton1.addActionListener(listener);
        this.bettingAmountButton2.addActionListener(listener);
        this.bettingAmountButton3.addActionListener(listener);
        this.bettingAmountButton4.addActionListener(listener);
    }

    @Override
    public void updateCategory(Category category) {
        this.categoryLabel.setText("Category : "+category.toString());
    }

    @Override
    public void updateScore(List<Player> players) {
        instance.scoreLabel.setText("Score : "+ players.get(0).getScore());
    }
}
