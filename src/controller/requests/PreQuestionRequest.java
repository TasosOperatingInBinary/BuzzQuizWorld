package controller.requests;

import controller.Dispatcher;
import controller.FrontController;
import model.Model;
import model.gamemodes.Gamemodable;
import model.round.Round;
import view.gui.GUI;

/**
 * This class represents a request for pre question actions e.g. betting phase for High Stakes gamemode.
 * @author Tasos Papadopoulos
 * @version 5.1.2021
 */
public class PreQuestionRequest extends Request{
    private final GUI gamemodeFrame;

    public PreQuestionRequest(GUI gamemodeFrame) {
        this.gamemodeFrame = gamemodeFrame;
    }

    @Override
    public void execute(Dispatcher dispatcher) {
        Model model = dispatcher.getModel();

        // if rounds are over
        if(roundId == model.getNumOfRounds())
            return;

        Round currentRound = model.getRound(Request.roundId);
        gamemodeFrame.setHasTimer(currentRound.getGamemode().hasTimer());

        // if there are no pre-question actions
        if(!currentRound.hasPreQuestionFormat()) {
            gamemodeFrame.restartCount();
            gamemodeFrame.startTimer();
            gamemodeFrame.setVisible(true);
            return;
        }

        // otherwise there are pre question actions
        Gamemodable currentGamemode = currentRound.getGamemode();
        for(int i=0;i<dispatcher.getModel().getPlayers().size();i++)
            currentGamemode.checkZeroScoreAndUpdate(model, i);

        GUI preQuestionFrame = gamemodeFrame.getPreQuestionFrame();
        preQuestionFrame.updateCategory(currentRound.getQuestions().get(questionId).getCategory());
        preQuestionFrame.updateScore(FrontController.getInstance().getModel().getPlayers());
        preQuestionFrame.setVisible(true);
    }
}
