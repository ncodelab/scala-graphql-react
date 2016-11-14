package com.ncodelab.data

import org.scalatest.{Matchers, WordSpec}

class TaskRepoSpec extends WordSpec with Matchers {

  "TaskRepo" when {
    "Working with Folders" should {
      "Return folders" in {
        val taskRepo = new TaskRepoImpl
        val folders = taskRepo.getFolders
        folders.length shouldBe 2
        folders.map(_.name) shouldBe List("Work", "Home")
      }

      "Remove folder" in {
        val taskRepo = new TaskRepoImpl
        taskRepo.removeFolder(1)
        val folders = taskRepo.getFolders
        folders.length shouldBe 1
        folders.map(_.name) shouldBe List("Home")
      }

      "Add folder correctly" in {
        val taskRepo = new TaskRepoImpl
        val testFolder = taskRepo.addFolder("TestFolder")
        val folders = taskRepo.getFolders
        folders.length shouldBe 3
        folders.contains(testFolder) shouldBe true
      }
    }
    "Working with Tasks" should {
      "Add task" in {
        val taskRepo = new TaskRepoImpl
        taskRepo.addTask(2, "Title", "Text")
        val folders = taskRepo.getFolders
        folders.filter(_.id == 2).head.tasks.find(_.title == "Title").isDefined shouldBe true
      }

      "Remove task" in {
        val taskRepo = new TaskRepoImpl
        taskRepo.removeTask(4, 2)
        val folders = taskRepo.getFolders
        folders.filter(_.id == 2).head.tasks.length shouldBe 0
      }

      "Set status" in {
        val taskRepo = new TaskRepoImpl
        taskRepo.setStatus(1, 1, Status.OPEN)
        val folders = taskRepo.getFolders
        folders.find(_.id == 1).get.getTask(1).get.status shouldBe Status.OPEN

      }
    }
  }

}
