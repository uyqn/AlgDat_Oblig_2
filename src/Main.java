public class Main {
    public static void main(String[] args) {
        String[] s1 = {}, s2 = {"A"}, s3 = {"null", "A", null, "B", null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);

        System.out.println(l2);
    }
}
