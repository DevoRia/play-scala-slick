package repositories

import models.{Student, StudentTableDef}
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

object StudentRepo {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val users = TableQuery[StudentTableDef]

  def add(user: Student): Future[String] = {
    dbConfig.db.run(users += user).map(res => "User successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def update(user: Student): Future[String] = {
    dbConfig.db.run(users.filter(_.id === user.id).update(user)).map(res => "User updated").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(users.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[Student]] = {
    dbConfig.db.run(users.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[Student]] = {
    dbConfig.db.run(users.result)
  }
}
