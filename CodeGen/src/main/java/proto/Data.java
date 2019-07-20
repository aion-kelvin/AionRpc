package proto;

public class Data {
    private byte[] value;

    public Data(byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return this.value;
    }
}
