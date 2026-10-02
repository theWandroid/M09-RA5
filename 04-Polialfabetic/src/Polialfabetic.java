
public class Polialfabetic {

    
   
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
            initRandom(clauSecreta); String msg = desxifraPoliAlfa(msgsXifrats[i]); 
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg); 
        } 
        }

        public static void permutaAlfabet(){

        }

        public static String xifraPoliAlfa(){
            String newMessage = "";
            return newMessage;

        }

        public static String desxifraPoliAlfa(){
            String newMessage = "";
            return newMessage;

        }
    
}
