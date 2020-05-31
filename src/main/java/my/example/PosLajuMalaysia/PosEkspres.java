package my.example.PosLajuMalaysia;

public class PosEkspres extends Pos {

    private String type;

    public PosEkspres(double weight, int quantity, String type) {
        super(weight, quantity);
        this.type = type;
    }

    public PosEkspres(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
