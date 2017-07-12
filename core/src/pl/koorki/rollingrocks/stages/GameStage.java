package pl.koorki.rollingrocks.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by marcin on 10.07.17.
 */

public class GameStage extends Stage {

    private pl.koorki.rollingrocks.actors.Player player;

    public GameStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
    }

    @Override
    public void draw() {
        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        getBatch().begin();

        for (Actor actor : getActors())
            if (!(actor instanceof pl.koorki.rollingrocks.actors.Player))
                ((pl.koorki.rollingrocks.actors.MyActor) actor).draw(getBatch());

        player.draw(getBatch());

        getBatch().end();
    }

    @Override
    public void act() {
        float delta = Gdx.graphics.getDeltaTime();
        float shift = player.move(delta);

        for (Actor actor : getActors())
            if (!(actor instanceof pl.koorki.rollingrocks.actors.Player))
                ((pl.koorki.rollingrocks.actors.MyActor) actor).move(delta, shift);

    }

    public void addPlayer(pl.koorki.rollingrocks.actors.Player player) {
        addActor(player);
        this.player = player;
    }


}
