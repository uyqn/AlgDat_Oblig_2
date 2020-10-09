# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 

# Arbeidsfordeling

Oppgaven er levert av følgende studenter:
* Uy Quoc Nguyen, s341864, s341864@oslomet.no
* Marius Havnaas, s341877, s341877@oslomet.no
* Ruben Sønstabø Johanssen, s341851, s341851@oslomet.no

Vi har brukt git til å dokumentere arbeidet vårt. Vi har 16 commits totalt, og hver logg-melding beskriver det vi har gjort av endringer.

I oppgaven har vi hatt følgende arbeidsfordeling:
* Uy har hatt hovedansvar for oppgave 2, 3b, og 5
* Ruben har hatt hovedansvar for oppgave 6, 7, 8 og 9
* Marius har hatt hovedansvar for oppgave 1, 3a, 4 og 10

# Beskrivelse av oppgaveløsning (maks 5 linjer per oppgave)

* Oppgave 1: 
Metodene int antall() og Boolean tom() var forhåldsvis rettfrem, enkle metoder. 
Konstruktøren var vanskeligere å forstå og denne krevde at jeg brukte en del tid på å sette meg inn i "tankegangen" rundt noder og lenket liste.
* Oppgave 2:
a) Metodene toString og omvendtString legger bare til verdien i stringBuilderen med peker på hodet og halen henholdsvis. Pekeren itererer gjennom hele listen og returnerer resultatet. Dersom listen er tom returnerer den bare "[]".
b) Metoden ble implementert ved å bare oppdatere hale.neste til en ny node som peker tilbake til halen (neste = null). Halen blir deretter bare oppdatert til den nye noden som ble lagt til.
* Oppgave 3:
a) Startet med å lage en metode som bare fant noden basert på hode, forså å utvikle den og gjøre den mer kompleks når jeg fikk det til å funke. Gjorde denne etter jeg lagde konstruktøren i oppgave 1, gikk greit da jeg allerede hadde tankegangen inne.
b) Sublisten starter først med å sjekke om listen er tom. Dersom den ikke er tom bruker den finnNode metoden for å finne noden den starter å peke på. Deretter itererer den (til - fra - 1) ganger og hver gang legger til noden bakerst via metoden implementert fra Oppgave 3b).
* Oppgave 4:
Denne lagde jeg først veldig enkelt ved å bruke hent metoden og unngå null verdi med en if-setning. Videre effektiviserte jeg metoden ved å bruke pekere isteden for på gå igjennom hele hent metoden hver gang. Dermed fikk metoden effektivitet O(n) mot tidligere O(n^2).
* Oppgave 5: Etter at metoden har passert indekskontrollen og null-kontrollen så sjekker den om listen er tom. Dersom den er tom så blir hode og hale til den nye noden. Dersom den ikke er tom så sjekker den om indeks er 0 (hode oppdateres) eller indeks er lik antall (hale oppdateres). Ellers så bruker metoden finnNode metoden for å finne noden til venstre for den nye noden som skal legges til. Den finner også noden som skal være til høyre for den nye noden. Deretter så opprettes den nye noden med pekere til venstre og høyre og setter de to nodene som allerede eksisterte sin forrige og neste til lik hverandre.
* Oppgave 6: Metoden «T fjern(int indeks)» sjekker først for nullverdier. Setter deretter en midlertidig «temp» lik hode, bruker «while» for å sjekke om verdien er i listen og lager til slutt hoveddelen av metoden gjennom en if-setning med tre deler for om det er første, siste eller en mellom verdi som skal fjernes gjennom hode, hale, temp og dem sin neste og forrige m.m..  Metoden boolean fjern(T verdi) sjekker først indeksen, lager midlertidig node som på forgje metode, og bruker if-setning for å fjerne første (sjekker også om listen er tom eller ikke), siste eller en i mellom to noder (oppdaterer da pekerne). Avslutter begge med å oppdatere antall og endringer.
* Oppgave 7: ...
* Oppgave 8: ...
* Oppgave 9: ...
* Oppgave 10:
Her ble det forsøkt å effektivisere sorteringen ved bruk av quick- eller mergesort. Dette ble vanskelig å få til da oppgaveteksten presiserte at det skulle være en "på plass"-sortering. Oppgaven ble løst både ved å flytte pekere og ved å flytte verdiene inne i nodene. Slik vi så det var det ingen av de to variantene som hadde noen nevneverdig fordel over den andre mtp. effektivitet. 
