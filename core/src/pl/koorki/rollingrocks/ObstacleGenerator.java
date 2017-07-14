package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Random;

import pl.koorki.rollingrocks.actors.Obstacle;

/**
 * Created by marcin on 05.07.17.
 */

public class ObstacleGenerator {

    private ObstacleBuilder builder;
    private Random random;
    private int width = RollingRocks.WORLD_WIDTH;
    private int height;
    private int colors = 4;

    private int minSpeed = 70;
    private int rndSpeed = 100;

    private int minGapWidth = 200;
    private int rndGapWidth = 100;

    private Texture[] textures = new Texture[colors];
    private Pixmap[] pixmaps = new Pixmap[colors];

    private Texture gapTexture;
    private Pixmap gapPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);


    public ObstacleGenerator(Random random, int height) {
        this.random = random;
        this.height = height;
        for (int i = 0; i < colors; ++i)
            textures[i] = makeTexture(pixmaps[i], i);

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
                .setObstacle(makeSprite())
                .setGap(gap)
                .build();

        return obstacle;
    }


    private Texture makeTexture(Pixmap pixmap, int number) {
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        switch (number) {
            case 0:  pixmap.setColor(Color.CORAL);      break;
            case 1:  pixmap.setColor(Color.OLIVE);      break;
            case 2:  pixmap.setColor(Color.GOLDENROD);  break;
            case 3:  pixmap.setColor(Color.SLATE);      break;
        }
        pixmap.fill();

        return new Texture(pixmap);
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


    private Sprite makeSprite() {
        int number = random.nextInt(colors);
        return new Sprite(textures[number], width, height);
    }


    private Sprite makeGapSprite() {
        int gapWidth = random.nextInt(rndGapWidth) + minGapWidth;
        return new Sprite(gapTexture, gapWidth, height);
    }


    public void dispose() {
        for (Texture texture : textures)
            texture.dispose();

        for (Pixmap pixmap : pixmaps)
            pixmap.dispose();

        gapPixmap.dispose();
        gapTexture.dispose();
    }


}
