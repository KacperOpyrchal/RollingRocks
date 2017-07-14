package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Random;

import pl.koorki.rollingrocks.actors.Coin;

/**
 * Created by marcin on 11.07.17.
 */

public class CoinGenerator {

    private Random random;
    private Texture coinSheet;
    private Animation<TextureRegion> animation;

    private int radius;
    private float duration = 0.05f;

    private int rndY;
    private int minY;
    private final int safeDistance = 15;


    public CoinGenerator(Random random, int heightOfObstacle, int distance) {
        this.random = random;
        loadAnimation();
        rndY = distance - heightOfObstacle - 2*safeDistance - 2*radius;
        minY = heightOfObstacle + radius + safeDistance;
    }


    public Coin getNewCoin(int y) {
        Coin coin = null;
        if (isCoin())
            coin = new Coin(randX(), y + randY(), radius, animation);

        return coin;
    }


    private boolean isCoin() { // TODO more complex randomizing system than this
        return random.nextBoolean();
    }


    private int randX() {
        return random.nextInt(RollingRocks.WORLD_WIDTH - 2*radius) + radius;
    }


    private int randY() {
        return random.nextInt(rndY) + minY;
    }


    private void loadAnimation() {
        coinSheet = new Texture("coin_spritesheet_scaled.png");
        TextureRegion[][] coinTR = TextureRegion.split(coinSheet, coinSheet.getWidth()/6, coinSheet.getHeight()/2);
        TextureRegion[] coinAnimation = new TextureRegion[12];
        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 6; ++j)
                coinAnimation[6*i + j] = coinTR[i][j];

        radius = coinAnimation[0].getRegionWidth()/2;
        animation = new Animation<TextureRegion>(duration, coinAnimation);
    }


    public void dispose() {
        coinSheet.dispose();
    }


}
