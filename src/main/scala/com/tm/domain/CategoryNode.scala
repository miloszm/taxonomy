package com.tm.domain

import scala.annotation.tailrec

case class CategoryNode(id: Id, children: Seq[CategoryNode]){
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
}

