package models

import play.api.data.Form
import play.api.data.Forms._
import slick.driver.MySQLDriver.api._


case class Group(id: Long, name: String)
case class GroupFormData(name: String)

object GroupForm {
  val form = Form(
    mapping(
      "name" -> nonEmptyText,
    )(GroupFormData.apply)(GroupFormData.unapply)
  )
}

class GroupTableDef(tag: Tag) extends Table[Group](tag, "groups") {

  def id = column[Long]("idgroups", O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")
  override def * =
    (id, name) <>(Group.tupled, Group.unapply)
}
