package game.view;

import api.ColorRGB;
import game.gameobject.GameObject;
import game.gameobject.whacamoleobjects.Status;

/**
 * The interface to update the way an object is drawn.
 */
public interface Drawings {

    /**
     * The instructions on how to draw the circle.
     * 
     * @param object the gameObject of the circle to draw.
     * @param color  the color of the circle to draw.
     * @param radius the radius of the circle.
     */
    void drawCircle(GameObject object, ColorRGB color, double radius);

    /**
     * The instructions on how to draw a sqare.
     * 
     * @param object the gameObject of the square to draw.
     * @param color  the color of the square to draw.
     * @param side   the lenght of the side of the square.
     */
    void drawSquare(GameObject object, ColorRGB color, double side);

    /**
     * The instructions on how to draw a rectangle.
     * 
     * @param object the gameObject of the rectangle to draw.
     * @param color  the color of the rectangle to draw.
     * @param width  the width of the rectangle to draw.
     * @param height the height of the rectangle to draw.
     */
    void drawRectangle(GameObject object, ColorRGB color, double width, double height);

    /**
     * The instructions on how to draw a triangle.
     * 
     * @param object the gameObject of the triangle to draw.
     * @param color  the color of the triangle to draw.
     * @param side   the side lenght of the triangle.
     */
    void drawTriangle(GameObject object, ColorRGB color, double side);

    /**
     * The instructions on how to draw a mole.
     * 
     * @param object the gameObject of the mole to draw.
     * @param status the state in which to represent the mole.
     */
    void drawMole(GameObject object, Status status);

    /**
     * The instructions on how to draw a bomb.
     * 
     * @param object the gameObject of the bomb to draw.
     * @param status the state in which to represent the bomb.
     */
    void drawBomb(GameObject object, Status status);

    /**
     * Method to draw a label.
     * 
     * @param object the label gameObject.
     * @param color  the color of the label.
     * @param size   the size of the label.
     * @param string the string to draw.
     */
    void drawLabel(GameObject object, ColorRGB color, int size, String string);

}
