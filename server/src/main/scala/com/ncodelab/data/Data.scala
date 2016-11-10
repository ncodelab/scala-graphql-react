package com.ncodelab.data

import java.util.concurrent.ConcurrentHashMap

import com.typesafe.scalalogging.StrictLogging
import sangria.macros.derive.{GraphQLDescription, GraphQLName}

import scala.collection.JavaConverters._


object Status extends Enumeration {
  val OPEN, IN_PROGRESS, FINISH = Value
}

@GraphQLName("Task")
@GraphQLDescription("The task")
case class Task(
                 id: String,
                 title: String,
                 text: String,
                 status: Status.Value
               ) {
  def setStatus(status: Status.Value): Task = {
    this.copy(status = status)
  }
}

@GraphQLName("Folder")
@GraphQLDescription("Folder for tasks")
case class Folder(
                   id: String,
                   name: String,
                   tasks: List[Task]
                 ) {
  def hasTask(taskId: String): Boolean = {
    tasks.map(_.id).contains(taskId)
  }

  def addTask(task: Task): Folder = {
    this.copy(tasks = tasks :+ task)
  }

  def removeTask(taskId: String): Folder = {
    this.copy(tasks = tasks.filterNot(_.id == taskId))
  }

  def getTask(taskId: String): Option[Task] = {
    tasks.find(_.id == taskId)
  }
}

class TaskRepoImpl extends StrictLogging {

  def addFolder(id: String, name: String): Boolean =
    folders.putIfAbsent(id, Folder(id, name, List())).isEmpty

  def getFolders: List[Folder] = folders.values.toList

  def removeFolder(folderId: String): Boolean = {
    folders.remove(folderId)
    true
  }

  def setStatus(taskId: String, folderId: String, status: Status.Value): Boolean = {
    require(folders.contains(folderId), "Wrong folderId")
    val oldFolder = folders(folderId)
    require(oldFolder.hasTask(taskId), "Wrong taskId")
    val oldTask = oldFolder.getTask(taskId).get
    folders.replace(folderId, oldFolder.removeTask(taskId).addTask(oldTask.setStatus(status)))
    true
  }

  def addTask(id: String, title: String, text: String, folderId: String): Boolean = {
    require(folders.contains(folderId), "Try to add task to missing folder")
    val oldFolder = folders(folderId)
    val task = Task(id, title, text, Status.OPEN)
    if (oldFolder.hasTask(id)) {
      false
    } else {
      folders.replace(folderId, oldFolder.addTask(task))
      true
    }
  }

  def removeTask(taskId: String, folderId: String): Boolean = {
    require(folders.contains(folderId), "Try to remove task from missing folder")
    val oldFolder = folders(folderId)
    require(oldFolder.hasTask(taskId), "Try to remove missing task")
    folders.replace(folderId, oldFolder.removeTask(taskId))
    true
  }

  private val folders = new ConcurrentHashMap[String, Folder]().asScala
  folders.put("1", Folder("1", "Work", List(
    Task("1", "TODO app", "Make awesome TODO app.", Status.FINISH),
    Task("2", "GraphQL support", "Add GraphQL support for TODO app.", Status.IN_PROGRESS),
    Task("3", "Share app", "Share app with world", Status.OPEN)
  )))
  folders.put("2", Folder("2", "Home", List(
    Task("1", "Relax", "Relax with pizza and PS4", Status.OPEN)
  )))

}
