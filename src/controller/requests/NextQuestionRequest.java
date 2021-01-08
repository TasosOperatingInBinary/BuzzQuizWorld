package controller.requests;

import controller.Dispatcher;
import model.Model;
import view.gui.UI;

public class NextQuestionRequest extends Request{
    private final UI gamemodeFrame;

    public NextQuestionRequest(UI gamemodeFrame) {
        this.gamemodeFrame = gamemodeFrame;
    }

    @Override
    public void execute(Dispatcher dispatcher) {
        if(Model.getInstance().allAnswered() && roundId!=Model.getInstance().getNumOfRounds()) {
            gamemodeFrame.setVisible(false);
            dispatcher.dispatch(new PreQuestionRequest(gamemodeFrame));
        }
    }
}
