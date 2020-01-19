package sample.Tables;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="\"Bank\"")
public class Bank implements Serializable
{
    @Column(name = "\"ID\"", unique=true) @Id private int id;
    @Column(name = "\"Funds\"")private double funds;

    public void setId(int id) { this.id = id; } public int getId() { return id; }
    public void setFunds(double funds) { this.funds = funds; } public double getFunds() { return funds; }
}
