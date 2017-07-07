package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by marcin on 07.07.17.
 */

public class GameManager {

    private GameStage stage;

    public GameManager (Viewport viewport, SpriteBatch batch) {
        this.stage = new GameStage(viewport, batch);
    }


    public void update() {

    }



}
