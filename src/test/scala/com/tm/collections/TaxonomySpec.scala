package collections

import com.tm.collections.Taxonomy
import com.tm.domain._
import org.scalatest.{Matchers, WordSpec}

class TaxonomySpec extends WordSpec with Matchers {

  val categories = TestCategories
  val tagsRepository = TestTags
  val taxonomy = Taxonomy(categories.root, categories.repository)

  "Taxonomy collection" should {

    "serialise a tree to CSV" in {
      taxonomy.toCsv(Lang("it_IT"), tagsRepository).toList should contain theSameElementsInOrderAs List(
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

    "deserialise a tree from CSV in French and serialise in English" in {
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

