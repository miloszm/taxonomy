package com.tm.collections

import com.tm.domain.{CategoryNode, Tag}

trait Tags {

  def add(tag: Tag): Unit

  def associate(tag: Tag, categoryNode: CategoryNode): Unit

  def getNodes(tag: Tag): Seq[CategoryNode]

  def getTags(categoryNode: CategoryNode): Seq[Tag]

  def hasTag(tag: Tag, node: CategoryNode): Boolean

}
