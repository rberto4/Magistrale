file:///C:/Users/mitic/GitHub/Magistrale/PA/Temi%20d'esame/2024-07-17/es7_visitor/src/App.java
### java.util.NoSuchElementException: next on empty iterator

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 691
uri: file:///C:/Users/mitic/GitHub/Magistrale/PA/Temi%20d'esame/2024-07-17/es7_visitor/src/App.java
text:
```scala
import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
       ArrayList<Spettatore> spettatori = new ArrayList<>();

       Tifoso t1 = new Tifoso("mario", "rossi", 17, 6);
       Tifoso t2 = new Tifoso("giuseppe", "verdi", 16, 6);
       Tifoso t3 = new Tifoso("giovanni", "storti", 99, 5);

       Spettatore v1 = new Vip("Claudio", "Menghi");
       Spettatore g1 = new Giornalista("Stefano", "tigli", "Gazzetta dello sport");

       spettatori.add(t1);
       spettatori.add(t2);
       spettatori.add(v1);
       spettatori.add(g1);

       stampaCredenziali sc = new stampaCredenz@@iali();
       AccessoTribunaVip atv = new AccessoTribunaVip();
       for (Spettatore s : spettatori){
        s.accept(sc);
        s.accept(atv);
       }
       System.out.println( " --------------------------------------------------------- ");
       ArrayList<Tifoso> tifosiStandard = new ArrayList<>();
       tifosiStandard.add(t1);
       tifosiStandard.add(t2);
       tifosiStandard.add(t3);
       Collections.sort(tifosiStandard);

       for (Tifoso s : tifosiStandard){
        s.accept(sc);
       }

    }
}

```



#### Error stacktrace:

```
scala.collection.Iterator$$anon$19.next(Iterator.scala:973)
	scala.collection.Iterator$$anon$19.next(Iterator.scala:971)
	scala.collection.mutable.MutationTracker$CheckedIterator.next(MutationTracker.scala:76)
	scala.collection.IterableOps.head(Iterable.scala:222)
	scala.collection.IterableOps.head$(Iterable.scala:222)
	scala.collection.AbstractIterable.head(Iterable.scala:935)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:164)
	dotty.tools.pc.MetalsDriver.run(MetalsDriver.scala:45)
	dotty.tools.pc.HoverProvider$.hover(HoverProvider.scala:40)
	dotty.tools.pc.ScalaPresentationCompiler.hover$$anonfun$1(ScalaPresentationCompiler.scala:376)
```
#### Short summary: 

java.util.NoSuchElementException: next on empty iterator