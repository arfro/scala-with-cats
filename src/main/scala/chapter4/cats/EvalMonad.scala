package chapter4.cats

import cats.Eval

object EvalMonad {

  // Eval.defer takes an existing instance of Eval and defers its evaluation. Defer is trampolined like map and flatMap and so the solution is stack safe.

  def evalFactorial(i: BigInt): Eval[BigInt] = {
    if (i == 0) return Eval.now(i)
    else if (i == 1) return Eval.now(i)
    else Eval.defer(evalFactorial(i - 1).map(_ * i))
  }

  def stackSafeFoldRight[A, B](as: List[A], acc: B)(fun: (A, B) => B): B = {

    def stackSafeFoldRightHelper[A, B](as: List[A], acc: Eval[B])(fun: (A, Eval[B]) => Eval[B]): Eval[B] =
      as match {
        case head :: tail => Eval.defer(fun(head, stackSafeFoldRightHelper(tail, acc)(fun)))
        case Nil => acc
      }

    stackSafeFoldRightHelper(as, Eval.now(acc)){
      (a, b) => b.map(fun(a, _))
    }.value

  }
}
