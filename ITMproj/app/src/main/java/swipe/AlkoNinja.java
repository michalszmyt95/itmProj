package swipe;


import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.uwm.wmii.student.michal.itmproj.api.dto.WynikTestuDTO;
import com.uwm.wmii.student.michal.itmproj.api.service.TestRestService;
import com.uwm.wmii.student.michal.itmproj.singletons.AppRestManager;

import java.util.Date;


import retrofit2.Call;
import retrofit2.Callback;
import swipe.mesh.SwipeTriStrip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

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
    private long timerStartTime;
    private ObjectRenderer objectRenderer;

    public final static float PTM = 100f;
    private HitDetector hitDetector;
    private PointsCounter pointsCounter;
    private TimeCounter timeCounter;
    private boolean running = false;
    private boolean ended = false;
    private StartEndGame startEndGame;
    private AppRestManager appRestManager;

    @Override
    public void create() {
        appRestManager = AppRestManager.getInstance(getApplicationContext());
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
        timeCounter = new TimeCounter(batch, 30, this);
        hitDetector = new HitDetector(objectSpawner, swipe, pointsCounter);

        startEndGame = new StartEndGame(batch, this);
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

        if (running) {
            if (TimeUtils.timeSinceMillis(startTime) > 500) {
                objectSpawner.spawn();
                startTime = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(timerStartTime) > 1000) {
                timeCounter.addTick();
                timerStartTime = TimeUtils.millis();
            }
        }


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PTM,
                PTM, 0);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        objectRenderer.renderObjects(objectSpawner.getBeerVisible());

        if (running) {
            pointsCounter.printScore();
            timeCounter.printTime();
        }

        if(!running && !ended){
            startEndGame.printStart();
        }
        if(!running && ended){
            startEndGame.printEnd();
        }
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

    public void timeUp() {
        int points = pointsCounter.getPoints();
        startEndGame.setPoints(points);
        TestRestService testService = appRestManager.podajTestService();
        WynikTestuDTO wynikTestu = new WynikTestuDTO(points, new Date(), true);
        testService.dodajWynikNinjaTestu(wynikTestu).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("RES:", response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        running = false;
        ended = true;
    }

    public void startClicked() {
        this.running = true;
        Gdx.input.setInputProcessor(swipe);
    }

    public void endClicked() {
        this.running = false;
        dispose();
        Gdx.app.exit();
    }
}
