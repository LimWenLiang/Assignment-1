package PosLajuMalaysia;

public class PrepaidBoxNEnvelope extends Pos {

    private String jenis;

    public PrepaidBoxNEnvelope(double weight, int quantity, double price, String jenis) {
        super(weight, quantity, price);
        this.jenis = jenis;
    }

    public PrepaidBoxNEnvelope(double weight, int quantity, String jenis) {
        super(weight, quantity);
        this.jenis = jenis;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

}
