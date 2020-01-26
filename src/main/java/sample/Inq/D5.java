package sample.Inq;

public class D5
{
    private String name; private String price;

    public String getName() { return name; } public void setName(String name) { this.name=name; }
    public String getPrice() { return price; } public void setPrice(String price) { this.price=price; }

    public boolean equals(Object a)
    {
        if(this == a) return true; if(!(a instanceof D5)) return false;
        D5 a1 = (D5) a;

        return this.name.equals(a1.name) && this.price.equals(a1.price);
    }
}
