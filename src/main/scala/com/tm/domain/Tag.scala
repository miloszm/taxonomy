package com.tm.domain


case class Lang(lang: String)


case class Translations(map: Map[Lang, String])


case class TagId(id: String)


case class Tag(id: TagId, translations: Translations)
