
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monoalfabetic {
    public static final String alfabet = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    public static final char [] alfabetCharArray = alfabet.toCharArray();
    private static final char[] alfabetXifrat = getAlfabetXifrat(alfabetCharArray);

    public static void main(String[] args) {
        showCharArray(alfabetCharArray);
        showCharArray(alfabetXifrat);
        //PRoxim pas xifrar i desxifrar
    }

    public static void showCharArray(char[] theArray){
        for(int i=0; i < theArray.length; i++)
            System.out.print(theArray[i]);
        System.out.println();
    }

    public static char[] getAlfabetXifrat(char[] theArray){

        //Convertir de char[] a List
        List<Character> llistaXifrada = new ArrayList<>();
        for(int i=0; i < theArray.length; i++){
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
}
