package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"))));
            stage.setTitle("Customer Form");
            stage.show();
        //    stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void itemButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemForm.fxml"))));
            stage.setTitle("Item Form");
            stage.show();
            //    stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logoutButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
            stage.setTitle("Login Form");
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void orderDetailButtonOnAction(ActionEvent event) {

    }

    @FXML
    void passwordButtonOnAction(ActionEvent event) {

    }

    @FXML
    void placeOrderButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"))));
            stage.setTitle("Place Order Form");
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
