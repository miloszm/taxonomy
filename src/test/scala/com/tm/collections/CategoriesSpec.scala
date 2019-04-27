package collections

import com.tm.collections.{Categories, Tags}
import com.tm.domain._
import org.scalatest.{Matchers, WordSpec}



object TestCategories extends Categories {

  val children = List(
    CategoryNode(Id("shows"), "shows",
      List(
        CategoryNode(Id("theatre"), "theatre", Nil),
        CategoryNode(Id("films"), "films",
          List(
            CategoryNode(Id("chinese1"), "chinese", Nil, Seq(TagId("chinese"))),
            CategoryNode(Id("comedy"), "comedy", Nil),
            CategoryNode(Id("action"), "action", Nil)
          )
        )
      )
    ),
    CategoryNode(Id("music"), "music",
      List(
        CategoryNode(Id("jazz"), "jazz", Nil),
        CategoryNode(Id("pop"), "pop", Nil),
        CategoryNode(Id("rock"), "rock", Nil)
      )
    ),
    CategoryNode(Id("restaurants"), "restaurants",
      List(
        CategoryNode(Id("chinese2"), "chinese", Nil, Seq(TagId("chinese"))),
        CategoryNode(Id("french"), "french", Nil),
        CategoryNode(Id("italian"), "italian", Nil)
      ),
      Seq(TagId("restaurant"))
    )
  )
  override val root = CategoryNode(Id("categories"), "categories", children)

  override def fromCsv(csv: Stream[String]) = ???
}

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

class CategoriesSpec extends WordSpec with Matchers {

  val categories = TestCategories
  val tagsRepository = TestTags

  "Categories collection" should {

    "retrieve node by id" in {
      categories.getNode(Id("action")) shouldBe Some(CategoryNode(Id("action"), "action", Nil))
    }

    "retrieve descendants of a node" in {
      val maybeParent = categories.getNode(Id("music"))
      maybeParent should not be empty
      maybeParent.foreach { parent =>
        categories.getDescendants(parent) should contain theSameElementsAs List(
          CategoryNode(Id("jazz"), "jazz", Nil),
          CategoryNode(Id("pop"), "pop", Nil),
          CategoryNode(Id("rock"), "rock", Nil)
        )
      }
    }

    "retrieve multiple nodes with a tag" in {
      categories.getNodesWithTag(TagId("chinese")) should contain theSameElementsAs List(
        CategoryNode(Id("chinese1"), "chinese", Nil, Seq(TagId("chinese"))),
        CategoryNode(Id("chinese2"), "chinese", Nil, Seq(TagId("chinese")))
      )
    }

    "retrieve non-leaf node with a tag" in {
      categories.getNodesWithTag(TagId("restaurant")).map(_.copy(children = Nil)) should contain theSameElementsAs List(
        CategoryNode(Id("restaurants"), "restaurants", Nil, Seq(TagId("restaurant")))
      )
    }

    "serialise a tree to CSV" in {
      categories.toCsv(Lang("it_IT"), tagsRepository).toList should contain theSameElementsInOrderAs List(
        "categories",
        ",shows",
        ",,theatre",
        ",,films",
        ",,,chinese,Cinese",
        ",,,comedy",
        ",,,action",
        ",music",
        ",,jazz",
        ",,pop",
        ",,rock",
        ",restaurants,Restaurantes",
        ",,chinese,Cinese",
        ",,french",
        ",,italian"
      )
    }

  }

}

