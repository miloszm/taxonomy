package com.tm.collections

import com.tm.domain.{Lang, Tag, TagId}

trait Tags {

  def tagRepository: Map[TagId, Tag]

  def getTranslation(tagId: TagId, lang: Lang): String = tagRepository.get(tagId).flatMap {
    _.translations.get(lang)
  }.getOrElse("")

  def getTags(lang: Lang, translations: Seq[String]): Seq[Tag] =
    translations.flatMap{ translation =>
      tagRepository.values.find(_.translations.get(lang).contains(translation))
    }

}
