object main {
  def main(args: Array[String]): Unit = {
    def something(): Int = {
      println("calling something")
      1
    }

    def callByValue(x: Int): Unit = {
      println("x1=" + x)
      println("x2=" + x)
    }

    def callByName(x: => Int): Unit = {
      println("x1=" + x)
      println("x2=" + x)
    }
    // something chiamata 2 volte, ogni volta che x viene usto nella funzione, quindi 2 volte

    callByName(something());
    println("---------------------")
    callByValue(something());
    // calling something viene stampato 1 volta perchè la funzione something è valutata solo
    // 1 volta prima di entrare nella funzione callbyvalue

    val a = 10
    val b = 35
    val add = (x: Int, y:Int) => {
      x+y
    }
    def applyFun (f1 : (Int,Int)=>Int)(a: Int)(b: Int) = f1(a,b)
    print(applyFun(add)(a)(b))

    val nums = List(1,2,3)
    val doubleValueList = nums.map(_ * 2)


    def mod (x: Int) = if(x%2 == 0) true else false
    def mult(x: Int) = x*2
    def magic(f:Int=>Boolean, g:Integer=>Integer)(x: Int) = {
      if (f(x)) {
        g(x)
      }else{
        x
      }

    }
    def h (x: Int) =  magic(mod,mult)(x)
    val lista = List(1,2,3)
    print(lista.map(h(_)))

    val sum = lista.reduce(_ + _)
    println(sum) // Output: 8
  }
}