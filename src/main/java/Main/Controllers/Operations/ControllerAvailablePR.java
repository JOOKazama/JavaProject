package Main.Controllers.Operations;
import Main.DatabaseValidators.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Main.Tables.Products;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class ControllerAvailablePR implements Initializable
{
    @FXML TableView<Products>table;
    @FXML private TableColumn<Products, String>name;
    @FXML private TableColumn<Products, Double>price;
    @FXML private TableColumn<Products, Integer>stock;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Database db=new Database();
        ObservableList<Products>observable_list=FXCollections.observableArrayList(db.getSession().createQuery("from Products where stock>0").list());

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table.getItems().addAll(observable_list);
        db.getSession().close();
        db.getSession().close();
    }
}