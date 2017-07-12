package pl.koorki.rollingrocks.actors.buttons;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by marcin on 12.07.17.
 */

public class Button extends Actor {

    private Texture skin;
    private Sound sound;
    private boolean clicked = false;

    public Button(float x, float y, Texture skin, Sound sound) {
        super.setBounds(x, y, skin.getWidth(), skin.getHeight());
        this.skin = skin;
        this.sound = sound;
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                clicked = true;
            }
        });

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(skin, getX(), getY());
    }

    public boolean wasClicked() {
        return clicked;
    }

}
