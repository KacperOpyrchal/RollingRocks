package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by marcin on 05.07.17.
 */

public class ObstacleBuilder {

    private int x;
    private int y;
    private int speed;
    private Sprite obstacle;
    private Sprite gap;

    public ObstacleBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public ObstacleBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public ObstacleBuilder setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public ObstacleBuilder setObstacle(Sprite obstacle) {
        this.obstacle = obstacle;
        return this;
    }

    public ObstacleBuilder setGap(Sprite gap) {
        this.gap = gap;
        return this;
    }

    public pl.koorki.rollingrocks.actors.Obstacle build() {
        return new pl.koorki.rollingrocks.actors.Obstacle(x, y, speed, obstacle, gap);
    }

}
