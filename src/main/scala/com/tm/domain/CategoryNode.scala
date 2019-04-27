package com.tm.domain

import scala.annotation.tailrec

case class Id(id: String)

case class CategoryNode(id: Id, children: Seq[CategoryNode], tags: Seq[TagId] = Nil){
  def getNode(id: Id): Option[CategoryNode] = {
    if (this.id == id) Some(this)
    else {
      @tailrec
      def go(nodes: Seq[CategoryNode]): Option[CategoryNode] = nodes match {
        case Nil => None
        case x::xs => x.getNode(id) match {
          case found@Some(_) => found
          case None => go(xs)
        }
      }
      go(children)
    }
  }
  def getDescendants: Seq[CategoryNode] = children ++ children.flatMap(_.getDescendants)
  def getNodesWithTag(tagId: TagId): Seq[CategoryNode] =
    List(this).filter(_.tags.exists(_ == tagId)) ++ children.flatMap(_.getNodesWithTag(tagId))
}

