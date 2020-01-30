package cellsociety;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    private final static String SIMULATION_NAME = "fire";

    /**
     * Start of the program.
     */
    public static void main(String[] args) {
        // Declare new simulation
        Driver myDriver = new Driver(SIMULATION_NAME);
        myDriver.launch(args);
    }
}
