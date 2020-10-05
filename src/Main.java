public class Main {
    public static void main(String[] args) {
        Character[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',};

        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        /*
        System.out.println(liste.subliste(3,8));
        System.out.println(liste.subliste(5,5));
        System.out.println(liste.subliste(8, liste.antall()));
        System.out.println(liste.subliste(0, 11));
*/

        System.out.println(liste.indeksTil('I'));
        System.out.println(liste.inneholder('A'));


        /*
        // Oppgave 8d
        String[] navn = {"Lars","Anders","Bodil","Kari","Per","Berit"};
        Liste<String> liste = new DobbeltLenketListe<>(navn);

        liste.forEach(s -> System.out.print(s + " "));

        System.out.println();
        for (String s : liste) System.out.print(s + " "); // Utskrift: "Lars Anders Bodil Kari Per Berit" og "Lars Anders Bodil Kari Per Berit"
         */

        /*
        // Oppgave 9
        DobbeltLenketListe<String> liste =
            new DobbeltLenketListe<>(new String[]
            {"Birger","Lars","Anders","Bodil","Kari","Per","Berit"});

        liste.fjernHvis(navn -> navn.charAt(0) == 'B'); // fjerner navn som starter med B

        System.out.println(liste + " " + liste.omvendtString());
        // Utskrift: [Lars, Anders, Kari, Per] [Per, Kari, Anders, Lars]
         */

    }
}
