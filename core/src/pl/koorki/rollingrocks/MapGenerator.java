package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by marcin on 07.07.17.
 */

public class MapGenerator {

    private final int distance = 500;
    public static final int yPlayer = 450;
    private int currentY = yPlayer - distance/2;
    private int Y = yPlayer - distance/2;
    private ObstacleGenerator obstacleGenerator;
    private Random random;
    private int height;
    private int heightOfCoin;
    private int safeDistance = 20;

    TextureRegion[] coinAnimation;


    public MapGenerator() {
        obstacleGenerator = new ObstacleGenerator();
        height = obstacleGenerator.getHeight();
        random = obstacleGenerator.getRandom();
        makeTextureRegionCoin();
    }



    private void makeTextureRegionCoin() {
        coinAnimation = new TextureRegion[12];
        Texture coinSpriteSheet = new Texture("coin_spritesheet.png");
        TextureRegion[][] coinSheet = TextureRegion.split(coinSpriteSheet, coinSpriteSheet.getWidth()/6, coinSpriteSheet.getHeight()/2);
        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 6; ++j)
                coinAnimation[6*i + j] = coinSheet[i][j];
        heightOfCoin = coinAnimation[0].getRegionHeight();
    }



    public Obstacle[] getObstacles() {
        Obstacle[] array = new  Obstacle[5];

        for (int i = 0; i < 5; ++i) {
            currentY += distance;
            array[i] = obstacleGenerator.getNewObstacle(currentY);
        }

        return array;
    }


    public Coin[] getCoins() {
        Coin[] array = new Coin[5];
        for (int i = 0; i < 5; ++i) {
            Y += distance;
            array[i] = getCoin(Y, 1);
        }
        return array;
    }

    public Obstacle getObstacle(int y, int howMuch) {
        return obstacleGenerator.getNewObstacle(y + distance * howMuch);
    }


    public Coin getCoin(int y, int howMuch) {
        Coin coin = null;
        if (random.nextBoolean()) {
            int rnd = random.nextInt(distance - height - heightOfCoin - 2 * safeDistance);
            int x = random.nextInt(RollingRocks.WORLD_WIDTH - heightOfCoin);
            coin = new Coin(x + heightOfCoin/2, y * howMuch + rnd + height + heightOfCoin/2 + safeDistance, coinAnimation);
        }
        return coin;
    }


    public Player getPlayer() {
        return new Player(RollingRocks.WORLD_WIDTH/2, yPlayer, new Texture("ball.png"));
    }


}
