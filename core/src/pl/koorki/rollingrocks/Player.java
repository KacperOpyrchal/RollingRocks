package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Vector2 speed = new Vector2(0, 0);
    private Vector2 line = new Vector2(0, 0);
    private ShapeRenderer renderer = new ShapeRenderer();
    private Texture texture = new Texture("ball.png");
    private Sprite sprite = new Sprite(texture);

    private final float touchBoundsScale = 3.5f;


    public Player(int x, int y) {
        preparePlayer(x, y);

        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                line.set(circle.x, circle.y);
                line = stageToLocalCoordinates(line);
                line.sub(x, y);
            }


            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                speed.set(circle.x, circle.y);
                speed = stageToLocalCoordinates(speed);
                speed.sub(x, y);
                line.set(0, 0);
            }

        });
    }


    public void draw(Batch batch) {
        drawLine(batch);
        sprite.draw(batch);
    }

    private void drawLine(Batch batch) {
        batch.end();

        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);

        renderer.rectLine(circle.x, circle.y, circle.x + line.x, circle.y + line.y, 3);
        renderer.end();

        batch.begin();
    }



    private void preparePlayer(int x, int y) {
        circle = new Circle(x, y, texture.getWidth()/2);
        float length = touchBoundsScale*circle.radius;
        super.setBounds(x - length, y - length, 2*length, 2*length);
        setPosition(x, y);
        setTouchable(Touchable.enabled);
    }


    public float move(float delta) {
        collisionWithFrames();

        float x = circle.x;
        float y = circle.y;

        x += speed.x * delta;
        y += speed.y * delta;

        float speedY = 0;

        if (y > MapGenerator.yPlayer) {
            speedY = y - MapGenerator.yPlayer;
            y = MapGenerator.yPlayer;
        }

        setPosition(x, y);
        return speedY;
    }


    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - touchBoundsScale*circle.radius, y - touchBoundsScale*circle.radius);
        sprite.setPosition(x - circle.radius, y - circle.radius);
        circle.setPosition(x, y);
    }


    private void collisionWithFrames() {
        if (getSpriteX() <= 0)
            speed.x = Math.abs(speed.x);
        else if (getSpriteX() + getSpriteWidth() >= RollingRocks.WORLD_WIDTH)
            speed.x = -Math.abs(speed.x);

        if (getSpriteY() <= 0)
            speed.y = Math.abs(speed.y);
    }


    public float getSpriteX() {
        return sprite.getX();
    }

    public float getSpriteY() {
        return sprite.getY();
    }

    private float getSpriteWidth() {
        return sprite.getWidth();
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

    public Circle getShape() {
        return circle;
    }

    public void dispose() {
        texture.dispose();
    }

}
