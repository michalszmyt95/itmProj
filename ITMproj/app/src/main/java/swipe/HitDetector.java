package swipe;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

import static swipe.AlkoNinja.PTM;


public class HitDetector {

    private final Array<Fixture> fixtures;
    private ObjectSpawner objectSpawner;
    private SwipeHandler swipe;
    private PointsCounter pointsCounter;

    public HitDetector(ObjectSpawner objectSpawner, SwipeHandler swipe, PointsCounter pointsCounter){
        this.objectSpawner = objectSpawner;
        fixtures = objectSpawner.getBeerVisible();
        this.swipe = swipe;
        this.pointsCounter = pointsCounter;
    }

    public void detectHits(){
        for (Fixture fixture: fixtures) {
            if (swipe.input().size > 0) {
                if (fixture.testPoint(swipe.lastPoint.x/PTM, swipe.lastPoint.y/PTM))
                    hit(fixture);

            }
        }
    }

    private void hit(Fixture fixture){
        objectSpawner.bodyHit(fixture);
        if (((AlkoData) fixture.getUserData()).alkoType == AlkoData.AlkoType.DENATURAT){
            pointsCounter.addScore(-1);
        }else{
            pointsCounter.addScore(1);
        }
    }
}
