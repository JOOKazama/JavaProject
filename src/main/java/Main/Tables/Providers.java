package Main.Tables;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="\"Providers\"", schema="public")
public class Providers implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="\"ID\"", unique=true) @Id private int id;
    @Column(name="\"FirstName\"", nullable=false) private String fname;
    @Column(name="\"MiddleName\"", nullable=false) private String mname;
    @Column(name="\"LastName\"", nullable=false) private String lname;
    @Column(name="\"Company\"", nullable=false) private String company;
    @Column(name="\"VatNumber\"", nullable=false) private int vatnumber;

    public int getId() { return id; }
    public void setId(int id) { this.id=id; }
    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname=fname; }
    public String getMname() { return mname; }
    public void setMname(String mname) { this.mname=mname; }
    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname=lname; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company=company; }
    public int getVatnumber() { return vatnumber; }
    public void setVatnumber(int vatnumber) { this.vatnumber=vatnumber; }
}