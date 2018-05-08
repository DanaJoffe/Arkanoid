package gamerelated;

import java.io.Serializable;

/**
 * @author joffeda.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * constructor.
     * @param name the player's name.
     * @param score the player's score.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     *
     * @return the player's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return the player's score.
     */
    public int getScore() {
        return this.score;
    }
}