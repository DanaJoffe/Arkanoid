package iostuff;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import gamerelated.GameFlow;
import gamerelated.Task;
import levelsinfo.LevelInformation;
import screensandanimations.AnimationRunner;
import screensandanimations.Menu;
import screensandanimations.MenuAnimation;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author joffeda.
 */
public class LevelSetsReader {
    /**
     *
     * @param reader a reader of a level_sets file.
     * @param keyboard a KeyboardSensor.
     * @param gui a GUI.
     * @param runner an AnimationRunner.
     * @param width width size of the game window.
     * @param height height size of the game window.
     * @return a subMenu with the selection information from the level_sets file.
     */
    public static Menu<Task<Void>> fromReader(java.io.Reader reader, KeyboardSensor keyboard, GUI gui,
                                  AnimationRunner runner, int width, int height) {
        Menu<Task<Void>> levelsMenu = new MenuAnimation<Task<Void>>("Levels", keyboard,
                "background_images/main_menu.jpg");
        BufferedReader br = new BufferedReader(reader);
        List<String> levelsSet = new ArrayList<String>();
        try {
            String l = br.readLine();
            while (l != null) {
                levelsSet.add(l);
                l = br.readLine();
            }
            List<Map<String, String>> mapsOfLevels = LevelSetsReader.levelsOrganizer(levelsSet);
            for (Map<String, String> map: mapsOfLevels) {
                Task<Void> level = new Task<Void>() {
                    public Void run() {
                        List<LevelInformation> levels = null;
                        Reader is = null;
                        InputStream it = ClassLoader.getSystemClassLoader()
                                    .getResourceAsStream(map.get("returnVal"));
                        is = new InputStreamReader(it);
                        LevelSpecificationReader l = new LevelSpecificationReader();
                        levels = l.fromReader(is);

                        DialogManager dialog = gui.getDialogManager();
                        GameFlow gameflow = new GameFlow(runner, keyboard, dialog, width, height);
                        gameflow.runLevels(levels);
                        return null;
                    }
                };
                levelsMenu.addSelection(map.get("key"), map.get("message"), level);
            }
        } catch (IOException e) {
            Error.printAndExit(e.toString(), "LevelSetsReader: fromReader");
        }
        return levelsMenu;
    }

    /**
     *
     * @param levelsSet lines of a level_sets file.
     * @return a list of the different levels selection possible.
     */
    private static List<Map<String, String>> levelsOrganizer(List<String> levelsSet) {
        List<Map<String, String>> mapsOfLevels = new ArrayList<Map<String, String>>();
        for (String line: levelsSet) {
            if (line.contains(":")) {
                Map<String, String> levelmap = new TreeMap<String, String>();
                String[] keyMessage = line.split(":");
                levelmap.put("message", keyMessage[1]);
                levelmap.put("key", keyMessage[0]);
                while (!line.contains("txt")) {
                    line = levelsSet.get(levelsSet.indexOf(line) + 1); //give me the next.
                }
                levelmap.put("returnVal", line);
                mapsOfLevels.add(levelmap);
            }
        }
        return mapsOfLevels;
    }
}