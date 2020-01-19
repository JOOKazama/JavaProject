package sample.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerNC implements Initializable
{
    @FXML private RadioButton c1;
    @FXML private RadioButton c2;
    @FXML private RadioButton c3;
    @FXML private RadioButton c4;
    @FXML private RadioButton c5;

    ToggleGroup tg = new ToggleGroup(); @FXML AnchorPane right=new AnchorPane();

    public void clients() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/nomcl.fxml")); right.getChildren().setAll(pnlOne); }
    public void prov() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/nomprod.fxml")); right.getChildren().setAll(pnlOne); }
    public void prod() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/nomprodd.fxml")); right.getChildren().setAll(pnlOne); }
    public void sales() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/nomsales.fxml")); right.getChildren().setAll(pnlOne); }
    public void del() throws IOException
    { AnchorPane pnlOne = FXMLLoader.load(this.getClass().getResource("/fxml/nomdel.fxml")); right.getChildren().setAll(pnlOne); }

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    { c1.setToggleGroup(tg); c2.setToggleGroup(tg); c3.setToggleGroup(tg); c4.setToggleGroup(tg); c5.setToggleGroup(tg); }
}