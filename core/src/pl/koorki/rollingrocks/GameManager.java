package pl.koorki.rollingrocks;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.LinkedList;

import pl.koorki.rollingrocks.actors.Coin;
import pl.koorki.rollingrocks.actors.MyActor;
import pl.koorki.rollingrocks.actors.Obstacle;
import pl.koorki.rollingrocks.actors.Player;
import pl.koorki.rollingrocks.stages.GameStage;

/**
 * Created by marcin on 07.07.17.
 */

public class GameManager {

    private GameStage stage;
    public Player player;
    public LinkedList<Obstacle> obstacles = new LinkedList<Obstacle>();
    public LinkedList<Obstacle> obstaclesToRemove = new LinkedList<Obstacle>();;
    public LinkedList<Coin> coins = new LinkedList<Coin>();
    public LinkedList<Coin> coinsToRemove = new LinkedList<Coin>();
    private MapGenerator generator = new MapGenerator();
    private CollisionDetector detector;
    private boolean collision = false;
    private Sound collisionSound;
    private Sound coinSound;

    private HUD hud;


    public GameManager (GameStage stage, HUD hud) {
        this.stage = stage;
        this.hud = hud;
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

    void collision(Obstacle obstacle) {
        if (obstacle != null) {
            collision = true;
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
