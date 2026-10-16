package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {

    public static final String ALFABET = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    public static final char[] alfabetCharArray = ALFABET.toCharArray();
    private List<Character> alfabetPermutat;
    private Random randomNum;

    public void permutaAlfabet() {
        List<Character> llista = new ArrayList<>();
        for (char eachChar : alfabetCharArray) {
            llista.add(eachChar);
        }
        Collections.shuffle(llista, randomNum);
        alfabetPermutat = llista;
    }

    public String xifraPoliAlfa(String missatge) {
        return substitueixText(missatge, true);
    }

    public String desxifraPoliAlfa(String missatgeXifrat) {
        return substitueixText(missatgeXifrat, false);
    }

    public void initRandom(long clau) {
        randomNum = new Random(clau);
    }

    private int buscaLletra(char laLletra, boolean sentit) {
        if (sentit) {
            return ALFABET.indexOf(laLletra); 
        }else {
            return alfabetPermutat.indexOf(laLletra);
        }
    }

    private char substitueixChar(int numLletra, boolean sentit) {
        if (sentit) {
            return alfabetPermutat.get(numLletra); 
        }else {
            return ALFABET.charAt(numLletra);
        }
    }

    private String substitueixText(String missatge, boolean sentit) {
        StringBuilder nouMissatge = new StringBuilder();
        for (int i = 0; i < missatge.length(); i++) {
            char theChar = missatge.charAt(i);
            permutaAlfabet();
            boolean esMinuscula = Character.isLowerCase(theChar);
            char charMajusc = Character.toUpperCase(theChar);
            int numLletra = buscaLletra(charMajusc, sentit);
            if (numLletra != -1) {
                char lletraSubstituida = substitueixChar(numLletra, sentit);
                nouMissatge.append(esMinuscula ? Character.toLowerCase(lletraSubstituida) : lletraSubstituida);
            } else {
                nouMissatge.append(theChar);
            }
        }
        return nouMissatge.toString();
    }

    private long validaClau(String clau) throws ClauNoSuportada {
        try {
            return Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada(
                    "La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
    }

    @Override
    public TextXifrat xifra(String msg, String clau)
            throws ClauNoSuportada {

        long clauNumerica = validaClau(clau);
        initRandom(clauNumerica);

        String resultat = xifraPoliAlfa(msg);

        return new TextXifrat(resultat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau)
            throws ClauNoSuportada {

        long clauNumerica = validaClau(clau);
        initRandom(clauNumerica);

        String text = new String(xifrat.getBytes(), StandardCharsets.UTF_8);

        return desxifraPoliAlfa(text);
    }
}
