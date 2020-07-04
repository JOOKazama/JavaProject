package Main.Controllers.Operations;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerShowNC implements Initializable
{
    public Tab choose;
    public SplitPane splitpane;
    @FXML private RadioButton clients;
    @FXML private RadioButton providers;
    @FXML private RadioButton products;
    @FXML private RadioButton sales;
    @FXML private RadioButton deliveries;
    @FXML AnchorPane right=new AnchorPane();
    ToggleGroup toggle_group=new ToggleGroup();

    public void clients() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/list_clients.fxml"))); }

    public void providers() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/list_providers.fxml"))); }

    public void products() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/list_products.fxml"))); }

    public void sales() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/list_sales.fxml"))); }

    public void deliveries() throws IOException
    { right.getChildren().setAll((AnchorPane)FXMLLoader.load(this.getClass().getResource("/fxml/list_deliveries.fxml"))); }

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        clients.setToggleGroup(toggle_group);
        providers.setToggleGroup(toggle_group);
        products.setToggleGroup(toggle_group);
        sales.setToggleGroup(toggle_group);
        deliveries.setToggleGroup(toggle_group);
    }
}