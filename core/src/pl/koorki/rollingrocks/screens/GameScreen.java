package pl.koorki.rollingrocks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import pl.koorki.rollingrocks.GameManager;
import pl.koorki.rollingrocks.GameStage;
import pl.koorki.rollingrocks.RollingRocks;

/**
 * Created by marcin on 07.07.17.
 */

public class GameScreen implements Screen {

    final RollingRocks game;
    private GameStage stage;
    private GameManager manager;

    public GameScreen(final RollingRocks game) {
        this.game = game;
        stage = new GameStage(game.viewport, game.batch);
        RollingRocks.WORLD_WIDTH = (int) game.viewport.getWorldWidth();
        RollingRocks.WORLD_HEIGHT = (int) game.viewport.getWorldHeight();
        Gdx.input.setInputProcessor(stage);
        manager = new GameManager(stage);
    }


    @Override
    public void show() {
        Gdx.app.log("show", "Now i know when it works");
    }

    @Override
    public void render(float delta) {
        manager.update();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
