package com.example.quizapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        showSplashScreen(primaryStage);

        // Simulate some initialization or loading process
        // In a real application, replace this with your actual initialization logic
        initializeApplication();

        // Close the splash screen after a delay and continue with your main application logic
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            // Close the splash screen
            primaryStage.close();

            // Continue with your main application logic
            try {
                showMainApplication();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void initializeApplication() throws InterruptedException {
        // Simulate some initialization process
        // Thread.sleep(2000);
    }

    private void showSplashScreen(Stage splashStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizapp/fxml/SplashScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);


        splashStage.setScene(scene);
        splashStage.setTitle("Splash Screen");

        // Set the splash screen to be transparent
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        // Remove default window controls
        splashStage.initStyle(StageStyle.UNDECORATED);


        // Add fade-in animation
        javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(javafx.util.Duration.seconds(2), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        splashStage.show();
    }

    private void showMainApplication() throws Exception {
        // Load your main application FXML and show the main window
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/example/quizapp/fxml/AuthenticationScreen.fxml"));
        Parent mainRoot = mainLoader.load();
        Scene mainScene = new Scene(mainRoot,900,700);

        Stage mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle("Main Application");

        // Add your main application logic here

        mainStage.show();
    }




}