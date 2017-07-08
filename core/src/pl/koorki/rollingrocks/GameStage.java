package pl.koorki.rollingrocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by marcin on 07.07.17.
 */

public class GameStage extends Stage {

    int collisionCounter = 0;
    int score = 0;

    private Queue<Obstacle> obstacles = new ArrayDeque<Obstacle>();
    private LinkedList<Obstacle> ObstaclesToRemove = new LinkedList<Obstacle>();
    private Queue<Coin> coins = new ArrayDeque<Coin>();
    private LinkedList<Coin> coinsToRemove = new LinkedList<Coin>();
    private Player player;

    MapGenerator mapGenerator;


    public GameStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
        mapGenerator = new MapGenerator();
    }

    @Override
    public void draw() {
        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        getBatch().begin();
        for (Obstacle obstacle : obstacles)
            obstacle.draw(getBatch());

        for (Obstacle obstacle : ObstaclesToRemove)
            obstacle.draw(getBatch());

        for (Coin coin : coins)
            coin.draw(getBatch());

        for (Coin coin : coinsToRemove)
            coin.draw(getBatch());

        player.draw(getBatch());
        getBatch().end();
    }

    @Override
    public void act() {
        float delta = Gdx.graphics.getDeltaTime();
        float speed = player.move(delta);

        for (Obstacle obstacle : obstacles)
            obstacle.move(delta, speed);

        for (Obstacle obstacle : ObstaclesToRemove)
            obstacle.move(delta, speed);

        for (Coin coin : coins)
            coin.move(speed);

        for (Coin coin : coinsToRemove)
            coin.move(speed);


        if(collisionDetector()) {
            //++collisionCounter;

        }

        if(passedObstacle(obstacles.peek())) {
            makeCoin();
            Obstacle peek = obstacles.peek();
            Obstacle obstacle = mapGenerator.getObstacle((int) peek.getY(), obstacles.size());
            addObstacle(obstacle);
        }

        if(collisionWithCoinDetector()) {
            score++;
        }


        removeObstacle();

    }


   public void loadMap() {
        mapGenerator = new MapGenerator();
        addPlayer(mapGenerator.addPlayer());

        Obstacle[] array = mapGenerator.generateMap();
        for (Obstacle obstacle : array)
            addObstacle(obstacle);
    }


    public boolean passedObstacle(Obstacle obstacle) {
        boolean passed = player.getY() > obstacle.getY() + obstacle.getHeight();

        if (passed)
            ObstaclesToRemove.add(obstacles.remove());

        return passed;
    }


    private void removeObstacle() {
        if (ObstaclesToRemove.size() > 2)
            ObstaclesToRemove.removeFirst();
    }

    public void addCoin(Coin coin) {
        super.addActor(coin);
        coins.add(coin);
    }


    private void addObstacle(Obstacle actor) {
        addActor(actor);
        obstacles.add(actor);
    }


    private void addPlayer(Player actor) {
        addActor(actor);
        player = actor;
    }


    public boolean collisionDetector() {
        boolean collision = collisionWithObstacle(obstacles.peek());

        if (collision)
            return true;

        if (ObstaclesToRemove.isEmpty())
            return false;

        return collisionWithObstacle(ObstaclesToRemove.getLast());
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


    private boolean collisionWithCoinDetector() {
        boolean collision = false;
        if(!coins.isEmpty())
            collision = collisionWithCoin(coins.peek());

        if (collision) {
            // then increase score
            // or amount of money
            Gdx.app.log("collision", "collision DETECTED!!!");
            coins.remove();
            return true;
        }

        if (coinsToRemove.isEmpty())
            return false;

        return collisionWithCoin(coinsToRemove.getLast());
    }


    private boolean collisionWithCoin(Coin coin) {
        return player.getShape().overlaps(coin.getShape());
    }



    /////////////////////////
    private void makeCoin () {
        addCoin(new Coin(RollingRocks.WORLD_WIDTH/2, 1500, new Texture("coin.png")));
    }

}
