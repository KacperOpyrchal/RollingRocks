package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by marcin on 06.07.17.
 */

public class Player extends Actor {

    private Circle circle;
    private Texture texture;
    private Sprite sprite;
    private Vector2 speed = new Vector2(0, 0);
    private Vector2 line = new Vector2(0, 0);
    private boolean move = false;
    private ShapeRenderer renderer = new ShapeRenderer();



    public Player(int x, int y, Texture texture) {
        int radius = texture.getWidth() / 2;
        circle = new Circle(x, y, radius);
        super.setBounds(x - radius, y - radius, 2 * radius, 2 * radius);
        this.texture = texture;
        this.sprite = new Sprite(texture);
        setPosition(x, y);
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }


            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector2 coord = new Vector2(circle.x, circle.y);
                coord = stageToLocalCoordinates(coord);
                line.set(coord.x - x, coord.y - y);
            }


            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 coord = new Vector2(circle.x, circle.y);
                coord = stageToLocalCoordinates(coord);
                speed.set(coord.x - x, coord.y - y);
                line.set(0, 0);
                move = true;
            }

        });
    }


    public void draw(Batch batch) {
        batch.end();

        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);

        renderer.rectLine(circle.x, circle.y, circle.x + line.x, circle.y + line.y, 3);
        renderer.end();

        batch.begin();
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        if (!move) return;

        collisionWithFrames();

        float x = circle.x;
        float y = circle.y;

        x += speed.x * delta;
        y += speed.y * delta;

        setPosition(x, y);
    }


    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - circle.radius, y - circle.radius);
        sprite.setPosition(x - circle.radius, y - circle.radius);
        circle.setPosition(x, y);
    }


    private void collisionWithFrames() {
        if (getX() <= 0)
            speed.x = speed.x > 0 ? speed.x : -speed.x;
        else if (getX() + getWidth() >= RollingRocks.WORLD_WIDTH)
            speed.x = speed.x < 0 ? speed.x : -speed.x;

        if (getY() <= 0)
            speed.y = speed.y > 0 ? speed.y : -speed.y;
        else if (getY() + getHeight() >= RollingRocks.WORLD_HEIGHT)
            speed.y = speed.y < 0 ? speed.y : -speed.y;
    }


    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }


    public Circle getShape() {
        return circle;
    }



}
