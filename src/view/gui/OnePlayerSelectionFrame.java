package view.gui;

import controller.FrontController;
import controller.requests.*;
import model.gamemodes.factories.OnePlayerGamemodeFactory;
import resources.utilResources.Image;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the frame when the player is going to type his username and choose how many rounds he wants to play.
 * @author Thodwrhs Myridis
 * @author Tasos Papadopoulos
 */
public class OnePlayerSelectionFrame extends GUI {
    private final PlayFrame playFrame;
    private JLabel backgroundImageLabel;
    private JButton backButton;
    private JButton confirmButton;
    // TODO ADD PARAMETRIZED JCOMBOBOX
    private JComboBox roundSelectionBox;
    private JTextField usernameField;
    private final String[] roundsList={"Select rounds:","1","2","3","4","5","6","7","8","9","10"};
    private JPanel onePlayerSelectionPanel;
    private JPanel backPanel;
    private JPanel componentsPanel;

    /**
     * Default constructor
     * @param playFrame
     */
    public OnePlayerSelectionFrame(PlayFrame playFrame){
        this.playFrame=playFrame;
        UtilGUI.setUpJFrameProperties(this.frame);
        backgroundImageLabel = UtilGUI.setUpBackGround(this.frame, Image.ONE_PLAYER_SELECTION_PAGE_BACKGROUND_IMG);
        onePlayerSelectionPanel =new JPanel();
        onePlayerSelectionPanel.setLayout(new BorderLayout());
        onePlayerSelectionPanel.setOpaque(false);
        this.setUpLeftPanel();
        this.setUpBackPanel();
        this.connectPanels();
        this.setUpButtonListeners();
        FrontController.getInstance().setView(this);
        this.setVisible(true);
    }

    /**
     * This method creates left panel of the frame.
     */
    private void setUpLeftPanel(){
        componentsPanel=new JPanel();
        componentsPanel.setLayout(new GridLayout(3,1,0,(int)(0.231*UtilGUI.getScreenHeight())));
        componentsPanel.setOpaque(false);
        componentsPanel.setBorder(BorderFactory.createEmptyBorder((int)(0.138*UtilGUI.getScreenHeight()),(int)(0.078*UtilGUI.getScreenWidth()),
                (int)(0.138*UtilGUI.getScreenHeight()),(int)(0.781*UtilGUI.getScreenWidth())));

        this.setUpLeftComponents();

        componentsPanel.add(usernameField);
        componentsPanel.add(roundSelectionBox);
        componentsPanel.add(confirmButton);
    }

    /**
     * This method constructs necessary components for the left panel.
     */
    private void setUpLeftComponents(){
        usernameField =new JTextField("Enter username:");
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        usernameField.setFont(UtilGUI.getCustomFont());

        roundSelectionBox =new JComboBox(roundsList);
        roundSelectionBox.setAlignmentX(2);
        roundSelectionBox.setSelectedIndex(0);
        roundSelectionBox.setFont(UtilGUI.getCustomFont());

        confirmButton= UtilGUI.getButtonInstance("Confirm");
    }

    /**
     * This method creates the back panel (bottom panel) where the back button is going to be.
     */
    private void setUpBackPanel(){
        backPanel=new JPanel();
        backPanel.setLayout(new BorderLayout());
        backPanel.setOpaque(false);

        backButton= UtilGUI.getButtonInstance("Back");
        backButton.setPreferredSize(new Dimension((int)(0.091*UtilGUI.getScreenWidth()),(int)(0.037*UtilGUI.getScreenHeight())));
        backPanel.add(backButton,BorderLayout.LINE_END);
        backPanel.setBorder(BorderFactory.createEmptyBorder(0,(int)(0.260*UtilGUI.getScreenWidth()),(int)(0.013*UtilGUI.getScreenHeight()),
                (int)(0.007*UtilGUI.getScreenWidth())));
    }

    /**
     * This method connects all available panels.
     */
    private void connectPanels() {
        onePlayerSelectionPanel.add(backPanel,BorderLayout.PAGE_END);
        onePlayerSelectionPanel.add(componentsPanel,BorderLayout.CENTER);
        backgroundImageLabel.add(onePlayerSelectionPanel);
    }

    /**
     * @see UI
     * @return
     */
    @Override
    public List<String> getUsernames() {
        List<String> usernames = new ArrayList<>();
        usernames.add(usernameField.getText());
        return usernames;
    }

    /**
     * @see UI
     * @return
     */
    @Override
    public int getNumOfRoundsChoice() {
        return Integer.parseInt((String)roundSelectionBox.getSelectedItem());
    }

    /**
     * This method sets all button listeners.
     */
    private void setUpButtonListeners() {
        backButton.addActionListener(e -> {
            playFrame.setVisible(true);
            OnePlayerSelectionFrame.this.setVisible(false);
        });

       usernameField.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               super.mouseClicked(e);
               if (usernameField.getText().equals("Enter username:"))
                   usernameField.setText("");
           }
       });

       // TODO add focus lost listener
       backgroundImageLabel.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               super.mouseClicked(e);
               if (usernameField.getText().equals(""))
                   usernameField.setText("Enter username:");
           }
       });

       confirmButton.addActionListener(e -> {
           if (!(usernameField.getText().equals("") || usernameField.getText().equals("Enter username:")))
                if (!(roundSelectionBox.getSelectedItem().equals("Select rounds:"))){
                    FrontController.getInstance().dispatchRequest(new SetGamemodeFactoryRequest(
                            OnePlayerGamemodeFactory.getInstance()
                    ));
                    FrontController.getInstance().dispatchRequest(new LoadRequest());
                    FrontController.getInstance().dispatchRequest(new ClearDataRequest());
                    FrontController.getInstance().dispatchRequest(new AddUsernamesRequest());
                    FrontController.getInstance().dispatchRequest(new AddNumOfRoundsRequest());
                    FrontController.getInstance().setView(OnePlayerFrame.getInstance());
                    FrontController.getInstance().dispatchRequest(new SetMaximumPlayersRequest(1));
                    FrontController.getInstance().dispatchRequest(new UpdateDataRequest(-1,
                            -1,0));
                    FrontController.getInstance().dispatchRequest(new PreQuestionRequest(
                            OnePlayerFrame.getInstance()));
                    OnePlayerSelectionFrame.this.setVisible(false);
               }
       });

       roundSelectionBox.addActionListener(e -> roundSelectionBox.removeItem("Select rounds:"));
    }
}
