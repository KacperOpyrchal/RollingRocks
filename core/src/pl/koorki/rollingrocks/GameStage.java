package pl.koorki.rollingrocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
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

    private Queue<Obstacle> obstacles = new ArrayDeque<Obstacle>();
    private LinkedList<Obstacle> toRemove = new LinkedList<Obstacle>();
    private Player player;


    public GameStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
    }

    @Override
    public void draw() {
        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        getBatch().begin();
        for (Obstacle obstacle : obstacles)
            obstacle.draw(getBatch());

        for (Obstacle obstacle : toRemove)
            obstacle.draw(getBatch());

        player.draw(getBatch());
        getBatch().end();
    }


    @Override
    public void act() {
        float delta = Gdx.graphics.getDeltaTime();

        for (Obstacle obstacle : obstacles)
            obstacle.act(delta);

        for (Obstacle obstacle : toRemove)
            obstacle.act(delta);

        player.act(delta);

        if(collisionDetector()) {
            ++collisionCounter;
            Gdx.app.log("Collision", "" + collisionCounter);
        }
    }

    public void addObstacle(Obstacle actor) {
        addActor(actor);
        obstacles.add(actor);
    }

    public void addPlayer(Player actor) {
        addActor(actor);
        player = actor;
    }


    private boolean collisionDetector() {
        boolean collision = collisionWithObstacle(obstacles.peek());

        if (collision == true)
            return true;

        if (toRemove.isEmpty())
            return false;

        return collisionWithObstacle(toRemove.getLast());
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
