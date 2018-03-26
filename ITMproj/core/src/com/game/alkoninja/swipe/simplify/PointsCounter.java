package com.game.alkoninja.swipe.simplify;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PointsCounter {

    private SpriteBatch batch;
    private int score = 0;
    private BitmapFont font = new BitmapFont();

    public PointsCounter(SpriteBatch batch){
        this.batch = batch;
        font.getData().setScale(3);
    }

    public void printScore(){
        font.draw(batch, "Score: " + score, 10,Gdx.graphics.getHeight()-10);
    }

    public void addScore(int score){
        if (this.score + score < 0)
            return;
        this.score += score;
    }
}
