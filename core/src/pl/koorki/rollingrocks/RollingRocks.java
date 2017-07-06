package pl.koorki.rollingrocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class RollingRocks extends ApplicationAdapter {
	SpriteBatch batch;
	Stage stage;

	static int WIDTH = 480;
	static int HEIGHT = 800;
	static float ASPECT_RATIO;
	public static int WORLD_WIDTH;
	public static int WORLD_HEIGHT;

	Texture texture;

	OrthographicCamera camera;
	ExtendViewport viewport;

	
	@Override
	public void create () {
		ASPECT_RATIO = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(WIDTH, WIDTH * ASPECT_RATIO);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(WIDTH, HEIGHT, camera);
		stage = new Stage(viewport, batch);
		WORLD_WIDTH = (int) viewport.getWorldWidth();
		WORLD_HEIGHT = (int) viewport.getWorldHeight();

		ObstacleGenerator generator = new ObstacleGenerator();
		Obstacle obs1 = generator.getNewObstacle(WORLD_HEIGHT / 4);
		Obstacle obs2 = generator.getNewObstacle(WORLD_HEIGHT / 2);
		Obstacle obs3 = generator.getNewObstacle(3 * WORLD_HEIGHT / 4);

		texture = new Texture("SafeSpot.png");


		Group group = new Group();
		group.addActor(obs1);
		group.addActor(obs2);
		group.addActor(obs3);


		stage.addActor(group);
	}

	@Override
	public void render () {
		stage.act(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(texture, 0, WORLD_HEIGHT / 8);
		batch.end();

		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

		stage.dispose();
	}
}
