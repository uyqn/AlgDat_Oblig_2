import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public interface Beholder<T> extends Iterable<T> {
    boolean leggInn(T t);       // legger inn t i beholderen
    boolean inneholder(T t);    // sjekker om den inneholder t
    boolean fjern(T t);         // fjerner t fra beholderen
    int antall();               // returnerer antallet i beholderen
    boolean tom();              // sjekker om beholderen er tom
    void nullstill();           // tømmer beholderen
    Iterator<T> iterator();     // returnerer en iterator

    default boolean fjernHvis(Predicate<? super T> p)  // betingelsesfjerning
    {
        Objects.requireNonNull(p);                       // kaster unntak

        boolean fjernet = false;
        for (Iterator<T> i = iterator(); i.hasNext(); )  // løkke
        {
            if (p.test(i.next()))                          // betingelsen
            {
                i.remove();                                  // fjerner
                fjernet = true;                              // minst en fjerning
            }
        }
        return fjernet;
    }

} // grensesnitt Beholder
