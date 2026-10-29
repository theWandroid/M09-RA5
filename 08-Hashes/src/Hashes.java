
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
//Caracters permesos per generar contrasenyes

    private static final String CHARSET
            = "abcdefABCDEF1234567890!";

    private static final int MAX_LONGITUD = 6;
//númeo contrasenyes provades
    int npass = 0;

    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            MessageDigest resum = MessageDigest.getInstance("SHA-512");

            resum.update(salt.getBytes(StandardCharsets.UTF_8));

            byte[] bytes = resum.digest(
                    pw.getBytes(StandardCharsets.UTF_8));

            HexFormat hex = HexFormat.of();

            return hex.formatHex(bytes);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 no disponible", e);
        }
    }

    public String getPBKDF2AmbSalt(String pw, String salt) {
        PBEKeySpec especificacio = new PBEKeySpec(
                pw.toCharArray(),
                salt.getBytes(StandardCharsets.UTF_8),
                65536,
                128);

        try {
            SecretKeyFactory generador
                    = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] bytes
                    = generador.generateSecret(especificacio).getEncoded();

            HexFormat hex = HexFormat.of();

            return hex.formatHex(bytes);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(
                    "No s'ha pogut calcular PBKDF2", e);
        } finally {
            especificacio.clearPassword();
        }
    }

    public String getInterval(long t1, long t2) {
        long temps = t2 - t1;

        long millis = temps % 1000;
        long segons = (temps / 1000) % 60;
        long minuts = (temps / (1000 * 60)) % 60;
        long hores = (temps / (1000 * 60 * 60)) % 24;
        long dies = temps / (1000L * 60 * 60 * 24);

        return dies + " dies / "
                + hores + " hores / "
                + minuts + " minuts / "
                + segons + " segons / "
                + millis + " millis";
    }

    private boolean comprovaPassword(
            String pw, String alg, String hash, String salt) {

        npass++;

        String hashCalculat;

        if (alg.equals("SHA-512")) {
            hashCalculat = getSHA512AmbSalt(pw, salt);
        } else if (alg.equals("PBKDF2")) {
            hashCalculat = getPBKDF2AmbSalt(pw, salt);
        } else {
            throw new IllegalArgumentException(
                    "Algorisme no suportat: " + alg);
        }

        return hashCalculat.equals(hash);
    }

    public String forcaBruta(String alg, String hash, String salt) {
        npass = 0;

        char[] caracters = CHARSET.toCharArray();
        char[] password = new char[MAX_LONGITUD];

        for (char c1 : caracters) {
            password[0] = c1;
            String candidata = new String(password, 0, 1);

            if (comprovaPassword(candidata, alg, hash, salt)) {
                return candidata;
            }

            for (char c2 : caracters) {
                password[1] = c2;
                candidata = new String(password, 0, 2);

                if (comprovaPassword(candidata, alg, hash, salt)) {
                    return candidata;
                }

                for (char c3 : caracters) {
                    password[2] = c3;
                    candidata = new String(password, 0, 3);

                    if (comprovaPassword(candidata, alg, hash, salt)) {
                        return candidata;
                    }

                    for (char c4 : caracters) {
                        password[3] = c4;
                        candidata = new String(password, 0, 4);

                        if (comprovaPassword(candidata, alg, hash, salt)) {
                            return candidata;
                        }

                        for (char c5 : caracters) {
                            password[4] = c5;
                            candidata = new String(password, 0, 5);

                            if (comprovaPassword(candidata, alg, hash, salt)) {
                                return candidata;
                            }

                            for (char c6 : caracters) {
                                password[5] = c6;
                                candidata = new String(password, 0, 6);

                                if (comprovaPassword(
                                        candidata, alg, hash, salt)) {
                                    return candidata;
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        String salt = "qpowei ruañslkdfjz";
        String pw = "aaabF!";

        Hashes h = new Hashes();

        String[] aHashes = {
            h.getSHA512AmbSalt(pw, salt),
            h.getPBKDF2AmbSalt(pw, salt)
        };

        String[] algorismes = {"SHA-512", "PBKDF2"};

        for (int i = 0; i < aHashes.length; i++) {
            System.out.println("==========================");
            System.out.printf("Algorisme: %s%n", algorismes[i]);
            System.out.printf("Hash: %s%n", aHashes[i]);
            System.out.println("--------------------------");
            System.out.println("-- Inici de força bruta --");

            long t1 = System.currentTimeMillis();

            String pwTrobat
                    = h.forcaBruta(algorismes[i], aHashes[i], salt);

            long t2 = System.currentTimeMillis();

            System.out.printf("Pass: %s%n", pwTrobat);
            System.out.printf("Provats: %d%n", h.npass);
            System.out.printf("Temps: %s%n", h.getInterval(t1, t2));
            System.out.println("--------------------------\n");
        }
    }
}
