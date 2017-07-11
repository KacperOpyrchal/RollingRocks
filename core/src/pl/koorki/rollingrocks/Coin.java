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


    public Coin(int x, int y, int radius, Animation<TextureRegion> animation) {
        this.animation = animation;
        super.setBounds(x - radius, y - radius, 2*radius, 2*radius);
        circle = new Circle(x, y, radius);
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
