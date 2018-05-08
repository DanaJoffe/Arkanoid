package tests;

import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.DialogManager;
import gamerelated.HighScoresTable;
import screensandanimations.*;
import gamerelated.GameFlow;
import gamerelated.Task;
import levelsinfo.LevelInformation;
import redundants.DirectHit;
import redundants.FinalFour;
import redundants.Green3;
import redundants.WideEasy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joffeda.
 */
public class Ass5Game {
    private final int width = 800;
    private final int height = 600;

    public static final int width1 = 800;
    public static final int height1 = 600;

    /**
     * starts a new game with given levels. if no levels were given- start game with levels 1-4.
     * @param args array of level numbers.
     */
    public static void main(String[] args) {
        Ass5Game game = new Ass5Game();
        List<Integer> levelNumbers = game.transformToLevelNumbers(args);
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        for (Integer number: levelNumbers) {
            levels.add(game.transNumToLevelInfo(number));
        }
        if (levels.isEmpty()) {
            levels = game.initializeFourLevels();
            game.run(levels);
        } else {
            game.run(levels);
        }
    }


    public static List<LevelInformation> loadLevels(String[] args) {
        Ass5Game game = new Ass5Game();
        List<Integer> levelNumbers = game.transformToLevelNumbers(args);
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        for (Integer number: levelNumbers) {
            levels.add(game.transNumToLevelInfo(number));
        }
        if (levels.isEmpty()) {
            levels = game.initializeFourLevels();
        }
        return levels;
    }
    /**
     * @param args an array of level numbers in String form.
     * @return array with level numbers in Integer form.
     */
    public List<Integer> transformToLevelNumbers(String[] args) {
        List<Integer> arguments = new ArrayList<Integer>();
        for (int i = 0; i < args.length; i++) {
            try {
                Integer levelNum = Integer.parseInt(args[i]);
                if (levelNum < 5 && levelNum > 0) {
                    arguments.add(levelNum);
                }
            } catch (Exception e) {
                int x = 1; //do nothing.
            }
        }
        return arguments;
    }

    /**
     * @param num al level number.
     * @return the LevelInformation object according to the given number.
     */
    public LevelInformation transNumToLevelInfo(int num) {
        if (num == 1) {
            return new DirectHit(this.width, this.height, 20);
        }
        if (num == 2) {
            return new WideEasy(this.width, this.height, 20);
        }
        if (num == 3) {
            return new Green3(this.width, this.height, 20);
        }
        return new FinalFour(this.width, this.height, 20);
    }

    /**
     * @return a list of levels 1-4.
     */
    public List<LevelInformation> initializeFourLevels() {
        LevelInformation levelInfo1 = new DirectHit(this.width, this.height, 20);
        LevelInformation levelInfo2 = new WideEasy(this.width, this.height, 20);
        LevelInformation levelInfo3 = new Green3(this.width, this.height, 20);
        LevelInformation levelInfo4 = new FinalFour(this.width, this.height, 20);
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        levels.add(levelInfo1);
        levels.add(levelInfo2);
        levels.add(levelInfo3);
        levels.add(levelInfo4);
        return levels;
    }

    /**
     * runs a game with the levels given.
     * @param levels an array of Levels Information.
     */
    public void run(List<LevelInformation> levels) {

        GUI gui = new GUI("Arkanoid", this.width, this.height);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60, new biuoop.Sleeper());

        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid", keyboard);



        Task<Void> highScores = new Task<Void>() {
            public Void run() {
                HighScoresTable highScoresTable = HighScoresTable.loadFromFile(new File("highscores"));
                runner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(highScoresTable, KeyboardSensor.SPACE_KEY)));
                return null;
            }
        };

        Task<Void> startGame = new Task<Void>() {
            public Void run() {
                DialogManager dialog = gui.getDialogManager();
                GameFlow gameflow = new GameFlow(runner, keyboard, dialog, Ass5Game.width1, Ass5Game.height1);
                gameflow.runLevels(levels);
                return null;
            }
        };

        Task<Void> quit = new Task<Void>() {
            public Void run() {
                System.exit(1);
                return null;
            }
        };

        menu.addSelection("s", "Start Game", startGame);
        menu.addSelection("h", "High Scores", highScores);
        menu.addSelection("q", "Quit", quit);

        while (true) {
            runner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
            //gui.close();
        }
        //gui.close();
    }
}