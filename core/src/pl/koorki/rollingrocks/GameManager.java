package pl.koorki.rollingrocks;


import com.badlogic.gdx.Gdx;
import java.util.LinkedList;

/**
 * Created by marcin on 07.07.17.
 */

public class GameManager {

    private GameStage stage;
    public LinkedList<Obstacle> obstacles;
    public LinkedList<Obstacle> obstaclesToRemove;
    public LinkedList<Coin> coins;
    public LinkedList<Coin> coinsToRemove;
    public Player player;
    private MapGenerator generator;
    private CollisionDetector detector;

    public Obstacle rcnHitObst = null;
    private HUD hud;


    public GameManager (GameStage stage, HUD hud) {
        this.stage = stage;
        this.hud = hud;
        obstacles = new LinkedList<Obstacle>();
        obstaclesToRemove = new LinkedList<Obstacle>();
        coins = new LinkedList<Coin>();
        coinsToRemove = new LinkedList<Coin>();
        generator = new MapGenerator();
        loadMap();
        detector = new CollisionDetector(this);
    }


    private void collisionHandling() {
        collision(detector.collisionWithObstacle());
        collision(detector.collisionWithCoin());
    }

    void collision(Obstacle obstacle) {
        if (obstacle != null) {
            if (obstacle != rcnHitObst)
                Gdx.app.log("Collision", "Collision with OBSTACLE detected!");
            rcnHitObst = obstacle;
            //
            //
            // then handle end of game
            // or decrement amount of lives
        }
    }

    void collision(Coin coin) {
        if (coin != null) {
            Gdx.app.log("Collision", "Collision with COIN detected!");
            hud.increaseCoins();

            if (!coins.isEmpty() && coin == coins.peek())
                coins.removeFirst();
            else
                coinsToRemove.remove(coin);
            coin.remove();
        }
    }


    private void loadMap() {
        loadObstacles();
        loadCoins();
        loadPlayer();
    }

    private void loadPlayer() {
        player = generator.getPlayer();
        stage.addPlayer(player);
    }

    private void loadObstacles() {
        for (Obstacle obstacle : generator.getObstacles()) {
            obstacles.add(obstacle);
            stage.addActor(obstacle);
        }
    }

    private void loadCoins() {
        for (Coin coin : generator.getCoins()) {
            if (coin != null) {
                coins.add(coin);
                stage.addActor(coin);
            }
        }
    }

    public void update() {
        stage.act();
        passedObjects();
        removeUnnecessaryObjects();
        collisionHandling();
    }

    private void addObstacle() {
        Obstacle obstacle = generator.getObstacle((int) obstacles.peek().getY(), obstacles.size());
        obstacles.add(obstacle);
        stage.addActor(obstacle);
    }

    private void addCoin() {
        Coin coin = generator.getCoin((int) obstacles.peek().getY(), obstacles.size());
        if (coin != null) {
            coins.add(coin);
            stage.addActor(coin);
        }
    }

    private void passedObjects() {
        if (!obstacles.isEmpty() && passedObject(obstacles.getFirst())) {
            obstaclesToRemove.add(obstacles.removeFirst());
            addObstacle();
            addCoin();
            // increase score
            hud.increaseScore();
        }

        if (!coins.isEmpty() && passedObject(coins.getFirst()))
            coinsToRemove.add(coins.removeFirst());
    }

    private boolean passedObject(MyActor object) {
        return player.getSpriteY() > object.getY() + object.getHeight();
    }

    private void removeUnnecessaryObjects() {
        if (coinsToRemove.size() > 2)
            coinsToRemove.removeFirst().remove();

        if (obstaclesToRemove.size() > 2)
            obstaclesToRemove.removeFirst().remove();
    }

    public void dispose() {
        generator.dispose();
    }

}
