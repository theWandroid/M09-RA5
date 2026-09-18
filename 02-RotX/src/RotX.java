public class RotX {
    
    private static String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    private static char[] minuscules = alfabet.toCharArray();
    private static char[] majuscles =  alfabet.toUpperCase().toCharArray();
    
    static void main(String[] args) {
        //System.out.println("Hello World!");
        //System.out.println(buscaLletra('A', minuscules));
        //System.out.println(minuscules.length);
        //System.out.println(substitueix(1, minuscules, false));
        System.out.println();
        System.out.println("Xifrat");
        System.out.println("---------");
        System.out.print("ABC                   -->     ");
        xifratRot13("ABC");
        System.out.println();
        System.out.print("XYZ                   -->     ");
        xifratRot13("XYZ");
        System.out.println();
        System.out.print("Hola Mr. Calçot       -->     ");
        xifratRot13("Hola Mr. Calçot");
        System.out.println();
        System.out.print("Perdó per tú què és   -->     ");
        xifratRot13("Perdó per tú què és");
        System.out.println();
        System.out.println();
        System.out.println("Desxifrat");
        System.out.println("---------");
         System.out.print("IÏJ                   -->     ");
        desxifratRot13("IÏJ");
        System.out.println();
        System.out.print("FGH                   -->     ");
        desxifratRot13("FGH");
        System.out.println();
        System.out.print("Òwúi Ùá. Jiúkwb       -->     ");
        desxifratRot13("Òwúi Ùá. Jiúkwb");
        System.out.println();
        System.out.print("Zmálx zmá bç acñ nà   -->     ");
        desxifratRot13("Zmálx zmá bç acñ nà");
        System.out.println();
        System.out.println();




    }

    public static void transformaText(boolean sentit, String text) {

        //implementar StringBuffer en comptes de String per optimitzar el codi, ja que StringBuffer és mutable i no crea un nou objecte cada vegada que s'afegeix una lletra.
        StringBuffer newText = new StringBuffer();

        if (text == null || text.isEmpty()) {
            System.out.println("El text no pot ser buit");
            return;
        } else {
            for (int i = 0; i < text.length(); i++) {
                char lletra = text.charAt(i);
                if (Character.isLetter(lletra)) {
                    if (Character.isLowerCase(lletra)) {
                        newText.append(substitueix(buscaLletra(lletra, minuscules), minuscules, sentit));
                    } else {
                        newText.append(substitueix(buscaLletra(lletra, majuscles), majuscles, sentit));
                    }

                } else {
                    newText.append(lletra);
                }
            }
        }
        System.out.print(newText);

    }

     public static void xifratRot13(String text) {
        transformaText(true, text);
    }

    public static void desxifratRot13(String text) {
        transformaText(false, text);
    }

    public static int buscaLletra(char laLletra, char[] arrayLletres) {
        boolean found = false;
        int counter = 0;

        while (!found && counter < arrayLletres.length) {
            if (laLletra == arrayLletres[counter]) {
                found = true; 
            }else {
                counter++;
            }
        }
        return counter;
    }

    public static char substitueix(int inici, char[] arrayLletres, boolean sentit) {
        char novaLletra = ' ';
        int fi = 0;
        if (inici < arrayLletres.length) {

            if (sentit) {
                fi = inici + rotacio;
                if (fi >= arrayLletres.length) {
                    fi = fi % arrayLletres.length;
                }
            } else {
                fi = inici - rotacio;
                if (fi < 0) {
                    fi = arrayLletres.length + fi;
                }
            }
            novaLletra = arrayLletres[fi];

        }
        return novaLletra;

    }


    
}
