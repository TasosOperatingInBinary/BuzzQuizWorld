package model.gamemodes.factories;

import model.gamemodes.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a gamemode factory for two players game session. Contains Point Builder, Stop The Clock,
 * High Stakes, Boiling Points and Fastest Finger gamemodes.
 * Singleton Design Pattern is used.
 * @author Tasos Papadopoulos
 * @version 13.1.2021
 */
public class TwoPlayersGamemodeFactory implements GamemodeFactory{
    private static final Gamemodable[] gamemodes = {new PointBuilder(), new HighStakes(), new StopTheClock(),
                                                new FastestFinger()};
    private static final TwoPlayersGamemodeFactory instance = new TwoPlayersGamemodeFactory();

    /**
     * Default constructor.
     */
    private TwoPlayersGamemodeFactory() { }

    /**
     * Returns the unique {@code TwoPlayersGamemodeFactory} instance
     * @return the gamemode factory for two players game sessions as {@code TwoPlayersGamemodeFactory} object
     */
    public static TwoPlayersGamemodeFactory getInstance() {
        return instance;
    }

    /**
     * @see GamemodeFactory
     * @return The random gamemode as {@code Gamemodable}
     */
    @Override
    public Gamemodable getRandomGamemode() {
        int randomNumber = ThreadLocalRandom.current().nextInt(5);

        if(randomNumber == 0)
            return gamemodes[0];
        else if ( randomNumber == 1)
            return gamemodes[1];
        else if( randomNumber == 2)
            return gamemodes[2];
        else if( randomNumber == 3)
            return new BoilingPoint();

        return gamemodes[3];
    }

    /**
     * @see GamemodeFactory
     * Creates new objects for each gamemode the gamemode factory contains.
     */
    @Override
    public void clearGamemodeData() {
        gamemodes[0] = new PointBuilder();
        gamemodes[1] = new HighStakes();
        gamemodes[2] = new StopTheClock();
        gamemodes[3] = new FastestFinger();
    }
}
