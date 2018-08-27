package services

import models.{Student, StudentFormData}
import repositories.StudentRepo

import scala.concurrent.Future

object StudentService {

  def addUser(user: Student): Future[String] = {
    StudentRepo.add(user)
  }

  def updateUser(user: Student): Future[String] = {
    StudentRepo.update(user)
  }

  def deleteUser(id: Long): Future[Int] = {
    StudentRepo.delete(id)
  }

  def getUser(id: Long): Future[Option[Student]] = {
    StudentRepo.get(id)
  }

  def listAllUsers: Future[Seq[Student]] = {
    StudentRepo.listAll
  }
}
