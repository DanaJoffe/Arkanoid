package iostuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
/**
 * @author joffeda.
 */
public class BlocksDefinitionReader {
    private List<Map<String, String>> blocksInfo;
    private List<Map<String, String>> spacersInfo;
    private List<Map<String, String>> defaultInfo;
    private BlocksFromSymbolsFactory bfsFactory;

    /**
     * constructor.
     */
    public BlocksDefinitionReader() {
        this.bfsFactory = new BlocksFromSymbolsFactory();
        this.defaultInfo = new ArrayList<Map<String, String>>();
        this.spacersInfo = new ArrayList<Map<String, String>>();
        this.blocksInfo = new ArrayList<Map<String, String>>();
    }

    /**
     *
     * @param reader a reader of a block_definition file.
     * @return an object that can create blocks from the symbols in the block_definition file.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BlocksDefinitionReader  factory = new BlocksDefinitionReader();
        BufferedReader is = new BufferedReader(reader);
        try {
            String l = is.readLine();

            while (l != null) {
                if (l.contains("spacers definitions")
                        || l.contains("block definitions")
                        || l.contains("default values for blocks")) {
                    if (l.contains("default values for blocks")) {
                        l = factory.managingInformation(is, "default", factory.defaultInfo);
                    } else if (l.contains("block definitions")) {
                        l = factory.managingInformation(is, "bdef", factory.blocksInfo);
                    } else if (l.contains("spacers definitions")) {
                        l = factory.managingInformation(is, "sdef", factory.spacersInfo);
                    }
                } else {
                    l = is.readLine();
                }
            }
            factory.spacersTender();
            factory.blocksCreatorsTender();
        } catch (IOException e) {
            iostuff.Error.printAndExit(e.toString(), "BlocksDefinitionReader: fromReader");
        }
        return factory.bfsFactory;
    }

    /**
     * the method fills the mapList given with the information from the reader, according
     * to the prefix' restrictions.
     * @param reader a reader of a block_definition file.
     * @param prefix a prefix of one of the fields in the block_definition file within the reader.
     * will be: bdef, sdef, or default.
     * @param mapList a list of maps. the list itself represents a field in the block_definition file,
     * and each map represents a line in the field.
     * will be one of the following: this.defaultInfo, this.blocksInfo, this.spacersInfo.
     * @return the last place we visited in the file.
     */
    public String managingInformation(BufferedReader reader, String prefix, List<Map<String, String>> mapList) {
        String l = "";
        try {
            l = reader.readLine();
            while (l != null && !l.contains(prefix)) {
                l = reader.readLine();
            }
            l = l.trim();
            while (l != null && (l.contains(prefix) || l.trim().isEmpty())) {
                l = l.trim();
                if (!l.isEmpty() && l.indexOf("#") != 0) {
                    Map<String, String> p = this.lineMapper(l, prefix);
                    mapList.add(p);
                }
                l = reader.readLine();
            }
        } catch (IOException e) {
            iostuff.Error.printAndExit(e.toString(), "BlocksDefinitionReader: managingInformation");
        }
        return l;
    }

    /**
     *
     * @param line a line in a field in the block_definition file
     * @param prefix a prefix of one of the fields in the block_definition file.
     * @return the information in the given line, organized in a map.
     */
    public Map<String, String> lineMapper(String line, String prefix) {
        Map<String, String> map = new TreeMap<String, String>();
        line = line.replace(prefix, "");
        line = line.trim();
        String[] array = line.split(" ");
        for (String keyVal: array) {
            String[] toMap = keyVal.split(":");
            map.put(toMap[0], toMap[1]);
        }
        return map;
    }

