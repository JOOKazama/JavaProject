package Main.Controllers.DateInquiries;
import Main.DatabaseValidators.Database;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.query.Query;
import Main.InquirieClasses.DateClientsClass;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerDateClients
{
    @FXML DatePicker date1, date2;
    @FXML Button button;
    @FXML TextField search;
    @FXML TableView<DateClientsClass>table;
    @FXML private TableColumn<DateClientsClass, String>date;
    @FXML private TableColumn<DateClientsClass, String>first;
    @FXML private TableColumn<DateClientsClass, String>middle;
    @FXML private TableColumn<DateClientsClass, String>last;
    @FXML private TableColumn<DateClientsClass, String>quantity;
    @FXML private TableColumn<DateClientsClass, String>name;
    Alert alert_error=new Alert(Alert.AlertType.ERROR);

    public void show()
    {
        try
        {
            if(date1.getValue()==null || date2.getValue()==null || date2.getValue().isBefore(date1.getValue()))
            {
                alert_error.setHeaderText("Error!");
                alert_error.setContentText("The field must not be empty or have a invalid value!");
                alert_error.showAndWait();
            }
            else
            {
                Database db=new Database();
                table.getItems().clear();
                table.refresh();

                Query<Object[]>query_objects=db.getSession().createQuery
                ("SELECT a.date, b.fname, b.mname, b.lname, c.quantity, d.name "+
                "FROM Sales a inner join Clients b on a.clientid=b.id inner join ProductSales c on a.id=c.saleid "+
                "inner join Products d on d.id=c.productid where a.date between :first and :second");
                query_objects.setParameter("first", java.sql.Date.valueOf(date1.getValue()));
                query_objects.setParameter("second", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]>list_objects=query_objects.getResultList();
                final ObservableList<DateClientsClass>observable_list=FXCollections.observableArrayList();

                date.setCellValueFactory(new PropertyValueFactory<>("date"));
                first.setCellValueFactory(new PropertyValueFactory<>("first"));
                middle.setCellValueFactory(new PropertyValueFactory<>("middle"));
                last.setCellValueFactory(new PropertyValueFactory<>("last"));
                quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));

                for(Object[]object:list_objects)
                {
                    DateClientsClass element=new DateClientsClass();
                    element.setDate(""+object[0]);
                    element.setFirst((String)object[1]);
                    element.setMiddle((String)object[2]);
                    element.setLast((String)object[3]);
                    element.setQuantity(""+object[4]);
                    element.setName((String)object[5]);
                    observable_list.add(element);
                }

                table.getItems().addAll(observable_list);
                button.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));

                FilteredList<DateClientsClass>filtered_list=new FilteredList<>(observable_list, e-> true);
                search.setOnKeyReleased(e-> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
                filtered_list.setPredicate((Predicate<?super DateClientsClass>) client->
                {
                    if(newValue==null || newValue.isEmpty()) { return true; }
                    String lower_case=newValue.toLowerCase();
                    if(client.getDate().toLowerCase().contains(newValue)) { return true; }
                    else if(client.getFirst().toLowerCase().contains(lower_case)) { return true; }
                    else if(client.getMiddle().toLowerCase().contains(lower_case)) { return true; }
                    else if(client.getLast().toLowerCase().contains(lower_case)) { return true; }
                    else if(client.getQuantity().toLowerCase().contains(lower_case)) { return true; }
                    else return client.getName().toLowerCase().contains(lower_case);
                }));

                    SortedList<DateClientsClass>sorted_list=new SortedList<>(filtered_list);
                    sorted_list.comparatorProperty().bind(table.comparatorProperty());
                    table.getItems().clear();
                    table.getItems().addAll(sorted_list);
                });

                db.getSession().close();
                db.getFactory().close();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}