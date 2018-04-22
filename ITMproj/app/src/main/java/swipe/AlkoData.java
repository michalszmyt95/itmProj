package swipe;


import com.badlogic.gdx.graphics.g2d.Sprite;

public class AlkoData {

    public Sprite sprite;
    public AlkoType alkoType;

    public AlkoData(Sprite sprite, AlkoType alkoType){
        this.sprite = sprite;
        this.alkoType = alkoType;
    }

    public enum AlkoType{
        BEER,
        CHAMPAGNE,
        WINE,
        DENATURAT
    }
}


