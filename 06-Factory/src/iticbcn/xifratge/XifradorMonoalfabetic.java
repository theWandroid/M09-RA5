package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Tennir en compte temps d'execució i temps de compilació
public class XifradorMonoalfabetic implements Xifrador {

    public XifradorMonoalfabetic() {
        alfabetXifrat = getAlfabetXifrat(alfabetCharArray);
    }
    public static final String alfabet = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    public static final char[] alfabetCharArray = alfabet.toCharArray();
    private final char[] alfabetXifrat;

    public void showCharArray(char[] theArray) {
        StringBuffer text = new StringBuffer();
        for (int i = 0; i < theArray.length; i++) {
            text.append(theArray[i]);
        }
        System.out.println(text);
    }

    public char[] getAlfabetXifrat(char[] theArray) {

        //Convertir de char[] a List
        List<Character> llistaXifrada = new ArrayList<>();
        for (int i = 0; i < theArray.length; i++) {
            llistaXifrada.add(theArray[i]);
        }

        Collections.shuffle(llistaXifrada);

        //Convertir un altre
        char[] alfabetXifrat = new char[llistaXifrada.size()];
        for (int i = 0; i < llistaXifrada.size(); i++) {
            alfabetXifrat[i] = llistaXifrada.get(i);
        }

        return alfabetXifrat;
    }

    private int buscaLletra(char laLletra, boolean sentit) {
    boolean found = false;
    int count = 0;

    char[] alfabet = sentit ? alfabetCharArray : alfabetXifrat;

    while (!found && count < alfabet.length) {
        char theChar = alfabet[count];

        if (laLletra == theChar) {
            found = true;
        } else {
            count++;
        }
    }

    if (found) {
        return count;
    } else {
        return -1;
    }
}

    private String xifraMonoAlfa(String missatge) {
        return subtitueixText(missatge, true);

    }

    private String desxifrMonoAlfa(String missatge) {
        return subtitueixText(missatge, false);
    }

    private char substitueixChar(int numLletra, boolean sentit) {
        char theChar = '+';
        if (sentit) {
            theChar = alfabetXifrat[numLletra];
        } else {
            theChar = alfabetCharArray[numLletra];
        }
        return theChar;

    }

    private String subtitueixText(String missatge, boolean sentit) {
    StringBuffer nouMissatge = new StringBuffer();
    char[] missatgeCharArray = missatge.toCharArray();

    for (int i = 0; i < missatge.length(); i++) {
        char theChar = missatgeCharArray[i];

        if (Character.isLetter(theChar)) {
            if (Character.isLowerCase(theChar)) {
                int numLletra = buscaLletra(
                        Character.toUpperCase(theChar), sentit);

                if (numLletra != -1) {
                    nouMissatge.append(Character.toLowerCase(
                            substitueixChar(numLletra, sentit)));
                } else {
                    nouMissatge.append(theChar);
                }

            } else {
                int numLletra = buscaLletra(theChar, sentit);

                if (numLletra != -1) {
                    nouMissatge.append(substitueixChar(numLletra, sentit));
                } else {
                    nouMissatge.append(theChar);
                }
            }

        } else {
            nouMissatge.append(theChar);
        }
    }

    return nouMissatge.toString();
}

    @Override
    public TextXifrat xifra(String msg, String clau)
            throws ClauNoSuportada {

        if (clau != null) {
            throw new ClauNoSuportada(
                    "Xifratxe monoalfabètic no suporta clau != null");
        }

        String resultat = xifraMonoAlfa(msg);
        return new TextXifrat(resultat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau)
            throws ClauNoSuportada {

        if (clau != null) {
            throw new ClauNoSuportada(
                    "Xifratxe monoalfabètic no suporta clau != null");
        }

        String text = new String(xifrat.getBytes(), StandardCharsets.UTF_8);
        return desxifrMonoAlfa(text);
    }
}
