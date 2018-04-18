package swipe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TimeCounter {
    private SpriteBatch batch;
    private int score = 0;
    private BitmapFont timeFont;
    private BitmapFont countdownFont;
    private Texture texture;
    private float scale = 1.5f;
    private float additionalScale = 0f;
    private int time = 0;
    private int remainedTime = 0;
    private AlkoNinja alkoNinja;

    public TimeCounter(SpriteBatch batch, int time, AlkoNinja alkoNinja){
        this.batch = batch;
        this.time = time;
        remainedTime = time;
        this.alkoNinja = alkoNinja;
        texture = new Texture(Gdx.files.internal("font.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        timeFont = new BitmapFont(Gdx.files.internal("font.fnt"), new TextureRegion(texture), false);
        countdownFont = new BitmapFont(Gdx.files.internal("font.fnt"), new TextureRegion(texture), false);
        timeFont.getData().setScale(1.3f);

    }

    public void printTime(){
        countdownFont.getData().setScale((remainedTime < 10 ? 1.5f : 1.3f) + additionalScale);
        timeFont.draw(batch, "Czas: ", 20,Gdx.graphics.getHeight()-102);
        if(remainedTime <=10) {
            countdownFont.setColor(1, remainedTime/10f, remainedTime/10f, 1);
        }
        countdownFont.draw(batch, remainedTime+"", 135,Gdx.graphics.getHeight()-100);
        if (additionalScale >= .1)
            additionalScale-=.1f;
    }

    public void addTick(){
        if (remainedTime > 0) {
            remainedTime -= 1;
            additionalScale = .5f;
            if (remainedTime <= 10)
                additionalScale += .4f;
        }
        else {
            alkoNinja.timeUp();
        }
    }
}
