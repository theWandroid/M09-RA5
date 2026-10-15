package iticbcn.xifratge;

public interface Xifrador {
    TextXifrat xifra(String msg, String clau) throws ClauNoSuportada;

    String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada;
}
