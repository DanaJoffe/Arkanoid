import biuoop.GUI;
import biuoop.KeyboardSensor;
import gamerelated.HighScoresTable;
import gamerelated.Task;
import iostuff.Error;
import iostuff.LevelSetsReader;
import screensandanimations.AnimationRunner;
import screensandanimations.Menu;
import screensandanimations.HighScoresAnimation;
import screensandanimations.KeyPressStoppableAnimation;
import screensandanimations.MenuAnimation;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.InputStream;
/**
 * @author joffeda.
 */
public class Ass6Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    /**
     *
     * @param args may contain a relative path to a level sets file.
     */
    public static void main(String[] args) {
        Ass6Game game = new Ass6Game();

        if (args.length == 0) {
            game.buildAndRun("level_sets.txt");
        } else {
            game.buildAndRun(args[0].trim());
        }
    }

    /**
     *
     * @param levelSetsFile a relative path to a level sets file.
     */
    public void buildAndRun(String levelSetsFile) {
        Ass6Game game = new Ass6Game();
        GUI gui = new GUI("Arkanoid", Ass6Game.WIDTH, Ass6Game.HEIGHT);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60, new biuoop.Sleeper());
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid", keyboard,
                "background_images/main_menu.jpg");
        Task<Void> highScores = new Task<Void>() {
            public Void run() {
//                InputStream it = ClassLoader.getSystemClassLoader()
//                        .getResourceAsStream("highscores");

                HighScoresTable highScoresTable = HighScoresTable.loadFromFile(new File("highscores"));
                runner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(highScoresTable, KeyboardSensor.SPACE_KEY,
                                "background_images/highscores.png")));
                return null;
            }
        };
        Task<Void> quit = new Task<Void>() {
            public Void run() {
                System.exit(1);
                return null;
            }
        };
        menu.addSelection("h", "High Scores", highScores);
        menu.addSelection("q", "Quit", quit);
        menu.addSubMenu("s", "Start Game", game.subMenuBuildes(keyboard, gui, runner, levelSetsFile));
        while (true) {
            runner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }

    }

    /**
     *
     * @param keyboard a KeyboardSensor.
     * @param gui a GUI.
     * @param runner an AnimationRunner.
     * @param levelSetsFile a relative path to a level sets file.
     * @return a subMenu for a level selection.
     */
    public Menu<Task<Void>> subMenuBuildes(KeyboardSensor keyboard, GUI gui, AnimationRunner runner,
                               String levelSetsFile) {
        Reader is = null;
        Menu<Task<Void>> menu = null;
        try {
            InputStream it = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(levelSetsFile);
            is = new InputStreamReader(it);
            menu = LevelSetsReader.fromReader(is, keyboard, gui, runner, Ass6Game.WIDTH, Ass6Game.HEIGHT);
            Task<Void> back = new Task<Void>() {
                public Void run() {
                    return null;
                }
            };
            menu.addSelection("b", "go back to main menu", back);
        } catch (Exception e) {
            Error.printAndExit(e.toString(), "Ass6Game: subMenuBuildes");
        }
        return menu;
    }
}