package Main.Tables;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Table(name="\"Products\"")
public class Products implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="\"ID\"", unique=true, nullable=false) @Id private int id;
    @Column(name="\"Name\"") private String name;
    @Column(name="\"Price\"") private Double price;
    @Column(name="\"Stock\"") private int stock;

    public int getId() { return id; }
    public void setId(int id) { this.id=id; }
    public String getName() { return name; }
    public void setName(String name) { this.name=name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price=price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock=stock; }
}