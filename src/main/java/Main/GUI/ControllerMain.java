package Main.GUI;
import Main.GUI.ControllerLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable
{
    public Font x1;
    public Color x2;
    @FXML private RadioButton create_operator;
    @FXML private RadioButton create_provider;
    @FXML private RadioButton create_client;
    @FXML private RadioButton create_bank;
    @FXML private RadioButton nomenclatures;
    @FXML private RadioButton buy_products;
    @FXML private RadioButton receive_products;
    @FXML private RadioButton products_sale_price;
    @FXML private RadioButton show_warehouse;
    @FXML private RadioButton funds;
    @FXML private RadioButton deliveries;
    @FXML private RadioButton clients;
    @FXML private RadioButton activity_operators;
    @FXML private RadioButton warehouse;
    @FXML private RadioButton costs_income_profits;
    @FXML private RadioButton movement_funds;

    @FXML private Tab table;
    ToggleGroup toggle_group=new ToggleGroup();
    @FXML AnchorPane right=new AnchorPane();

    public void create_operator() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/create_operator.fxml"))); }

    public void create_provider() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/create_provider.fxml"))); }

    public void create_client() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/create_client.fxml"))); }

    public void create_bank() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/create_bank.fxml"))); }

    public void nomenclatures() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/show_nomenclatures.fxml"))); }

    public void buy_products() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/buy_products.fxml"))); }

    public void receive_products() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/receive_products.fxml"))); }

    public void products_sale_price() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/sale_price_products.fxml"))); }

    public void show_warehouse() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/available_products.fxml"))); }

    public void funds() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/show_funds.fxml"))); }

    public void deliveries() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/date_deliveries.fxml"))); }

    public void clients() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/date_clients.fxml"))); }

    public void activity_operators() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/date_activity_operators.fxml"))); }

    public void warehouse() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/date_products_warehouse.fxml"))); }

    public void costs_income_profits() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/date_costs_income_profits.fxml"))); }

    public void movement_funds() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/date_movement_funds.fxml"))); }

    public void handle() throws IOException
    {
        ControllerLogin.admin.close();
        ControllerLogin.operator.close();
        Parent root=new FXMLLoader(getClass().getResource("/fxml/login.fxml")).load();
        ControllerLogin.primary_stage.setScene(new Scene(root));
        ControllerLogin.primary_stage.show();
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        create_operator.setToggleGroup(toggle_group);
        create_provider.setToggleGroup(toggle_group);
        create_client.setToggleGroup(toggle_group);
        create_bank.setToggleGroup(toggle_group);
        nomenclatures.setToggleGroup(toggle_group);
        buy_products.setToggleGroup(toggle_group);
        receive_products.setToggleGroup(toggle_group);
        products_sale_price.setToggleGroup(toggle_group);
        show_warehouse.setToggleGroup(toggle_group);
        funds.setToggleGroup(toggle_group);
        deliveries.setToggleGroup(toggle_group);
        clients.setToggleGroup(toggle_group);
        activity_operators.setToggleGroup(toggle_group);
        warehouse.setToggleGroup(toggle_group);
        costs_income_profits.setToggleGroup(toggle_group);
        movement_funds.setToggleGroup(toggle_group);

        if(ControllerLogin.admin_or_operator>1) { table.setDisable(true); }
        ControllerLogin.primary_stage.close();
    }
}