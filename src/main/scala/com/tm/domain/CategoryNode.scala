package com.tm.domain

import com.tm.collections.Tags

import scala.annotation.tailrec

case class Id(id: String)

case class NodeLevel(node: CategoryNode, level: Int)

case class CategoryNode(id: Id, name: String, children: Seq[Id], tags: Seq[TagId] = Nil){

  def getNode(id: Id, repository: Map[Id, CategoryNode]): Option[CategoryNode] = findFirst(_.id == id, repository)

  def getDescendants(repository: Map[Id, CategoryNode]): Seq[CategoryNode] = children.flatMap {
    repository.get
  }.flatMap(_.findAll(_ => true, repository))

  def getDescendantsWithLevel(level: Int, repository: Map[Id, CategoryNode]): Seq[NodeLevel] = findAllWithLevel(_ => true, level, repository)

  def getNodesWithTag(tagId: TagId, repository: Map[Id, CategoryNode]): Seq[CategoryNode] = findAll(_.tags.exists(_ == tagId), repository)

  def findFirst(f: CategoryNode => Boolean, repository: Map[Id, CategoryNode]): Option[CategoryNode] = {
    if (f(this)) Some(this)
    else {
      @tailrec
      def go(nodes: Seq[CategoryNode]): Option[CategoryNode] = nodes match {
        case Nil => None
        case x::xs => x.findFirst(f, repository) match {
          case found@Some(_) => found
          case None => go(xs)
        }
      }
      go(children.flatMap(repository.get))
    }
  }

  def findAll(f: CategoryNode => Boolean, repository: Map[Id, CategoryNode]): Seq[CategoryNode] =
    List(this).filter(f) ++ children.flatMap {
      repository.get
    }.flatMap(_.findAll(f, repository))

  def findAllWithLevel(f: CategoryNode => Boolean, level: Int, repository: Map[Id, CategoryNode]): Seq[NodeLevel] =
    List(this).filter(f).map(NodeLevel(_,level)) ++ children.flatMap {
      repository.get
    }.flatMap(_.findAllWithLevel(f, level + 1, repository))

  def asCsv(lang: Lang, tagsRepository: Tags): String = {
    (name +: tags.map(tagsRepository.getTranslation(_, lang))).mkString(",")
  }

}

object CategoryNode {
  def fromCsv(csv: String, id: Id, lang: Lang, tagsRepository: Tags): Option[CategoryNode] = {
    csv.split(",").toList match {
      case Nil => None
      case x::xs => Some(CategoryNode(id, x, Nil, tagsRepository.getTags(lang, xs).map(_.id)))
    }
  }
}

