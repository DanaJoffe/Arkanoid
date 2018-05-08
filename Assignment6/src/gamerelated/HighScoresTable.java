package gamerelated;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author joffeda.
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> highScores;
    private int capacity;
    public static final int DEFAULT_CAPACITY = 5;
    /**
     * constructor.
     * Create an empty high-scores table with the specified size.
     * @param size the maximum amount of scores that the table can hold.
     */
    public HighScoresTable(int size) {
        this.highScores = new LinkedList<ScoreInfo>();
        this.capacity = size;
    }

    /**
     * Add a high-score.
     * @param score a score to add.
     */
    public void add(ScoreInfo score) {
        if (this.highScores.isEmpty()) { //no scores in the table.
            this.highScores.add(score);
        } else {
            int i = 0;
            while (i < highScores.size() && score.getScore() < highScores.get(i).getScore()) {
                i++;
            }
            if (i == highScores.size()) { //add to the end of the table - score is the smallest.
                highScores.add(i, score);
            } else if (score.getScore() > highScores.get(i).getScore()) { // i< highScores.size().
                highScores.add(i, score);
            } else if (score.getScore() == highScores.get(i).getScore()) { // i< highScores.size().
                highScores.add(i + 1, score);
            }
            if (highScores.size() > this.capacity) { // there are too many scores.
                highScores.remove(this.capacity);
            }
        }
    }

    /**
     * @return the max table size.
     */
    public int size() {
        return this.capacity;
    }

    /**
     * The list is sorted such that the highest
     * scores come first.
     * @return the current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        List<ScoreInfo> l = new ArrayList<ScoreInfo>();
        l.addAll(this.highScores); //make a copy;
        return l;
    }

    /**
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     * Rank 0 means the score will be in the table, not the highest and not the lowest.
     * @param score a score;
     * @return the rank of the current score: where will it
     * be on the list if added? as detailed above.
     */
    public int getRank(int score) {
        if (this.highScores.isEmpty()) { //no scores in the table.
            return 1;
        } else {
            if (score > highScores.get(0).getScore()) {
                return 1;
            }
            int i = 1;
            while (i < highScores.size() && score < highScores.get(i).getScore()) {
                i++;
            }
            if (i == highScores.size()) { //score is the lowest.
                if (i < this.size()) { //there is room for score- it will be last in the table.
                    return this.size();
                } else { //i >= this.size(), there is no room for score.
                    return (i + 1);
                }
            }
            return 0;
        }
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     * @param filename a file's name.
     * @throws IOException if something went wrong with the loading process.
     * if the given filename was not found, Current table data is cleared, and size becomes default.
     */
    public void load(File filename) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HighScoresTable table = (HighScoresTable) ois.readObject();
            ois.close();
            this.capacity = table.size();
            this.highScores = table.getHighScores();
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e2) {
            System.out.println(e2);
            this.capacity = HighScoresTable.DEFAULT_CAPACITY;
            this.highScores.clear();
            System.out.println("table has been cleared. new size is: " + HighScoresTable.DEFAULT_CAPACITY);
        }
    }

    /**
     * Save table data to the specified file.
     * @param filename a file's name.
     * @throws IOException if something went wrong with the saving process.
     */
    public void save(File filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }
    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned (of default size).
     * @param filename a file's name.
     * @return a table which was read from the given file.
     */
    public static HighScoresTable loadFromFile(File filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HighScoresTable table = (HighScoresTable) ois.readObject();
            ois.close();
            return table;
        } catch (Exception e) { //filename was not found - return empty table.
            return new HighScoresTable(HighScoresTable.DEFAULT_CAPACITY);
        }
    }

    /**
     * how to print an HighScoresTable object.
     * @return a String representation of an HighScoresTable object.
     */
    public String toString() {
        String table = "";
        if (this.highScores.isEmpty()) {
            return "[]";
        }
        for (ScoreInfo s: this.highScores) {
            table = table.concat(s.getName()).concat(" : ").concat(Integer.toString(s.getScore())).concat("\n");
        }
        return table;
    }
}