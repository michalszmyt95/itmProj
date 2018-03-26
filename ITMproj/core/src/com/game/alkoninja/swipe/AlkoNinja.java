package com.game.alkoninja.swipe;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.game.alkoninja.swipe.mesh.SwipeTriStrip;
import com.game.alkoninja.swipe.simplify.PointsCounter;

public class AlkoNinja implements ApplicationListener {

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private SwipeHandler swipe;

    private Texture tex;
    private Texture background;

    private ShapeRenderer shapes;

    private SwipeTriStrip tris;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;
    private ObjectSpawner objectSpawner;
    private long startTime;
    private ObjectRenderer objectRenderer;

    public final static float PTM = 100f;
    private HitDetector hitDetector;
    private PointsCounter pointsCounter;

    @Override
    public void create() {
        swipe = new SwipeHandler(10);
        tris = new SwipeTriStrip(swipe);
        swipe.minDistance = 10;
        swipe.initialDistance = 10;
        tex = new Texture("data/gradient.png");
        background = new Texture("background.jpg");
        tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        shapes = new ShapeRenderer();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.
                getHeight());
        debugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(swipe);


        world = new World(new Vector2(0, -9.8f), true);

        objectSpawner = new ObjectSpawner(world);
        objectSpawner.createBeers(100);
        objectRenderer = new ObjectRenderer(batch);
        pointsCounter = new PointsCounter(batch);
        hitDetector = new HitDetector(objectSpawner, swipe, pointsCounter);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    @Override
    public void render() {
        camera.update();
        world.step(Gdx.graphics.getDeltaTime(), 1000, 1000);
        hitDetector.detectHits();
        objectSpawner.changeXPosIfOnEdge();
        objectSpawner.removeIfFell();
        if (TimeUtils.timeSinceMillis(startTime) > 500) {
            objectSpawner.spawn();
            startTime = TimeUtils.millis();
        }



        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PTM,
                PTM, 0);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        objectRenderer.renderObjects(objectSpawner.getBeerVisible());
        pointsCounter.printScore();
        batch.end();

        //debugRenderer.render(world, debugMatrix);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        tex.bind();
        //tris.endcap = 5f;
        tris.thickness = 30f;
        tris.update(swipe.path());
        //tris.color = Color.WHITE;
        tris.draw(camera);
        //drawDebug();

    }

    //optional debug drawing..
    void drawDebug() {
        Array<Vector2> input = swipe.input();

        //draw the raw input
        shapes.begin(ShapeType.Line);
        shapes.setColor(Color.GRAY);
        for (int i = 0; i < input.size - 1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i + 1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();

        //draw the smoothed and simplified path
        shapes.begin(ShapeType.Line);
        shapes.setColor(Color.RED);
        Array<Vector2> out = swipe.path();
        for (int i = 0; i < out.size - 1; i++) {
            Vector2 p = out.get(i);
            Vector2 p2 = out.get(i + 1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();


        //render our perpendiculars
        shapes.begin(ShapeType.Line);
        Vector2 perp = new Vector2();

        for (int i = 1; i < input.size - 1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i + 1);

            shapes.setColor(Color.LIGHT_GRAY);
            perp.set(p).sub(p2).nor();
            perp.set(perp.y, -perp.x);
            perp.scl(10f);
            shapes.line(p.x, p.y, p.x + perp.x, p.y + perp.y);
            perp.scl(-1f);
            shapes.setColor(Color.BLUE);
            shapes.line(p.x, p.y, p.x + perp.x, p.y + perp.y);
        }
        shapes.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        tex.dispose();
    }

}
