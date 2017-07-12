package pl.koorki.rollingrocks.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.koorki.rollingrocks.RollingRocks;
import pl.koorki.rollingrocks.actors.buttons.Button;

/**
 * Created by marcin on 11.07.17.
 */

public class GameOverScreen extends InputListener implements Screen  {

    final RollingRocks game;
    private Stage stage;
    private Button playButton;
    private Texture playBtn = new Texture("play_button.png");

    public GameOverScreen(final RollingRocks game) {
        this.game = game;
        stage = new Stage(game.viewport, game.batch);
        playButton = new Button((game.viewport.getWorldWidth() - playBtn.getWidth())/2, (game.viewport.getWorldHeight() - playBtn.getHeight())/2, playBtn, null);
        stage.addActor(playButton);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        if (playButton.wasClicked()) {
            dispose();
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
