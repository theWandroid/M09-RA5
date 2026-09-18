
public class Rot13 {

    private final static int rotacio = 13;

    private static char[] minuscules = {'a', 'á', 'à', 'b', 'c', 'ç', 'd', 'e', 'é', 'è', 'f', 'g', 'h', 'i', 'í', 'ì', 'ï', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'ó', 'ò', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ù', 'ü', 'v', 'w', 'x', 'y', 'z'};
    private static char[] majuscles = {'A', 'Á', 'À', 'B', 'C', 'Ç', 'D', 'E', 'É', 'È', 'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'};

    static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println(buscaLletra('A', minuscules));
        //System.out.println(minuscules.length);
        System.out.println(substitueix(1, minuscules, false));


    }

    public void xifratRot13(String text){
        String newText = "";

        if(text == null || text.isEmpty()){
            System.out.println("El text no pot ser buit");
            return;
        }else{
            for(int i = 0; i < text.length(); i++){
                char lletra = text.charAt(i);
                if(Character.isLetter(lletra)){
                    if(Character.isLowerCase(lletra)){
                        if(buscaLletra(lletra, minuscules))
                           newText += substitueix(i, minuscules, true);
                    }else{
                        if(buscaLletra(lletra, majuscles))
                           newText += substitueix(i, majuscles, true);
                    }

                }
            }
    }
}

    public void desxifratRot13(String text){

    }

    public static boolean buscaLletra(char laLletra, char [] arrayLletres){
        boolean found = false;
        int counter = 0;

        while(!found && counter < arrayLletres.length){
            if(laLletra == arrayLletres[counter])
                found = true;
            else
                counter ++;
        }
        return found; 
    }

    public static char substitueix(int inici, char [] arrayLletres, boolean sentit){
        char novaLletra = ' ';
        int fi = 0;
        if(inici < arrayLletres.length){

            if(sentit){
                fi = inici + rotacio;
                if(fi > arrayLletres.length)
                    fi = fi /arrayLletres.length;
            }else{
                fi = inici - rotacio;
                if(fi < 0)
                    fi = arrayLletres.length + fi;
            }
            novaLletra = arrayLletres[fi];

        }
        return novaLletra;

    }

}
