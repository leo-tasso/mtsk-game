package game.view.javafx;

import java.util.ArrayList;
import java.util.List;

import api.Point2D;
import game.Engine;
import game.controlling.Input;
import game.controlling.KeyboardInput;
import game.gameobject.GameObject;
import game.view.Drawings;
import game.view.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Implementation of {@link View} using JavaFx.
 */
public final class JavaFxView extends Application implements View {

    private static final int BUTTON_SPACING = 10;
    private static final int ASPECT_WIDTH = 16;
    private static final int ASPECT_HEIGHT = 9;
    private static final double ASPECT_RATIO = ASPECT_WIDTH / (double) ASPECT_HEIGHT;

    private static final int START_WINDOW_WIDTH = 800;
    private static final int START_WINDOW_HEIGHT = 450;
    private static final List<Color> BACKGROUND_COLORS = List.of(Color.AQUAMARINE, Color.DARKSEAGREEN, Color.RED);
    private final List<Canvas> minigameCanvases = new ArrayList<>();

    private Stage stage;
    private Scene gameScene;
    private GridPane gp;
    private boolean windowOpen;
    private final Input input = new KeyboardInput();

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final Canvas canvas = new Canvas(START_WINDOW_WIDTH, START_WINDOW_HEIGHT);

        gp = new GridPane();
        minigameCanvases.add(canvas);
        gp.add(canvas, 0, 0);
        gameScene = new Scene(gp, START_WINDOW_WIDTH, START_WINDOW_HEIGHT);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/LayerIcon.png")));
        stage.setOnCloseRequest(event -> {
            Platform.exit();
        });

        stage.setTitle("MTSK-Game");
        stage.setScene(gameScene);
        final Window w = gameScene.getWindow();
        if (w instanceof Stage) {
            this.stage = (Stage) w;
        }
        stage.setFullScreen(false);
        stage.show();

        gameScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            final double width = newValue.doubleValue();
            minigameCanvases.forEach(c -> c.setWidth(width / gp.getColumnCount()));
        });

        gameScene.heightProperty().addListener((observable, oldValue,
                newValue) -> {
            final double height = newValue.doubleValue();
            minigameCanvases.forEach(c -> c.setHeight(height / gp.getRowCount()));
        });

        gameScene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.W)) {
                input.setMoveUp(true);
            } else if (e.getCode().equals(KeyCode.A)) {
                input.setMoveLeft(true);
            } else if (e.getCode().equals(KeyCode.S)) {
                input.setMoveDown(true);
            } else if (e.getCode().equals(KeyCode.D)) {
                input.setMoveRight(true);
            } else if (e.getCode().equals(KeyCode.F)) {
                this.toggleFullScreen();
            }

        });
        gameScene.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.W)) {
                input.setMoveUp(false);
            } else if (e.getCode().equals(KeyCode.A)) {
                input.setMoveLeft(false);
            } else if (e.getCode().equals(KeyCode.S)) {
                input.setMoveDown(false);
            } else if (e.getCode().equals(KeyCode.D)) {
                input.setMoveRight(false);
            }
        });
        new Thread(() -> new Engine(this, input).mainLoop()).start();
        windowOpen = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final List<List<GameObject>> objectsList) {
        Platform.runLater(() -> {
            if (objectsList.size() > minigameCanvases.size()) {
                final Canvas c = new Canvas();
                minigameCanvases.add(c);
                gp.add(c,
                        (minigameCanvases.size() - 1) % 2, (minigameCanvases.size() - 1) / 2);
            }
            if (objectsList.size() < minigameCanvases.size()) {
                minigameCanvases.remove(minigameCanvases.size() - 1);
            }
            minigameCanvases.forEach(c -> {
                final GraphicsContext gc = c.getGraphicsContext2D();
                c.setHeight(gameScene.getHeight() / gp.getRowCount());
                c.setWidth(gameScene.getWidth() / gp.getColumnCount());

                gc.clearRect(0, 0, c.getWidth(), c.getHeight());
                gc.setStroke(Color.BLACK);
                gc.strokeRect(
                        /*
                         * coordinates of the upper left corner of rectangle: the
                         * last addendum is necessary to enter the right play field
                         */
                        getStartingPoint().getX(), // TODO is it better to draw also outside the area?
                        getStartingPoint().getY(),
                        boxWidth(),
                        boxHeight());
                gc.setFill(BACKGROUND_COLORS.size() > minigameCanvases.indexOf(c)
                        ? BACKGROUND_COLORS.get(minigameCanvases.indexOf(c))
                        : BACKGROUND_COLORS.get(BACKGROUND_COLORS.size() - 1));
                gc.fillRect(
                        getStartingPoint().getX(),
                        getStartingPoint().getY(),
                        boxWidth(),
                        boxHeight());
                final Drawings d = new JavaFxDrawings(c, this.getStartingPoint(), this.boxHeight());
                objectsList.get(minigameCanvases.indexOf(c)).forEach(o -> o.updateAspect(d));
            });
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderGameOver(final Long points) {
        Platform.runLater(() -> {
            // Create a label to display the score
            final Label scoreLabel = new Label("Score: " + points);

            // Create a button to exit the application
            final Button exitButton = new Button("Exit");
            exitButton.setOnAction(e -> Platform.exit());

            // Create a button to play again
            final Button playAgainButton = new Button("Play Again");
            // TODO implement
            /*
             * playAgainButton.setOnAction(e -> {
             * stage.close();
             * 
             * Platform.runLater(() -> {
             * new Engine(this,input).mainLoop();
             * });
             * 
             * });
             */

            // Create a horizontal box to hold the buttons
            final HBox buttonBox = new HBox(BUTTON_SPACING, exitButton, playAgainButton);
            buttonBox.setAlignment(Pos.CENTER);

            // Create a vertical box to hold the label and buttons
            final VBox root = new VBox(BUTTON_SPACING, scoreLabel, buttonBox);
            root.setAlignment(Pos.CENTER);

            // Create a scene with the root container
            final Scene goScene = new Scene(root, gameScene.getWidth(), gameScene.getHeight());

            stage.setScene(goScene);

        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMessage(final String tutorial, final Engine controller) {
        Platform.runLater(() -> {
            final Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText(tutorial);

            alert.initOwner(stage);

            alert.showAndWait();
            controller.setPaused(false);
        });
    }

    /**
     * Checks if game is set to fullscreen.
     * 
     * @return if game is set to fullscreen.
     */
    public boolean isFullScreen() {

        return stage.isFullScreen();

    }

    /**
     * toggle full screen.
     */
    public void toggleFullScreen() {

        stage.setFullScreen(!isFullScreen());

        input.reset();
    }

    /**
     * Method to get the starting coordinates of the top left corner of the
     * rectangle of the game area.
     * 
     * @return the starting point of the game area.
     */
    private Point2D getStartingPoint() {
        if (!windowOpen) {
            return Point2D.origin();
        }
        if (isLarger()) {
            return new Point2D(
                    (minigameCanvases.get(0).getWidth() - minigameCanvases.get(0).getHeight() * ASPECT_RATIO) / 2, 0);
        }
        return new Point2D(0,
                (minigameCanvases.get(0).getHeight() - minigameCanvases.get(0).getWidth() / ASPECT_RATIO) / 2);
    }

    /**
     * Method to get the width of the canvas.
     *
     * @return the width of the canvas
     */
    private double boxWidth() {
        if (!windowOpen) {
            return 0;
        }
        if (isLarger()) {
            return minigameCanvases.get(0).getHeight() * ASPECT_RATIO;
        }
        return minigameCanvases.get(0).getWidth();
    }

    /**
     * Method to get the height of the canvas.
     *
     * @return the height of the canvas
     */
    private double boxHeight() {
        if (!windowOpen) {
            return 0;
        }
        if (isLarger()) {
            return minigameCanvases.get(0).getHeight();
        }
        return minigameCanvases.get(0).getWidth() / ASPECT_RATIO;
    }

    /**
     * Method to determine if the frame is larger than taller.
     *
     * @return whether the frame is larger
     */
    private boolean isLarger() {
        if (!windowOpen) {
            return true;
        }
        return minigameCanvases.get(0).getHeight() / ASPECT_HEIGHT < minigameCanvases.get(0).getWidth() / ASPECT_WIDTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isViewActive() {
        return windowOpen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() throws Exception {
        windowOpen = false;
    }
}
