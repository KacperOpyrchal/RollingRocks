package pl.koorki.rollingrocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by marcin on 05.07.17.
 */

public class Obstacle extends Actor {

    private Sprite obstacle;
    private Sprite gap;
    private int speed;


    public Obstacle (int x, int y, int speed, Sprite obstacle, Sprite gap) {
        this.obstacle = obstacle;
        this.gap = gap;
        super.setBounds(0, y, obstacle.getWidth(), obstacle.getHeight());
        setPosition(x, y);
        this.speed = speed;
    }


    public void move(float delta, float ySpeed) {
        gap.translateX(delta * speed);

        moveY(ySpeed);

        if (gap.getX() <= 0)
            speed = speed > 0 ? speed : -speed;
        else if (gap.getX() + gap.getWidth() >= RollingRocks.WORLD_WIDTH)
            speed = speed < 0 ? speed : -speed;
    }


    private void moveY(float y) {
        setY(getY() - y); // i'm not sure if there should be a minus
        gap.translateY(-y); // here too
        obstacle.translateY(-y); // ...
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(0, y);
        obstacle.setPosition(0, y);
        gap.setPosition(x, y);
    }


    public void draw(Batch batch) {
        obstacle.draw(batch);
        gap.draw(batch);
    }


    public Rectangle getObstacleBounds() {
        return obstacle.getBoundingRectangle();
    }


    public Rectangle getGapBounds() {
        return gap.getBoundingRectangle();
    }


    public void dispose() {

    }
}
