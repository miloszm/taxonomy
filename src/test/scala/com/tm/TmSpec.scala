package com.tm

import org.scalatest.{FlatSpec, Matchers}

class TmSpec extends FlatSpec with Matchers {

  "Pies" should
    "wag the tail" in {
      Tm.dajGlos
    }

}
