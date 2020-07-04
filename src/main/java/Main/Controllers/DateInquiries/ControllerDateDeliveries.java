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
import Main.InquirieClasses.DateDeliveriesClass;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerDateDeliveries
{
    @FXML DatePicker date1, date2;
    @FXML Button button;
    @FXML TextField search;
    @FXML TableView<DateDeliveriesClass>table;
    @FXML private TableColumn<DateDeliveriesClass, String>date;
    @FXML private TableColumn<DateDeliveriesClass, String>first;
    @FXML private TableColumn<DateDeliveriesClass, String>middle;
    @FXML private TableColumn<DateDeliveriesClass, String>last;
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
                ("SELECT e.date, p.fname, p.mname, p.lname FROM Deliveries e inner join Providers p on e.providerid=p.id where e.date between :first and :second");
                query_objects.setParameter("first", java.sql.Date.valueOf(date1.getValue()));
                query_objects.setParameter("second", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]>list_objects=query_objects.getResultList();
                final ObservableList<DateDeliveriesClass>observable_list=FXCollections.observableArrayList();

                date.setCellValueFactory(new PropertyValueFactory<>("date"));
                first.setCellValueFactory(new PropertyValueFactory<>("first"));
                middle.setCellValueFactory(new PropertyValueFactory<>("middle"));
                last.setCellValueFactory(new PropertyValueFactory<>("last"));

                for(Object[]object:list_objects)
                {
                    DateDeliveriesClass element=new DateDeliveriesClass();
                    element.setDate(""+object[0]);
                    element.setFirst((String)object[1]);
                    element.setMiddle((String)object[2]);
                    element.setLast((String)object[3]);
                    observable_list.add(element);
                }

                table.getItems().addAll(observable_list);
                button.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));

                FilteredList<DateDeliveriesClass>filtered_list=new FilteredList<>(observable_list, e->true);
                search.setOnKeyReleased(e-> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
                filtered_list.setPredicate((Predicate<?super DateDeliveriesClass>) delivery->
                {
                    if(newValue==null || newValue.isEmpty()) { return true; }
                    String lower_case=newValue.toLowerCase();
                    if(delivery.getDate().toLowerCase().contains(newValue)) { return true; }
                    else if(delivery.getFirst().toLowerCase().contains(lower_case)) { return true; }
                    else if(delivery.getMiddle().toLowerCase().contains(lower_case)) { return true; }
                    else return delivery.getLast().toLowerCase().contains(lower_case);
                }));

                    SortedList<DateDeliveriesClass>sorted_list=new SortedList<>(filtered_list);
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