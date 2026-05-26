package van.edu.duanquanlybanhang;

import java.util.ArrayList;

public class OrderModel {

    private String table;
    private String total;
    private String date;
    private ArrayList<CartItem> items;

    public OrderModel() {}

    public OrderModel(String table, String total, String date, ArrayList<CartItem> items) {
        this.table = table;
        this.total = total;
        this.date = date;
        this.items = items;
    }

    public String getTable() { return table; }
    public String getTotal() { return total; }
    public String getDate() { return date; }
    public ArrayList<CartItem> getItems() { return items; }
}