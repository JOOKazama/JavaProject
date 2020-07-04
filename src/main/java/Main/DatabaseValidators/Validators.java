package Main.DatabaseValidators;
import Main.Tables.Bank;
import javafx.scene.control.DatePicker;
import java.util.List;
import java.util.regex.Pattern;

public class Validators
{
    private String error="";

    public String getError() { return error; }
    public void setError(String error) { this.error=error; }

    private Pattern pattern=Pattern.compile("\\d+");
    private Pattern pattern1=Pattern.compile
    ("[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)"+
    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|"+
    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))"+
    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

    public void ValidatorLogin(String username, String password)
    {
        if(username.equals("") || password.equals("")) { error="The field(s) must not be empty!"; }
        else if(username.length()>20 || password.length()>20) { error="The field(s) must not contain more that 20 characters!"; }
    }

    public void ValidatorBuy(String id_client, String name, String quantity, String price, DatePicker datepicker, List<Integer>clientslist)
    {
        if(id_client.equals("") || name.equals("") || quantity.equals("") || price.equals(""))
        { error="The field(s) must not be empty!"; }
        else if(id_client.length()>20 || name.length()>20 || quantity.length()>20 || price.length()>20)
        { error="The field(s) must not contain more that 20 characters!"; }
        else if(!pattern.matcher(id_client).matches()) { error="Please enter a valid Client ID!"; }
        else if(!pattern1.matcher(price).matches()) { error="Please enter a valid Price!"; }
        else if(datepicker.getValue()==null) { error="The date field must not be empty or have a invalid value!"; }
        else if(!clientslist.contains(Integer.parseInt(id_client))) { error="No such Client found!"; }
        else if(!pattern.matcher(quantity).matches()) { error="Please enter a valid Quantity number!"; }
        else if(Integer.parseInt(quantity)<=0 || Integer.parseInt(quantity)>30)
        { error="Please enter a valid  Quantity (from 1 to 30)!"; }
    }

    public void ValidatorCreateBank(String starting_money)
    {
        if(starting_money.equals("")) { error="The field must not be empty!"; }
        else if(!pattern.matcher(starting_money).matches()) { error="Please enter a number!"; }
        else if(Double.parseDouble(starting_money)<=0 || Double.parseDouble(starting_money)>500000)
        { error="Enter a value bigger than 0 or smaller or equal to 500000!"; }
    }

    public void ValidatorCreateClient(String first_name, String middle_name, String last_name, String company_name, String vat_number)
    {
        if(first_name.equals("") || middle_name.equals("") || last_name.equals("") || company_name.equals("") || vat_number.equals(""))
        { error="The field(s) must not be empty!"; }
        else if(first_name.length()>20 || middle_name.length()>20 || last_name.length()>20 || company_name.length()>20 || vat_number.length()>20)
        { error="The field(s) must not contain more that 20 characters!"; }
        else if(!pattern.matcher(vat_number).matches() || Integer.parseInt(vat_number)<=0)
        { error="Please enter a valid  Vatnumber!"; }
    }

    public void ValidatorCreateOperator(String first_name, String middle_name, String last_name, String username, String password)
    {
        if(first_name.equals("") || middle_name.equals("") || last_name.equals("") || username.equals("") || password.equals(""))
        { error="The field(s) must not be empty!"; }
        else if(first_name.length()>20 || middle_name.length()>20 || last_name.length()>20 || username.length()>20 || password.length()>20)
        { error="The field(s) must not contain more that 20 characters!"; }
        else if(username.equals("Admin1")) { error="You can't use that name!"; }
    }

    public void ValidatorCreateProvider(String first_name, String middle_name, String last_name, String company_name, String vat_number)
    {
        if(first_name.equals("") || middle_name.equals("") || last_name.equals("") || company_name.equals("") || vat_number.equals(""))
        { error="The field(s) must not be empty!"; }
        else if(first_name.length()>20 || middle_name.length()>20 || last_name.length()>20 || company_name.length()>20 || vat_number.length()>20)
        { error="The field(s) must not contain more that 20 characters!"; }
        else if(!pattern.matcher(vat_number).matches() || Integer.parseInt(vat_number)<=0)
        { error="Please enter a valid  Vatnumber!"; }
    }

    public void ValidatorReceiveProducts(String id, List<Integer>list_provider_ids, String name, String delivery_price, String sale_price, String quantity, DatePicker date, Bank bank)
    {
        if(!pattern.matcher(id).matches()) { error="Please enter a valid Provider ID!"; }
        else if(!list_provider_ids.contains(Integer.parseInt(id))) { error="No such Provider found!"; }
        else if(id.equals("") || name.equals("") || delivery_price.equals("") || sale_price.equals("") || quantity.equals(""))
        { error="The field(s) must not be empty!"; }
        else if(id.length()>20 || name.length()>20 || delivery_price.length()>20 || sale_price.length()>20 || quantity.length()>20)
        { error="The field(s) must not contain more that 20 characters!"; }
        else if(date.getValue()==null) { error="The date field must not be empty or have a invalid value!"; }
        else if(!pattern.matcher(quantity).matches()) { error="Please enter a valid  Quantity number!"; }
        else if(Integer.parseInt(quantity)<=0 || Integer.parseInt(quantity)>30)
        { error="Please enter a valid  Quantity (from 1 to 30)!"; }
        else if(!pattern1.matcher(delivery_price).matches()) { error="Please enter a valid Delivery price!"; }
        else if(!pattern1.matcher(sale_price).matches()) { error="Please enter a valid Sale price!"; }
        else if(Double.parseDouble(delivery_price)<=0 || Double.parseDouble(delivery_price)>5000)
        { error="Enter a value bigger than 0 or smaller or equal to 5000 for Delivery price!"; }
        else if(Double.parseDouble(sale_price)<=0 || Double.parseDouble(sale_price)>5000)
        { error="Enter a value bigger than 0 or smaller or equal to 5000 for Sale price!"; }
        else if(Double.parseDouble(delivery_price)*Double.parseDouble(quantity)>bank.getFunds())
        { error="Not enough funds in the bank."; }
    }
}