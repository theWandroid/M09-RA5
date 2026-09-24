public class RotX {

    //para poner texto en negrita
    public static final String BOLD = "\u001B[1m"; 
    //para resertear texto a normal
    public static final String RESET = "\u001B[0m";
    
    private static String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    private static char[] minuscules = alfabet.toCharArray();
    private static char[] majuscles =  alfabet.toUpperCase().toCharArray();
    private static String [] provesXifrat = {"ABC", "XYZ", "Hola Mr. Calçot", "Perdó per tú què és"};
    private static String [] provesDesXifrat = {"IÏJ", "FGH", "Òwúi Ùá. Jiúkwb", "Zmálx zmá bç acñ nà"};

    private static int [] numRotacions = {0, 2, 4, 6};

    static void main(String[] args) {
       
        System.out.println();
        System.out.println("Xifrat");
        System.out.println("---------");

        for (int i = 0; i < provesXifrat.length; i++) {
            System.out.printf("(%d) - %-25s  -->",numRotacions[i],provesXifrat[i] );
            xifratRotX(provesXifrat[i], numRotacions[i]);
            System.out.println();
        }

        System.out.println();
        System.out.println("Desxifrat");
        System.out.println("---------");

         for (int i = 0; i < provesDesXifrat.length; i++) {
            System.out.printf("(%d) - %-25s  -->",numRotacions[i],provesXifrat[i] );
            desXifratRotX(provesXifrat[i], numRotacions[i]);
            System.out.println();
        }
        
        System.out.println();

        String missatgeXifrat = "Úiüht, úiü wx ùxì ív?";
        System.out.println("Misstge xifrat:"+missatgeXifrat);
        forcaBrutaRotX(missatgeXifrat);

    }

    public static String transformaText(boolean sentit, String text, int rotacio) {

        //implementar StringBuffer en comptes de String per optimitzar el codi, ja que StringBuffer és mutable i no crea un nou objecte cada vegada que s'afegeix una lletra.
        StringBuffer newText = new StringBuffer();

        if (text == null || text.isEmpty()) {
            System.out.println("El text no pot ser buit");
        } else {
            for (int i = 0; i < text.length(); i++) {
                char lletra = text.charAt(i);
                if (Character.isLetter(lletra)) {
                    if (Character.isLowerCase(lletra)) {
                        newText.append(substitueix(buscaLletra(lletra, minuscules), minuscules, sentit, rotacio ));
                    } else {
                        newText.append(substitueix(buscaLletra(lletra, majuscles), majuscles, sentit, rotacio));
                    }

                } else {
                    newText.append(lletra);
                }
            }
        }
        return newText.toString();

    }

     public static void xifratRotX(String text, int rotacio) {
        System.out.print(transformaText(true, text, rotacio));
    }

    public static void desXifratRotX(String text, int rotacio) {
        System.out.print(transformaText(false, text, rotacio));
    }

    public static void forcaBrutaRotX(String msg){
            boolean found = false;
        for (int i = 0; i < alfabet.length(); i++) {
            int counter = 0;
            while(!found && counter < provesXifrat.length){
                if( transformaText(false, msg, i).equals(provesXifrat[counter]))
                    found = true;
               else
                counter++;
            }
                if(found){
            System.out.printf(BOLD+"(%d) ->",i);
            desXifratRotX(msg, i);
            System.out.println(" "+RESET);
            found = false;
                }else{
                    System.out.printf("(%d) ->",i);
                    desXifratRotX(msg, i);
                    System.out.println();
                }

        }
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

    public static char substitueix(int inici, char[] arrayLletres, boolean sentit, int rotacio) {
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
