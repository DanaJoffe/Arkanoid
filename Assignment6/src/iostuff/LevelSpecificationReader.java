package iostuff;
import characteristics.Velocity;
import levelsinfo.BasicLevelInformation;
import levelsinfo.LevelInformation;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import sprites.Block;

/**
 * @author joffeda.
 */
public class LevelSpecificationReader  {
    private List<LevelInformation> levels;

    /**
     * constructor.
     */
    public LevelSpecificationReader() {
        this.levels = new ArrayList<LevelInformation>();
    }

    /**
     *
     * @param reader a reader of a level_definition file.
     * @return a list of LevelInformation, according to the file.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        //go over the file
        BufferedReader br = new BufferedReader(reader);
        List<String> levelLines = null;
        List<String> blocksLines = null;
        try {
            String l = br.readLine();
            while (l != null) {
                if (l.contains("START_LEVEL")) {
                    levelLines = new ArrayList<String>();
                    while (!l.contains("END_LEVEL")) {
                        if (l.contains("START_BLOCKS")) {
                            blocksLines = new ArrayList<String>();
                            blocksLines.add(l);
                            this.blocksReader(br, blocksLines);
                        }
                        if (!l.contains("START_BLOCKS")) {
                            levelLines.add(l);
                        }
                        l = br.readLine();
                    }
                    levelLines.add(l);
                    if (blocksLines != null) {
                        blocksLines.remove("START_BLOCKS");
                        blocksLines.remove("END_BLOCKS");
                    }
                    this.levels.add(this.levelReader(levelLines, blocksLines));
                }
                l = br.readLine();
            }
            return this.levels;
        } catch (IOException e) {
            System.out.println(e);
        }
        return this.levels;
    }

    /**
     *
     * @param reader a reader of a level_definition file.
     * @param blocksLines an empty list of lines. the method will fill it with
     * the blocks' design in a level.
     */
    private void blocksReader(BufferedReader reader, List<String> blocksLines) {
        String l = "";
        try {
            l = reader.readLine();
            while (!l.contains("END_BLOCKS")) {
                blocksLines.add(l);
                l = reader.readLine();
            }
            blocksLines.add(l);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     *
     * @param levelInfo all the information about a level.
     * @param levelBlocksInfo all the information about the blocks organization in the level.
     * @return a levelInformation object.
     */
    private LevelInformation levelReader(List<String> levelInfo, List<String> levelBlocksInfo) {
        BasicLevelInformation levelInfoOb = new BasicLevelInformation();
        Map<String, String> levelMap = this.listToMap(levelInfo);
        this.checkIfValid(levelMap); //all the fields are here, for sure.
        for (String key: levelMap.keySet()) {
            populateLevInfo(levelInfoOb, levelMap, key);
        }
        levelInfoOb.setBlocks(this.createBlocksList(levelMap, levelBlocksInfo));
        return levelInfoOb;
    }

    /**
     * assistant to levelReader.
     * @param levelInfo all the information about a level.
     * @return the same information, organized in a map.
     */
    private Map<String, String> listToMap(List<String> levelInfo) {
        Map<String, String> levelMap = new TreeMap<String, String>();
        for (String line: levelInfo) {
            if (line.contains(":")) {
                line = line.trim();
                String[] toMap = line.split(":");
                levelMap.put(toMap[0], toMap[1]);
            }
        }
        return levelMap;
    }

    /**
     * assistant to levelReader.
     * checks that there are no missing fields for creating the level.
     * @param levelInfo all the information about a level.
     */
    private void checkIfValid(Map<String, String> levelInfo) {
        Set<String> keys = levelInfo.keySet();
        if (keys.contains("level_name") && keys.contains("ball_velocities") && keys.contains("background")
                && keys.contains("paddle_speed") && keys.contains("paddle_width") && keys.contains("num_blocks")
                && keys.contains("blocks_start_x") && keys.contains("blocks_start_y") && keys.contains("row_height")
                && keys.contains("block_definitions")) {
            int x;
        } else {
            Error.printAndExit("there are missing fields, can't create a levelInfo.",
                    "LevelSpecificationReader: checkIfValid");
        }
    }

    /**
     * assistant to levelReader.
     * fills the given levelInfo according to the key, and it's value in the map.
     * @param levelInfo BasicLevelInformation object.
     * @param map all the information about a level.
     * @param key a level' feature.
     */
    private void populateLevInfo(BasicLevelInformation levelInfo, Map<String, String> map, String key) {
        if (key.contains("level_name")) {
            levelInfo.setLevelName(map.get(key));
        } else if (key.contains("ball_velocities")) {
            levelInfo.setVelocities(this.getVelocities(map.get(key)));
        } else if (key.contains("background")) {
            if (map.get(key).contains("color")) {
                ColorsParser cp = new ColorsParser();
                levelInfo.setBackground(cp.colorFromString(map.get(key)));
            } else { //contains "Image".
                String name = map.get(key);
                String fileName = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
                levelInfo.setBackground(fileName);
            }
        } else if (key.contains("paddle_speed")) {
            int speed = Integer.parseInt(map.get(key));
            if (speed < 0) {
                Error.printAndExit("an invalid paddle speed was given",
                        "LevelSpecificationReader: populateLevInfo");
            }
            levelInfo.setPaddleSpeed(speed);
        } else if (key.contains("paddle_width")) {
            int width = Integer.parseInt(map.get(key));
            if (width < 0) {
                Error.printAndExit("an invalid paddle width was given",
                        "LevelSpecificationReader: populateLevInfo");
            }
            levelInfo.setPaddleWidth(width);
        } else if (key.contains("num_blocks")) {
            Integer numBlocks = Integer.parseInt(map.get(key));
            levelInfo.setNumberOfBlocksToRemove(numBlocks);
        }
    }

    /**
     * assistant to levelReader.
     * @param map  all the information about a level.
     * @param levelBlocksInfo all the information about the blocks organization in the level.
     * @return a list of blocks for the level.
     */
    private List<Block> createBlocksList(Map<String, String> map, List<String> levelBlocksInfo) {
        List<Block> blocks = new ArrayList<Block>();
        int y = Integer.parseInt(map.get("blocks_start_y"));
        int xStart = Integer.parseInt(map.get("blocks_start_x"));
        int x = xStart;
        int rowHeight = Integer.parseInt(map.get("row_height"));
        String blockDefinitions = map.get("block_definitions");
        Reader is = null;
        try {

            InputStream it = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(blockDefinitions);
            is = new InputStreamReader(it);
            BlocksFromSymbolsFactory blockFactory = BlocksDefinitionReader.fromReader(is);

            for (String line: levelBlocksInfo) {
                for (int i = 0; i < line.length(); i++) { //(Character ch: line.toCharArray()) {
                    String c = String.valueOf(line.charAt(i));

                    if (blockFactory.isSpaceSymbol(c)) {
                        x = x + blockFactory.getSpaceWidth(c);
                    } else if (blockFactory.isBlockSymbol(c)) {
                        Block b = blockFactory.getBlock(c, x, y);
                        blocks.add(b);
                        x = (int) (x + b.getWidth());
                   }

                }
                x = xStart;
                y = y + rowHeight;
            }
            return blocks;
        } catch (Exception e) {
            System.out.println(e);
        }
        return blocks;
    }

    /**
     * assistant to populateLevInfo.
     * @param v velocities from the file.
     * @return list of Velocities.
     */
    private List<Velocity> getVelocities(String v) {
        v = v.trim();
        String[] velos = v.split(" ");
        Map<Integer, Integer> mapVelos = new TreeMap<Integer, Integer>();
        for (String vel: velos) {
            String[] oneVel = vel.split(",");
            mapVelos.put(Integer.parseInt(oneVel[0]), Integer.parseInt(oneVel[1]));
        }
        List<Velocity> velocitiesList = new ArrayList<Velocity>();
        for (Integer angle: mapVelos.keySet()) {
            velocitiesList.add(Velocity.fromAngleAndSpeed(angle, mapVelos.get(angle)));
        }
        return velocitiesList;
    }




}