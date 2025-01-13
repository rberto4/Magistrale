object EsercizioScala {
  @main
  def Main(): Unit = {
    println(s"ciao")
    println(mod(6))
    println(mult(6))



    def mod(x: Int): Boolean = {
      if x % 2 == 0 then true else false
    }

    def mult(x: Int): Int = {
      x * 2
    }

    def magic(f:Int=>Boolean,g: Int=>Int) : Int=>Int = {
      x => if (f(x)) g(x) else x
    }

    val h = magic(mod,mult)
    val res = h(3)
    println(s"funzione magic : $res")

    val numbers = List(1, 2, 3)
    val resultWithH = numbers.map(h)

    println(s"Lista trasformata con h: $resultWithH")

    val sommaLista = resultWithH.reduce(_+_)
    println(s"Lista con h sommata: $sommaLista")
  }
}