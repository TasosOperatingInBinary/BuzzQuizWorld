package model.fileHandler;

import model.Model;
import model.player.Player;
import model.questions.Category;
import model.questions.Difficulty;
import model.questions.ImagedQuestion;
import model.questions.Question;
import resources.utilResources.Constants;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a file handler which handles the files containing the data of the app.
 * @author Tasos Papadopoulos
 * @version 9.1.2021
 * */
public class FileHandler {
    private String db_file = Constants.DB_FILE_URL;
    private final List<Question> questionList;
    private final Path textDataPath;
    private int questionsReturned;
    private final Path imagedDataPath;
    private boolean loadedQuestions;

    /**
     * Create a file handler using the given {@code List<Question>} and {@code Path} given.
     * @param textDataPath the path of the file that contains the questions
     * @param questionList the List that will be stored the questions from the file
     */
    public FileHandler(List<Question> questionList,Path textDataPath, Path imagedDataPath) {
        this.loadedQuestions = false;
        this.questionList = questionList;
        this.textDataPath = textDataPath;
        this.questionsReturned = 0;
        this.imagedDataPath = imagedDataPath;
    }

    public void setDb_file(String db_file) {
        this.db_file = db_file;
    }

    /**
     * Reads the questions from the file with path the path that was given in the constructor of the class.
     * @throws IOException if the file was not found
     */
    public void readQuestions() throws IOException{
        if(loadedQuestions) {
            Collections.shuffle(this.questionList);
            questionsReturned = 0;
            return;
        }
        loadedQuestions = true;
        this.readTextQuestions();
        this.readImagedQuestions();
    }

    /**
     * Returns 5 questions when invoked.
     * @return 5 new questions as {@code List<Question>}
     */
    public List<Question> getNextQuestions() {
        List<Question> nextQuestions = new ArrayList<>(5);
        int i;
        for(i=this.questionsReturned;i<questionsReturned+5;i++)
            nextQuestions.add(this.questionList.get(i));

        this.questionsReturned = i;
        Collections.shuffle(nextQuestions);
        return nextQuestions;
    }

    public void savePlayers() throws IOException {
        List<Player> currentPlayers = new ArrayList<>(Model.getInstance().getPlayers());
        System.out.println("Current Players");
        currentPlayers.forEach(e-> System.out.println(e.toString()));
        List<Player> previousPlayers = null;
        try {
            previousPlayers = this.readPlayers();
        } catch(IOException|ClassNotFoundException e) {
            // TODO ADD ERROR FRAME
            System.exit(-5);
        }
        System.out.println("Previous Players");
        previousPlayers.forEach(e-> System.out.println(e.toString()));

        if(previousPlayers.size()==0) {
            previousPlayers.addAll(currentPlayers);
        }
        else {
            for(Player currentPlayer : currentPlayers) {
                boolean found = false;
                for(Player previousPlayer : previousPlayers) {
                    if(currentPlayer.getUsername().equals(previousPlayer.getUsername())) {
                        int previousScore = previousPlayer.getScore();
                        int currentScore = currentPlayer.getScore();
                        int previousWins = previousPlayer.getWins();
                        int currentWins = currentPlayer.getWins();
                        previousPlayer.setScore(
                                Math.max(previousScore, currentScore)
                        );
                        previousPlayer.setWins(
                                currentWins > 0 ? previousWins + currentWins : previousWins
                        );
                        found = true;
                    }
                }
                if(!found) {
                    previousPlayers.add(currentPlayer);
                }
            }
        }
        System.out.println("Current Players after processing");
        currentPlayers.forEach(e-> System.out.println(e.toString()));
        System.out.println("Previous Players after processing");
        previousPlayers.forEach(e-> System.out.println(e.toString()));
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.db_file))) {
            out.writeObject(previousPlayers);
        }

    }

    public List<Player> readPlayers() throws IOException, ClassNotFoundException{
        List<Player> players=null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.db_file))) {
            Object object = in.readObject();
            if(object instanceof List) {
                players = (List<Player>)object;
            }
        }

        return players;
    }

    private void readTextQuestions() throws IOException{
        try (BufferedReader reader = Files.newBufferedReader(textDataPath, StandardCharsets.UTF_8)) {
            String line;
            while(( line = reader.readLine() ) != null) {
                String[] splitQuestion = line.split(";");
                Question currentQuestion = new Question();
                currentQuestion.setCategory(Category.valueOf(splitQuestion[0]));
                currentQuestion.setQuestionText(splitQuestion[1]);
                List<String> answers = new ArrayList<>( List.of(splitQuestion[2],
                        splitQuestion[3],
                        splitQuestion[4],
                        splitQuestion[5]));
                Collections.shuffle(answers);
                currentQuestion.setAnswers(answers);
                currentQuestion.setCorrectAnswer(splitQuestion[6]);
                currentQuestion.setDifficulty(Difficulty.valueOf(splitQuestion[7]));
                questionList.add(currentQuestion);
            }
            Collections.shuffle(this.questionList);
        }
    }

    private void readImagedQuestions() throws IOException{
        try(BufferedReader reader = Files.newBufferedReader(imagedDataPath, StandardCharsets.UTF_8)) {
            String line;
            while(( line = reader.readLine() ) != null) {
                String [] splitQuestion = line.split(";");
                ImagedQuestion currentQuestion = new ImagedQuestion("src/resources/images/"+splitQuestion[8]);
                currentQuestion.setCategory(Category.valueOf(splitQuestion[0]));
                currentQuestion.setQuestionText(splitQuestion[1]);
                List<String> answers = new ArrayList<>( List.of(splitQuestion[2],
                        splitQuestion[3],
                        splitQuestion[4],
                        splitQuestion[5]));
                Collections.shuffle(answers);
                currentQuestion.setAnswers(answers);
                currentQuestion.setCorrectAnswer(splitQuestion[6]);
                currentQuestion.setDifficulty(Difficulty.valueOf(splitQuestion[7]));
                questionList.add(currentQuestion);
            }
            Collections.shuffle(this.questionList);
        }
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    /*
    public static void main(String[] args) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Constants.DB_FILE_URL))) {
            List<Player> players = new ArrayList<>();
            out.writeObject(players);
        } catch(FileNotFoundException e) {
            System.out.println("File not found exception line 142 File Handler");
        } catch(IOException e) {
            System.out.println("IO exception line 144 File Handler");
        }
    }*/
}
