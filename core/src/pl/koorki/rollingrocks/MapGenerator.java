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
    private CoinGenerator coinGenerator;

    private Random random = new Random();
    private final int height = 32;



    public MapGenerator() {
        obstacleGenerator = new ObstacleGenerator(random, height);
        coinGenerator = new CoinGenerator(random, height, distance);
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
        return coinGenerator.getNewCoin(y + howMuch*distance);
    }


    public Player getPlayer() {
        return new Player(RollingRocks.WORLD_WIDTH/2, yPlayer);
    }


    public void  dispose() {
        obstacleGenerator.dispose();
        coinGenerator.dispose();
    }


}
