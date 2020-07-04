package Main.Tables;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name="\"Sales\"")
public class Sales implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="\"ID\"", unique=true) @Id private int id;
    @Column(name="\"ClientID\"", nullable=false) private int clientid;
    @Column(name="\"Date\"", nullable=false, columnDefinition="DATE") private Date date;
    @Column(name="\"CreatedByID\"", nullable=false) private int cbid;

    public int getId() { return id; }
    public void setId(int id) { this.id=id; }
    public int getClientId() { return clientid; }
    public void setClientId(int clientid) { this.clientid=clientid; }
    public Date getDate() {return date;}
    public void setDate(Date date) { this.date=date; }
    public int getCbId() { return cbid; }
    public void setCbId(int cbid) { this.cbid=cbid; }

    public ObservableValue<Integer>nameProperty() { return new ReadOnlyObjectWrapper<>(clientid); }
}