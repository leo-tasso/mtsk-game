package game.controlling;

import api.Input;

/**
 * 
 */
public class KeyboardInput implements Input {
    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;

    /**
     * Method to check if moveUp input is active.
     * 
     * @return if the moveUp command is active
     */
    @Override
    public boolean isMoveUp() {
        return moveUp;
    }

    /**
     * Method to set if moveUp input is active.
     * 
     * @param moveUp if the moveUp command is active
     */
    @Override
    public void setMoveUp(final boolean moveUp) {
        this.moveUp = moveUp;
    }

    /**
     * Method to check if moveDown input is active.
     * 
     * @return if the moveDown command is active
     */
    @Override
    public boolean isMoveDown() {
        return moveDown;
    }

    /**
     * Method to set moveDown input.
     * 
     * @param moveDown if the moveDown command is active
     */
    @Override
    public void setMoveDown(final boolean moveDown) {
        this.moveDown = moveDown;
    }

    /**
     * Method to check if moveLeft input is active.
     * 
     * @return if the moveLeft command is active
     */
    @Override
    public boolean isMoveLeft() {
        return moveLeft;
    }

    /**
     * Method to set moveLeft input.
     * 
     * @param moveLeft new value for moveLeft
     */
    @Override
    public void setMoveLeft(final boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    /**
     * Method to check if moveRight input is active.
     * 
     * @return if the moveright command is active
     */
    @Override
    public boolean isMoveRight() {
        return moveRight;
    }

    /**
     * Method to set if moveRight input is active.
     * 
     * @param moveRight if the moveRight command is active
     */
    @Override
    public void setMoveRight(final boolean moveRight) {
        this.moveRight = moveRight;
    }
}