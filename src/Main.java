public class Main {
    public static void main(String[] args) {

        String[] s = {null, "Ole", "Jens", null, "Per", "Kari", null};

        Liste<String> liste = new DobbeltLenketListe<>(s);

        System.out.println(liste.antall() + " " + liste.tom());

        System.out.println(liste.hent(2));
    }
}
