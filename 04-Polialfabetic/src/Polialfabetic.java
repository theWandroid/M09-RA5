
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Polialfabetic {

    
    public static final String ALFABET = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    public static final char [] alfabetCharArray = ALFABET.toCharArray();
    private static final int llavorXifratge = 2;
    //private static char[] alfabetPermutat = new char[alfabetCharArray.length];
    private static List<Character> alfabetPermutat;
    private static Random randomNum;

   
    public static void main(String[] args) { 

        String msgs[] = {"Test 01 àrbitre, coixí, Perímetre", "Test 02 Taüll, DÍA, año", "Test 03 Peça, Òrrius, Bòvila"}; 
        String msgsXifrats[] = new String[msgs.length]; 
        System.out.println("Xifratge:\\n----------"); 

        for (int i = 0; i < msgs.length; i++) { 
            initRandom(llavorXifratge); 
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]); 
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]); 
        } 
        System.out.println("Desxifratge:\\n----------"); 
        for (int i = 0; i < msgs.length; i++) { 
            initRandom(llavorXifratge); 
            String msg = desxifraPoliAlfa(msgsXifrats[i]); 
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg); 
        } 
        }

        public static void permutaAlfabet(){
            List<Character> llista = new ArrayList<>();
            for(char eachChar: alfabetCharArray){
                llista.add(eachChar);
            }
            Collections.shuffle(llista,randomNum);
            alfabetPermutat = llista;
        }

        public static String xifraPoliAlfa(String missatge){
           return substitueixText(missatge, true);

        }

        public static String desxifraPoliAlfa(String missatgeXifrat){
           return substitueixText(missatgeXifrat, false);

        }

        public static void initRandom(int clau){
            randomNum = new Random(clau);
        }

        private static int buscaLletra(char laLletra, boolean sentit) { 
            if (sentit) { 
                return ALFABET.indexOf(laLletra); 
             } else 
                return alfabetPermutat.indexOf(laLletra); 
        }

        private static char substitueixChar(int numLletra, boolean sentit) { if (sentit) { // Xifrar: agafem la lletra de l'alfabetPermutat return alfabetPermutat.get(numLletra); } else { // Desxifrar: agafem la lletra de l'ALFABET original return ALFABET.charAt(numLletra); } }

        private static String substitueixText(String missatge, boolean xifrar) { 
            StringBuilder nouMissatge = new StringBuilder(); 
            for (int i = 0; i < missatge.length(); i++) { 
            char theChar = missatge.charAt(i); 
            // 1\. Generem la nova permutació per a AQUEST caràcter 
            permutaAlfabet(); 
            boolean esMajuscula = Character.isUpperCase(theChar); 
            char charMinusc = Character.toLowerCase(theChar); 
            if (xifrar) { 
                // Busquem a l'alfabet original -> agafem de l'alfabet permutat 
                int pos = ALFABET.indexOf(charMinusc); 
                if (pos != -1) { 
                    char xifrat = alfabetPermutat.get(pos); 
                    nouMissatge.append(esMajuscula ? Character.toUpperCase(xifrat) : xifrat); 
                } else { 
                    nouMissatge.append(theChar); 
                    // Espais, comes, etc.
                     } 
                    } else { 
                        // Busquem a l'alfabet permutat -> agafem de l'alfabet original 
                        int pos = alfabetPermutat.indexOf(charMinusc); 
                        if (pos != -1) { 
                            char original = ALFABET.charAt(pos); 
                            nouMissatge.append(esMajuscula ? Character.toUpperCase(original) : original); 
                        } else { 
                            nouMissatge.append(theChar); 
                        } 
                    } 
                } 
                return nouMissatge.toString(); 
            }
    
}
