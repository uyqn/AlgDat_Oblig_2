import java.util.Iterator;

public interface Liste<T> extends Beholder<T> {
    boolean leggInn(T verdi);           // Nytt element bakerst
    void leggInn(int indeks, T verdi);  // Nytt element på plass indeks
    boolean inneholder(T verdi);        // Er verdi i listen?
    T hent(int indeks);                 // Hent element på plass indeks
    int indeksTil(T verdi);             // Hvor ligger verdi?
    T oppdater(int indeks, T verdi);    // Oppdater på plass indeks
    boolean fjern(T verdi);             // Fjern objektet verdi
    T fjern(int indeks);                // Fjern elementet på plass indeks
    int antall();                       // Antallet i listen
    boolean tom();                      // Er listen tom?
    void nullstill();                   // Listen nullstilles (og tømmes)
    Iterator<T> iterator();             // En iterator

    default String melding(int indeks)  // Unntaksmelding
    {
        return "Indeks: " + indeks + ", Antall: " + antall();
    }

    default void indeksKontroll(int indeks, boolean leggInn)
    {
        if (indeks < 0 || (leggInn ? indeks > antall() : indeks >= antall()))
            throw new IndexOutOfBoundsException(melding(indeks));
    }
}  // Liste
