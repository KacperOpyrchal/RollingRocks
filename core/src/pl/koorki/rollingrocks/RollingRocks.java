package pl.koorki.rollingrocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class RollingRocks extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage stage;

	static int WIDTH = 480;
	static int HEIGHT = 800;
	static float ASPECT_RATIO;
	public static int WORLD_WIDTH;
	public static int WORLD_HEIGHT;

	OrthographicCamera camera;
	ExtendViewport viewport;

	
	@Override
	public void create () {
		ASPECT_RATIO = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera(WIDTH, WIDTH * ASPECT_RATIO);
		camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
		viewport = new ExtendViewport(WIDTH, HEIGHT, camera);
		stage = new Stage(viewport, batch);
		WORLD_WIDTH = (int) viewport.getWorldWidth();
		WORLD_HEIGHT = (int) viewport.getWorldHeight();
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		Texture texture = new Texture(pixmap);
		Sprite obstacle = new Sprite(texture, WORLD_WIDTH, 50);
		Sprite gap = new Sprite(img);
		Obstacle obs = new Obstacle((int)(WORLD_WIDTH / 2.0), (int)(WORLD_HEIGHT / 2.0), 100, obstacle, gap);
		stage.addActor(obs);
	}

	@Override
	public void render () {
		stage.act(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		stage.draw();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();

		stage.dispose();
	}
}
