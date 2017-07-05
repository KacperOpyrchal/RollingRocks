package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by marcin on 05.07.17.
 */

public class Obstacle extends Actor {

    private Texture texture;


    public Obstacle (int x, int y, Texture texture) {
        setPosition(x, y);
        setBounds(x, y, texture.getWidth(), texture.getHeight());
        setTouchable(Touchable.disabled);
        this.texture = texture;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
}
