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
        indeksKontroll(indeks, false);

        if(antall == 0) //Sjekker om listen er tom.
            throw new IndexOutOfBoundsException("Kan ikke finne node i en tom-liste");

        Node<T> current; //Initierer current node.
        if(indeks<antall/2) {   //Sjekker om det er mest effektivt å bruke hode eller hale til å lete.
            current = hode;     //Setter current lik hode.
            for (int i = 0; i < indeks; i++) {
                current = current.neste;        //Løper gjennom listen frem til i når indeks etterspurt.
            }
        } else {
            current = hale;     //Setter current lik hale.
            for (int i = 1; i < antall-indeks; i++) {
                current = current.forrige;        //Løper gjennom listen frem til i når indeks etterspurt.
            }
        }
        return current;         //Returnerer current
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
        antall = 0;
        endringer = 0;

        int i = 0;
        while(i<a.length && a[i] == null) i++;
        if(i<a.length){
            //Det er funnet et ellement som ikke er null.
            hode = hale = new Node<>(a[i]);
            i++;
            antall++;
        }
        for(; i<a.length; i++)
            if(a[i]!=null){
                hale.neste = new Node<>(a[i], hale, null);
                hale = hale.neste;
                antall++;
            }
    }

    // subliste
    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(fra, til);                                   //Sjekker intervall med fratilkontroll
        DobbeltLenketListe<T> subList = new DobbeltLenketListe<>(); //Instansierer en ny DobbeltLenketListe

        if (antall > 0) {                                           //Dersom listen ikke er tom
            Node<T> current = finnNode(fra);                        //Finner node med index = fra;

            //Looper igjennom og legger elementer til subliste med leggInn og hent() metodene.
            for (int i = fra; i < til; i++) {
                subList.leggInn(current.verdi);
                current = current.neste;                            //Oppdaterer peker til neste node
            }
            subList.endringer = 0;                                  //Setter endringer til 0;
        }

        return subList;                                             //Returner subliste
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
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

        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        //Sjekker om verdi er null;
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {

        for(int i = 0; i<antall(); i++){    //Løper gjennom liste for å sammenlikne verdier.
            if(verdi.equals(hent(i))){      //Sammenlikner verdi med verdi hentet fra hent metoden.
                return i;                   //returnerer indeks til verdi dersom den er lik den oppgitte verdi.
            }
        }
        return -1;                          //Ellers returneres -1.
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks, false);
        Objects.requireNonNull(nyverdi);

        T temp = finnNode(indeks).verdi;
        finnNode(indeks).verdi = nyverdi;
        endringer++;
        return temp;
    }

    @Override
    public boolean fjern(T verdi) {
        //Sjekker om verdi er null;
        if(verdi == null) {
            return false;
        }

        Node<T> temp = hode; // Setter den midlertidige lik hode

        // Hvis verdi ikke er i listen, skal metoden returnere false
        while (temp != null) {
            if (temp.verdi.equals(verdi)) {
                break;
            }
            temp = temp.neste;
        }
        if (temp == null) {
            return false;
        }

        // Hvis den første verdien skal fjernes
        if (temp == hode) {
            hode = hode.neste;

            if (hode != null) {
                hode.forrige = null;
            } else {
                hale = null;
            }
        }

        // Hvis den siste verdien skal fjernes
        else if (temp == hale) {
            hale = hale.forrige;
            hale.neste = null;
        }

        // Hvis en verdi mellom to andre skal fjernes
        else {
            temp.forrige.neste = temp.neste;
            temp.neste.forrige = temp.forrige;
        }

        temp.verdi = null;
        temp.forrige = temp.neste = null;

        antall--; // Oppdaterer antall
        endringer++; // Oppdaterer endringer
        return true; // Returnerer true;
    }

    @Override
    public T fjern(int indeks) {

        indeksKontroll(indeks, false);

        Node<T> temp = hode; // Setter den midlertidige lik hode
        T verdi;

        // Hvis indeks er lik 0 så skal den første noden fjernes
        if(indeks == 0) {
            verdi = temp.verdi;

            // Hvis listen ikke er tom
            if (temp.neste != null) {
                hode = temp.neste; // Sett hode sin forrige peker en til høyre
                hode.forrige = null; // Oppdaterer hode til forrige node
            }
            // Hvis listen er tom
            else {
                hode = null; // Oppdaterer hode til null
                hale = null; // Oppdaterer hale til null
            }

        }
        // Hvis indeks er lik antall skal den siste node fjernes
        else if(indeks == antall - 1) {
            temp = hale;
            verdi = hale.verdi;
            hale = temp.forrige; // Sett hode sin forrige peker en til venstre
            hale.neste = null; //Oppdater hale til null
        }
        // Fjerner noden mellom to noder og oppdater pekere
        else {
            for (int i = 0; i < indeks; i++) {
                temp = temp.neste;
            }
            verdi = temp.verdi;

            temp.forrige.neste = temp.neste; //Noden til venstre peker nå på noden til høyre
            temp.neste.forrige = temp.forrige; //Noden til høyre peker nå på noden til venstre
        }
        antall--; // Oppdaterer antall
        endringer++; // Oppdaterer endringer
        return verdi; // Returner posisjon indeks
    }

    @Override
    public void nullstill() {
        // Tømmer listen og nuller alt
        for (Node<T> temp = hode; temp != null; temp = null) {
            temp.verdi = null;
            temp.forrige = temp.neste = null;
        }

        hode = hale = null;

        antall = 0;
        endringer++;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");         //Starter med en åpen parantes
        if(antall == 0) return str.append("]").toString();  //Hvis listen er tom returner med lukket parantes

        Node<T> i = hode;                                   //Lager en node peker, starter på hode

        for(;i != hale ; i = i.neste)                       //Looper igjennom alle nodene utenom hale
            str.append(i.verdi).append(", ");               //Legger til verdien til hver node

        return str.append(i.verdi).append("]").toString();  //Returnerer streng med hale-verdi.
    }

    public String omvendtString() {
        StringBuilder str = new StringBuilder("[");         //Starter med åpen parantes
        if(antall == 0) return str.append("]").toString();  //Returner med lukket parantes dersom listen er tom

        Node<T> i = hale;                                   //Initierer peker på hale

        for(; i != hode ; i = i.forrige)                    //Looper noder igjennom baklengs
            str.append(i.verdi).append(", ");               //Legger til verdien til hver node

        return str.append(i.verdi).append("]").toString();  //Returnerer streng med hode-verdi
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        T temp;
        T maks;
        int indeks;
        //Løkke som itererer listen
        for(int i = 0; i < liste.antall(); i++){
            maks = liste.hent(0);   //Instansierer maks.
            indeks = 0;                   //Instansierer indeks.
            //Løkke som itererer for å finne maks
            for(int j = 1; j < liste.antall()-i; j++){  //Reduser list length med en for å ungå å ta med siste tall i ny sammenlikning.(Antall()-i).
                if(c.compare(liste.hent(j), maks)>0){   //Sjekker om ny verdi er større enn maks.
                    maks = liste.hent(j);               //Endrer maks.
                    indeks = j;                         //Oppdaterer indeks.
                }
            }
            temp = liste.hent(liste.antall()-1-i);      //Setter siste verdi (indeks-i) i arrayet til temp.
            liste.oppdater(liste.antall()-1-i, maks);   //Oppdaterer siste verdi (indeks-i) i arrayet.
            liste.oppdater(indeks, temp);                     //Oppdaterer verdi på indeks til det som tidligere var siste verdi (indeks-i)
        }
    }

    @Override
    public Iterator<T> iterator() {
        // Returnere en instans av iteratorklassen
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        // Indeksen er lovlig
        indeksKontroll(indeks, false);

        // Returnere en instans av iteratorklassen ovenfor
        return new DobbeltLenketListeIterator(indeks);
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
            // Indeksen er lovlig
            indeksKontroll(indeks, false);

            // Sett pekeren "denne" til noden som hører til indeks
            denne = finnNode(indeks);
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            // Sjekk om iteratorendringer er lik endringer
            if (endringer != iteratorendringer) {
                throw new ConcurrentModificationException("Iteratorendringer er ikke lik endringer");
            }

            // Det er ikke flere igjen i listen
            else if (!hasNext()) {
                throw new NoSuchElementException("Det ikke er flere verdier igjen i listen");
            }

            fjernOK = true; // fjernOK settes til true
            T temp = denne.verdi; // Setter den midlertidige lik denne sin verdi
            denne = denne.neste; // Denne flyttes til den neste node

            return temp; // Verdien til temo returneres
        }

        @Override
        public void remove() {
            // Hvis det ikke er tillatt å kalle denne metoden
            if (!fjernOK) {
                throw new IllegalStateException("Det ikke er tillatt å kalle denne metoden");
            }

            // Hvis endringer og iteratorendringer er forskjellige,
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException("Endringer og iteratorendringer er forskjellige");
            }

            // Om de to ovenfor passeres settes fjernOK til usann/false
            fjernOK = false;

            // Den som skal fjernes er eneste verdi (antall == 1) --> Hode og hale må nulles
            if (antall == 1) {
                hode = hale = null;
            }

            // Den siste skal fjernes (denne == null) --> Hale må oppdateres
            else if (denne == null) {
                hale = hale.forrige;
                hale.neste = null;
            }

            // Den første skal fjernes (denne.forrige == hode) --> Hode må oppdateres
            else if (denne.forrige == hode) {
                hode = hode.neste;
                hode.forrige = null;
            }

            // En node unne i listen skal fjernes (denne.forrige == hode) --> Pekerene må oppdateres
            else {
                denne.forrige = denne.forrige.forrige;
                denne.forrige.neste = denne;
            }


            antall--; // Antall Reduseres
            endringer++; // Øker endringer
            iteratorendringer++; // Øker iteratorendringer
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
