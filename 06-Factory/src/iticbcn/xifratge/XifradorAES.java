package iticbcn.xifratge;

//import javax.crypto;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;

    private byte[] generaIV() {
        byte[] noutIV = new byte[MIDA_IV];
        SecureRandom random = new SecureRandom();
        random.nextBytes(noutIV);
        return noutIV;
    }

    private SecretKeySpec generaHash(String password) throws Exception {
        byte[] bytesPassword = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = digest.digest(bytesPassword);
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);
    }

    private byte[] xifraAES(String msg, String password) throws Exception {
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

    private byte[] extreureIv(byte[] dades) {
        return Arrays.copyOfRange(dades, 0, MIDA_IV);
    }

    private byte[] getBytesXifrats(byte[] dades) {
        return Arrays.copyOfRange(dades, MIDA_IV, dades.length);
    }

    private String desxifraAES(byte[] dades, String password)
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

    @Override
    public TextXifrat xifra(String msg, String clau)
            throws ClauNoSuportada {
        try {
            byte[] resultat = xifraAES(msg, clau);
            return new TextXifrat(resultat);

        } catch (Exception e) {
            System.err.println("Error de xifrat AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau)
            throws ClauNoSuportada {
        try {
            byte[] dades = xifrat.getBytes();
            return desxifraAES(dades, clau);

        } catch (Exception e) {
            System.err.println("Error de desxifrat AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

}
