package sample;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="\"Sales\"")
public class Sales implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"ID\"", unique=true) @Id private int id;
    @Column(name = "\"ClientID\"", nullable=false) private int clientid;
    @Column(name = "\"Date\"", nullable=false, columnDefinition = "DATE") private Date date;

    public int getId() {return id;} public void setId(int id) {this.id=id;}
    public int getClientId() {return clientid;} public void setClientId(int clientid) {this.clientid=clientid;}
    public Date getDate() {return date;} public void setDate(Date date) {this.date=date;}

}
