package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Texture;

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
    private int heightOfCoin = 40;
    private int safeDistance = 10;

    Texture texture = new Texture("coin.png");


    public MapGenerator() {
        obstacleGenerator = new ObstacleGenerator();
        height = obstacleGenerator.getHeight();
        random = obstacleGenerator.getRandom();
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
        Y += height;
        Coin[] array = new Coin[5];
        for (int i = 0; i < 5; ++i) {
            Y += distance;
            if (random.nextBoolean()) {
                int rnd = random.nextInt(distance - height - heightOfCoin - 2 * safeDistance);
                int x = random.nextInt(RollingRocks.WORLD_WIDTH - heightOfCoin);
                array[i] = new Coin(x + heightOfCoin/2, Y + rnd + heightOfCoin/2 + safeDistance, texture);
            }
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
            coin = new Coin(x + heightOfCoin/2, y * howMuch + rnd + height + heightOfCoin/2 + safeDistance, texture);
        }
        return coin;
    }


    public Player getPlayer() {
        return new Player(RollingRocks.WORLD_WIDTH/2, yPlayer, new Texture("ball.png"));
    }


}
