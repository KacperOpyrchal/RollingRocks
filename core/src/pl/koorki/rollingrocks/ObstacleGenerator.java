package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

/**
 * Created by marcin on 05.07.17.
 */

public class ObstacleGenerator {

    private ObstacleBuilder builder;
    private Random random = new Random();
    private int width = RollingRocks.WORLD_WIDTH;
    private int height = 25;
    private int colors = 4;

    private int minSpeed = 50;
    private int rndSpeed = 90;

    private int minGapWidth = 80;
    private int rndGapWidth = 90;

    private Sprite[] obstacles = new Sprite[colors];
    private Pixmap[] pixmaps = new Pixmap[colors];

    private Texture gapTexture;
    private Pixmap gapPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);


    public ObstacleGenerator() {
        for (int i = 0; i < colors; ++i)
            obstacles[i] = makeSprite(pixmaps[i], i);

        gapTexture = makeGapTexture(gapPixmap);

        builder = new ObstacleBuilder();
    }


    public Obstacle getNewObstacle(int y) {
        Sprite gap = makeGapSprite();
        int x = (int) ((width - gap.getWidth()) / 2);

        Obstacle obstacle = builder
                .setX(x)
                .setY(y)
                .setSpeed(randSpeed())
                .setObstacle(randSprite())
                .setGap(gap)
                .build();

        return obstacle;
    }


    private Sprite makeSprite(Pixmap pixmap, int number) {
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        switch (number) {
            case 0:  pixmap.setColor(Color.CORAL);      break;
            case 1:  pixmap.setColor(Color.OLIVE);      break;
            case 2:  pixmap.setColor(Color.GOLDENROD);  break;
            case 3:  pixmap.setColor(Color.SLATE);      break;
        }
        pixmap.fill();

        return new Sprite(new Texture(pixmap), width, height);
    }


    private Texture makeGapTexture(Pixmap pixmap) {
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();

        return new Texture(pixmap);
    }


    private int randSpeed() {
        int speed = random.nextInt(rndSpeed) + minSpeed;
        boolean minus = random.nextBoolean();
        return minus == true ? -speed : speed;
    }


    private Sprite randSprite() {
        int number = random.nextInt(colors);

        return obstacles[number];
    }


    private Sprite makeGapSprite() {
        int gapWidth = random.nextInt(rndGapWidth) + minGapWidth;

        return new Sprite(gapTexture, gapWidth, height);
    }


    public void dispose() {
        for (Sprite sprite : obstacles)
            sprite.getTexture().dispose();

        for (Pixmap pixmap : pixmaps)
            pixmap.dispose();

        gapPixmap.dispose();
        gapTexture.dispose();
    }


}