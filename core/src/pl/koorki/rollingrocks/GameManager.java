package pl.koorki.rollingrocks;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

/**
 * Created by marcin on 07.07.17.
 */

public class GameManager {

  //  private GameStageOldVersion stage;
    private GameStage stage;
    private LinkedList<Obstacle> obstacles;
    private LinkedList<Obstacle> obstaclesToRemove;
    private LinkedList<Coin> coins;
    private LinkedList<Coin> coinsToRemove;
    private Player player;
    private MapGenerator generator;


    public GameManager (GameStage stage) {
        this.stage = stage;
        obstacles = new LinkedList<Obstacle>();
        obstaclesToRemove = new LinkedList<Obstacle>();
        coins = new LinkedList<Coin>();
        coinsToRemove = new LinkedList<Coin>();
        generator = new MapGenerator();
        loadMap();
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

        if (collisionDetector()) {
            // then game_over screen
            // or something else like amount of lives is decrementing
            Gdx.app.log("Collision", "Collision Detected");
        }

        if (collisionWithCoinDetector()) {
            // then increase amount of money
            Gdx.app.log("Collision", "Collision with coin!");

        }

        removeUnnecessaryObjects();
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








    /////////////////////////////////////////

    private void passedObjects() {
        if (!obstacles.isEmpty() && passedObject(obstacles.getFirst())) {
            obstaclesToRemove.add(obstacles.removeFirst());
            addObstacle();
            addCoin();
            // increase score
        }

        if (!coins.isEmpty() && passedObject(coins.getFirst()))
            coinsToRemove.add(coins.removeFirst());
    }

    private boolean collisionDetector() {
        boolean collision = collisionWithObstacle(obstacles.peek());

        if (collision)
            return true;

        if (obstaclesToRemove.isEmpty())
            return false;

        return collisionWithObstacle(obstaclesToRemove.getLast());
    }

    private boolean collisionWithCoinDetector() {
        boolean collision = false;
        if(!coins.isEmpty())
            collision = collisionWithCoin(coins.peek());

        if (collision) {
            coins.removeFirst().remove();
            return true;
        }

        if (coinsToRemove.isEmpty())
            return false;

        collision = collisionWithCoin(coinsToRemove.getLast());

        if (collision)
            coinsToRemove.removeLast().remove();

        return collision;
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

    private boolean collisionWithCoin(Coin coin) {
        return player.getShape().overlaps(coin.getShape());
    }

    private boolean collisionWithObstacle(Obstacle obstacle) {
        if (obstacle == null)
            return false;

        Rectangle obstacleBounds = obstacle.getObstacleBounds();
        Rectangle playerBounds = player.getBounds();

        if (!playerBounds.overlaps(obstacleBounds))
            return false;

        Rectangle gapBounds = obstacle.getGapBounds();

        if (player.getX() > gapBounds.getX() && player.getX() + player.getWidth() < gapBounds.getX() + gapBounds.getWidth())
            return false;

        if (player.getX() + player.getWidth()/2 <= gapBounds.getX() || player.getX() + player.getWidth()/2 >= gapBounds.getX() + gapBounds.getWidth())
            return true;

        Vector2[] gapVertices = getVertices(gapBounds);
        Circle circle = player.getShape();
        boolean collision = false;

        for (Vector2 vertex : gapVertices)
            collision = collision || circle.contains(vertex);

        return collision;
    }

    private Vector2[] getVertices(Rectangle r) {
        Vector2[] vertices = new Vector2[4];
        int it = 0;
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                vertices[it] = new Vector2(r.getX() + i*r.getWidth(), r.getY() + j*r.getHeight());
                ++it;
            }
        }

        return vertices;
    }

}
