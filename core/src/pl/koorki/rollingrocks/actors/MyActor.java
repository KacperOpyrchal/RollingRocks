package pl.koorki.rollingrocks.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by marcin on 10.07.17.
 */

public abstract class MyActor extends Actor {

    public abstract void move(float delta, float shift);

    public abstract void draw(Batch batch);

}
