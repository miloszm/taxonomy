package collections

import com.tm.collections.Categories
import com.tm.domain.{CategoryNode, Id, TagId}

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

