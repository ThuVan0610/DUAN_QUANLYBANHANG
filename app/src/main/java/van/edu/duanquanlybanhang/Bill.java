package van.edu.duanquanlybanhang;

import java.util.ArrayList;

public class Bill {

    private String table;
    private double total;
    private String time;
    private ArrayList<OrderItem> items;
    public Bill() {
    }

    public Bill(String table,
                double total,
                String time,
                ArrayList<OrderItem> items) {

        this.table = table;
        this.total = total;
        this.time = time;
        this.items = items;
    }

    public String getTable() {
        return table;
    }

    public double getTotal() {
        return total;
    }

    public String getTime() {
        return time;
    }
    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public void setItems(
            ArrayList<OrderItem> items) {

        this.items = items;
    }
}