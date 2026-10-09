
//import javax.crypto;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;

    private static byte[] iv = new byte[MIDA_IV];

    private static final String MY_KEY = "HelloWorld!";

 public static void main(String[] args) {
    String[] msgs = {
        "Hello World!",
        "Lorem ipsum dicet",
        "Hola Andrés cómo está tu cuñado",
        "Àgora ïlla Ôtto",
        "Hola q ase?"
    };

    for (int i = 0; i < msgs.length; i++) {
        String msg = msgs[i];

        try {
            byte[] bXifrats = xifraAES(msg, MY_KEY);
            String desxifrat = desxifraAES(bXifrats, MY_KEY);

            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
            System.out.println("Coincideixen: " + msg.equals(desxifrat));

        } catch (Exception e) {
            System.err.println("Error de xifrat: " + e.getMessage());
        }
    }
}

    private static byte[] generaIV() {
        byte[] noutIV = new byte[MIDA_IV];
        SecureRandom random = new SecureRandom();
        random.nextBytes(noutIV);
        return noutIV;
    }

    private static SecretKeySpec generaHash(String password) throws Exception {
        byte[] bytesPassword = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = digest.digest(bytesPassword);
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);
    }

    public static byte[] xifraAES(String msg, String password) throws Exception {
        byte[] bytesMsg = msg.getBytes(StandardCharsets.UTF_8);

        byte[] noutIv = generaIV();
        IvParameterSpec IvSpec = new IvParameterSpec(noutIv);

        SecretKeySpec clau = generaHash(password);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, clau, IvSpec);
        byte[] bytesXifrats = cipher.doFinal(bytesMsg);

        byte[] resultat = new byte[noutIv.length + bytesXifrats.length];
        System.arraycopy(noutIv, 0, resultat, 0, noutIv.length);
        System.arraycopy(bytesXifrats, 0, resultat, noutIv.length, bytesXifrats.length);

        return resultat;
    }

    private static byte[] extreureIv(byte[] dades) {
        return Arrays.copyOfRange(dades, 0, MIDA_IV);
    }

    private static byte[] getBytesXifrats(byte[] dades) {
        return Arrays.copyOfRange(dades, MIDA_IV, dades.length);
    }

    public static String desxifraAES(byte[] dades, String password)
            throws Exception {

        // Separar l'IV i el missatge xifrat
        byte[] ivExtret = extreureIv(dades);
        byte[] bytesXifrats = getBytesXifrats(dades);

        // Preparar l'IV i la mateixa clau que hem utilitzat per xifrar
        IvParameterSpec ivSpec = new IvParameterSpec(ivExtret);
        SecretKeySpec clau = generaHash(password);

        // Configurar el desxifrador
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, clau, ivSpec);

        // Desxifrar i convertir els bytes en text
        byte[] bytesDesxifrats = cipher.doFinal(bytesXifrats);
        return new String(bytesDesxifrats, StandardCharsets.UTF_8);
    }

}
