package pl.koorki.rollingrocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
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
    private Vector2 speed;

    private boolean move;



    public Player(int x, int y, Texture texture) {
        int radius = texture.getWidth() / 2;
        circle = new Circle(x, y, radius);
        super.setBounds(x - radius, y - radius, 2 * radius, 2 * radius);
        setPosition(x, y);
        this.texture = texture;
        setTouchable(Touchable.enabled);
        speed = new Vector2(0, 0);
        move = false;

        addListener(new InputListener() {


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }


            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector2 coord = new Vector2(circle.x, circle.y);
                coord = stageToLocalCoordinates(coord);
                speed.set(coord.x - x, coord.y - y);
            }


            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                move = true;
            }

        });
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        if (!move) return;

        float x = circle.x;
        float y = circle.y;

        x += speed.x * delta;
        y += speed.y * delta;

        setPosition(x, y);
    }


    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - circle.radius, y - circle.radius);
        circle.setPosition(x, y);
    }
}
