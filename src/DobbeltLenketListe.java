import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DobbeltLenketListe<T> implements Liste<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks) {
        if(antall == 0)
            throw new IndexOutOfBoundsException("Kan ikke finne node i en tom-liste");

        Node<T> current;
        if(indeks<antall/2) {
            current = hode;
            for (int i = 0; i < indeks; i++) {
                current = current.neste;
            }
        } else {
            current = hale;
            for (int i = 1; i < antall-indeks; i++) {
                current = current.forrige;
            }
        }
        return current;
    }

    // konstruktør
    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a) {
        //Kaster avvik om tabellen er null.
        Objects.requireNonNull(a, "Kan ikke lage liste av null-verdi!");
        antall = 0;     //Instansierer antall.
        endringer = 0;  //Instansierer endringer.

        int i = 0;
        while(i<a.length && a[i] == null) {     //Finner første indeks i a[] som ikke er null. Dersom den ikke finner noe, vil i være større enn a.length og resten av koden vil bli hoppet over.
            i++;                                //Øker i.
        }
        if(i<a.length){     //Dersom i er mindre enn a.length, har while løkken over funnet en verdi som ikke er null.
            //Det er funnet et ellement som ikke er null.
            hode = hale = new Node<>(a[i]);     //Setter hode og hale lik den første ikke-null verdien som er funnet.
            i++;                                //Øker i.
            antall++;                           //Øker antall.
        }
        while(i<a.length){      //Fortsetter på løkken over ved å ta i bruk samme i verdi som dn brukt i løkken over.
            if(a[i]!=null){     //Sjekker at a[i] ikke er null.
                hale.neste = new Node<>(a[i], hale, null);      //Legger til nytt objekt av hale og tilegner pekere.
                hale = hale.neste;                                    //Angir siste ogjekt hale pekeren.
                antall++;                                             //Øker antall.
            }
            i++;                                                      //Øker i.
        }
    }

    // subliste
    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(fra, til);                                   //Sjekker intervall med fratilkontroll
        DobbeltLenketListe<T> subList = new DobbeltLenketListe<>(); //Instansierer en ny DobbeltLenketListe
        Node<T> current = finnNode(fra);                            //Finner node med index = fra;

        //Looper igjennom og legger elementer til subliste med leggInn og hent() metodene.
        for(int i = fra; i < til; i++) {
            subList.leggInn(current.verdi);
            current = current.neste;                                //Oppdaterer peker til neste node
        }
        subList.endringer = 0;                                      //Setter endringer til 0;
        return subList;                                             //Returnerer subliste.
    }

    @Override
    public int antall() {
        return antall;          //Returnerer antall objekter i listen.
    }

    @Override
    public boolean tom() {
        return hode == null;    //Returnerer true dersom array er tomt, retunerer ellers false.
    }

    @Override
    public boolean leggInn(T verdi) {
        //Sjekker om verdi er null;
        Objects.requireNonNull(verdi, "Kan ikke legge til en null-verdi!");

        //Dersom listen er tom:
        if(antall == 0) {
            hode = hale = new Node<>(verdi); //Setter hode og hale til den nye noden med verdi
        }
        else { //Dersom listen ikke er tom
            //Sett den nye noden bak halen og forrige-peker mot hale
            hale.neste = new Node<>(verdi, hale, null);

            //Oppdaterer halen til å være den nye noden som nettopp har blitt lagt inn
            hale = hale.neste;
        }
        antall++; //Oppdaterer antall
        endringer++; //Oppdater endringer
        return true; //returner true innleggelse suksessful!
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        //Kast avvik dersom indeks < 0 eller om indeks > antall
        indeksKontroll(indeks, true);
        Objects.requireNonNull(verdi, "Verdi kan ikke være null");  //Kast avvik dersom verdi = null

        //Dersom listen er tom så settes hode og hale til den nye node(verdi)
        if(antall == 0)
            hode = hale = new Node<>(verdi);
        else if(indeks == 0) { //Dersom indeks == 0 så skal noden legges først
            //Sett hode-forrige-peker til den nye noden(verdi, null, hode)
            hode.forrige = new Node<>(verdi, null, hode);
            hode = hode.forrige; //Oppdaterer hode til forrige node.
        }
        else if(indeks == antall) { //Dersom indeks == antall skal noden legges på slutten
            //Sett hale-neste-peker til den nye noden(verdi, hale, null)
            hale.neste = new Node<>(verdi, hale, null);
            hale = hale.neste; //Oppdater hale til neste node
        }
        else { //Ellers sett inn noden mellom to noder og oppdater pekere.
            Node<T> p = finnNode(indeks-1); //Noden til venstre for den nye noden.
            Node<T> q = p.neste; //Noden til høyre for den nye noden
            p.neste = new Node<>(verdi, p, q); //Setter inn node: p <-> ny Node -> q
            q.forrige = p.neste; //Oppdaterer q node peker til p <-> ny node <-> q
        }

        antall++;       //Oppdaterer antall
        endringer++;    //Oppdaterer endringer
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);  //Kontrollerer indeks.
        return finnNode(indeks).verdi;          //Returnerer verdi for gitt indeks.
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks, false);

        T temp = finnNode(indeks).verdi;    //Lagrer nåværende verdi for retur.
        finnNode(indeks).verdi = nyverdi;   //Oppdaterer ny verdi.
        endringer++;                        //Oppdaterer endringer.
        return temp;                        //Returnerer tidligere verdi.
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");                 //Starter med en åpen parantes
        if(antall == 0) return str.append("]").toString();          //Hvis listen er tom returner med lukket parantes

        Node<T> current = hode;                                     //Lager en node peker, starter på hode
        while(current != hale) {                                    //Så lenge pekeren ikke peker på hale
            str.append(current.verdi).append(", ");                 //Legg til verdien til node i strengen
            current = current.neste;                                //Oppdaterer node til neste peker
        }

        return str.append(current.verdi).append("]").toString();    //Returnerer streng med hale-verdi.
    }

    public String omvendtString() {
        StringBuilder str = new StringBuilder("[");                 //Starter med åpen parantes
        if(antall == 0) return str.append("]").toString();          //Returner med lukket parantes dersom listen er tom

        Node<T> current = hale;                                     //Initierer peker på hale
        while(current != hode) {                                    //Så lenge pekeren ikke peker på hodet
            str.append(current.verdi).append(", ");                 //Legg til verdien til node i strengen
            current = current.forrige;                              //Oppdaterer peker til forrige node
        }

        return str.append(current.verdi).append("]").toString();    //Returnerer streng med hode-verdi
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

    private void fratilKontroll(int fra, int til) {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

} // DobbeltLenketListe
