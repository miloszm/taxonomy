package collections

import com.tm.collections.Tags
import com.tm.domain.{Lang, Tag, TagId}

object TestTags extends Tags {
  val chineseTransations = Map(
    Lang("en_GB") -> "Chinese",
    Lang("fr_FR") -> "Chinois",
    Lang("it_IT") -> "Cinese"
  )
  val restaurantTranslations = Map(
    Lang("en_GB") -> "Restaurants",
    Lang("fr_FR") -> "Restaurants",
    Lang("it_IT") -> "Restaurantes"
  )
  override def tagRepository = Map(
    TagId("chinese") -> Tag(TagId("chinese"), chineseTransations),
    TagId("restaurant") -> Tag(TagId("restaurant"), restaurantTranslations)
  )
}

