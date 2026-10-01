
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Tennir en compte temps d'execució i temps de compilació
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
        StringBuffer text = new StringBuffer();
        for(int i=0; i < theArray.length; i++)
            text.append(theArray[i]);
        System.out.println(text);
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

    private static int buscaLletra(char laLletra){
        boolean found = false;
        int count = 0;
        while(!found && count < alfabetCharArray.length){
            char theChar = alfabetCharArray[count];
            if(laLletra == theChar)
                found = true;
            else
                count++;


        }

        return found? true: false;


    }

    private static String xifraMonoAlfa(String missatge){

    }

    private static String desxifrMonoAlfa(String missatge){

    }

    private static char substitueixChar( char laLletra){

    }

    private static String subtitueixText( String missatge){
        for(int i=0; i < missatge.length(); i++ ){
            char theChar = alfabetCharArray[i];
            if(Character.isLetter(theChar)){
                if(Character.isUpperCase(theChar)){
                    buscaLletra(Character.toLowerCase(theChar));

                }else
                    buscaLletra(theChar);

            }

        }
    }
}
