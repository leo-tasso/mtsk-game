package game.engine.minigame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import api.Point2D;
import api.Vector2D;
import game.controlling.DodgerInputModel;
import game.controlling.NullInput;
import game.engine.gameobject.GameObject;
import game.engine.gameobject.SimplePhysics;
import game.engine.gameobject.dodgeatriangleobjects.DatTriangle;
import game.engine.gameobject.dodgeatriangleobjects.Dodger;
import game.engine.gameobject.dodgeatriangleobjects.SlotAspect;
import game.engine.gameobject.hitboxmodel.Collider;
import game.engine.gameobject.hitboxmodel.ColliderImpl;

/**
 * Class that models the dodge a triangle minigame, where 
 * the player must move a small square on a vertical 
 * axis, avoiding triangles that come from left to right.
 */
public class DodgeATriangle implements Minigame {

    private static final int INITIAL_X = 800;
    private static final int INITIAL_Y = 450;
    private static final int SIDE_LENGTH = 140;
    private static final int SPAWN_LEFT = - SIDE_LENGTH;
    private static final int SPAWN_RIGHT = 1600 + SIDE_LENGTH;
    private static final Vector2D ENEMY_SPEED = new Vector2D(40, 0);
    private static final int NUM_SLOTS = 5;

    private final List<GameObject> l = new ArrayList<>();
    private final GameObject slots;
    private final Collider c = new ColliderImpl();
    private final Random rand = new Random();
    private long totalElapsed;

    /**
     * Constructor that initializes the 
     * list with the main character.
     */
    public DodgeATriangle() {
        this.l.add(new Dodger(INITIAL_Y, SIDE_LENGTH,
                new DodgerInputModel(SIDE_LENGTH, INITIAL_Y)));
        this.slots = new GameObject(new Point2D(INITIAL_X, INITIAL_Y), ENEMY_SPEED);
        this.slots.setAspectModel(new SlotAspect(SIDE_LENGTH, new Point2D(INITIAL_X, INITIAL_Y), NUM_SLOTS));
        this.slots.setInputModel(new NullInput());
        this.slots.setPhysicsModel(new SimplePhysics());
        this.slots.setVel(Vector2D.nullVector());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        final GameObject pl = l.get(0);
        return l.stream()
            .skip(1)
            .anyMatch(o -> c.isColliding(pl, o));                               
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void compute(final long elapsed) {
        this.totalElapsed += elapsed;
        if (l.size() == 1) {
            int enemyY = Optional.of(rand.nextInt(NUM_SLOTS))
                    .map(y -> INITIAL_Y + (y - 2) * SIDE_LENGTH)
                    .get();
            int enemyX = Optional.of(rand.nextInt(2))
                    .map(x -> x == 0 ? SPAWN_LEFT : SPAWN_RIGHT)
                    .get();
            l.add(new DatTriangle(new Point2D(enemyX, enemyY),
                    enemyX < 0 ? ENEMY_SPEED : ENEMY_SPEED.invert(),
                    SIDE_LENGTH));
        }
        l.removeIf(o -> o.getCoor().getX() < SPAWN_LEFT
                || o.getCoor().getX() > SPAWN_RIGHT);
        l.forEach(e -> e.updatePhysics(elapsed, this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObject> getObjects() {
        return Stream.concat(l.stream(), Stream.of(slots)).toList();
        // return new ArrayList<>(this.l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTutorial() {
        return "Use the arrows to move forward and backwards.\nAvoid the triangles.";
    }
}
