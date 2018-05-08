package gamerelated;
import biuoop.DialogManager;
import levelsinfo.LevelInformation;
import java.util.List;
import biuoop.KeyboardSensor;
import screensandanimations.AnimationRunner;
import screensandanimations.GameLevel;
import screensandanimations.KeyPressStoppableAnimation;
import screensandanimations.EndScreen;
import screensandanimations.HighScoresAnimation;
import java.io.File;

/**
 * @author joffeda.
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private DialogManager dialog;
    private int width;
    private int height;
    private Counter score;
    private Counter numberOfLives;

    /**
     * constructor.
     * @param ar an animationRunner object.
     * @param ks a keyboard sensor to get out of the EndScreen.
     * @param width the width of the window.
     * @param height the height of the window.
     * @param di a DialogManager component from the biuoop.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, DialogManager di, int width, int height) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.dialog = di;
        this.width = width;
        this.height = height;
        this.score = new Counter(0);
        this.numberOfLives = new Counter(7);
    }

    /**
     * @param levels a list of levels to run.
     */
    public void runLevels(List<LevelInformation> levels) {
        HighScoresTable highScores = HighScoresTable.loadFromFile(new File("highscores"));
        for (LevelInformation levelInfo: levels) {
            GameLevel level = new GameLevel(levelInfo,
                    this.keyboardSensor, this.animationRunner,
                    this.score, this.numberOfLives,
                    this.width, this.height);

            level.initialize();
            while (level.getRemainingBlocks() > 0 && level.getRemainingLife() > 0) {
                level.playOneTurn();
            }
            if (level.getRemainingLife() == 0) {
                break;

            }
        }

        this.addPlayerToScoresTable(highScores, this.score.getValue());

        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new EndScreen(KeyboardSensor.SPACE_KEY, this.numberOfLives.getValue() > 0,
                        this.score.getValue(), "background_images/win.png", "background_images/lose.jpg")));

        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(highScores, KeyboardSensor.SPACE_KEY,
                        "background_images/highscores.png")));
    }

    /**
     * checks if the score can get into the table. if it can- ask for name
     * , add the score and save the changes.
     * @param t the game's high scores table.
     * @param score1 a score.
     */
    private void addPlayerToScoresTable(HighScoresTable t, int score1) {
        if (t.getRank(score1) <= t.size()) { //score goes on the table.
            ///ask for name.
            String name = this.dialog.showQuestionDialog("Name", "What is your name?", "");
            t.add(new ScoreInfo(name, score1));
            try {
                t.save(new File("highscores"));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}