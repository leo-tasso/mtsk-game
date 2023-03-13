package game.gameobject.whacamoleobjects;

import game.gameobject.AspectModel;
import game.gameobject.GameObject;
import game.view.Drawings;

/**
 * The AspectModel for the Bomb object.
 */
public class BombAspectModel implements AspectModel {

    /**
     * I order to draw the Bomb only if it has actually 
     * entered the game, i.e. if it has already been 
     * spawned, in which case I distinguish the object's 
     * appearance based on whether it has been hit or not.
     */
    @Override
    public void update(GameObject object, Drawings drawing) {
        final Status status = ((WamObject) object).getStatus();
        if (!status.equals(Status.WAITING)) {
            if (status.equals(Status.HIT)) {
                drawing.drawBomb(object, true);
            } else {
                drawing.drawBomb(object, false);
            }
        } 
    }
}
