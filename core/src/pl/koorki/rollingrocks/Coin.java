package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;

/**
 * Created by marcin on 06.07.17.
 */

public class Coin extends MyActor {

    private Texture texture;
    private Circle circle;


    public Coin(int x, int y, Texture texture) {
        super.setPosition(x - texture.getWidth()/2, y - texture.getHeight()/2);
        super.setBounds(x - texture.getWidth()/2, y - texture.getHeight()/2, texture.getWidth(), texture.getHeight());
        circle = new Circle(x, y, texture.getHeight()/2);
        this.texture = texture;
    }


    @Override
    public void move(float delta, float y) {
        setY(getY() - y);
        circle.setY(circle.y - y);
    }


    public Circle getShape() {
        return circle;
    }


    public void draw(Batch batch) {
        batch.draw(texture, getX(), getY());
    }

}
