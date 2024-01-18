package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    public void initialize(){
        showTime();
        showDate();
    }
    private void showTime() {
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO,
                actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                , new KeyFrame(Duration.seconds(1)));

        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
    private void showDate() {
        Timeline date = new Timeline(new KeyFrame(Duration.ZERO,
                actionEvent -> lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                , new KeyFrame(Duration.seconds(1)));

        date.setCycleCount(Animation.INDEFINITE);
        date.play();
    }
    @FXML
    void customerButtonOnAction(ActionEvent event) {

    }

    @FXML
    void itemButtonOnAction(ActionEvent event) {

    }

    @FXML
    void logoutButtonOnAction(ActionEvent event) {

    }

    @FXML
    void orderItemButtonOnAction(ActionEvent event) {

    }

    @FXML
    void passwordButtonOnAction(ActionEvent event) {

    }

    @FXML
    void placeOrderButtonOnAction(ActionEvent event) {

    }

}
