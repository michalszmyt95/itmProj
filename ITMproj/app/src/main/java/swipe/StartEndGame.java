package swipe;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class StartEndGame {

    private final Stage startStage;
    private final Stage endStage;
    private SpriteBatch batch;
    private AlkoNinja alkoNinja;
    private BitmapFont font;
    private BitmapFont textFont;
    private Texture texture;
    private TextButton startButton;
    private TextButton endButton;
    private TextButton.TextButtonStyle textButtonStyle;
    private int points;

    public StartEndGame(SpriteBatch batch, final AlkoNinja alkoNinja){
        this.batch = batch;
        this.alkoNinja = alkoNinja;
        startStage = new Stage();
        endStage = new Stage();

        texture = new Texture(Gdx.files.internal("font.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), new TextureRegion(texture), false);
        textFont = new BitmapFont(Gdx.files.internal("font.fnt"), new TextureRegion(texture), false);
        font.getData().setScale(2f);
        textFont.getData().setScale(1.3f);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor =new Color(1,0,1,1);
        startButton = new TextButton("Ja nie dam rady?\nPotrzymaj mi piwo!", textButtonStyle);
        endButton = new TextButton("No to elo!", textButtonStyle);

        startButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                alkoNinja.startClicked();
                return true;
            }
        });

        endButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                alkoNinja.endClicked();
                return true;
            }
        });
        startStage.addActor(startButton);
        endStage.addActor(endButton);
    }

    public void printStart(){
        Gdx.input.setInputProcessor(startStage);
        startButton.setPosition(Gdx.graphics.getWidth()/2 - startButton.getWidth()/2,100);
        textFont.draw(batch, "          Twoim zadaniem jest zebranie\njak największej ilośći alkoholu w ciągu 30 sekund. \n" +
                "                   Lecz uważaj!\n        Mimo że denaturat jest alkoholem,\n           za nic nie chcesz go dotknąć!\n" +
                "           Dasz rade młody padawanie?", 170,Gdx.graphics.getHeight()-80);
        startButton.draw(batch, 1f);
    }

    public void printEnd(){
        Gdx.input.setInputProcessor(endStage);
        textFont.getData().setScale(1.5f);
        font.getData().setScale(3f);
        endButton.setPosition(Gdx.graphics.getWidth()/2 - endButton.getWidth()/2,100);
        textFont.draw(batch, "     GG WP EZ\nUzbierałeś " + points+" trunków", 400,Gdx.graphics.getHeight()-150);
        textFont.getData().setScale(2f);
        String endText = "";
        if (points==0){
            endText = "         Okrągłe 0!\n    Przegryw! XDDD";
        }
        else if (points<=10){
            endText = "     Mizerny wynik...\nMoże idź już do domu?";
        }
        else if (points>10 && points <= 20){
            endText = "       Mogło być lepiej\n    W sume gorzej też...";
        }
        else if (points>20 && points <= 30){
            endText = "Wynik w normie pij dalej!";
        }
        else if (points>30){
            endText = "Ponad 30?! Wciągałeś coś?!";
        }
        textFont.draw(batch, endText, 250,Gdx.graphics.getHeight()-300);
        endButton.draw(batch, 1f);
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
