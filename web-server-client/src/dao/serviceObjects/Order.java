package dao.serviceObjects;

import dao.Company;

import java.util.Date;

/**
 * Created by Mostafa on 1/22/2016.
 */
public class Order {
    public String type;
    public int count;
    public double itemPrice;
    public double totalPrice;
    public Date deliveryDate;
    public Company buyer;
    public Company seller;
}
