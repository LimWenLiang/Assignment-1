package PosLajuMalaysia;

public class Pos {
    private double weight;
    private int quantity;
    private double price;

    public Pos(double weight, int quantity, double price) {
        this.weight = weight;
        this.quantity = quantity;
        this.price = price;
    }

    public Pos(double weight, int quantity) {
        this.weight = weight;
        this.quantity = quantity;
    }

    public Pos(double weight, double price) {
        this.weight = weight;
        this.price = price;
    }

    public Pos(double weight) {
        this.weight = weight;
    }

    public Pos() {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void displayReceipt() {
        double[] grand = {Menu1_NextDayDelivery.grand(), Menu2_SameDayDelivery.grand(), Menu3_PrepaidBoxNEnvelope.grand(), Menu4_PosEkspres.grand()};
        double grandTotal = 0;
        String format1 = "%-30s%-10s\n",
                format2 = "%-30s%-10.2f\n";

        System.out.printf("%25s\n", "Receipt");
        System.out.printf(format1, "List of Delivery", "Price (RM)");
        System.out.printf(format2, "Next-Day Delivery", grand[0]);
        System.out.printf(format2, "Same-Day Delivery", grand[1]);
        System.out.printf(format2, "Prepaid Box & Envelope", grand[2]);
        System.out.printf(format2, "Pos Ekspres", grand[3]);

        /*Calculate Grand Total of all delivery*/
        for (int i = 0; i < grand.length; i++) {
            grandTotal += grand[i];
        }
        System.out.printf("%-31s%-10.2f\n", "\nTotal Charge", grandTotal);
        System.out.println("\nThank You, Bye");
    }
}
