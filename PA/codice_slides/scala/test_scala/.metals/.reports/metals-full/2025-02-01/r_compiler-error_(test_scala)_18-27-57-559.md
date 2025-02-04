error id: EEAB5D0EAE2205FE260B7DAA815E1FB3
file:///C:/Users/mitic/Documents/GitHub/Magistrale/PA/codice_slides/scala/test_scala/src/main/scala/Main.scala
### scala.reflect.internal.FatalError: 
  ThisType(method numeroMaiuscole) for sym which is not a class
     while compiling: file:///C:/Users/mitic/Documents/GitHub/Magistrale/PA/codice_slides/scala/test_scala/src/main/scala/Main.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.12
    compiler version: version 2.13.12
  reconstructed args: -classpath <WORKSPACE>\.bloop\test_scala\bloop-bsp-clients-classes\classes-Metals-wuIwdVa2TkCRLDA-LI0GJA==;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\sourcegraph\semanticdb-javac\0.10.3\semanticdb-javac-0.10.3.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.12\scala-library-2.13.12.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-parser-combinators_2.13\2.3.0\scala-parser-combinators_2.13-2.3.0.jar -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: Literal(Constant(()))
       tree position: line 5 of file:///C:/Users/mitic/Documents/GitHub/Magistrale/PA/codice_slides/scala/test_scala/src/main/scala/Main.scala
            tree tpe: <error>
              symbol: null
           call site: <none> in <none>

== Source file context for tree position ==

     2   val message = "Hello, World!"
     3   def numeroMaiuscole (s : _CURSOR_S) : Int ={
     4     var count = s.exists(_.isUpperCase)
     5   }
     6 }
     7 

occurred in the presentation compiler.



action parameters:
offset: 75
uri: file:///C:/Users/mitic/Documents/GitHub/Magistrale/PA/codice_slides/scala/test_scala/src/main/scala/Main.scala
text:
```scala
object Main {
  val message = "Hello, World!"
  def numeroMaiuscole (s : @@S) : Int ={
    var count = s.exists(_.isUpperCase)
  }
}
```


presentation compiler configuration:
Scala version: 2.13.12
Classpath:
<WORKSPACE>\.bloop\test_scala\bloop-bsp-clients-classes\classes-Metals-wuIwdVa2TkCRLDA-LI0GJA== [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\sourcegraph\semanticdb-javac\0.10.3\semanticdb-javac-0.10.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.12\scala-library-2.13.12.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-parser-combinators_2.13\2.3.0\scala-parser-combinators_2.13-2.3.0.jar [exists ]
Options:
-Yrangepos -Xplugin-require:semanticdb




#### Error stacktrace:

```
scala.reflect.internal.Reporting.abort(Reporting.scala:70)
	scala.reflect.internal.Reporting.abort$(Reporting.scala:66)
	scala.reflect.internal.SymbolTable.abort(SymbolTable.scala:28)
	scala.reflect.internal.Types$ThisType.<init>(Types.scala:1394)
	scala.reflect.internal.Types$UniqueThisType.<init>(Types.scala:1414)
	scala.reflect.internal.Types$ThisType$.apply(Types.scala:1418)
	scala.meta.internal.pc.AutoImportsProvider$$anonfun$autoImports$3.applyOrElse(AutoImportsProvider.scala:76)
	scala.meta.internal.pc.AutoImportsProvider$$anonfun$autoImports$3.applyOrElse(AutoImportsProvider.scala:61)
	scala.collection.immutable.List.collect(List.scala:267)
	scala.meta.internal.pc.AutoImportsProvider.autoImports(AutoImportsProvider.scala:61)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$autoImports$1(ScalaPresentationCompiler.scala:386)
```
#### Short summary: 

scala.reflect.internal.FatalError: 
  ThisType(method numeroMaiuscole) for sym which is not a class
     while compiling: file:///C:/Users/mitic/Documents/GitHub/Magistrale/PA/codice_slides/scala/test_scala/src/main/scala/Main.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.12
    compiler version: version 2.13.12
  reconstructed args: -classpath <WORKSPACE>\.bloop\test_scala\bloop-bsp-clients-classes\classes-Metals-wuIwdVa2TkCRLDA-LI0GJA==;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\sourcegraph\semanticdb-javac\0.10.3\semanticdb-javac-0.10.3.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.12\scala-library-2.13.12.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-parser-combinators_2.13\2.3.0\scala-parser-combinators_2.13-2.3.0.jar -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: Literal(Constant(()))
       tree position: line 5 of file:///C:/Users/mitic/Documents/GitHub/Magistrale/PA/codice_slides/scala/test_scala/src/main/scala/Main.scala
            tree tpe: <error>
              symbol: null
           call site: <none> in <none>

== Source file context for tree position ==

     2   val message = "Hello, World!"
     3   def numeroMaiuscole (s : _CURSOR_S) : Int ={
     4     var count = s.exists(_.isUpperCase)
     5   }
     6 }
     7 