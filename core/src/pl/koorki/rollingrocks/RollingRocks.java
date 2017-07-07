package pl.koorki.rollingrocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class RollingRocks extends ApplicationAdapter {
	SpriteBatch batch;
	GameStage stage;

	static int WIDTH = 720;
	static int HEIGHT = 1280;
	static float ASPECT_RATIO;
	public static int WORLD_WIDTH;
	public static int WORLD_HEIGHT;

	Texture texture;

	OrthographicCamera camera;
	ExtendViewport viewport;

	int xLeftTexture;

	
	@Override
	public void create () {
		ASPECT_RATIO = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(WIDTH, WIDTH * ASPECT_RATIO);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(WIDTH, HEIGHT, camera);
		stage = new GameStage(viewport, batch);
		WORLD_WIDTH = (int) viewport.getWorldWidth();
		WORLD_HEIGHT = (int) viewport.getWorldHeight();
		Gdx.input.setInputProcessor(stage);

		ObstacleGenerator generator = new ObstacleGenerator();
		Obstacle obs1 = generator.getNewObstacle(WORLD_HEIGHT / 4);
		Obstacle obs2 = generator.getNewObstacle(WORLD_HEIGHT / 2);
		Obstacle obs3 = generator.getNewObstacle(3 * WORLD_HEIGHT / 4);

		Player player = new Player(WORLD_WIDTH / 2, WORLD_HEIGHT / 6, new Texture("ball.png"));

		texture = new Texture("safe_spot.png");
		SafeSpot spot = new SafeSpot(0, 10, texture);
		SafeSpot spot1 = new SafeSpot(0, 1000, texture);
		SafeSpot spot2 = new SafeSpot(0, 766, texture);
		xLeftTexture = (WORLD_WIDTH - texture.getWidth()) / 2;


		stage.addObstacle(obs1);
		stage.addObstacle(obs2);
		stage.addObstacle(obs3);

		stage.addPlayer(player);

	}

	@Override
	public void render () {
		stage.act();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
	}
}
