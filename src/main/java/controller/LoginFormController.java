package controller;

import bo.BoFactory;
import bo.BoType;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.UserDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoginFormController {
    @FXML
    public AnchorPane pane;
    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    public static String currentUser;
    public static String currentUserId;

    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

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
    void loginButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        List<UserDto> dtoList= userBo.allUsers();
        for (UserDto dto:dtoList) {
            if(dto.getUsername().equalsIgnoreCase(txtUsername.getText()) && dto.getPassword().equals(txtPassword.getText())){
                if (dto.getJobRole().equalsIgnoreCase("admin")) {
                    Stage stage = (Stage) pane.getScene().getWindow();
                    try {
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
                        stage.setTitle("Admin Dashboard Form");
                        stage.show();
                        stage.setResizable(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Stage stage = (Stage) pane.getScene().getWindow();
                    try {
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
                        stage.setTitle("Dashboard Form");
                        stage.show();
                        stage.setResizable(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                currentUser=dto.getJobRole();
                currentUserId=dto.getUserId();

                return;
            }
        }
        new Alert(Alert.AlertType.ERROR,"Username or Password is wrong! Please check & try again...").show();
    }

    @FXML
    void passwordButtonOnAction(ActionEvent event) {
        // send email
    }

}
