package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by marcin on 07.07.17.
 */

public class MapGenerator {

    private final int distance = 500;
    private final int yPlayer = 300;
    private int currentY = 50;
    private ObstacleGenerator obstacleGenerator;


    public MapGenerator() {
        obstacleGenerator = new ObstacleGenerator();
    }



    public Obstacle[] generateMap() {
        Obstacle[] array = new  Obstacle[5];

        for (int i = 0; i < 5; ++i) {
            currentY += distance;
            array[i] = obstacleGenerator.getNewObstacle(currentY);
        }

        return array;
    }


    public Obstacle getObstacle(int y, int howMuch) {
        return obstacleGenerator.getNewObstacle(y + distance * howMuch);
    }


    public Player addPlayer() {
        return new Player(RollingRocks.WORLD_WIDTH/2, yPlayer, new Texture("ball.png"));
    }


}
