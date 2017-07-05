package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by marcin on 05.07.17.
 */

public class Obstacle extends Actor {

    private Sprite obstacle;
    private Sprite gap;
    private float speed;


    public Obstacle (int x, int y, float speed, Sprite obstacle, Sprite gap) {
        this.obstacle = obstacle;
        this.gap = gap;
        super.setBounds(0, y, obstacle.getWidth(), obstacle.getHeight());
        setPosition(x, y);
        this.speed = speed;
    }

    @Override
    public void act(float delta) {
        gap.translateX(delta * speed);

        if (gap.getX() <= 0)
            speed = speed > 0 ? speed : -speed;
        else if (gap.getX() + gap.getWidth() >= RollingRocks.WORLD_WIDTH)
            speed = speed < 0 ? speed : -speed;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(0, y);
        obstacle.setPosition(0, y);
        gap.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        obstacle.draw(batch);
        gap.draw(batch);
    }
}