    /**
     * creating a blockCreator based on the information in this.blocksInfo, and initialize
     * this.bfsFactory with it.
     */
    public void blocksCreatorsTender() {
        for (Map<String, String> blockInfoMap: this.blocksInfo) { //goes over on block each time.
            BasicBlockCreator blockCreator = new BasicBlockCreator();
            for (Map<String, String> defaultMap: this.defaultInfo) { //goes over all defaults each time.
                for (String dkey: defaultMap.keySet()) { //goes over all keys in default.
                    this.populatingBasicBlockCreator(blockCreator, defaultMap, dkey);
                }
            }
            for (String bkey: blockInfoMap.keySet()) { //goes over all keys in block info.
                this.populatingBasicBlockCreator(blockCreator, blockInfoMap, bkey);
            }
            if (!blockCreator.isValid()) {
                Error.printAndExit("invalid blockCreator", "BlocksDefinitionReader: blocksCreatorsTender");
            }
            this.bfsFactory.addBlockCreator(blockInfoMap.get("symbol"), blockCreator);
        }
    }

    /**
     * assistant to blocksCreatorsTender.
     * fills the given blockCreator according to the key and value.
     * @param blockCreator a blockCreator to fill.
     * @param map a map between a block' feature and it's value.
     * @param key a block' feature.
     */
    public void populatingBasicBlockCreator(BasicBlockCreator blockCreator, Map<String, String> map, String key) {
        if (key.contains("width")) {
            Integer width = Integer.parseInt(map.get(key));
            if (width < 0) {
                Error.printAndExit("an invalid ball width was given",
                        "BlocksDefinitionReader: blocksCreatorsTender");
            }
            blockCreator.setWidth(width);
        } else if (key.contains("height")) {
            Integer height = Integer.parseInt(map.get(key));
            if (height < 0) {
                Error.printAndExit("an invalid ball height was given",
                        "BlocksDefinitionReader: blocksCreatorsTender");
            }
            blockCreator.setHeight(height);
        } else if (key.contains("hit_points")) {
            Integer hitPoints = Integer.parseInt(map.get(key));
            if (hitPoints < 0) {
                Error.printAndExit("an invalid ball hit_points was given",
                        "BlocksDefinitionReader: blocksCreatorsTender");
            }
            blockCreator.setHitPoints(hitPoints);
        } else if (key.contains("stroke")) {
            ColorsParser cp = new ColorsParser();
            blockCreator.setStroke(cp.colorFromString(map.get(key)));
        } else if (key.contains("fill")) {
            if (map.get(key).contains("color")) {
                ColorsParser cp = new ColorsParser();
                blockCreator.addFillOption(this.fillOptionInterpreter(key),
                        cp.colorFromString(map.get(key)));
            } else { //contains "Image".
                String name = map.get(key);
                String fileName = this.fillImageValidName(name);
                blockCreator.addFillOption(this.fillOptionInterpreter(key), fileName);
            }
        }
    }

    /**
     * assistant to populatingBasicBlockCreator.
     * @param fillKey a fill feature. can be: fill, fill-k.
     * @return for "fill-k" returns: k. for "fill" returns: -1.
     */
    public Integer fillOptionInterpreter(String fillKey) {
        if (fillKey.contains("-")) {
            String strOp = fillKey.substring(fillKey.indexOf("-") + 1);
            Integer intOp = Integer.parseInt(strOp);
            return intOp;
        } else {
            return -1; //default fill mapping.
        }
    }

    /**
     * assistant to populatingBasicBlockCreator.
     * @param name the full description of an image from a line of bdef.
     * @return the pure file name.
     */
    public String fillImageValidName(String name) {
        String fileName = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
        return fileName;
    }

    /**
     * initialize this.bfsFactory with the information in this.spacersInfo.
     */
    public void spacersTender() {
        for (Map<String, String> spacerInfo: this.spacersInfo) { //go over each spacer.
            this.bfsFactory.addSpacer(spacerInfo.get("symbol"), Integer.parseInt(spacerInfo.get("width")));

            //validness check:
            if (spacerInfo.get("symbol").length() != 1 || Integer.parseInt(spacerInfo.get("width")) < 0) {
                Error.printAndExit("an invalid spacer was given", "BlocksDefinitionReader:spacersTender");
            }
        }
    }
}