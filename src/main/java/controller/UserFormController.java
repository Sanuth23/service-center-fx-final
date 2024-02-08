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
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public class UserFormController {

    @FXML
    private AnchorPane customerPane;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtJobRole;

    @FXML
    private JFXTextField txtSearch;

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

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtPassword;

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

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblUser.setPredicate(new Predicate<TreeItem<UserTm>>() {
                    @Override
                    public boolean test(TreeItem<UserTm> treeItem) {
                        return treeItem.getValue().getUserId().contains(newValue) ||
                                treeItem.getValue().getUserId().toLowerCase().contains(newValue) ||
                                treeItem.getValue().getName().contains(newValue) ||
                                treeItem.getValue().getName().toLowerCase().contains(newValue);
                    }
                });
            }
        });
    }

    private void setData(TreeItem<UserTm> newValue) {
        if (newValue != null){
            txtId.setEditable(false);
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
            List<UserDto> dtoList = userBo.allUsers();

            for (UserDto dto:dtoList) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #af0c0c; -fx-text-fill: white; ");
                UserTm tm = new UserTm(dto.getUserId(),
                        dto.getName(),
                        dto.getContactNumber(),
                        dto.getJobRole(),
                        dto.getUsername(),
                        dto.getPassword(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteUser(tm.getUserId());
                });

                tmList.add(tm);
            }
            TreeItem<UserTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblUser.setRoot(treeItem);
            tblUser.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(String id) {
        try {
            boolean isDeleted = userBo.deleteUser(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION,"User Deleted!").show();
                loadUserTable();
                clearFields();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Went Wrong!").show();
            }
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
        txtId.setEditable(true);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {

    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {

    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {

    }

}
