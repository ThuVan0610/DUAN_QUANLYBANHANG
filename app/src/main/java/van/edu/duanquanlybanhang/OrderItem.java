package van.edu.duanquanlybanhang;

public class OrderItem {

    private String name;
    private double price;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(String name,
                     double price,
                     int quantity) {

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }
}