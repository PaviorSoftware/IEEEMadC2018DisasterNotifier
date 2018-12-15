package gr.teicm.ieee.madc.disasternotifierservice.model;

public class DisasterRange {

    private Long red;
    private Long orange;
    private Long green;

    public DisasterRange(Long red, Long orange, Long green) {
        this.red = red;
        this.orange = orange;
        this.green = green;
    }

    public Long getRed() {
        return red;
    }

    public void setRed(Long red) {
        this.red = red;
    }

    public Long getOrange() {
        return orange;
    }

    public void setOrange(Long orange) {
        this.orange = orange;
    }

    public Long getGreen() {
        return green;
    }

    public void setGreen(Long green) {
        this.green = green;
    }
}
