package Main.InquirieClasses;

public class DateProductsWHClass
{
    private String name; private String price;

    public String getName() { return name; } public void setName(String name) { this.name=name; }
    public String getPrice() { return price; } public void setPrice(String price) { this.price=price; }

    public boolean equals(Object a)
    {
        if(this == a) return true; if(!(a instanceof DateProductsWHClass)) return false;
        DateProductsWHClass a1 = (DateProductsWHClass) a;

        return this.name.equals(a1.name) && this.price.equals(a1.price);
    }
}
