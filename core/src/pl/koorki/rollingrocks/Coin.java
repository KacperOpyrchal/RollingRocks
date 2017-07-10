package pl.koorki.rollingrocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

/**
 * Created by marcin on 06.07.17.
 */

public class Coin extends MyActor {

    Animation<TextureRegion> animation;
    private Circle circle;
    private float time;


    public Coin(int x, int y, TextureRegion[] spritesheet) {
        int radius = spritesheet[0].getRegionHeight()/2;
        Gdx.app.log("radius", "Radius = " + radius);
        super.setBounds(x - radius, y - radius, 2*radius, 2*radius);
        super.setPosition(x - radius, y - radius);
        circle = new Circle(x, y, radius);
        animation = new Animation<TextureRegion>(0.05f, spritesheet);
        time = 0;
    }


    @Override
    public void move(float delta, float y) {
        time += delta;
        setY(getY() - y);
        circle.setY(circle.y - y);
    }


    public Circle getShape() {
        return circle;
    }


    public void draw(Batch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(time, true);
        batch.draw(currentFrame, getX(), getY());
    }

}
