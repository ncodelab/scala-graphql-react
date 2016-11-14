package com.ncodelab.data

import java.util.concurrent.ConcurrentHashMap

import com.typesafe.scalalogging.StrictLogging
import sangria.macros.derive.{GraphQLDescription, GraphQLName}

import scala.collection.JavaConverters._


object Status extends Enumeration {
  val OPEN, FINISH = Value
}

@GraphQLName("Task")
@GraphQLDescription("The task")
case class Task(
                 id: Int,
                 title: String,
                 text: String,
                 status: Status.Value
               ) {
  def setStatus(status: Status.Value): Task = {
    this.copy(status = status)
  }

  def setTitle(title: String): Task = {
    this.copy(title = title)
  }

  def setText(text: String): Task = {
    this.copy(text = text)
  }
}

@GraphQLName("Folder")
@GraphQLDescription("Folder for tasks")
case class Folder(
                   id: Int,
                   name: String,
                   tasks: List[Task]
                 ) {
  def hasTask(taskId: Int): Boolean = {
    tasks.map(_.id).contains(taskId)
  }

  def addTask(task: Task): Folder = {
    this.copy(tasks = tasks :+ task)
  }

  def removeTask(taskId: Int): Folder = {
    this.copy(tasks = tasks.filterNot(_.id == taskId))
  }

  def getTask(taskId: Int): Option[Task] = {
    tasks.find(_.id == taskId)
  }

  def setName(name: String): Folder = {
    this.copy(name = name)
  }
}

class TaskRepoImpl extends StrictLogging {

  var lastTaskId = 0
  var lastFolderId = 0

  private def replaceTask(taskId: Int, folderId: Int)(mutator: (Task) => Task): Folder = {
    require(folders.contains(folderId), "Wrong folderId")
    val oldFolder = folders(folderId)
    require(oldFolder.hasTask(taskId), "Wrong taskId")
    val oldTask = oldFolder.getTask(taskId).get
    val newFolder = oldFolder.removeTask(taskId).addTask(mutator(oldTask))
    folders.replace(folderId, newFolder)
    newFolder
  }

  def addFolder(name: String): Folder = {
    lastFolderId += 1
    val newFolder = Folder(lastFolderId, name, List())
    folders.putIfAbsent(newFolder.id, newFolder)
    newFolder
  }

  def getFolders: List[Folder] = folders.values.toList

  def removeFolder(folderId: Int): Boolean = {
    folders.remove(folderId)
    true
  }

  def setTaskText(taskId: Int, folderId: Int, text: String): Folder = {
    replaceTask(taskId, folderId)(_.setText(text))
  }

  def setTaskTitle(taskId: Int, folderId: Int, title: String): Folder = {
    replaceTask(taskId, folderId)(_.setTitle(title))
  }

  def setFolderName(folderId: Int, name: String): Folder = {
    require(folders.contains(folderId), "Missing folder")
    val oldFolder = folders(folderId)
    val newFolder = oldFolder.setName(name)
    folders.replace(folderId, newFolder)
    newFolder
  }

  def setStatus(taskId: Int, folderId: Int, status: Status.Value): Folder = {
    replaceTask(taskId, folderId)(_.setStatus(status))
  }

  def getFolder(folderId: Int): Folder = {
    require(folders.contains(folderId), "Missing folder")
    folders(folderId)
  }

  def addTask(folderId: Int, title: String, text: String): Folder = {
    require(folders.contains(folderId), "Try to add task to missing folder")
    val oldFolder = folders(folderId)
    lastTaskId += 1
    val task = Task(lastTaskId, title, text, Status.OPEN)
    val newFolder = oldFolder.addTask(task)
    folders.replace(folderId, newFolder)
    newFolder
  }

  def removeTask(taskId: Int, folderId: Int): Folder = {
    require(folders.contains(folderId), "Try to remove task from missing folder")
    val oldFolder = folders(folderId)
    require(oldFolder.hasTask(taskId), "Try to remove missing task")
    val newFolder =oldFolder.removeTask(taskId)
    folders.replace(folderId, newFolder)
    newFolder
  }

  private val folders = new ConcurrentHashMap[Int, Folder]().asScala

  addFolder("Work")

  addTask(lastFolderId, "TODO app", "Make awesome TODO app.")
  setStatus(lastTaskId, lastFolderId, Status.FINISH)
  addTask(lastFolderId, "GraphQL support", "Add GraphQL support for TODO app.")
  addTask(lastFolderId, "Share app", "Share app with world")

  addFolder("Home")
  addTask(lastFolderId, "Relax", "Relax with pizza and PS4")
}
