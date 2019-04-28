package com.tm.domain

case class Lang(lang: String)

case class TagId(id: String)

case class Tag(id: TagId, translations: Map[Lang, String])
