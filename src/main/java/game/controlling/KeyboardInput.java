package game.controlling;

import java.util.Optional;

/**
 * Class that encodes the input of the user, that indicates
 * the direction in which the GameObject should be moved,
 * setting one of the 4 boolean values ​​to true.
 */
public class KeyboardInput implements Input {
    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;
    private Optional<Integer> numberPressed = Optional.empty();

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
     * Method to set moveUp.
     * 
     * @param moveUp if the moveUp command is active.
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
     * Method to set moveDown.
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
     * Method to set moveLeft.
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
     * Method to set moveRight.
     * 
     * @param moveRight if the moveRight command is active
     */
    @Override
    public void setMoveRight(final boolean moveRight) {
        this.moveRight = moveRight;
    }

    /**
     * Method that returns an Optional containing, 
     * possibly, the number pressed (in [1,9]).
     * 
     * @return an Optional of Integer
     */
    @Override
    public Optional<Integer> getNumberPressed() {
        return this.numberPressed;
    }

    /**
     * Sets the number pressed on the keyboard by the player.
     * 
     * @param numberPressed int in [1,9]
     */
    @Override
    public void setNumberPressed(int numberPressed) {
        this.numberPressed = Optional.of(numberPressed);
    }

    /**
     * Method to reset all inputs to false.
     */
    @Override
    public void reset() {
        setMoveDown(false);
        setMoveLeft(false);
        setMoveRight(false);
        setMoveUp(false);
        this.numberPressed = Optional.empty();
    }
/**
 * {@inheritDoc}
 */
    @Override
    public KeyboardInput clone() {
        try {
            return (KeyboardInput) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

}
