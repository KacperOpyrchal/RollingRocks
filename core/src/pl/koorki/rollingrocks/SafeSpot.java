package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by marcin on 06.07.17.
 */

public class SafeSpot extends Actor {

    private Texture texture;


    public SafeSpot(int x, int y, Texture texture) {
        super.setPosition(x, y);
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }


}
