object Esercizio3 {
  @main
  def main(): Unit =
    {
      def raddoppia (x: Int): Int ={
        x*2
      }
      def somma (f:Int=>Int) : (Int, Int) => Int = {
        (a:Int, b:Int) => {
          val range = b - a
          var res = 0
          for (i <- a + 1 to a + range - 1) {
            res += f(i)
          }
          res
        }
      }

      println(somma(raddoppia)(2, 6))

      //    def raddoppiaESomma (f:Int, g:Int)
    }

}
