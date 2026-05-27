package van.edu.duanquanlybanhang;

import java.util.ArrayList;

public class OrderModel {

    private String table;
    private String total;
    private String date;
    private String dateOnly;
    private ArrayList<CartItem> items;

    public OrderModel() {}

    public OrderModel(String table, String total, String date, String dateOnly, ArrayList<CartItem> items) {
        this.table = table;
        this.total = total;
        this.date = date;
        this.dateOnly = dateOnly;
        this.items = items;
    }

    public String getTable() { return table; }
    public String getTotal() { return total; }
    public String getDate() { return date; }
    public String getDateOnly() { return dateOnly; }
    public ArrayList<CartItem> getItems() { return items; }
}