package pl.koorki.rollingrocks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.koorki.rollingrocks.GameManager;
import pl.koorki.rollingrocks.stages.GameStage;
import pl.koorki.rollingrocks.HUD;
import pl.koorki.rollingrocks.RollingRocks;

/**
 * Created by marcin on 07.07.17.
 */

public class GameScreen implements Screen {

    final RollingRocks game;
    private GameStage stage;
    private Stage hudStage;
    private HUD hud;
    private GameManager manager;

    public GameScreen(final RollingRocks game) {
        this.game = game;
        stage = new GameStage(game.viewport, game.batch);
        RollingRocks.WORLD_WIDTH = (int) game.viewport.getWorldWidth();
        RollingRocks.WORLD_HEIGHT = (int) game.viewport.getWorldHeight();
        hudStage = new Stage(stage.getViewport(), stage.getBatch());
        hud = new HUD(hudStage);
        Gdx.input.setInputProcessor(stage);
        manager = new GameManager(stage, hud);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        manager.update();
        if (manager.getCollision())
            game.setScreen(new GameOverScreen(game));
        stage.draw();
        hudStage.draw();
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
