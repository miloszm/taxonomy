package com.tm.domain

import scala.annotation.tailrec


case class Lang(lang: String)


case class Translations(map: Map[Lang, String])


case class Tag(name: String, translations: Translations)


case class Id(id: String)



