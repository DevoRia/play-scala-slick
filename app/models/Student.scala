package models

import play.api.data.Form
import play.api.data.Forms._
import play.shaded.ahc.io.netty.util.Mapping
import slick.driver.MySQLDriver.api._

case class Student(id: Long, name: String, group: Long)

case class StudentFormData(name: String, group: Long)


object StudentForm {

  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "group" -> longNumber,
    )(StudentFormData.apply)(StudentFormData.unapply)
  )
}

class StudentTableDef(tag: Tag) extends Table[Student](tag, "student") {

  def id = column[Long]("idstudent", O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")
  def group = column[Long]("group")
  def groupKey = foreignKey("groups", group, TableQuery[GroupTableDef])(_.id)
  override def * = (id, name, group) <>(Student.tupled, Student.unapply)

}

