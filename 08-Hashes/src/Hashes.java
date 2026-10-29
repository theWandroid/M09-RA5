import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {

    // Caràcters permesos per generar les contrasenyes candidates.
    private static final String CHARSET =
            "abcdefABCDEF1234567890!";

    // Longitud màxima de les contrasenyes que provarem.
    private static final int MAX_LONGITUD = 6;

    // Comptador de contrasenyes provades en cada cerca.
    int npass = 0;

    // Calcula el hash SHA-512 de la contrasenya amb el salt.
    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            // Preparem el generador de hashes SHA-512.
            MessageDigest resum =
                    MessageDigest.getInstance("SHA-512");

            // Incorporem primer el salt, convertit a bytes.
            resum.update(salt.getBytes(StandardCharsets.UTF_8));

            // Afegim la contrasenya i calculem el hash.
            byte[] bytes = resum.digest(
                    pw.getBytes(StandardCharsets.UTF_8));

            // Convertim els bytes a un String hexadecimal.
            HexFormat hex = HexFormat.of();
            return hex.formatHex(bytes);

        } catch (NoSuchAlgorithmException e) {
            // Informem si l'algorisme no està disponible.
            throw new IllegalStateException(
                    "SHA-512 no disponible", e);
        }
    }

    // Calcula el resultat de PBKDF2 amb contrasenya i salt.
    public String getPBKDF2AmbSalt(String pw, String salt) {

        // Configurem la contrasenya, el salt, les 65536 iteracions
        // i la mida del resultat: 128 bits.
        PBEKeySpec especificacio = new PBEKeySpec(
                pw.toCharArray(),
                salt.getBytes(StandardCharsets.UTF_8),
                65536,
                128);

        try {
            // Seleccionem la variant de PBKDF2 que utilitzarem.
            SecretKeyFactory generador =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            // Generem la clau derivada i n'obtenim els bytes.
            byte[] bytes =
                    generador.generateSecret(especificacio).getEncoded();

            // Convertim el resultat a hexadecimal.
            HexFormat hex = HexFormat.of();
            return hex.formatHex(bytes);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(
                    "No s'ha pogut calcular PBKDF2", e);

        } finally {
            // Netegem la còpia de la contrasenya guardada
            // dins de l'especificació, fins i tot si hi ha un error.
            especificacio.clearPassword();
        }
    }

    // Converteix la diferència entre dos instants en una durada llegible.
    public String getInterval(long t1, long t2) {

        // Els dos instants estan expressats en mil·lisegons.
        long temps = t2 - t1;

        // La divisió converteix les unitats.
        // El residu (%) obté la part que no forma una unitat superior.
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

    // Comprova si una candidata genera el hash que busquem.
    private boolean comprovaPassword(
            String pw, String alg, String hash, String salt) {

        // Comptem aquest intent.
        npass++;

        String hashCalculat;

        // Calculem el hash amb l'algorisme indicat.
        if (alg.equals("SHA-512")) {
            hashCalculat = getSHA512AmbSalt(pw, salt);

        } else if (alg.equals("PBKDF2")) {
            hashCalculat = getPBKDF2AmbSalt(pw, salt);

        } else {
            throw new IllegalArgumentException(
                    "Algorisme no suportat: " + alg);
        }

        // Retornem true si coincideix amb el hash objectiu.
        return hashCalculat.equals(hash);
    }

    // Prova totes les combinacions d'entre 1 i 6 caràcters
    // fins que troba una candidata amb el hash indicat.
    public String forcaBruta(String alg, String hash, String salt) {

        // Reiniciem el comptador abans de cada cerca.
        npass = 0;

        char[] caracters = CHARSET.toCharArray();

        // Reutilitzem un mateix array per construir les candidates.
        char[] password = new char[MAX_LONGITUD];

        // Cada bucle controla una posició de la contrasenya.
        for (char c1 : caracters) {
            password[0] = c1;

            // Construïm una candidata amb el primer caràcter.
            String candidata = new String(password, 0, 1);

            if (comprovaPassword(candidata, alg, hash, salt)) {
                return candidata;
            }

            for (char c2 : caracters) {
                password[1] = c2;

                // Utilitzem els dos primers caràcters de l'array.
                candidata = new String(password, 0, 2);

                if (comprovaPassword(candidata, alg, hash, salt)) {
                    return candidata;
                }

                for (char c3 : caracters) {
                    password[2] = c3;

                    // Candidata de tres caràcters.
                    candidata = new String(password, 0, 3);

                    if (comprovaPassword(candidata, alg, hash, salt)) {
                        return candidata;
                    }

                    for (char c4 : caracters) {
                        password[3] = c4;

                        // Candidata de quatre caràcters.
                        candidata = new String(password, 0, 4);

                        if (comprovaPassword(candidata, alg, hash, salt)) {
                            return candidata;
                        }

                        for (char c5 : caracters) {
                            password[4] = c5;

                            // Candidata de cinc caràcters.
                            candidata = new String(password, 0, 5);

                            if (comprovaPassword(
                                    candidata, alg, hash, salt)) {
                                return candidata;
                            }

                            for (char c6 : caracters) {
                                password[5] = c6;

                                // Candidata de sis caràcters.
                                candidata = new String(password, 0, 6);

                                if (comprovaPassword(
                                        candidata, alg, hash, salt)) {

                                    // El return surt de tot el mètode,
                                    // encara que siguem dins dels bucles.
                                    return candidata;
                                }
                            }
                        }
                    }
                }
            }
        }

        // No hem trobat cap coincidència entre les candidates.
        return null;
    }

    // Executa la prova amb SHA-512 i PBKDF2.
    public static void main(String[] args) throws Exception {

        // Dades de prova: el salt s'utilitza tant per generar
        // el hash inicial com per comprovar cada candidata.
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";

        // Creem l'objecte que conté els mètodes i el comptador.
        Hashes h = new Hashes();

        // Generem els dos hashes que després intentarem trobar.
        String[] aHashes = {
            h.getSHA512AmbSalt(pw, salt),
            h.getPBKDF2AmbSalt(pw, salt)
        };

        // L'ordre dels noms coincideix amb el dels hashes.
        String[] algorismes = { "SHA-512", "PBKDF2" };

        for (int i = 0; i < aHashes.length; i++) {
            System.out.println("==========================");
            System.out.printf("Algorisme: %s%n", algorismes[i]);
            System.out.printf("Hash: %s%n", aHashes[i]);
            System.out.println("--------------------------");
            System.out.println("-- Inici de força bruta --");

            // Guardem el moment d'inici.
            long t1 = System.currentTimeMillis();

            // La cerca rep el hash i el salt.
            // No rep la contrasenya original.
            String pwTrobat =
                    h.forcaBruta(algorismes[i], aHashes[i], salt);

            // Guardem el moment en què acaba la cerca.
            long t2 = System.currentTimeMillis();

            // Mostrem la candidata trobada, els intents i el temps.
            System.out.printf("Pass: %s%n", pwTrobat);
            System.out.printf("Provats: %d%n", h.npass);
            System.out.printf("Temps: %s%n", h.getInterval(t1, t2));
            System.out.println("--------------------------\n");
        }
    }
}