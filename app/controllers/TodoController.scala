package controllers
import services.{Todo, TodoService}
//コピペ
import javax.inject._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
/**
  * Created by user on 2017/08/01.
  */
class TodoController @Inject()(todoService: TodoService, mcc: MessagesControllerComponents) extends MessagesAbstractController(mcc) {
  def helloworld() = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok("Hello World")
  }

  def list() = Action { implicit request: MessagesRequest[AnyContent] =>
    val items: Seq[Todo] = todoService.list()
    Ok(views.html.list(items))
  }

  val todoForm: Form[String] = Form("name" -> nonEmptyText)

  def todoNew = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.createForm(todoForm))
  }

  def todoAdd() = Action { implicit request: MessagesRequest[AnyContent] =>
    val name: String = todoForm.bindFromRequest().get
    println(name)
    Ok("Save")
  }
}
