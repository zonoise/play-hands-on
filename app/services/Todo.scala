package services
import org.joda.time.{DateTime, _}
import scalikejdbc._
import java.text.SimpleDateFormat
import javax.inject.Inject

import play.api.db.DBApi
import java.util.Date

import play.api.Logger
import scalikejdbc.sqls
import scala.language.postfixOps

case class Todo(id:Option[Long], name: String, created: Option[DateTime], dueDate: Option[DateTime],
                startDate: Option[DateTime])

object Todo extends SQLSyntaxSupport[Todo] {
  override val tableName = "todo"
  def apply(rs: WrappedResultSet) = new Todo(
    rs.longOpt("id"),
    rs.string("name"),
    rs.jodaDateTimeOpt("created"),
    rs.jodaDateTimeOpt("duedate"),
    rs.jodaDateTimeOpt("startdate")
  )
}

@javax.inject.Singleton
class TodoService @Inject() (dbapi: DBApi) {
  implicit val session = AutoSession

  //
//  private val db = dbapi.database("default")
//
  def list(): Seq[Todo] = {
    val members: List[Todo] = sql"select * from todo".map(rs => Todo(rs)).list.apply()

  //
//    db.withConnection { implicit connection =>
//
//      SQL(
//        """
//          select * from todo
//        """
//      ).as(simple *)
//
//      val rowParser = RowParser[Todo]{
//        case Row(id:Option[Long],
//        name: String,
//        created: Option[Date],
//        dueDate: Option[Date],
//        startDate: Option[Date]) =>  Success(Todo(id,name,created,dueDate,startDate))
//      }
//
//      SQL(
//        """
//          select * from todo
//        """
//      ).as(rowParser *)
//
//
//    }

      members
  }
//
  def insert(todo: Todo) = {
//    db.withConnection { implicit connection =>
//      SQL(
//        """
//        insert into todo values ((select next value for todo_seq), {name},{created},{startdate},{duedate})
//        """
//      ).on(
//        'name -> todo.name,
//        'created -> todo.created,
//        'startdate -> todo.startDate,
//        'duedate -> todo.dueDate
//      ).executeUpdate()
//    }
    1
  }
//
  def findById(id: Long): Option[Todo] = {
//    db.withConnection { implicit connection =>
//      SQL("select * from todo where id = {id}").on('id -> id).as(simple.singleOpt)
//    }
    Option(
      Todo(Option(1L),"a",Option(new DateTime()),Option(new DateTime()),Option(new DateTime()))
    )
  }
//
  def update(id: Long, todo: Todo) = {
//    val d = todo.dueDate.get
//    val s = new SimpleDateFormat("yyyy-MM-dd").format(d)
//    db.withConnection { implicit connection =>
//      val sql = SQL(
//        """
//          update todo set
//            name = {name},
//            duedate = {duedate}
//          where id = {id}
//        """
//      ).on(
//        'id -> id,
//        'name -> todo.name,
//        'duedate -> s
//      )
//      val count = sql.executeUpdate()
//      count
//    }
      1
  }
//
  def delete(id: Long) = {
//    db.withConnection { implicit connection =>
//      SQL("delete from todo where id = {id}").on('id -> id).executeUpdate()
//    }
    1
  }
}