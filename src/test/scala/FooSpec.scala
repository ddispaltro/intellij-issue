
import org.scalatest.FlatSpec

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.async.Async._

object FooSpec {

  implicit class waitFor[T](val f: Future[T]) extends AnyVal {
    def w: T = Await.result(f, Duration.Inf)
  }

}

class FooSpec extends FlatSpec {
  import FooSpec._

  "test" should "stop at breakpoints" in {
    println("Foo")
    val f = Future{
      println("Bar")
    }
    println("Baz")
    f.w
  }

  it should "stop at breakpoints with scala.async" in async {
    await(Future {
      Thread.sleep(1000)
      println("Foo")
    })

    await(Future {
      Thread.sleep(500)
    })

    println("done")
  }.w
}
