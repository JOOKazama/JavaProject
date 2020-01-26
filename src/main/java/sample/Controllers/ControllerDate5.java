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
import sample.Inq.D5;
import java.util.*;
import java.util.function.Predicate;

public class ControllerDate5
{
    @FXML DatePicker date1, date2;
    @FXML Button but;
    @FXML TextField search;
    @FXML TableView<D5> table;
    @FXML private TableColumn<D5, String> name;
    @FXML private TableColumn<D5, String> price;
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
                Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory(); Session session = factory.openSession();

                table.getItems().clear(); table.refresh();

                Query qry = session.createQuery("SELECT e.name, m.deliveryprice FROM Products e inner join ProductDeliveries m on e.id=m.productid inner join Deliveries o on o.id=m.deliveryid where o.date between :d1 and :d2");
                qry.setParameter("d1", java.sql.Date.valueOf(date1.getValue()));
                qry.setParameter("d2", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]> r1 = qry.getResultList();
                final ObservableList<D5> d=FXCollections.observableArrayList();

                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                price.setCellValueFactory(new PropertyValueFactory<>("price"));

                for(Object[] elements: r1)
                {
                    D5 a = new D5();
                    a.setName(""+elements[0]);
                    a.setPrice(""+elements[1]);

                    if(!d.contains(a)) { d.add(a); }
                }
                table.getItems().addAll(d);
                but.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));

                FilteredList<D5> fd=new FilteredList<>(d, e-> true);
                search.setOnKeyReleased(e -> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
                {
                    fd.setPredicate((Predicate<? super D5>) d5->
                    {
                        if (newValue == null || newValue.isEmpty()) { return true; }
                        String lower = newValue.toLowerCase();
                        if (d5.getName().toLowerCase().contains(lower)) { return true; }
                        else if (d5.getPrice().contains(newValue)) { return true; }
                        return false;
                    });
                });

                    SortedList<D5> sd=new SortedList<>(fd);
                    sd.comparatorProperty().bind(table.comparatorProperty());
                    table.setItems(sd); });

                session.close(); factory.close();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}