package collections

import com.tm.collections.{Categories, Tags, Taxonomy}
import com.tm.domain._
import org.scalatest.{Matchers, WordSpec}



object TestCategories extends Categories {

  override val repository = Map(
    Id("categories") -> CategoryNode(Id("categories"), "categories", List(Id("shows"), Id("music"), Id("restaurants"))),
    Id("shows") -> CategoryNode(Id("shows"), "shows", List(Id("theatre"), Id("films"))),
    Id("theatre") -> CategoryNode(Id("theatre"), "theatre", Nil),
    Id("films") -> CategoryNode(Id("films"), "films", List(Id("chinese1"), Id("comedy"), Id("action"))),
    Id("chinese1") -> CategoryNode(Id("chinese1"), "chinese", Nil, Seq(TagId("chinese"))),
    Id("comedy") -> CategoryNode(Id("comedy"), "comedy", Nil),
    Id("action") -> CategoryNode(Id("action"), "action", Nil),
    Id("music") -> CategoryNode(Id("music"), "music", List(Id("jazz"), Id("pop"), Id("rock"))),
    Id("jazz") -> CategoryNode(Id("jazz"), "jazz", Nil),
    Id("pop") ->  CategoryNode(Id("pop"), "pop", Nil),
    Id("rock") -> CategoryNode(Id("rock"), "rock", Nil),
    Id("restaurants") -> CategoryNode(Id("restaurants"), "restaurants", List(Id("chinese2"), Id("french"), Id("italian")), Seq(TagId("restaurant"))),
    Id("chinese2") -> CategoryNode(Id("chinese2"), "chinese", Nil, Seq(TagId("chinese"))),
    Id("french") -> CategoryNode(Id("french"), "french", Nil),
    Id("italian") -> CategoryNode(Id("italian"), "italian", Nil),
  )

  override val root: CategoryNode = repository.getOrElse(Id("categories"), throw new IllegalStateException("missing root"))
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

    "deserialise a tree from CSV" in {
      val csv = List(
        "categories",
        ",shows",
        ",,theatre",
        ",,films",
        ",,,chinese,Chinois",
        ",,,comedy",
        ",,,action",
        ",music",
        ",,jazz",
        ",,pop",
        ",,rock",
        ",restaurants,Restaurants",
        ",,chinese,Chinois",
        ",,french",
        ",,italian"
      ).toStream
      val taxonomy = Taxonomy.fromCsv(csv, Lang("fr_FR"), tagsRepository)
      taxonomy.toCsv(Lang("en_GB"), tagsRepository).toList should contain theSameElementsInOrderAs List(
        "categories",
        ",shows",
        ",,theatre",
        ",,films",
        ",,,chinese,Chinese",
        ",,,comedy",
        ",,,action",
        ",music",
        ",,jazz",
        ",,pop",
        ",,rock",
        ",restaurants,Restaurants",
        ",,chinese,Chinese",
        ",,french",
        ",,italian"
      )
    }

  }

}

