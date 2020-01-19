package sample.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable
{
    @FXML private RadioButton c1;
    @FXML private RadioButton c2;
    @FXML private RadioButton c3;
    @FXML private RadioButton c4;

    @FXML private RadioButton o1;
    @FXML private RadioButton o2;
    @FXML private RadioButton o3;
    @FXML private RadioButton o4;
    @FXML private RadioButton o5;
    @FXML private RadioButton o6;

    @FXML private RadioButton i1;
    @FXML private RadioButton i2;
    @FXML private RadioButton i3;
    @FXML private RadioButton i4;
    @FXML private RadioButton i5;
    @FXML private RadioButton i6;

    @FXML private Tab cr; ToggleGroup tg = new ToggleGroup(); @FXML AnchorPane right=new AnchorPane();

    public void co() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/createoperator.fxml"));right.getChildren().setAll(pnlOne); }
    public void cp() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/createprovider.fxml")); right.getChildren().setAll(pnlOne); }
    public void cc() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/createclient.fxml"));right.getChildren().setAll(pnlOne); }
    public void cb() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/createbank.fxml")); right.getChildren().setAll(pnlOne); }
    public void sn() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/nc.fxml")); right.getChildren().setAll(pnlOne); }
    public void sp() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/buy.fxml")); right.getChildren().setAll(pnlOne); }
    public void rp() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/receive.fxml")); right.getChildren().setAll(pnlOne); }
    public void sap() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/sees1.fxml")); right.getChildren().setAll(pnlOne); }
    public void sw() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/sees.fxml")); right.getChildren().setAll(pnlOne); }
    public void sf() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/seem.fxml")); right.getChildren().setAll(pnlOne); }
    public void dap() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/date1.fxml")); right.getChildren().setAll(pnlOne); }
    public void cl() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/date2.fxml")); right.getChildren().setAll(pnlOne); }
    public void aop() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/date6.fxml")); right.getChildren().setAll(pnlOne); }
    public void pw() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/date5.fxml")); right.getChildren().setAll(pnlOne); }
    public void cip() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/date4.fxml")); right.getChildren().setAll(pnlOne); }
    public void mov() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/date3.fxml")); right.getChildren().setAll(pnlOne); }

    public void handle() throws IOException
    {
        ControllerFirst.admin.close(); ControllerFirst.operator.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/first.fxml")); Parent root = fxmlLoader.load();
        ControllerFirst.pr.setScene(new Scene(root)); ControllerFirst.pr.show();
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        c1.setToggleGroup(tg); c2.setToggleGroup(tg); c3.setToggleGroup(tg); c4.setToggleGroup(tg);
        o1.setToggleGroup(tg); o2.setToggleGroup(tg); o3.setToggleGroup(tg); o4.setToggleGroup(tg); o5.setToggleGroup(tg); o6.setToggleGroup(tg);
        i1.setToggleGroup(tg); i2.setToggleGroup(tg); i3.setToggleGroup(tg); i4.setToggleGroup(tg); i5.setToggleGroup(tg); i6.setToggleGroup(tg);

        if(ControllerFirst.aoo>1) { cr.setDisable(true); } ControllerFirst.pr.close();
    }
}