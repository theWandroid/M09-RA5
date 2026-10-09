
//import javax.crypto;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AES {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;

    private static byte[] iv = new byte[MIDA_IV];

    private static final String MY_KEY = "HelloWorld!";

    
    
    public static void main(String[] args) {
        System.out.println("Let's start!");
        iv = generaIV();
        System.out.println("Identicador IV: "+ iv.toString());
        System.out.println("Contingut IV: "+ Arrays.toString(iv));
        System.out.println("Mida"+iv.length);

        try{
            SecretKey clau = generaHash(MY_KEY);
            
        System.out.println("Clau"+Arrays.toString(clau.getEncoded()));
        System.out.println("Mida clau "+clau.getEncoded().length);


        }catch(Exception e){
            System.out.println("Error generant la clau"+e.getMessage());

        }
    }

       private static byte[] generaIV(){
        byte [] noutIV = new byte[MIDA_IV];
        SecureRandom random = new SecureRandom();
        random.nextBytes(noutIV);
        return noutIV;
    }

    private static SecretKeySpec generaHash(String password) throws Exception{
        byte[] bytesPassword = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = digest.digest(bytesPassword);
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);
    }
/*
    

    public static byte[] xifraAES(String msg, String password){

    }

    public static String desxifraAES(){

    }
    
    */

 

}
