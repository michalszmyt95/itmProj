package swipe;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;


public class ObjectSpawner {

    private final Texture beer;
    private final Sprite beerSprite;
    private final Texture champagne;
    private final Sprite champagneSprite;
    private final Texture wine;
    private final Sprite wineSprite;
    private final Texture denaturat;
    private final Sprite denaturatSprite;

    //private final BodyDef bodyDef;
    private final FixtureDef fixtureDefBeer;
    private final FixtureDef fixtureDefChampagne;
    private final FixtureDef fixtureDefWine;
    private final FixtureDef fixtureDefDenaturat;
    private World world;
    private Queue<Fixture> beerQueue = new Queue<Fixture>();
    private LinkedList<FixtureDef> fixturesDefs = new LinkedList<FixtureDef>();
    private HashMap<FixtureDef, Sprite> fixturesDefSprites = new HashMap<FixtureDef, Sprite>();
    private HashMap<FixtureDef, AlkoData.AlkoType> fixturesDefTypes = new HashMap<FixtureDef, AlkoData.AlkoType>();

    private Array<Fixture> beerVisible = new Array<Fixture>();
    private Integer width;
    private Random random = new Random();
    float s = .5f;
    private Array<PolygonShape> shapes = new Array<PolygonShape>();

    short beerMask = 0x0001;
    short champagneMask = 0x0010;
    short wineMask = 0x0100;
    short denaturatMask = 0x1000;

    int startY = -1;

    public ObjectSpawner(World world) {
        this.world = world;
        width = Gdx.graphics.getWidth();
        int objHeight = 140;
        int objWidth = objHeight / 2;

        /*Pixmap pixmap200 = new Pixmap(Gdx.files.internal("beer.jpg"));
        Pixmap pixmap100 = new Pixmap(objWidth, objHeight, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        beer = new Texture(pixmap100);
        pixmap200.dispose();
        pixmap100.dispose();*/
        beer = new Texture(Gdx.files.internal("beer140.png"));
        beerSprite = new Sprite(beer);
        champagne = new Texture(Gdx.files.internal("champagne140.png"));
        champagneSprite = new Sprite(champagne);
        wine = new Texture(Gdx.files.internal("wine140.png"));
        wineSprite = new Sprite(wine);
        denaturat = new Texture(Gdx.files.internal("denaturat140.png"));
        denaturatSprite = new Sprite(denaturat);


        fixtureDefBeer = createFixtureDef(beerSprite, beerMask, AlkoData.AlkoType.BEER);
        fixtureDefChampagne = createFixtureDef(champagneSprite, champagneMask, AlkoData.AlkoType.CHAMPAGNE);
        fixtureDefWine = createFixtureDef(wineSprite, wineMask, AlkoData.AlkoType.WINE);
        fixtureDefDenaturat = createFixtureDef(denaturatSprite, denaturatMask, AlkoData.AlkoType.DENATURAT);
        //body.applyTorque(1000 *1000000,true);
        for (Shape shape : shapes) {
            shape.dispose();

        }
    }

    public void createBeers(Integer beers) {
        for (int i = 0; i < beers; i++) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.linearDamping = 0.1f;
            bodyDef.angularDamping = 0.1f;

            bodyDef.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            FixtureDef fixtureDef = getNextDixtureDef();
            Body body = world.createBody(bodyDef);
            body.resetMassData();
            body.getMassData().mass = 1;
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setDensity(0);
            fixture.getShape();
            fixture.setUserData(new AlkoData(new Sprite(fixturesDefSprites.get(fixtureDef)), fixturesDefTypes.get(fixtureDef)));
            body.setActive(false);
            beerQueue.addLast(fixture);
        }
    }

    public void spawn() {
        Integer x = random.nextInt(width / (int) AlkoNinja.PTM);
        Integer y = startY;
        Integer randomObj = random.nextInt(beerQueue.size);
        Fixture fixture = beerQueue.get(randomObj);
        beerQueue.removeIndex(randomObj);
        Float angle = angleByPos(precFromMiddle(x));
        Float randomAngle = angle + (10 - random.nextFloat() * 20);
        Vector2 shotDir = radiansToVector(degreeToRadians(randomAngle));
        if (randomAngle < 0) {
            shotDir.x *= -1;
        }
        Body body = fixture.getBody();
        body.setActive(true);
        body.setTransform(x, y, body.getAngle());
        beerVisible.add(fixture);
        float force = body.getMass() * 4.5f;
        body.applyAngularImpulse(random.nextFloat() * (force / 2 - force / 4) + force / 4 * (randomAngle < 0 ? 1 : -1), true);
        body.applyLinearImpulse((shotDir.x) * (force + random.nextFloat() * force), (force + random.nextFloat() * (force * 2 - force) + force), body.getPosition().x, body.getPosition().y, true);
    }

    public Float precFromMiddle(Integer x) {
        return x / (width / AlkoNinja.PTM / 2f) - 1;
    }

    public Float angleByPos(float pos) {
        return -80 * pos;
    }

    public double degreeToRadians(float degree) {
        if (degree < 0)
            degree = 360 - degree;
        return Math.toRadians(degree);
    }

    public Vector2 radiansToVector(double radians) {
        return new Vector2((float) Math.cos(radians), (float) Math.sin(radians));
    }

    public void bodyHit(Fixture fixture) {
        beerVisible.removeValue(fixture, false);
        beerQueue.addLast(fixture);
        fixture.getBody().setActive(false);
        fixture.getBody().setTransform(-10, -10, 0);
    }

    public Array<Fixture> getBeerVisible() {
        return beerVisible;
    }

    private FixtureDef createFixtureDef(Sprite sprite, short mask, AlkoData.AlkoType alkoType) {
        PolygonShape shape = new PolygonShape();
        FixtureDef fixture = new FixtureDef();
        shape.setAsBox(sprite.getWidth() / 2f / AlkoNinja.PTM, sprite.getHeight() / 2f / AlkoNinja.PTM);
        shapes.add(shape);

        fixture.shape = shape;
        //shape.dispose();

        fixture.density = 1f;
        fixture.friction = .5f;
        fixture.filter.maskBits = mask;
        fixture.filter.categoryBits = mask;

        fixturesDefs.offer(fixture);
        fixturesDefSprites.put(fixture, sprite);
        fixturesDefTypes.put(fixture, alkoType);
        return fixture;
    }

    private FixtureDef getNextDixtureDef() {
        FixtureDef fixtureDef = fixturesDefs.pop();
        fixturesDefs.offer(fixtureDef);
        return fixtureDef;
    }

    public void changeXPosIfOnEdge(){
        for (Fixture fixture: beerVisible) {
            Body body = fixture.getBody();
            if(body.getPosition().x > Gdx.graphics.getWidth()/ AlkoNinja.PTM){
                body.setTransform(0, body.getPosition().y, body.getAngle());
            }else if(body.getPosition().x < 0){
                body.setTransform(Gdx.graphics.getWidth()/ AlkoNinja.PTM, body.getPosition().y, body.getAngle());
            }
        }
    }

    public void removeIfFell() {
        for (Fixture fixture: beerVisible) {
            Body body = fixture.getBody();
            if(body.getPosition().y < startY){
                bodyHit(fixture);
            }
        }
    }
}
