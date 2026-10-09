
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Tennir en compte temps d'execució i temps de compilació
public class Monoalfa {
    public static final String alfabet = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    public static final char [] alfabetCharArray = alfabet.toCharArray();
    private static final char[] alfabetXifrat = getAlfabetXifrat(alfabetCharArray);

    public static void main(String[] args) {
        showCharArray(alfabetCharArray);
        showCharArray(alfabetXifrat);
        System.out.println("Xifratge:"); 
        System.out.printf("%-35s  --> %s\n", "Test 01 àrbitre, coixí, Perímetre",xifraMonoAlfa("àrbitre, coixí, Perímetre")); 
        System.out.printf("%-35s  --> %s\n", "Test 02 Taüll, DÍA, año", xifraMonoAlfa("Taüll, DÍA, año")); 
        System.out.printf("%-35s  --> %s\n", "Test 03 Peça, Òrrius, Bòvila", xifraMonoAlfa("Peça, Òrrius, Bòvila"));

        System.out.println("Desxifratge:"); 
        System.out.printf("%-35s  --> %s\n", "Àùjà 01 qéóéoàéù, emoaz, Xùézyùàéù", desxifrMonoAlfa("Àùjà 01 qéóéoàéù, emoaz, Xùézyùàéù")); 
        System.out.printf("%-35s  --> %s\n", "Àùjà 02 Àúbññ, ÌZÚ, útm", desxifrMonoAlfa("Àùjà 02 Àúbññ, ÌZÚ, útm")); 
        System.out.printf("%-35s  --> %s\n", "Àùjà 03 Xùwú, Ïééosj, Óïfoñú   ", desxifrMonoAlfa("Àùjà 03 Xùwú, Ïééosj, Óïfoñú"));
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

    private static int buscaLletra(char laLletra, boolean sentit){
        boolean found = false;
        int count = 0;
        char [] alfabet = sentit ? alfabetCharArray : alfabetXifrat;
        //showCharArray(alfabet);

        while(!found && count < alfabet.length){
            char theChar = alfabet[count];
            if(laLletra == theChar)
                found = true;
            else
                count++;
        }
        return count;


    }

    private static String xifraMonoAlfa(String missatge){
        return subtitueixText(missatge, true);

    }

    private static String desxifrMonoAlfa(String missatge){
       return subtitueixText(missatge, false);
    }

    private static char substitueixChar( int numLletra, boolean sentit){
        char theChar = '+';
        if(sentit){
            theChar =alfabetXifrat[numLletra];
        }else{
            theChar = alfabetCharArray[numLletra];
        }
return theChar;

    }

    private static String subtitueixText( String missatge, boolean sentit){
        StringBuffer nouMissatge = new StringBuffer();
        char [] missatgeCharArray = missatge.toCharArray();
        for(int i=0; i < missatge.length(); i++ ){
            char theChar = missatgeCharArray[i];
            if(Character.isLetter(theChar)){
                if(Character.isLowerCase(theChar)){
                int numLletra =  buscaLletra(Character.toUpperCase(theChar), sentit);
                    if(numLletra != -1)
                        nouMissatge.append(Character.toLowerCase(substitueixChar(numLletra, sentit)));
                }else{
                int numLletra =  buscaLletra(theChar, sentit);
                    if(numLletra != -1)
                        nouMissatge.append(substitueixChar(numLletra, sentit));
                }
            }else{
                nouMissatge.append(theChar);
            }

        }
        return nouMissatge.toString();
    }
}
