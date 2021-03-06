package model.questions;

import javax.swing.*;
import java.util.List;

/**
 * This interface contains the methods that must be implemented by a class so it can be added as type of
 * question in the game.For example questions that have just text or questions with image or video etc.
 * @author Tasos Papadopoulos
 * @version 13.1.2021
 * */
public interface Questionable {
    /**
     * This method returns the question's text
     * @return the question's text as {@code String}
     * */
    String getQuestionText();

    /**
     * This method returns the question's correct answer
     * @return the correct answer as {@code String}
     * */
    String getCorrectAnswer();

    /**
     * This method returns the question's possible answers
     * @return the question's possible answers as {@code List<String>}
     * */
    List<String> getAnswers();

    /**
     * This method returns the question's difficulty
     * @return the question's difficulty as a value of enumerated type {@code Difficulty}
     * */
    Difficulty getDifficulty();

    /**
     * This method returns the question's category
     * @return the question's category as a value of enumerated type {@code Category}
     * */
    Category getCategory();

    /**
     * This method returns whether or not the question has content to show e.g image,video or even sound content
     * @return Whether the question has content or not as {@code boolean}
     * */
    boolean hasContent();

    /**
     * This method returns the image associated with a {@code Question} object.
     * @return the image associated with the {@code Question} object as {@code ImageIcon}
     */
    ImageIcon getContent();
}
