package controllers
import java.util.Date

import org.joda.time.DateTime
import play.Logger
import services.{Todo, TodoService}
import views.html.defaultpages.todo
//コピペ
import javax.inject._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import play.api.data.{Form, Forms}

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

  val todoForm2: Form[(String,Option[Date])] = Form(
    tuple(
      "name" -> text,
      "duedate"  -> optional(date)

    )
  )
  def todoNew = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.createForm(todoForm))
  }

  def todoAdd() = Action { implicit request: MessagesRequest[AnyContent] =>
    val name: String = todoForm.bindFromRequest().get
    todoService.insert(
      Todo(id = None, name,Some(new DateTime()),Some( new DateTime() ),Some(new DateTime()))
    )
    Redirect(routes.TodoController.list())
  }

  def todoEdit(todoId: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    todoService.findById(todoId).map { todo =>

      val form = todoForm2.fill((todo.name,Some(new Date())))
      Ok(views.html.editForm(todoId,form))
    }.getOrElse(NotFound)
  }

  def todoUpdate(todoId: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    val (name,duedate) = todoForm2.bindFromRequest().get

    todoService.update(
      todoId,
      Todo(Some(todoId), name,Some(new DateTime()),Some( new DateTime() ),Some(new DateTime()))
    )
    Redirect(routes.TodoController.list())
  }

  def todoDelete(todoId: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    todoService.delete(todoId)
    Redirect(routes.TodoController.list())
  }
}
