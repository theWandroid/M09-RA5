package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class ClauPublica {

    public KeyPair generaParellClausRSA() throws Exception {
        KeyPairGenerator generador
                = KeyPairGenerator.getInstance("RSA");

        generador.initialize(2048);

        return generador.generateKeyPair();
    }

    public byte[] xifraRSA(String msg, PublicKey clauPublica)
            throws Exception {

        Cipher xifrador = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        xifrador.init(Cipher.ENCRYPT_MODE, clauPublica);

        byte[] dades = msg.getBytes(StandardCharsets.UTF_8);

        return xifrador.doFinal(dades);
    }

    public String desxifraRSA(byte[] msgXifrat, PrivateKey clauPrivada)
            throws Exception {

        Cipher desxifrador = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        desxifrador.init(Cipher.DECRYPT_MODE, clauPrivada);

        byte[] dades = desxifrador.doFinal(msgXifrat);

        return new String(dades, StandardCharsets.UTF_8);
    }
}
