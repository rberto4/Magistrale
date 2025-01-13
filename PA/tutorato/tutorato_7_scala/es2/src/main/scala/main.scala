object Esercizio2 {
  @main
  def main(): Unit = {
    var a = 5

    def babbonatale (x: Int) : Int = {
      x*4
    }

    println(babbonatale(a))

    var stringa = "aaaxaayaaza"
    println (f(stringa))

    println(g("ciao"))
    def f(s: String): String = {
      if s.isEmpty || s.size < 9 then ""
      else s"${s.charAt(3)} ${s.charAt(6)} ${s.charAt(9)}"
    }

    def g(s: String) : String = {
      if s.isEmpty then ""
      else s"$s$s"
    }

    def h(i : String => String, m: String => String): String => String = {
      (x:String) => m(i(x))
    }

    val k = h(f, g)
    println(k("oloboslorat"))

    val listString = List("oloboslorat", "olocosiorat", "olomosaor!t")
    val listaKepped = listString.map(element => k(element))
    println(listaKepped)

    var listFinal = listaKepped.reduce(_ + _)
    println(listFinal)
  }


}
