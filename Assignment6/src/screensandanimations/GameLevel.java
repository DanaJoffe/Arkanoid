package screensandanimations;
import biuoop.KeyboardSensor;
import biuoop.DrawSurface;
import gamerelated.PaddleRemover;
import gamerelated.BallRemover;
import gamerelated.BlockRemover;
import gamerelated.Counter;
import gamerelated.ScoreTrackingListener;
import gamerelated.GameEnvironment;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import sprites.Paddle;
import sprites.Block;
import sprites.LivesIndicator;
import sprites.SpriteCollection;
import sprites.Ball;
import sprites.ScoreIndicator;
import sprites.IndicatorsSpace;
import sprites.LevelNameIndicator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import characteristics.Velocity;
import characteristics.HitListener;
import levelsinfo.LevelInformation;
/**
 * @author joffeda.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;

    private Rectangle gameSpace;
    private int bordersThick;
    private List<Block> borderBlocks;
    private List<Block> deathBlocks;

    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private Counter numberOfLives;
    private AnimationRunner runner;
    private boolean running;
    private LevelInformation levelInformation;

    private KeyboardSensor keyboard;

    /**
     * constructor.
     * @param levelInfo a level information.
     */
    public GameLevel(LevelInformation levelInfo) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.levelInformation = levelInfo;

        this.setGeneralFraming(1045, 800);
        this.setBorderBlocks();
        this.setDeathBlocks();
    }

    /**
     * constructor.
     * @param levelInfo a level information.
     * @param width the width of the window.
     * @param height the height of the window.
     */
    public GameLevel(LevelInformation levelInfo, int width, int height) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.levelInformation = levelInfo;

        this.setGeneralFraming(width, height);
        this.setBorderBlocks();
        this.setDeathBlocks();
    }

    /**
     * constructor.
     * @param levelInfo a level information.
     * @param keyboardSensor a keyboard sensor.
     * @param animationRunner an animationRunner object.
     * @param score a score counter.
     * @param lives a lives counter.
     * @param width the width of the window.
     * @param height the height of the window.
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboardSensor,
                     AnimationRunner animationRunner, Counter score, Counter lives,
                     int width, int height) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.levelInformation = levelInfo;
        this.runner = animationRunner;
        this.keyboard = keyboardSensor;
        this.score = score;
        this.numberOfLives = lives;

        this.setGeneralFraming(width, height);
        this.setBorderBlocks();
        this.setDeathBlocks();
    }

    /**
     * default bordersthick = 20.
     * sets gameSpace which is the space the game takes place, without the borders.
     * @param width the width of the window.
     * @param height the height of the window.
     */
    private void setGeneralFraming(int width, int height) {
        this.bordersThick = 25;
        this.gameSpace = new Rectangle(new Point(bordersThick, 2 * bordersThick),
                width - (2 * bordersThick), height - (2 * bordersThick));
    }

    /**
     * @return the game space.
     */
    private Rectangle getGameSpace() {
        return this.gameSpace;
    }

    /**
     * @return the width of the window.
     */
    private int getWindowWidth() {
        return (int) (this.getGameSpace().getWidth() + (2 * this.bordersThick));
    }

    /**
     * @return the height of the window.
     */
    private int getWindowHeight() {
        return (int) (this.getGameSpace().getHeight() + (2 * this.bordersThick));
    }

    /**
     * @return the border blocks of the game.
     */
    private List<Block> getBorderBlocks() {
        return borderBlocks;
    }

    /**
     * @return the death blocks of the game.
     */
    private List<Block> getDeathBlocks() {
        return this.deathBlocks;
    }

    /**
     * @return the point on the screen where the score should be written.
     */
    private Point getScorePosition() {
        int x = (int) this.gameSpace.getUpperLeft().getX();
        int y = (int) this.gameSpace.getUpperLeft().getY();
        return new Point(x + (3 * this.gameSpace.getWidth() / 8), y - this.bordersThick - 2);
    }

    /**
     * @return the point on the screen where the amount of lives left should be written.
     */
    private Point getLivesPosition() {
        int x = (int) this.gameSpace.getUpperLeft().getX();
        int y = (int) this.gameSpace.getUpperLeft().getY();
        return new Point(x + (this.gameSpace.getWidth() / 8), y - this.bordersThick - 2);
    }

    /**
     * @return the point on the screen where the name of the level should be written.
     */
    private Point getLevelNamePosition() {
        int x = (int) this.gameSpace.getUpperLeft().getX();
        int y = (int) this.gameSpace.getUpperLeft().getY();
        return new Point(x + (5 * this.gameSpace.getWidth() / 8), y - this.bordersThick - 2);
    }

    /**
     * set the border blocks of the game.
     */
    private void setBorderBlocks() {
        this.borderBlocks = new ArrayList<Block>();
        int x = (int) (this.getGameSpace().getUpperLeft().getX());
        int y = (int) (this.getGameSpace().getUpperLeft().getY());
        int spaceWidth = (int) this.getGameSpace().getWidth();
        int spaceHeight = (int) this.getGameSpace().getHeight();

        Block border1 =  new Block(new Rectangle(new Point(x - this.bordersThick,
                y - this.bordersThick), spaceWidth + (2 * bordersThick), bordersThick)); //up
        Block border3 =  new Block(new Rectangle(new Point(x - bordersThick, y),
                bordersThick, spaceHeight)); //left
        Block border4 = new Block(new Rectangle(new Point(x + spaceWidth, y),
                bordersThick, spaceHeight)); //right
        border1.setColor(Color.DARK_GRAY);
        border3.setColor(Color.GRAY);
        border4.setColor(Color.GRAY);
        borderBlocks.add(border4);
        borderBlocks.add(border3);
        borderBlocks.add(border1);
        for (Block borderBlock: borderBlocks) {
            borderBlock.checkColor().makePrminent();
            borderBlock.setRemovedAbility(false);
        }
    }

    /**
     * set the death blocks of the game.
     */
    private void setDeathBlocks() {
        this.deathBlocks = new ArrayList<Block>();
        int x = (int) (this.getGameSpace().getUpperLeft().getX());
        int y = (int) (this.getGameSpace().getUpperLeft().getY());
        int spaceWidth = (int) this.getGameSpace().getWidth();
        int spaceHeight = (int) this.getGameSpace().getHeight();
        deathBlocks = new ArrayList<Block>();
        Block border2 =  new Block(new Rectangle(new Point(x - this.bordersThick,
                y + spaceHeight + 15), spaceWidth + (2 * this.bordersThick), bordersThick)); //death region.

        deathBlocks.add(border2);

        for (Block borderBlock: deathBlocks) {
            borderBlock.setColor(Color.BLACK);
            borderBlock.checkColor().makePrminent();
            borderBlock.setRemovedAbility(false);
        }
    }

    /**
     * sprites array = objects that are drawn on the board game.
     * @return the sprites array.
     */
    public SpriteCollection getSprites() {
        return this.sprites;
    }
    /**
     * @return the environment.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        this.sprites.addSprite(this.levelInformation.getBackground());
        this.setIndicators();
        this.setBlocks();
    }

    /**
     * create the indicators: scoreIndicator, livesIndicator, levelNameIndicator.
     */
    private void setIndicators() {

        IndicatorsSpace is = new IndicatorsSpace(new Point(0, 0),
                (int) this.gameSpace.getWidth() + 2 * this.bordersThick,
                this.bordersThick, Color.WHITE);
        is.addToGame(this);

        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score, 18, this.getScorePosition());
        scoreIndicator.addToGame(this);

        LivesIndicator livesIndicator = new LivesIndicator(this.numberOfLives, 18, this.getLivesPosition());
        livesIndicator.addToGame(this);

        LevelNameIndicator levelName = new LevelNameIndicator(this.levelInformation.levelName(),
                18, this.getLevelNamePosition());
        levelName.addToGame(this);
    }

    /**
     * set all the blocks and their listeners.
     */
    private void setBlocks() {
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);
        List<Block> allBlocks = new ArrayList<Block>();
        for (Block obstacles: this.levelInformation.blocks()) {
            allBlocks.add(obstacles);
        }
        for (Block borderBlock: this.getBorderBlocks()) {
            allBlocks.add(borderBlock);
        }
        for (Block deathBlock: this.getDeathBlocks()) {
            allBlocks.add(deathBlock);
        }

        this.remainingBlocks = new Counter(this.levelInformation.numberOfBlocksToRemove());
        HitListener blockRemover = new BlockRemover(this, this.remainingBlocks);

        for (int i = 0; i < allBlocks.size(); i++) {
            allBlocks.get(i).addToGame(this);
            allBlocks.get(i).addHitListener(blockRemover);
            allBlocks.get(i).addHitListener(scoreTracker);
        }
    }

    /**
     * @return the amount of blocks that should be hit on board.
     */
    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }

    /**
     * @return how much life remaining for the  player.
     */
    public int getRemainingLife() {
        return this.numberOfLives.getValue();
    }

    /**
     * @return a point a little above the center of the paddle -
     * a start location for the game ball.
     */
    private Point ballLocation() {
        Rectangle gameSpacee = this.getGameSpace();
        int paddleHeight = (int) (gameSpacee.getHeight() / 23);
        int y = (int) (gameSpacee.bottomBorder() - (2 * paddleHeight));
        int x = (int) (gameSpacee.rBorder() - (gameSpacee.getWidth() / 2));
        return new Point(x, y);
    }

    /**
     * run the game - start the animation loop.
     */
    public void playOneTurn() {
        this.createBallsOnTopOfPaddle(); // or a similar method
        this.runner.run(new CountdownAnimation(2, 3, this.getSprites())); // countdown before turn starts.
        // use our runner to run the current animation -- which is one turn of
        // the game.

        this.running = true;
        this.runner.run(this);
    }

    /**
     * create balls, paddle, and listeners.
     */
    private void createBallsOnTopOfPaddle() {
        List<Ball> allBalls = new ArrayList<Ball>();
        for (Velocity vel: this.levelInformation.initialBallVelocities()) {
            Ball ball = new Ball((this.ballLocation()), 5, Color.WHITE);
            ball.setVelocity(vel);
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
            allBalls.add(ball);
        }

        Paddle paddle = new Paddle(this.getGameSpace(), this.levelInformation.paddleSpeed(),
                this.levelInformation.paddleWidth(), this.keyboard);
        paddle.checkColor().makePrminent();
        paddle.addToGame(this);
        PaddleRemover remoPaddle = new PaddleRemover(paddle);

        this.remainingBalls = new Counter(this.levelInformation.numberOfBalls());
        HitListener ballRemover = new BallRemover(this, this.remainingBalls, remoPaddle);
        List<Block> deathBlockss = this.getDeathBlocks();
        for (Block deathBlock: deathBlockss) {
            deathBlock.addHitListener(ballRemover);
        }
    }

    /**
     * @param d a drawing surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.keyboard.isPressed("p")) {
            this.runner.run(
                    new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                            new PauseScreen(KeyboardSensor.SPACE_KEY, "background_images/pause.png")));
        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(Math.pow(this.runner.getRate(), -1));

        if (this.remainingBalls.getValue() == 0) { //stop the animation when there are no balls.
            this.numberOfLives.decrease(1);
            this.running = false;
        }
        if (this.remainingBlocks.getValue() == 0) { //stop the animation when there are no Obstacles.
            this.score.increase(100);
            this.running = false;
        }
    }

    /**
     * @return true if the animation should stop, and false otherwise.
     */
    public boolean shouldStop() {
        return !this.running;
    }
}