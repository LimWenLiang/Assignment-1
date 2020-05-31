package my.example.PosLajuMalaysia;

public class SameDayDelivery extends Pos {

    private int townType;

    public SameDayDelivery(double weight, int quantity, double price, int townType) {
        super(weight, quantity, price);
        this.townType = townType;
    }

    public SameDayDelivery(double weight, double price, int townType) {
        super(weight, price);
        this.townType = townType;
    }

    public SameDayDelivery(double weight, int townType) {
        super(weight);
        this.townType = townType;
    }

    public int getTownType() {
        return townType;
    }

    public void setTownType(int townType) {
        this.townType = townType;
    }

}
