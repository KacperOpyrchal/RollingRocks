package pl.koorki.rollingrocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
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



    public Player(int x, int y, Texture texture) {
        int radius = texture.getWidth() / 2;
        super.setBounds(x - radius, y - radius, 2 * radius, 2 * radius);
        super.setPosition(x - radius, y - radius);
        circle = new Circle(x, y, radius);
        this.texture = texture;
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Down", x + ", " + y);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Up", x + ", " + y);
                super.touchUp(event, x, y, pointer, button);
            }

        });
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


}
