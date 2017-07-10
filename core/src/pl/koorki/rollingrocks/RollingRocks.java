package pl.koorki.rollingrocks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import pl.koorki.rollingrocks.screens.GameScreen;

public class RollingRocks extends Game {
	public SpriteBatch batch;

	static int WIDTH = 720;
	static int HEIGHT = 1280;
	static float ASPECT_RATIO;
	public static int WORLD_WIDTH;
	public static int WORLD_HEIGHT;



	public OrthographicCamera camera;
	public ExtendViewport viewport;


///////////////////////////////////////
	GameManager gameManager;
///////////////////////////////////////
	
	@Override
	public void create () {
		ASPECT_RATIO = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(WIDTH, WIDTH * ASPECT_RATIO);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(WIDTH, HEIGHT, camera);
		WORLD_WIDTH = (int) viewport.getWorldWidth();
		WORLD_HEIGHT = (int) viewport.getWorldHeight();

		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
