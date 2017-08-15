package services

import java.text.SimpleDateFormat
import javax.inject.Inject

import anorm.SqlParser._
import anorm._
import play.api.db.DBApi
import java.util.Date

import play.api.Logger

import scala.language.postfixOps

case class Todo(id:Option[Long],
                name: String,
                created: Option[Date],
                dueDate: Option[Date],
                startDate: Option[Date]
               )

@javax.inject.Singleton
class TodoService @Inject() (dbapi: DBApi) {

  private val db = dbapi.database("default")

  val simple = {
    get[Option[Long]]("todo.id") ~
      get[String]("todo.name") ~
      get[Option[Date]]("todo.created") ~
      get[Option[Date]]("todo.startdate") ~
      get[Option[Date]]("todo.duedate") map {
      case id~name~created~startdate~duedate => Todo(id, name,created,startdate,duedate)
    }
  }

  def list(): Seq[Todo] = {

    db.withConnection { implicit connection =>

      SQL(
        """
          select * from todo
        """
      ).as(simple *)

      val rowParser = RowParser[Todo]{
        case Row(id:Option[Long],
        name: String,
        created: Option[Date],
        dueDate: Option[Date],
        startDate: Option[Date]) =>  Success(Todo(id,name,created,dueDate,startDate))
      }
      
      SQL(
        """
          select * from todo
        """
      ).as(rowParser *)


    }

  }

  def insert(todo: Todo) = {
    db.withConnection { implicit connection =>
      SQL(
        """
        insert into todo values ((select next value for todo_seq), {name},{created},{startdate},{duedate})
        """
      ).on(
        'name -> todo.name,
        'created -> todo.created,
        'startdate -> todo.startDate,
        'duedate -> todo.dueDate
      ).executeUpdate()
    }
  }

  def findById(id: Long): Option[Todo] = {
    db.withConnection { implicit connection =>
      SQL("select * from todo where id = {id}").on('id -> id).as(simple.singleOpt)
    }
  }

  def update(id: Long, todo: Todo) = {
    val d = todo.dueDate.get
    val s = new SimpleDateFormat("yyyy-MM-dd").format(d)
    db.withConnection { implicit connection =>
      val sql = SQL(
        """
          update todo set
            name = {name},
            duedate = {duedate}
          where id = {id}
        """
      ).on(
        'id -> id,
        'name -> todo.name,
        'duedate -> s
      )
      val count = sql.executeUpdate()
      count
    }
  }

  def delete(id: Long) = {
    db.withConnection { implicit connection =>
      SQL("delete from todo where id = {id}").on('id -> id).executeUpdate()
    }
  }
}