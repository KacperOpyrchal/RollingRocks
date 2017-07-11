package pl.koorki.rollingrocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by marcin on 11.07.17.
 */

public class HUD {

    private Stage stage;
    private int score = 0;
    private int coins = 0;

    Label scoreLabel;
    Label coinsLabel;
    //Button pause; i'm waiting for Button class
    BitmapFont font = new BitmapFont();


    public HUD(Stage stage) {
        this.stage = stage;
        scoreLabel = new Label(String.format("SCORE: %04d", score), new Label.LabelStyle(font, Color.WHITE));
        coinsLabel = new Label(String.format("COINS: %04d", coins), new Label.LabelStyle(font, Color.WHITE));
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        scoreLabel.setFontScale(2.5f);
        coinsLabel.setFontScale(2.5f);
        table.add(scoreLabel).expandX().padTop(25);
        table.add(coinsLabel).expandX().padTop(25);
        stage.addActor(table);
    }

    public void increaseScore() {
        scoreLabel.setText(String.format("SCORE: %04d", ++score));
    }

    public void increaseCoins() {
        coinsLabel.setText(String.format("COINS: %04d", ++coins));
    }

}
