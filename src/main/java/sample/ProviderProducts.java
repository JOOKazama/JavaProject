package sample;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="\"ProviderProducts\"")
public class ProviderProducts implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"ID\"", unique=true) @Id private int id;
    @Column(name = "\"ProviderID\"", nullable=false) private int providerid;
    @Column(name = "\"ProductID\"", nullable=false) private int productid;

    public int getId() {return id;} public void setId(int id) {this.id=id;}
    public int getProviderId() {return providerid;} public void setProviderId(int providerid) {this.providerid=providerid;}
    public int getProductId() {return productid;} public void setProductId(int productid) {this.productid=productid;}
}
