package iticbcn.xifratge;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class ClauPublica {

    public KeyPair generaParellClausRSA() throws Exception {
        KeyPairGenerator generador =
                KeyPairGenerator.getInstance("RSA");

        generador.initialize(2048);

        return generador.generateKeyPair();
    }
}