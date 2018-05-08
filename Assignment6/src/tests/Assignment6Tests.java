package tests;
import java.awt.*;
import java.io.File;

import biuoop.DialogManager;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import characteristics.ColorFill;
import gamerelated.GameFlow;
import gamerelated.HighScoresTable;
import gamerelated.ScoreInfo;
import gamerelated.Task;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import iostuff.*;
import levelsinfo.LevelInformation;
import screensandanimations.*;

import java.io.IOException;
import java.util.*;
import java.lang.Character;

import characteristics.ImageFill;
import characteristics.Fill;
import screensandanimations.Menu;
import sprites.Block;
import javax.imageio.ImageIO;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.*;
import java.awt.Color;
import java.util.List;
import iostuff.LevelSetsReader;

/**
 * Created by djoff on 09/06/2017.
 * tests for assignment6
 */
public class Assignment6Tests {

    public static void testForHighScore() {
        HighScoresTable t = new HighScoresTable(4);
        ScoreInfo s1 = new ScoreInfo("dana", 50);
        ScoreInfo s2 = new ScoreInfo("amir", 60);
        ScoreInfo s3 = new ScoreInfo("dan", 40);
        ScoreInfo s4 = new ScoreInfo("yonatan", 45);
        ScoreInfo s5 = new ScoreInfo("hala", 25);


        System.out.println("max size = 4");

        System.out.println(t);
        System.out.println("rank of " + s1.getScore() + " is " + t.getRank(s1.getScore()));
        t.add(s1);
        System.out.println(t);
        System.out.println("rank of " + s2.getScore() + " is " + t.getRank(s2.getScore()));
        t.add(s2);
        System.out.println(t);
        System.out.println("rank of " + s3.getScore() + " is " + t.getRank(s3.getScore()));
        t.add(s3);
        System.out.println(t);
        System.out.println("rank of " + s4.getScore() + " is " + t.getRank(s4.getScore()));
        t.add(s4);
        System.out.println(t);
        System.out.println("rank of " + s5.getScore() + " is " + t.getRank(s5.getScore()));
        t.add(s5);
        System.out.println(t);
        try {
            t.save(new File("highscores"));
            System.out.println("file saved");
        } catch (Exception e) {
            System.out.println("couldn't wright file");
            System.out.println(e);
        }

    }
    public static void testForReadingFile() {
        HighScoresTable t = HighScoresTable.loadFromFile(new File("highscores"));
        System.out.println("open file: ");
        System.out.println(t);


        HighScoresTable t2 = new HighScoresTable(4);
        ScoreInfo s1 = new ScoreInfo("da", 100);
        ScoreInfo s2 = new ScoreInfo("ir", 30);
        ScoreInfo s3 = new ScoreInfo("an", 3892);
        ScoreInfo s4 = new ScoreInfo("natan", 45);
        ScoreInfo s5 = new ScoreInfo("ala", 30);

        t2.add(s1);
        t2.add(s2);
        System.out.println("new table t2: \n" + t2);

        try {
            System.out.println("loading t table into t2");
            t2.load(new File("highscores"));
        } catch (Exception e) {
            System.out.println("couldn't load");
        }
        System.out.println("t2: \n" + t2);
    }
    public static void testForLinkedList() {
        List<Integer> highScores = new LinkedList<Integer>();
        highScores.add(1);
        highScores.add(2);
        highScores.add(3);
        highScores.add(4);
        highScores.remove(2);
        System.out.println(highScores.get(2));
        System.out.println(highScores.size());

        System.out.println(highScores);
        highScores.add(2,3);
        highScores.add(4,90);
        System.out.println(highScores);
        if (highScores.size() == 5) {
            highScores.remove(4);
        }
        System.out.println(highScores);
    }
    public static void Ass6Game(String[] args) {
        GUI gui = new GUI("Arkanoid", Ass5Game.width1, Ass5Game.height1);
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
                List<LevelInformation> levels = null;//Ass5Game.loadLevels(args);
                Reader is = null ;
                try {
                    is = new InputStreamReader (
                            new FileInputStream ("resources/exampleBlocksDefinition.txt"));
                    LevelSpecificationReader l = new LevelSpecificationReader();
                    levels = l.fromReader(is);

                    } catch (Exception e) {
                    iostuff.Error.printAndExit(e.toString(), "Assignment6Tests: Ass6Game");
                }
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
    public static void ImageTest1() {
        Map<String, String> m = new TreeMap<String, String>();
        System.out.println(m.containsKey(1));
        System.out.println(m.isEmpty());

        Rectangle rect = new Rectangle(new Point(50,50), 100, 100);
        Fill background = new ColorFill(Color.BLUE, rect);
        Fill imagefill = new ImageFill("resources/block_images/zebra.jpg", new Point(0,0));

        GUI gui = new GUI("test", 800, 600);
        DrawSurface d = gui.getDrawSurface();
        //background.fillIn(d);
        //imagefill.fillIn(d);

        Block b = new Block(new Point(200,200), 110, 200);
        //b.addFillOption(-1, "zebra.jpg");
        //b.addFillOption(3, Color.CYAN);
        //b.setFrameColor(Color.BLACK);
        b.setColisionAmount(3);
        b.drawOn(d);
        gui.show(d);
    }
    public static void blockFromSymbolsTest() {
        Reader is = null ;
        try {
            is = new InputStreamReader (
                    new FileInputStream ("exampleBlocksDefinition.txt"));
            BlocksFromSymbolsFactory k = BlocksDefinitionReader.fromReader(is);
            Block b = k.getBlock("z", 100, 100);

            GUI gui = new GUI("test", 800, 600);
            DrawSurface d = gui.getDrawSurface();
            b.drawOn(d);
            gui.show(d);



        } catch ( IOException e ) {
            System.out.println(e);
        }

    }
    public static void BlockCreatorTest() {
        BasicBlockCreator creator = new BasicBlockCreator();
        creator.addFillOption(-1, "leopard.jpg");
        creator.setHeight(20);
        creator.setWidth(50);
        creator.setHitPoints(1);
        creator.setStroke(Color.GRAY);

        Block b = creator.create(100,100);
        Block b2 = creator.create(200,200);
        GUI gui = new GUI("test", 800, 600);
        DrawSurface d = gui.getDrawSurface();
        b2.drawOn(d);
        b.drawOn(d);
        gui.show(d);
    }
    public static void BlockCreatorTest2() {
//        List<Block> blocks = new ArrayList<Block>();
//        int y = Integer.parseInt(map.get("blocks_start_y"));
//        int x = Integer.parseInt(map.get("blocks_start_x"));
//        int row_height = Integer.parseInt(map.get("row_height"));
//        String block_definitionsPath = map.get("block_definitions");
//        Reader is = null ;
//        try {
//            is = new InputStreamReader(
//                    new FileInputStream("resources/" + block_definitionsPath));//************CHANEG**************
//            BlocksFromSymbolsFactory blockFactory = BlocksDefinitionReader.fromReader(is);
//
//            for(String line: level_blocksInfo) {
//                for(Character ch: line.toCharArray()) {
//
//                    if(blockFactory.isSpaceSymbol(String.valueOf(ch))) {
//                        x = x + blockFactory.getSpaceWidth(String.valueOf(ch));
//                    } else if(blockFactory.isBlockSymbol(String.valueOf(ch))) {
//                        Block b = blockFactory.getBlock(String.valueOf(ch), x, y);
//                        blocks.add(b);
//                    }
//                }
//                y = y + row_height;
//            }
//            return blocks;
//        } catch ( IOException e ) {
//            System.out.println(e);
//        }
    }
    public static void extractFileNameTest() {
        String name1 = "color(white)";
        //String name1 = "color(RGB(255,255,254))";


        Color aColor = null;
        String fileName;
        if(!name1.contains("RGB")) {
            String colorName = name1.substring(name1.indexOf("(") + 1, name1.indexOf(")"));
            try {
                aColor   = (Color) Color.class.getField(colorName).get(null);
                //System.out.print(colorName);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            fileName = name1.substring(name1.indexOf("(") + 1, name1.indexOf(")"));
            fileName = fileName.substring(fileName.indexOf("(") + 1);
            String[] strList = fileName.split(",");
            List<Integer> intList = new ArrayList<Integer>();
            for(String g: strList) {
                intList.add(Integer.parseInt(g));
            }
            aColor = new Color(intList.get(0), intList.get(1), intList.get(2));
        }

        System.out.println(aColor);











    }
    public static void levelReaderTest() {
        Reader is = null ;
        try {
            is = new InputStreamReader (
                    new FileInputStream ("exampleBlocksDefinition.txt"));
            LevelSpecificationReader l = new LevelSpecificationReader();
            List<LevelInformation> levels = l.fromReader(is);


            GUI gui = new GUI("test", 800, 600);
            DrawSurface d = gui.getDrawSurface();
            for(LevelInformation level: levels) {
                for(Block b: level.blocks()) {
                    //System.out.println(b.)
                    b.drawOn(d);
                }
            }
            gui.show(d);



        } catch ( IOException e ) {
            System.out.println(e);
        }
    }
    public static void levels() {
        String v = "45,200 -45,200 0,300";
        v = v.trim();
        String[] velos = v.split(" ");
        Map<String, String> mapVelos = new TreeMap<String, String>();
        for(String vel: velos) {
            String[] oneVel = vel.split(",");
            mapVelos.put(oneVel[0], oneVel[1]);
        }
        //System.out.println(mapVelos);


        String lines = "*\n" +
                "*\n" +
                "bbbbbbbbbb";
        String[] liness = lines.split("\n");

        List<String> l = new ArrayList<String>();
        for(String line: liness) {
            l.add(line);
        }
        System.out.println(l);

        for(String line: l) {
            for(Character ch: line.toCharArray()) {
                System.out.print(String.valueOf(ch));
            }
            System.out.println("");
        }




    }
    public static void menuTest() {
//        Reader is = null ;
//        try {
//            InputStream it = ClassLoader.getSystemClassLoader()
//                    .getResourceAsStream("level_sets.txt");
//            is = new InputStreamReader(it);
//            LevelSetsReader l = new LevelSetsReader();
//            l.fromReader(is);
//        } catch (Exception e){
//            System.out.println(e);
//        }
    }

    public static void main(String[] args) {
        Assignment6Tests.menuTest();
    }
}