package controller;

import bo.BoFactory;
import bo.BoType;
import bo.custom.PartBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.PartDto;
import dto.tm.PartTm;
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

public class PartFormController {

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTreeTableView<PartTm> tblPart;

    @FXML
    private TreeTableColumn<?, ?> colId;

    @FXML
    private TreeTableColumn<?, ?> colName;

    @FXML
    private TreeTableColumn<?, ?> colPrice;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    private PartBo partBo = BoFactory.getInstance().getBo(BoType.PART);

    public void initialize(){
        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("partId"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("partName"));
        colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadPartTable();

        tblPart.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblPart.setPredicate(new Predicate<TreeItem<PartTm>>() {
                    @Override
                    public boolean test(TreeItem<PartTm> treeItem) {
                        return treeItem.getValue().getPartId().contains(newValue) ||
                                treeItem.getValue().getPartId().toLowerCase().contains(newValue) ||
                                treeItem.getValue().getPartName().contains(newValue) ||
                                treeItem.getValue().getPartName().toLowerCase().contains(newValue);
                    }
                });
            }
        });
    }

    private void setData(TreeItem<PartTm> newValue) {
        if (newValue != null){
            txtId.setEditable(false);
            txtId.setText(newValue.getValue().getPartId());
            txtName.setText(newValue.getValue().getPartName());
            txtUnitPrice.setText(String.valueOf(newValue.getValue().getUnitPrice()));
        }
    }

    private void loadPartTable() {
        ObservableList<PartTm> tmList = FXCollections.observableArrayList();
        try {
            List<PartDto> dtoList = partBo.allParts();

            for (PartDto dto:dtoList) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #af0c0c; -fx-text-fill: white; ");
                PartTm partTm = new PartTm(dto.getPartId(),
                        dto.getPartName(),
                        dto.getUnitPrice(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deletePart(partTm.getPartId());
                });
                tmList.add(partTm);
            }
            TreeItem<PartTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblPart.setRoot(treeItem);
            tblPart.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deletePart(String id) {
        try {
            boolean isDeleted = partBo.deletePart(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION,"Part Deleted!").show();
                loadPartTable();
                clearFields();
                tblPart.refresh();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Went Wrong!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        tblPart.refresh();
        txtId.clear();
        txtName.clear();
        txtUnitPrice.clear();
        txtId.setEditable(true);
    }
    @FXML
    void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) tblPart.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void reloadButtonOnAction(ActionEvent event) {
        loadPartTable();
        tblPart.refresh();
        clearFields();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        try {
            boolean isSaved = partBo.savePart(new PartDto(txtId.getText(), txtName.getText(),
                    Double.parseDouble(txtUnitPrice.getText()))
            );
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION,"Part Saved!").show();
                loadPartTable();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        try {
            boolean isUpdated = partBo.updatePart(new PartDto(txtId.getText(), txtName.getText(),
                    Double.parseDouble(txtUnitPrice.getText()))
            );
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION,"Part Updated!").show();
                loadPartTable();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
