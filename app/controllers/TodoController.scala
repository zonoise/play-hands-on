package controllers

//コピペ
import javax.inject._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
/**
  * Created by user on 2017/08/01.
  */
class TodoController @Inject()(mcc: MessagesControllerComponents ) extends MessagesAbstractController(mcc){
  def helloworld() = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok("Hello World")
  }

  def list() = Action { implicit request: MessagesRequest[AnyContent] =>
    val message: String = "ここにリストを表示"
    Ok(views.html.list(message))
  }
}
