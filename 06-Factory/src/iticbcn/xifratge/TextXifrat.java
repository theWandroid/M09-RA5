package iticbcn.xifratge;
import java.nio.charset.StandardCharsets;

public class TextXifrat {

    private final byte[] dades;
    
    public TextXifrat(byte[] dades) {
        this.dades = dades;
    }

    public byte[] getBytes() {
        return dades;
    }

    @Override 
    public String toString() {
        return new String(dades, StandardCharsets.UTF_8);
    }
    
}
