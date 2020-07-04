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
import Main.InquirieClasses.DateActivityClass;
import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerDateActivity
{
    @FXML DatePicker date1, date2;
    @FXML Button button;
    @FXML TextField search;
    @FXML TableView<DateActivityClass>table;
    @FXML private TableColumn<DateActivityClass, String>id;
    @FXML private TableColumn<DateActivityClass, String>first;
    @FXML private TableColumn<DateActivityClass, String>middle;
    @FXML private TableColumn<DateActivityClass, String>last;
    @FXML private TableColumn<DateActivityClass, String>sid;
    @FXML private TableColumn<DateActivityClass, String>date;
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
                ("SELECT u.id, u.fname, u.mname, u.lname, d.id, d.date "+
                "FROM Users u inner join Deliveries d on u.id=d.cbid"+
                " where d.date between :first and :second and u.id>1");
                query_objects.setParameter("first", java.sql.Date.valueOf(date1.getValue()));
                query_objects.setParameter("second", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]>list_objects=query_objects.getResultList();
                final ObservableList<DateActivityClass>observable_list=FXCollections.observableArrayList();

                id.setCellValueFactory(new PropertyValueFactory<>("id"));
                first.setCellValueFactory(new PropertyValueFactory<>("first"));
                middle.setCellValueFactory(new PropertyValueFactory<>("middle"));
                last.setCellValueFactory(new PropertyValueFactory<>("last"));
                sid.setCellValueFactory(new PropertyValueFactory<>("sid"));
                date.setCellValueFactory(new PropertyValueFactory<>("date"));

                for(Object[]object:list_objects)
                {
                    DateActivityClass element=new DateActivityClass();
                    element.setId(""+object[0]);
                    element.setFirst((String)object[1]);
                    element.setMiddle((String)object[2]);
                    element.setLast((String)object[3]);
                    element.setSid("Del. ID: "+object[4]);
                    element.setDate("Del. Date: "+object[5]);
                    observable_list.add(element);
                }

                Query<Object[]>query_objects1= db.getSession().createQuery
                ("SELECT u.id, u.fname, u.mname, u.lname, s.id, s.date "+
                "FROM Users u inner join Sales s on u.id=s.cbid"+
                " where s.date between :first and :second and u.id>1");
                query_objects1.setParameter("first", java.sql.Date.valueOf(date1.getValue()));
                query_objects1.setParameter("second", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]>list_objects1=query_objects1.getResultList();

                for(Object[]object:list_objects1)
                {
                    DateActivityClass element=new DateActivityClass();
                    element.setId(""+object[0]);
                    element.setFirst((String)object[1]);
                    element.setMiddle((String)object[2]);
                    element.setLast((String)object[3]);
                    element.setSid("Sale ID: "+object[4]);
                    element.setDate("Sale Date: "+object[5]);
                    observable_list.add(element);
                }

                table.getItems().addAll(observable_list);
                button.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));

                FilteredList<DateActivityClass>filtered_list=new FilteredList<>(observable_list, e-> true);
                search.setOnKeyReleased(e-> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
                filtered_list.setPredicate((Predicate<?super DateActivityClass>) operator->
                {
                    if(newValue==null || newValue.isEmpty()) { return true; }
                    String lower_case=newValue.toLowerCase();
                    if(operator.getId().contains(newValue)) { return true; }
                    else if(operator.getFirst().toLowerCase().contains(lower_case)) { return true; }
                    else if(operator.getMiddle().toLowerCase().contains(lower_case)) { return true; }
                    else if(operator.getLast().toLowerCase().contains(lower_case)) { return true; }
                    else if(operator.getSid().contains(newValue)) { return true; }
                    else return operator.getDate().contains(newValue);
                }));

                    SortedList<DateActivityClass>sorted_list=new SortedList<>(filtered_list);
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