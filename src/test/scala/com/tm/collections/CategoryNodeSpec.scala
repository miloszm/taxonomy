package collections

import com.tm.collections.Tags
import com.tm.domain._
import org.scalatest.{Matchers, WordSpec}

class CategoryNodeSpec extends WordSpec with Matchers {

  val frenchRestaurantNode = CategoryNode(Id("french"), "french", Nil)
  val node = CategoryNode(Id("restaurants"), "restaurants",
    List(
      CategoryNode(Id("chinese2"), "chinese", Nil, Seq(TagId("chinese"))),
      frenchRestaurantNode,
      CategoryNode(Id("italian"), "italian", Nil)
    ),
    Seq(TagId("restaurant"))
  )
  object TestTagsRepository extends Tags {
    val restaurantTranslations = Map(
      Lang("en_GB") -> "Restaurants",
      Lang("fr_FR") -> "Restaurants",
      Lang("it_IT") -> "Restaurantes"
    )
    override def tagRepository = Map(
      TagId("restaurant") -> Tag(TagId("restaurant"), restaurantTranslations)
    )
  }
  val tagsReporitory = TestTagsRepository

  "Category node" should {
    "represent node with tags as CSV" in {
      node.asCsv(Lang("en_GB"), tagsReporitory) shouldBe "restaurants,Restaurants"
    }
    "represent node without tags as CSV" in {
      frenchRestaurantNode.asCsv(Lang("en_EN"), tagsReporitory) shouldBe "french"
    }
  }
}
