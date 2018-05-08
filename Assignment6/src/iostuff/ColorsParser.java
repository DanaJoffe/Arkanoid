package iostuff;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joffeda.
 */
public class ColorsParser {
    /**
     *  parse color definition and return the specified color.
     * @param s the full description of a color from a line of bdef.
     * @return a Color object.
     */
    public Color colorFromString(String s) {
        Color aColor = null;
        String fileName;
        if (!s.contains("RGB")) {
            String colorName = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
            try {
                aColor   = (Color) Color.class.getField(colorName).get(null);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            fileName = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
            fileName = fileName.substring(fileName.indexOf("(") + 1);
            String[] strList = fileName.split(",");
            List<Integer> intList = new ArrayList<Integer>();
            for (String g: strList) {
                intList.add(Integer.parseInt(g));
            }
            aColor = new Color(intList.get(0), intList.get(1), intList.get(2));
        }
        return aColor;
    }
}