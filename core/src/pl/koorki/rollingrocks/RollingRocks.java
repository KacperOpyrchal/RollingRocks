package pl.koorki.rollingrocks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class RollingRocks extends Game {
	SpriteBatch batch;
	//GameStageOldVersion stage;
	GameStage stage;

	static int WIDTH = 720;
	static int HEIGHT = 1280;
	static float ASPECT_RATIO;
	public static int WORLD_WIDTH;
	public static int WORLD_HEIGHT;



	OrthographicCamera camera;
	ExtendViewport viewport;


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
		//stage = new GameStageOldVersion(viewport, batch);
		stage = new GameStage(viewport, batch);
		WORLD_WIDTH = (int) viewport.getWorldWidth();
		WORLD_HEIGHT = (int) viewport.getWorldHeight();
		gameManager = new GameManager(stage);
		Gdx.input.setInputProcessor(stage);
		//stage.loadMap();

	}

	@Override
	public void render () {

		gameManager.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
	}
}
