package controller;

import bo.BoFactory;
import bo.BoType;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailDto;
import dto.PlaceOrderDto;
import dto.tm.PlaceOrderTm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {
    @FXML
    public Label lblDate;
    public JFXTextField txtServiceFee;
    @FXML
    private JFXComboBox<?> cmbCustId;

    @FXML
    private JFXComboBox<?> cmbCode;

    @FXML
    private JFXTextField txtCustName;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXTreeTableView<PlaceOrderTm> tblOrder;

    @FXML
    private TreeTableColumn<?, ?> colCode;

    @FXML
    private TreeTableColumn<?, ?> colItemName;

    @FXML
    private TreeTableColumn<?, ?> colIssue;

    @FXML
    private TreeTableColumn<?, ?> colAdvance;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    @FXML
    private Label lblOrderId;

    @FXML
    private JFXTextField txtIssue;

    private List<CustomerDto> customers = new ArrayList<>();
    private List<ItemDto> items = new ArrayList<>();

    private double tot = 0;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);

    private ObservableList<PlaceOrderTm> tmList = FXCollections.observableArrayList();

    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colIssue.setCellValueFactory(new TreeItemPropertyValueFactory<>("issue"));
        colAdvance.setCellValueFactory(new TreeItemPropertyValueFactory<>("serviceFee"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        generateIdAndDate();

        loadCustomerIds();
        loadItemCodes();

        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, id) -> {
            for (CustomerDto dto:customers) {
                if (dto.getId().equals(id)){
                    txtCustName.setText(dto.getName());
                }
            }
        });
        cmbCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, code) -> {
            for (ItemDto dto:items) {
                if (dto.getCode().equals(code)){
                    txtItemName.setText(dto.getName());
                    txtServiceFee.setText(String.format("%.2f",dto.getServiceFee()));
                }
            }
        });
    }

    private void generateIdAndDate() {
        try {
            lblOrderId.setText(orderBo.generateId());
            Timeline date = new Timeline(new KeyFrame(Duration.ZERO,
                    actionEvent -> lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                    , new KeyFrame(Duration.seconds(1)));

            date.setCycleCount(Animation.INDEFINITE);
            date.play();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemCodes()  {
        try {
            items = itemBo.allItems();
            ObservableList list = FXCollections.observableArrayList();
            for (ItemDto dto:items){
                list.add(dto.getCode());
            }
            cmbCode.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        try {
            customers = customerBo.allCustomers();
            ObservableList list = FXCollections.observableArrayList();
            for (CustomerDto dto:customers){
                list.add(dto.getId());
            }
            cmbCustId.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        if (LoginFormController.currentUser.equalsIgnoreCase("admin")){
            Stage stage = (Stage) tblOrder.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            Stage stage = (Stage) tblOrder.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void addToCartButtonOnAction(ActionEvent event) {

        try {
            double amount = itemBo.getItem(cmbCode.getValue().toString()).getServiceFee();
            JFXButton btn = new JFXButton("Delete");
            btn.setStyle("-fx-background-color: #af0c0c; -fx-text-fill: white; ");

            PlaceOrderTm placeOrderTm = new PlaceOrderTm(
                    cmbCode.getValue().toString(),
                    txtItemName.getText(),
                    txtIssue.getText(),
                    amount,
                    btn
            );

            btn.setOnAction(actionEvent -> {
                tmList.remove(placeOrderTm);
                tot -= placeOrderTm.getServiceFee();
                tblOrder.refresh();
                lblTotal.setText(String.format("%.2f",tot));
            });

            boolean isExist =false;

//            for (PlaceOrderTm order:tmList) {
//                if (order.getCode().equals(placeOrderTm.getCode())){
//                    order.setAmount(order.getAmount()+placeOrderTm.getAmount());
//                    isExist = true;
//                    tot += placeOrderTm.getAmount();
//                }
//            }

//            if (!isExist){
                tmList.add(placeOrderTm);
                tot += placeOrderTm.getServiceFee();
//            }

            TreeItem<PlaceOrderTm> treeItem = new RecursiveTreeItem<PlaceOrderTm>(tmList, RecursiveTreeObject::getChildren);
            tblOrder.setRoot(treeItem);
            tblOrder.setShowRoot(false);

            lblTotal.setText(String.format("%.2f",tot));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void placeOrderButtonOnAction(ActionEvent event) {
//        List<PlaceOrderDto> list = new ArrayList<>();
//        for (PlaceOrderTm tm:tmList) {
//            list.add(new PlaceOrderDto(
//                    lblOrderId.getText(),
//                    cmbCustId.getValue().toString(),
//                    tm.getCode(),
//                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//                    tm.getIssue(),
//                    tm.getServiceFee(),
//                    "Order Pending",null
//            ));
//        }

        boolean isSaved = false;
        try {
            isSaved = orderBo.saveOrder(new PlaceOrderDto(
                    lblOrderId.getText(),
                    cmbCustId.getValue().toString(),
                    cmbCode.getValue().toString(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    txtIssue.getText(),
                    Double.parseDouble(lblTotal.getText()),
                    "Order Pending",null
            ));
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Order Saved!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
