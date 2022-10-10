package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0; 
    }

    public DobbeltLenketListe(T[] a) {
        //https://www.softwaretestinghelp.com/doubly-linked-list-in-java/
        //https://study.com/academy/lesson/doubly-linked-lists-in-java-creation-nodes.html
        //Lager en if setning som kaster en nullpointexception hvis listen er tom
        if (a == null) {
            throw new NullPointerException("Tabell a er null!");
        }

        //Lager en if setning for å plassere hode peker. Viktig å passe på at elementene i starten av array kan
        //være null. Må derfor traversere gjennon med en for-løkke.
        if (a.length > 0) {
            //int i er satt utenfor for løkken fordi vi bruker break når hode er inistiert.
            // Vi vil vite hvilken index den er på (det kan være flere null i arrayet før vi får en verdi).
            int i = 0;
            for (; i < a.length; i++) {
                if (a[i] != null) {
                    hode = new Node(a[i]);
                    break;
                }
            }

                //Setter hale og hode til å bli lik. Hvis den ikke går inn i mer nå. Returneres 1 og
                // hale og hode peker på samme.
                hale = hode; 

                //Traverserer gjennom resten av arrayet og går bare inn i if-setningen når hale ikke er lik null.
                if (hale != null) {
                    // Legger til i for å komme på indeks +1 i arrayet.
                    i ++;
                    //Looper gjennom resten av arrayet for å legge det i listen
                    for (; i < a.length; i++) {
                     //Sjekker hvert element for om det er null
                        if (a[i] != null) {
                            //inistierer konstruktøren med å legge inn ny verdi på hale sin neste.
                            //Har satt hale til å være lik hode til å begynne med.
                            //verdi, deretter hale som var forrige og neste er fremdeles null.
                            hale.neste = new Node <> (a[i], hale, null);

                            //FLytter hale til hale.neste. Hale skal alltid være tilslutt. Derfor gjør vi dette.
                            hale = hale.neste;

                        }
                    }
                }
            }
        }

    public Liste<T> subliste(int fra, int til) {
        //skjekker om fra og til er lovlige argumenter og er innenfor listen sin lengde ved fratilKontroll metoden
        //fratilKontroll(antall, fra,til);

        //Henter hode og legger det inn i en objekt av Node<T>
        Node<T> noden = hode;

        //Bruker for løkke siden her vet vi hvor mange ganger løkken skal kjøres
        //flytter hoden til noden til noden med fra indeks
        for (int i = 0; i < fra; i++){
            noden = noden.neste;
        }

        //Oppretter liste av T node class som skal holde node verdiene
        T[] sublisteInn = (T[]) new Object[til-fra];

        int indeks = 0;

        //fyller sublisteInn med verdier [fra-til> noder ved bruk av for løkke
        //etter det oppdaterer noden til neste node for å legge inn, øker også indeks for å bruke til å hente neste node
        for (int i = fra; i < til; i++){
            sublisteInn[indeks] = noden.verdi;
            noden = noden.neste;
            indeks++;
        }

        //oppretter sublisten og fyller den for å returnere
        DobbeltLenketListe<T> subliste = new DobbeltLenketListe<>(sublisteInn);

        return subliste;
    }

    @Override
    public int antall() {
        //https://www.geeksforgeeks.org/program-find-size-doubly-linked-list/
        //Setter antall til 0;
        antall = 0;

        //Lager en current Nodevariabel
        Node current = hode;
        //Legger inn en if hvis listen har null -- sender nullexception
        if (current == null) {
            //Sender null exception
        }

        //Bruker en while løkke for å gå gjennom dobbel lenket liste helt til pekeren peker på null.
        while (current != null) {
            antall ++;
            //Flytter pekeren fra node til neste node og sjekker om vi skal inn i while-løkka igjen.
            current = current.neste;
        }
         //Må finne ut hvordan jeg løser når listen er 0;

        //returnerer antallet/lengden på listern
      return antall;
    }

    @Override
    public boolean tom() {
        //Sjekke om listen som kommer inn er tom
        Node current = hode;
        //While-løkke for å iterere og sjekke om hode har en peker
            if (current != null) {
                return false;
            }
        // Er listen tom returneres false.
        return true;
    }

    @Override
    public boolean leggInn(T verdi) {
        //throw new UnsupportedOperationException();

        // Må sjekke at verdi ikke er null
        Objects.requireNonNull(verdi,"Verdi kan ikke være null");

        // Hvis listen er tom
        if (hode == null) {
            hode = new Node<>(verdi);
            hale = hode;
            endringer++;
            antall++;
            return true;
        }
        // Hvis listen ikke er tom
        else {
            hale.neste = new Node<>(verdi, hale, null);
            hale = hale.neste;
            endringer++;
            antall++;
            return true;
        }
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean inneholder(T verdi) {
        if (indeksTil(verdi) > -1) { // linje/krav fra setning 4, oppgave 4
            return true;
        } else  // Else alltid return false.
        return false;
    }

    @Override
    public T hent(int indeks) {
        //Skjekker om indeksen er gyldig eller ugyldig
        indeksKontroll(indeks,false);

        //Finner noden til indeks med finnNode metoden og legger inn i en variabel av Node<T>
        Node<T> returNode = finnNode(indeks);

        //returnere verdien til noden
        return returNode.verdi;
    }

    //private hjelpemetoden for å returnere noden til den gitte indeksen
    private Node<T> finnNode(int indeks){
        Node<T> returNode;

        //Vis indeksen er mindre enn antall/2 starter letingen etter noden fra hodet og går mot høyre ved hjelp av neste-pekere
        if (indeks < antall/2){
            returNode = hode;
            int i = 0;

            //bruker while løkke siden spørringene gjøres ukjent mange ganger
            //Beveger mot høyre og setter neturNode til neste verdi til i ikke er mindre enn indeks
            while (i < indeks){
                returNode = returNode.neste;
                i++;
            }
        }

        //vis indeksen er større eller lik antall/2 starter letingen etter noden fra halen og går mot venstre ved hjelp av forrige-pekere
        else {
            returNode = hale;
            int i = antall-1;

            //bruker while løkke siden spørringene gjøres ukjent mange ganger
            //Beveger mot venstre og setter neturNode til forrige verdi til i ikke er større eller lik indeks
            while (i > indeks){
                returNode = returNode.forrige;
                i--;
            }
        }

        return returNode;
    }


    @Override
    public int indeksTil(T verdi) {
        Node<T> peker = hode;

        for (int i =0; i<antall; i++) {

            if (peker.verdi.equals(verdi)) {
                return i;// vil denne returne oss helt ut av hele greia
            }
            peker=peker.neste;
        }
        return -1; //tilfeldring tall
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        //Skjekker om nyverdi er null og kaster unntak vis den er
        if (nyverdi ==  null) {
            throw new NullPointerException("Ny verdi kan ikke være null, gi ny verdi");
        }

        //Skjekker om indeksen er gyldig eller ugyldig
        indeksKontroll(indeks,false);

        //Finner noden til indeks med finnNode metoden og legger inn i en variabel av Node<T>
        Node<T> node = finnNode(indeks);

        //henter nåværende verdi for å returnere og før den oppdateres
        T gammelVerdi = node.verdi;

        //Oppdaterer noden sin verdi til nyverdi som kommer inn som parameter
        node.verdi = nyverdi;

        //legger til en økning i endringer teller variablen med 1
        endringer++;

        //returnerer gammel node verdi altså før den ble oppdatert
        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        //https://www.geeksforgeeks.org/delete-a-node-in-a-doubly-linked-list/
        //https://www.geeksforgeeks.org/delete-doubly-linked-list-node-given-position/


        //private Node(T verdi) {this(verdi, null, null);
        //Fjerne og returnere verdien på posisjon indeks.

        //Setter hodenode til current for å bruke for-løkke til å finne riktig verdi.
        Node <T> current = hode;
        T verdi;


        //Bruker en for-løkke for å traversere frem til indeksen som skal fjernes.
        int i = 0;
        for (; i == indeks; i++ ) {
            current = current.neste;
        }

        //Hode fjernes
        //Hvis hode er noden som skal slettes. Flytter hodepeker og hode forsvinner.
        if (hode == current) {
            hode = current.neste;

        }

        //En verdi mellom hode og hale fjernes.
        //Sjekker om den som skal slettes ikke er siste node. Blir det riktig, eller skal det være hale her?
        if (current.neste != null) {
            //flytter pekerene fra current.neste til current.neste.forrige. Da vil noden bli fjernet siden den ikke har pekere på seg.
            current.neste.forrige = current.neste;

        }

        //Fjerner tilbakepekerene
        if (current.forrige != null) {
            //merk forrige.neste her mot neste.forrige ovenfor.
            current.forrige.neste = current.neste;
        }

        //slett current som er lik indeks.


        //sjekker om det er en tom liste etter fjerning.
        if (current == null) {
            return null;
        }

        //Verdien på posisjon indeks.
        verdi = current.verdi;

        //Returnere verdien på posisjon
        //Tar en mindre antall for den som blir fjernet og pluss på endringer.
        antall --;
        endringer ++;

        return verdi;
    }

    @Override
    public void nullstill() {
        //throw new UnsupportedOperationException();

        // Første forsøk
        Node<T> n = hode;
        hode = null;
        while (n.neste != null) {
            n.verdi = null;
            n = n.neste;
        }
        n.verdi = null;
        hale = null;
        antall = 0;
        endringer++;
    }

    @Override
    public String toString() {
        //throw new UnsupportedOperationException();


        Node<T> n = hode;
        StringBuilder ut = new StringBuilder();
        ut.append("[");

        // Sjekker om listen er tom, om ikke så legges n.verdi til i ut.
        if (n == null) return "[]";
        else ut.append(n.verdi);

        // Legger til n.neste.verdi så lenge n.neste ikke er null
        while (n.neste != null) {
            ut.append(", ");
            ut.append(n.neste.verdi);
            n = n.neste;
        }
        ut.append("]");

        return ut.toString();
    }

    public String omvendtString() {
        //throw new UnsupportedOperationException();
        Node<T> n = hale;

        StringBuilder ut = new StringBuilder();
        ut.append("[");

        if (n == null) return "[]";
        else ut.append(n.verdi);

        while (n.forrige != null) {
            ut.append(", ");
            ut.append(n.forrige.verdi);
            n = n.forrige;
        }
        ut.append("]");

        return ut.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        //skjekker om indeksen er lovlig
        indeksKontroll(indeks,false);

        //returnerer en instans av iterartorklassen
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks); //starter på den noden tilhørende indeksen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            //sjekker om iteratorendringer er lik endringer. Hvis ikke, kastes en ConcurrentModificationException.
            if (iteratorendringer != endringer){
                throw new ConcurrentModificationException("Iteratorendringer er ikke lik endringer!");
            }

            //en NoSuchElementException hvis det ikke er flere igjen i listen altså ingen neste med if løkke
            if (hasNext() != true){
                throw new NoSuchElementException("Det er ikke flere noder igjen i listen!");
            }

            //setter fjernOK til true
            fjernOK = true;

            // henter verdien til noden som et objekt av T
            T denneVerdien = denne.verdi;
            //peker på neste node i listen
            denne = denne.neste;
            //returnerer verdien til noden
            return denneVerdien;
        }

        @Override
        public void remove() {

            Node <T> currentp;


            if (!fjernOK) {
                throw new IllegalStateException();
            }
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException();
            }

            int teller =antall;
            fjernOK=false; // SLik setning 2, avsnitt 2 sier, hvis disse to testene over passeres, sett fjernOK=false


            if (teller== 1) { //Punkt 1: ser til at listen er over 1 element, hvis den generiske listen har 1 element, så vil hale og hode være null, ettersom vi fjerner da den siste noden i den generiske listen
                hale=null;
                hode=null;
            }

            //Trolig feil et av de 3 punktene nederst


            else if (denne== null) { // Punkt 2: Hvis første element fjernes
                hale=hale.forrige; // eller hale=hale.forrige
                hale.neste=null;

                //Kanskje ha en retur eller Break i hver eneste if setning
                //Hvis ikke så trenger du else if
            }

            else if (denne.forrige == hode) {
                hode=hode.neste; //Punkt 3: Hvis lista sin forrige er hode, så sett hodet til denne (elementet som fjernes) sin neste
                hode.forrige= null;
            }



            else { //Punkt 4: Der begge pekeree i hver ende må oppdateres
                currentp= denne.forrige; //Dette er noden som blir removed, i metoden
                currentp.forrige.neste= currentp.neste;
                currentp.neste.forrige= currentp.forrige;

               /*
                denne.forrige=denne.forrige.forrige;
                denne.forrige.neste=denne; */
            }

            antall-=1;
            endringer+=1;
            iteratorendringer+=1;
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }
    }
    // class DobbeltLenketListe


