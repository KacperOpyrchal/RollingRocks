package pl.koorki.rollingrocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RollingRocks extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage stage;

	static int WIDTH = 480;
	static int HEIGHT = 800;

	OrthographicCamera camera;
	Viewport viewport;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.position.set(WIDTH/2, HEIGHT/2, 0);
		viewport = new ExtendViewport(WIDTH, HEIGHT, camera);
		stage = new Stage(viewport, batch);
		ShapeRenderer sr = new ShapeRenderer(100);
		Obstacle obs = new Obstacle(WIDTH/2, HEIGHT/2, img);
		stage.addActor(obs);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.WHITE);
		renderer.rect(0, 640, 768, 1);
		renderer.end();


		camera.update();
		batch.setProjectionMatrix(camera.combined);

		stage.draw();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
