package sample.Controllers;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sample.Inq.D6;
import java.util.*;
import java.util.function.Predicate;

public class ControllerDate6
{
    @FXML DatePicker date1, date2;
    @FXML Button but;
    @FXML TextField search;
    @FXML TableView<D6> table;
    @FXML private TableColumn<D6, String> id;
    @FXML private TableColumn<D6, String> first;
    @FXML private TableColumn<D6, String> middle;
    @FXML private TableColumn<D6, String> last;
    @FXML private TableColumn<D6, String> sid;
    @FXML private TableColumn<D6, String> date;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public void showw()
    {
        try
        {
            if(date1.getValue()==null || date2.getValue()==null || date2.getValue().isBefore(date1.getValue()))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field must not be empty or have a invalid value!");
                errorAlert.showAndWait();
            }
            else
            {
                Configuration cfg = new Configuration();
                cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession();

                table.getItems().clear();
                table.refresh();

                Query qry = session.createQuery("SELECT u.id, u.fname, u.mname, u.lname, d.id, d.date " +
                        "FROM Users u inner join Deliveries d on u.id=d.cbid" +
                        " where d.date between :d1 and :d2 and u.id>1");
                qry.setParameter("d1", java.sql.Date.valueOf(date1.getValue()));
                qry.setParameter("d2", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]> r1 = qry.getResultList();

                final ObservableList<D6> d=FXCollections.observableArrayList();

                id.setCellValueFactory(new PropertyValueFactory<>("id"));
                first.setCellValueFactory(new PropertyValueFactory<>("first"));
                middle.setCellValueFactory(new PropertyValueFactory<>("middle"));
                last.setCellValueFactory(new PropertyValueFactory<>("last"));
                sid.setCellValueFactory(new PropertyValueFactory<>("sid"));
                date.setCellValueFactory(new PropertyValueFactory<>("date"));

                for(Object[] elements: r1)
                {
                    D6 a = new D6();
                    a.setId(""+elements[0]);
                    a.setFirst((String) elements[1]);
                    a.setMiddle((String) elements[2]);
                    a.setLast((String) elements[3]);
                    a.setSid("Del. ID: "+elements[4]);
                    a.setDate("Del. Date: "+elements[5]);
                    d.add(a);
                }

                Query qry1 = session.createQuery("SELECT u.id, u.fname, u.mname, u.lname, s.id, s.date " +
                        "FROM Users u inner join Sales s on u.id=s.cbid" +
                        " where s.date between :d1 and :d2 and u.id>1");
                qry1.setParameter("d1", java.sql.Date.valueOf(date1.getValue()));
                qry1.setParameter("d2", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]> r = qry1.getResultList();

                for(Object[] elements: r)
                {
                    D6 a = new D6();
                    a.setId(""+elements[0]);
                    a.setFirst((String) elements[1]);
                    a.setMiddle((String) elements[2]);
                    a.setLast((String) elements[3]);
                    a.setSid("Sale ID: "+elements[4]);
                    a.setDate("Sale Date: "+elements[5]);
                    d.add(a);
                }
                table.getItems().addAll(d);

                but.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));

                FilteredList<D6> fd=new FilteredList<>(d, e-> true);
                search.setOnKeyReleased(e -> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
                {
                    fd.setPredicate((Predicate<? super D6>) d6->
                    {
                        if (newValue == null || newValue.isEmpty()) { return true; }
                        String lower = newValue.toLowerCase();
                        if (d6.getId().contains(newValue)) { return true; }
                        else if (d6.getFirst().toLowerCase().contains(lower)) { return true; }
                        else if (d6.getMiddle().toLowerCase().contains(lower)) { return true; }
                        else if (d6.getLast().toLowerCase().contains(lower)) { return true; }
                        else if (d6.getSid().contains(newValue)) { return true; }
                        else if (d6.getDate().contains(newValue)) { return true; }
                        return false;
                    });
                });

                    SortedList<D6> sd=new SortedList<>(fd);
                    sd.comparatorProperty().bind(table.comparatorProperty());
                    table.setItems(sd); });
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}