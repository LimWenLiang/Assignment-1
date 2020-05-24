package PosLajuMalaysia;

public class NextDayDelivery extends Pos {

    private int zone;

    public NextDayDelivery(double weight, int quantity, double price, int zone) {
        super(weight, quantity, price);
        this.zone = zone;
    }

    public NextDayDelivery(double weight, int quantity, int zone) {
        super(weight, quantity);
        this.zone = zone;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

}
