package swipe;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PointsCounter {

    private SpriteBatch batch;
    private int score = 0;
    private BitmapFont scoreFont;
    private BitmapFont pointsFont;
    private Texture texture;
    private float scale = 1.5f;
    private float additionalScale = 0f;
    private boolean denaturatHit = false;
    public PointsCounter(SpriteBatch batch){
        this.batch = batch;

        texture = new Texture(Gdx.files.internal("font.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scoreFont = new BitmapFont(Gdx.files.internal("font.fnt"), new TextureRegion(texture), false);
        pointsFont = new BitmapFont(Gdx.files.internal("font.fnt"), new TextureRegion(texture), false);
        scoreFont.getData().setScale(1.5f);

    }

    public void printScore(){
        pointsFont.getData().setScale(1.5f + additionalScale);
        scoreFont.draw(batch, "Punkty: ", 20,Gdx.graphics.getHeight()-30);
        if (denaturatHit && additionalScale > .1f){
            pointsFont.setColor(1,0,0,1);}
        else if(!denaturatHit && additionalScale > .1f){
            pointsFont.setColor(0,1,0,1);
        }
        else{
            pointsFont.setColor(1,1,1,1);
        }
        pointsFont.draw(batch, ""+ score, 200,Gdx.graphics.getHeight()-30);
        if (additionalScale >= .1)
            additionalScale-=.1f;
    }

    public void addScore(int score){
        if (this.score + score < 0)
            return;
        denaturatHit = score < 0;
        this.score += score;
        additionalScale = .5f;

    }

    public int getPoints() {
        return score;
    }
}
