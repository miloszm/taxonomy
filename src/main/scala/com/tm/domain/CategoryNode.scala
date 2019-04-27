package com.tm.domain

import scala.annotation.tailrec

case class Id(id: String)

case class CategoryNode(id: Id, name: String, children: Seq[CategoryNode], tags: Seq[TagId] = Nil){

  def getNode(id: Id): Option[CategoryNode] = findFirst(_.id == id)

  def getDescendants: Seq[CategoryNode] = children.flatMap(_.findAll(_ => true))

  def getNodesWithTag(tagId: TagId): Seq[CategoryNode] = findAll(_.tags.exists(_ == tagId))

  def findFirst(f: CategoryNode => Boolean): Option[CategoryNode] = {
    if (f(this)) Some(this)
    else {
      @tailrec
      def go(nodes: Seq[CategoryNode]): Option[CategoryNode] = nodes match {
        case Nil => None
        case x::xs => x.findFirst(f) match {
          case found@Some(_) => found
          case None => go(xs)
        }
      }
      go(children)
    }
  }

  def findAll(f: CategoryNode => Boolean): Seq[CategoryNode] = {
    List(this).filter(f) ++ children.flatMap(_.findAll(f))
  }
}

