package tests;

import redundants.DirectHit;
import redundants.FinalFour;
import redundants.Green3;
import redundants.WideEasy;
import levelsinfo.*;
import java.util.List;
import java.util.ArrayList;
/**
 * @author joffeda.
 */
public class FlowTest {


    public static void main(String[] args) {
        int width = 1045;
        int height =  780;
        LevelInformation levelInfo1 = new DirectHit(width, height, 20);
        LevelInformation levelInfo2 = new WideEasy(width, height, 20);
        LevelInformation levelInfo3 = new Green3(width, height, 20);
        LevelInformation levelInfo4 = new FinalFour(width, height, 20);
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        levels.add(levelInfo1);
        levels.add(levelInfo2);
        //levels.add(levelInfo3);
        //levels.add(levelInfo4);


//        GUI gui = new GUI("Arkanoid", width, height); //CREATION
//        KeyboardSensor keyboard = gui.getKeyboardSensor();
//        AnimationRunner runner = new AnimationRunner(gui, 60, new biuoop.Sleeper());
//
//        GameFlow gameflow = new GameFlow(runner, keyboard, width, height);
//        gameflow.runLevels(levels);
//        gui.close();
    }
}