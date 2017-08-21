package services
import org.joda.time.{DateTime, _}
import scalikejdbc._
import java.text.SimpleDateFormat
import javax.inject.Inject

import play.api.db.DBApi
import java.util.Date
import play.Logger

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

  def list(): Seq[Todo] = {
    val todos: List[Todo] = sql"select * from todo".map(rs => Todo(rs)).list.apply()
    todos
  }

  def insert(todo: Todo) = {
    DB localTx { implicit session =>
      val insertSql = SQL("insert into todo (id , name, created) values ((select next value for todo_seq) , ?, ?)")
      val createdAt = DateTime.now
      insertSql.bind(todo.name, createdAt).update.apply()
    }
  }

  def findById(id: Long): Option[Todo] = {
    DB localTx { implicit session =>
      SQL("select * from todo where id = {id}").bindByName('id -> id).map(rs => Todo(rs)).first.apply()
    }
  }

  def update(id: Long, todo: Todo) = {
    DB localTx { implicit session =>
      val insertSql = SQL(
        """
        update todo set
          name = {name},
          duedate = {duedate}
         where id = {id}
        """
      ).bindByName(
        'name -> todo.name,
        'duedate -> todo.dueDate ,
        'id -> id
      ).update.apply()
    }
  }
  //
  def delete(id: Long) = {
    DB localTx { implicit session =>
      SQL("delete from todo where id = {id}").bindByName('id -> id).update.apply()
    }
  }
}