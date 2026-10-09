
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Polialfabetic {

    public static final String ALFABET = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    public static final char[] alfabetCharArray = ALFABET.toCharArray();
    private static final int clauSecreta = 2;
    //private static char[] alfabetPermutat = new char[alfabetCharArray.length];
    private static List<Character> alfabetPermutat;
    private static Random randomNum;
    

    public static void main(String[] args) {

        String msgs[] = {"Test 01 àrbitre, coixí, Perímetre", "Test 02 Taüll, DÍA, año", "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];
        System.out.println("Xifratge:\\n----------");

        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }
        System.out.println("Desxifratge:\\n----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }

    public static void permutaAlfabet() {
        List<Character> llista = new ArrayList<>();
        for (char eachChar : alfabetCharArray) 
            llista.add(eachChar);
        Collections.shuffle(llista, randomNum);
        alfabetPermutat = llista;
    }

    public static String xifraPoliAlfa(String missatge) {
        return substitueixText(missatge, true);
    }

    public static String desxifraPoliAlfa(String missatgeXifrat) {
        return substitueixText(missatgeXifrat, false);
    }

    public static void initRandom(int clau) {
        randomNum = new Random(clau);
    }

    private static int buscaLletra(char laLletra, boolean sentit) {
        if (sentit) 
            return ALFABET.indexOf(laLletra); 
        else 
            return alfabetPermutat.indexOf(laLletra);
    }

    private static char substitueixChar(int numLletra, boolean sentit) {
        if (sentit) 
            return alfabetPermutat.get(numLletra); 
        else 
            return ALFABET.charAt(numLletra);
    }

    private static String substitueixText(String missatge, boolean sentit) {
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
            } else 
                nouMissatge.append(theChar);
        }
        return nouMissatge.toString();
    }
}
