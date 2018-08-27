package controllers

import javax.inject._
import models.{Group, Student, StudentForm, StudentFormData}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import services.StudentService

import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder, executionContext: ExecutionContext)
  extends AbstractController(cc) {

  implicit val stud = Json.writes[Student]
  implicit val group = Json.writes[Group]

  def index =  Action.async { implicit request =>
    StudentService.listAllUsers.map { users =>
      Ok(Json.toJson(users))
    }
  }

  val form = Form(
    mapping(
      "idstudent" -> longNumber,
      "name" -> nonEmptyText,
      "group" -> longNumber,
    )(Student.apply)(Student.unapply)
  )

  def add = Action.async {implicit request =>
    val data: Student = form.bindFromRequest.get
    StudentService.addUser(data).map(_ => Redirect(routes.HomeController.index()))
  }

  def update = Action.async { implicit request =>
    val data: Student = form.bindFromRequest.get
    StudentService.updateUser(data).map(_ => Redirect(routes.HomeController.index()))
  }

  def remove(id: String) = Action.async {implicit request =>
    StudentService.deleteUser(Integer.parseInt(id)).map(_ => Redirect(routes.HomeController.index()))
  }

  def getOne(id: String) = Action.async {implicit request =>
    StudentService.getUser(Integer.parseInt(id)).map(user => Ok(Json.toJson(user)))
  }

}
