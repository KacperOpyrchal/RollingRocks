package pl.koorki.rollingrocks;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.LinkedList;

/**
 * Created by marcin on 07.07.17.
 */

public class GameManager {

    private pl.koorki.rollingrocks.stages.GameStage stage;
    public LinkedList<pl.koorki.rollingrocks.actors.Obstacle> obstacles;
    public LinkedList<pl.koorki.rollingrocks.actors.Obstacle> obstaclesToRemove;
    public LinkedList<pl.koorki.rollingrocks.actors.Coin> coins;
    public LinkedList<pl.koorki.rollingrocks.actors.Coin> coinsToRemove;
    public pl.koorki.rollingrocks.actors.Player player;
    private MapGenerator generator;
    private CollisionDetector detector;
    private boolean collision = false;
    private Sound collisionSound;
    private Sound coinSound;

    private HUD hud;


    public GameManager (pl.koorki.rollingrocks.stages.GameStage stage, HUD hud) {
        this.stage = stage;
        this.hud = hud;
        obstacles = new LinkedList<pl.koorki.rollingrocks.actors.Obstacle>();
        obstaclesToRemove = new LinkedList<pl.koorki.rollingrocks.actors.Obstacle>();
        coins = new LinkedList<pl.koorki.rollingrocks.actors.Coin>();
        coinsToRemove = new LinkedList<pl.koorki.rollingrocks.actors.Coin>();
        generator = new MapGenerator();
        loadMap();
        detector = new CollisionDetector(this);
    }


    private void collisionHandling() {
        collision(detector.collisionWithObstacle());
        collision(detector.collisionWithCoin());
    }

    public boolean getCollision() {
        return collision;
    }

    void collision(pl.koorki.rollingrocks.actors.Obstacle obstacle) {
        if (obstacle != null) {
            collision = true;
        }
    }

    void collision(pl.koorki.rollingrocks.actors.Coin coin) {
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
        for (pl.koorki.rollingrocks.actors.Obstacle obstacle : generator.getObstacles()) {
            obstacles.add(obstacle);
            stage.addActor(obstacle);
        }
    }

    private void loadCoins() {
        for (pl.koorki.rollingrocks.actors.Coin coin : generator.getCoins()) {
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
        pl.koorki.rollingrocks.actors.Obstacle obstacle = generator.getObstacle((int) obstacles.peek().getY(), obstacles.size());
        obstacles.add(obstacle);
        stage.addActor(obstacle);
    }

    private void addCoin() {
        pl.koorki.rollingrocks.actors.Coin coin = generator.getCoin((int) obstacles.peek().getY(), obstacles.size());
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

    private boolean passedObject(pl.koorki.rollingrocks.actors.MyActor object) {
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
