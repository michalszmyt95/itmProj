package swipe;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;


public class ObjectRenderer {

    private SpriteBatch batch;

    public ObjectRenderer(SpriteBatch batch) {
        this.batch = batch;
    }

    public void renderObjects(Array<Fixture> objects) {
        for (Fixture object : objects) {
            Sprite sprite = ((AlkoData) object.getUserData()).sprite;
            Body body = object.getBody();
            sprite.setPosition((body.getPosition().x * AlkoNinja.PTM) - sprite.getWidth()/2,
                    (body.getPosition().y * AlkoNinja.PTM) -sprite.getHeight()/2 );
            sprite.setRotation((float)Math.toDegrees(body.getAngle()));
            batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
				sprite.getOriginY(),
				sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
						getScaleY(),sprite.getRotation());
        }
    }

}
