package controller.requests;

import controller.Dispatcher;
import model.gamemodes.Gamemodable;
import model.round.Round;
import view.gui.UI;

/**
 * This class represents a request for pre question actions e.g. betting phase for High Stakes gamemode.
 * @author Tasos Papadopoulos
 * @version 8.1.2021
 */
public class PreQuestionRequest extends Request{
    private final UI gamemodeFrame;

    public PreQuestionRequest(UI gamemodeFrame) {
        this.gamemodeFrame = gamemodeFrame;
    }

    @Override
    public void execute(Dispatcher dispatcher) {
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
        for(int i=0;i<model.getPlayers().size();i++)
            currentGamemode.checkZeroScoreAndUpdate(model, i);

        UI preQuestionFrame = gamemodeFrame.getPreQuestionFrame();
        preQuestionFrame.updateCategory(currentRound.getQuestions().get(questionId).getCategory());
        preQuestionFrame.updateScores(model.getPlayers());
        preQuestionFrame.updateGamemode(currentRound.getGamemodeString());
        preQuestionFrame.setVisible(true);
    }
}
