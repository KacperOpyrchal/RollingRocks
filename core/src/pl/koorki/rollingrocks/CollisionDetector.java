package pl.koorki.rollingrocks;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by marcin on 10.07.17.
 */

public class CollisionDetector {

    private GameManager manager;
    private Player player;

    public CollisionDetector(GameManager manager) {
        this.manager = manager;
        player = manager.player;
    }

    public Obstacle collisionWithObstacle() {
        if (isCollision(manager.obstacles.peek()))
            return manager.obstacles.peek();

        if (manager.obstaclesToRemove.isEmpty())
            return null;

        if (isCollision(manager.obstaclesToRemove.peek()))
            return manager.obstaclesToRemove.peek();

        return null;
    }

    public Coin collisionWithCoin() {
        if (!manager.coins.isEmpty())
            if (isCollision(manager.coins.peek()))
                return manager.coins.peek();

        /// TODO Collision with the penultimate

        if (manager.coinsToRemove.size() >= 2) {
            int i = manager.coinsToRemove.size() - 2;
            if (isCollision(manager.coinsToRemove.get(i)))
                return manager.coinsToRemove.get(i);
        }

        if (!manager.coinsToRemove.isEmpty())
            if (isCollision(manager.coinsToRemove.getLast()))
                return manager.coinsToRemove.getLast();

        return null;
    }

    private boolean isCollision(Obstacle obstacle) {
        Rectangle playerBounds = player.getBounds();

        if (!playerBounds.overlaps(obstacle.getObstacleBounds()))
            return false;

        Rectangle gap = obstacle.getGapBounds();

        if (isInGap(gap))
            return false;

        if (isInObstacle(gap))
            return true;

        return containsGapVertex(getVertices(gap), player.getShape());
    }

    private boolean isCollision(Coin coin) {
        return player.getShape().overlaps(coin.getShape());
    }

    // Necessary functions for collision detection with obstacle

    private boolean isInGap(Rectangle gap) {
        return player.getX() > gap.getX() && player.getX() + player.getWidth() < gap.getX() + gap.getWidth();
    }

    private boolean isInObstacle(Rectangle gap) {
        return player.getX() + player.getWidth()/2 <= gap.getX() || player.getX() + player.getWidth()/2 >= gap.getX() + gap.getWidth();
    }

    private boolean containsGapVertex(Vector2[] vertices, Circle circle) {
        boolean collision = false;
        for (Vector2 vertex : vertices)
            collision = collision || circle.contains(vertex);
        return collision;
    }

    private Vector2[] getVertices(Rectangle r) {
        Vector2[] vertices = new Vector2[] {
                new Vector2(r.getX(), r.getY()),
                new Vector2(r.getX(), r.getY() + r.getHeight()),
                new Vector2(r.getX() + r.getWidth(), r.getY() + r.getHeight()),
                new Vector2(r.getX() + r.getWidth(), r.getY())
        };
        return vertices;
    }

    ///////////////////////////////////////////////////////////////



}
