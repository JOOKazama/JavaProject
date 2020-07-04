package Main.Tables;
import javax.persistence.Column;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="\"Roles\"")
public class Roles implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="\"ID\"", unique=true) @Id private int id;
    @Column(name="\"Name\"", nullable=false) private String name;

    public int getId() { return id; }
    public void setId(int id) { this.id=id; }
    public String getName() { return name; }
    public void setName(String name) { this.name=name; }
}