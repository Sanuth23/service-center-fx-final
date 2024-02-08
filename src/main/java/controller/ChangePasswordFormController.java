package controller;

import bo.BoFactory;
import bo.BoType;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.UserDto;
import dto.tm.UserTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.function.Predicate;

public class ChangePasswordFormController {

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtJobRole;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTreeTableView<UserTm> tblUser;

    @FXML
    private TreeTableColumn<?, ?> colId;

    @FXML
    private TreeTableColumn<?, ?> colName;

    @FXML
    private TreeTableColumn<?, ?> colContact;

    @FXML
    private TreeTableColumn<?, ?> colJobRole;

    @FXML
    private TreeTableColumn<?, ?> colEmail;

    @FXML
    private TreeTableColumn<?, ?> colPassword;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void initialize(){
        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new TreeItemPropertyValueFactory<>("contactNumber"));
        colJobRole.setCellValueFactory(new TreeItemPropertyValueFactory<>("jobRole"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new TreeItemPropertyValueFactory<>("password"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadUserTable();

        tblUser.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });

    }

    private void setData(TreeItem<UserTm> newValue) {
        if (newValue != null){
            txtId.setEditable(false);
            txtName.setEditable(false);
            txtContactNumber.setEditable(false);
            txtJobRole.setEditable(false);
            txtUsername.setEditable(false);
            txtId.setText(newValue.getValue().getUserId());
            txtName.setText(newValue.getValue().getName());
            txtContactNumber.setText(newValue.getValue().getContactNumber());
            txtJobRole.setText(newValue.getValue().getJobRole());
            txtUsername.setText(newValue.getValue().getUsername());
            txtPassword.setText(newValue.getValue().getPassword());
        }
    }


    private void loadUserTable() {
        ObservableList<UserTm> tmList = FXCollections.observableArrayList();

        try {
            UserDto dto = userBo.getUser(LoginFormController.currentUserId);

                JFXButton btn = new JFXButton();
                UserTm tm = new UserTm(dto.getUserId(),
                        dto.getName(),
                        dto.getContactNumber(),
                        dto.getJobRole(),
                        dto.getUsername(),
                        dto.getPassword(),
                        btn
                );

                tmList.add(tm);

        TreeItem<UserTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblUser.setRoot(treeItem);
            tblUser.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reloadButtonOnAction(ActionEvent event) {
        loadUserTable();
        tblUser.refresh();
        clearFields();
    }

    private void clearFields() {
        tblUser.refresh();
        txtId.clear();
        txtName.clear();
        txtContactNumber.clear();
        txtJobRole.clear();
        txtUsername.clear();
        txtPassword.clear();
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        try {
            boolean isUpdated = userBo.updateUser(new UserDto(txtId.getText(), txtName.getText(),
                    txtContactNumber.getText(), txtJobRole.getText(),
                    txtUsername.getText(),txtPassword.getText())
            );
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION,"Password Changed Successfully!").show();
                loadUserTable();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        if (LoginFormController.currentUser.equalsIgnoreCase("admin")){
            Stage stage = (Stage) txtPassword.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            Stage stage = (Stage) txtPassword.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
