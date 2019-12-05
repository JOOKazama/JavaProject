package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerMain
{
    @FXML private ComboBox cb1,cb2,cb3; String a;
    @FXML AnchorPane right=new AnchorPane();

    public void comboAction(ActionEvent event)
    {
        try {
            if (cb1.getValue().equals("New operator")) {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/createoperator.fxml"));
                right.getChildren().setAll(pnlOne);
            } else if (cb1.getValue().equals("New provider")) {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/createprovider.fxml"));
                right.getChildren().setAll(pnlOne);
            } else if (cb1.getValue().equals("New client")) {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/createclient.fxml"));
                right.getChildren().setAll(pnlOne);
            } else if (cb1.getValue().equals("New bank")) {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/createbank.fxml"));
                right.getChildren().setAll(pnlOne);
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void comboAction2(ActionEvent event)
    {
        try
        {
            if (cb2.getValue().equals("Receive products(delivery price) from provider"))
            {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/receive.fxml"));
                right.getChildren().setAll(pnlOne);
            }
            else if (cb2.getValue().equals("Buy products from warehouse"))
            {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/buy.fxml"));
                right.getChildren().setAll(pnlOne);
            }
            else if (cb2.getValue().equals("Show the funds in the bank"))
            {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/seem.fxml"));
                right.getChildren().setAll(pnlOne);
            }
            else if (cb2.getValue().equals("Show the products in warehouse"))
            {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/sees.fxml"));
                right.getChildren().setAll(pnlOne);
            }
            else if (cb2.getValue().equals("Show all products(sale price)"))
            {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/sees1.fxml"));
                right.getChildren().setAll(pnlOne);
            }
        }

        catch (Exception e) { e.printStackTrace(); }
    }

    public void comboAction3(ActionEvent event)
    {
        try
        {
            if (cb3.getValue().equals("Deliveries and providers"))
            {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/date1.fxml"));
                right.getChildren().setAll(pnlOne);
            }
            else if (cb3.getValue().equals("Clients"))
            {
                AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/date2.fxml"));
                right.getChildren().setAll(pnlOne);
            }
        }

        catch (Exception e) { e.printStackTrace(); }
    }

    public void handle(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/first.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
