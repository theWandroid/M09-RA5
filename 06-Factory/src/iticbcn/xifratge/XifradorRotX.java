package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;

public class XifradorRotX implements Xifrador {

    private static final String alfabet
            = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";

    private final char[] minuscules = alfabet.toCharArray();
    private final char[] majuscles = alfabet.toUpperCase().toCharArray();

    public String transformaText(boolean sentit, String text, int rotacio) {
        StringBuffer newText = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            char lletra = text.charAt(i);

            char[] lletres = Character.isLowerCase(lletra)
                    ? minuscules : majuscles;

            int posicio = buscaLletra(lletra, lletres);

            if (posicio != -1) {
                newText.append(
                        substitueix(posicio, lletres, sentit, rotacio));
            } else {
                newText.append(lletra);
            }
        }

        return newText.toString();
    }

    public void xifratRotX(String text, int rotacio) {
        System.out.print(transformaText(true, text, rotacio));
    }

    public void desXifratRotX(String text, int rotacio) {
        System.out.print(transformaText(false, text, rotacio));
    }

    public int buscaLletra(char laLletra, char[] arrayLletres) {
        boolean found = false;
        int counter = 0;

        while (!found && counter < arrayLletres.length) {
            if (laLletra == arrayLletres[counter]) {
                found = true;
            } else {
                counter++;
            }
        }
        if (found) {
            return counter;
        } else {
            return -1;
        }
    }

    public char substitueix(int inici, char[] arrayLletres, boolean sentit, int rotacio) {
        char novaLletra = ' ';
        int fi = 0;
        if (inici < arrayLletres.length) {

            if (sentit) {
                fi = (inici + rotacio) % arrayLletres.length;
            } else {
                fi = inici - rotacio;
                if (fi < 0) {
                    fi = arrayLletres.length + fi;
                }
            }
            novaLletra = arrayLletres[fi];
        }
        return novaLletra;
    }

    private int validaClau(String clau) throws ClauNoSuportada {
        try {
            int rotacio = Integer.parseInt(clau);

            if (rotacio >= 0 && rotacio <= 40) {
                return rotacio;
            }
        } catch (NumberFormatException e) {
            // La clau no es pot convertir a enter.
        }

        throw new ClauNoSuportada(
                "Clau de RotX ha de ser un sencer de 0 a 40");
    }

    @Override
    public TextXifrat xifra(String msg, String clau)
            throws ClauNoSuportada {

        int rotacio = validaClau(clau);
        String resultat = transformaText(true, msg, rotacio);

        return new TextXifrat(resultat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau)
            throws ClauNoSuportada {

        int rotacio = validaClau(clau);
        String text = new String(xifrat.getBytes(), StandardCharsets.UTF_8);

        return transformaText(false, text, rotacio);
    }
}
