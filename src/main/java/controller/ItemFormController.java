package controller;

import bo.BoFactory;
import bo.BoType;
import bo.custom.ItemBo;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.ItemDto;
import dto.tm.ItemTm;
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
import java.util.List;
import java.util.function.Predicate;

public class ItemFormController {
    @FXML
    public JFXTextField txtCategory;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTreeTableView<ItemTm> tblItem;

    @FXML
    private TreeTableColumn<?, ?> colCode;

    @FXML
    private TreeTableColumn<?, ?> colName;

    @FXML
    private TreeTableColumn<?, ?> colCategory;

    @FXML
    private TreeTableColumn<?, ?> colPrice;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("serviceFee"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblItem.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> treeItem) {
                        return treeItem.getValue().getCode().contains(newValue) ||
                                treeItem.getValue().getCode().toLowerCase().contains(newValue) ||
                                treeItem.getValue().getName().contains(newValue) ||
                                treeItem.getValue().getName().toLowerCase().contains(newValue) ||
                                treeItem.getValue().getCategory().contains(newValue) ||
                                treeItem.getValue().getCategory().toLowerCase().contains(newValue);
                    }
                });
            }
        });
    }

    private void setData(TreeItem<ItemTm> newValue) {
        if (newValue != null){
            txtCode.setEditable(false);
            txtCode.setText(newValue.getValue().getCode());
            txtName.setText(newValue.getValue().getName());
            txtCategory.setText(String.valueOf(newValue.getValue().getCategory()));
            txtPrice.setText(String.valueOf(newValue.getValue().getServiceFee()));
        }
    }

    private void loadItemTable() {
        ObservableList<ItemTm> itemList = FXCollections.observableArrayList();
        try {
            List<ItemDto> dtoList = itemBo.allItems();

            for (ItemDto dto:dtoList) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #af0c0c; -fx-text-fill: white; ");
                ItemTm itemTm = new ItemTm(dto.getCode(),
                        dto.getName(),
                        dto.getCategory(),
                        dto.getServiceFee(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteItem(itemTm.getCode());
                });
                itemList.add(itemTm);
            }
            TreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(itemList, RecursiveTreeObject::getChildren);
            tblItem.setRoot(treeItem);
            tblItem.setShowRoot(false);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteItem(String code) {
        try {
            boolean isDeleted = itemBo.deleteItem(code);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemTable();
                clearFields();
                tblItem.refresh();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Went Wrong!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        tblItem.refresh();
        txtCode.clear();
        txtName.clear();
        txtCategory.clear();
        txtPrice.clear();
        txtCode.setEditable(true);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) tblItem.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void reloadButtonOnAction(ActionEvent event) {

    }

    @FXML
    void reportButtonOnAction(ActionEvent event) {

    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {

    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {

    }

}
