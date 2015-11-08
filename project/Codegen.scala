import slick.codegen.SourceCodeGenerator
import slick.profile.SqlProfile.ColumnOption
import slick.{ model => m }

/**
 * Implement this trait to get your own custom columns.
 */
trait HasColMap {
  val colMap: PartialFunction[m.Column, String] = Map()
}

trait OnlyTables extends HasColMap { this: SourceCodeGenerator =>
  override def packageCode(profile: String, pkg: String, container: String,
    parentType: Option[String]): String = {
    s"""
package ${pkg}
// AUTO-GENERATED Slick data model. DO NOT CHANGE.
trait ${container} extends DriverExtensions {
  lazy val profile = is.launaskil.slick.Driver
  import profile.api._
  ${indent(code)}
}
      """.trim()
  }
  override def Table = (t: m.Table) => new TableDef(t) {
    override def EntityType = new EntityType {
      override def doc = ""
      override def code = ""
    }
    override def Column = (col: m.Column) => new Column(col) {
      override def rawType = colMap.applyOrElse(model, (c: m.Column) => super.rawType)
    }
  }
}

trait OnlyClasses extends HasColMap { this: SourceCodeGenerator =>
  override def packageCode(profile: String, pkg: String, container: String, parentType: Option[String]): String = {
    s"""package ${pkg}
          |
          |$code
          """.stripMargin
  }
  override def code = tables.map(_.code.mkString("\n")).mkString("\n\n")
  override def Table = (t: m.Table) => new Table(t) {
    override def PlainSqlMapper = new PlainSqlMapper {
      override def doc = ""
      override def code = ""
    }
    override def TableClass = new TableClass {
      override def doc = ""
      override def code = ""
    }
    override def TableValue = new TableValue {
      override def doc = ""
      override def code = ""
    }
    override def Column = (col: m.Column) => new Column(col) {
      override def rawType = colMap.applyOrElse(model, (c: m.Column) => super.rawType)
    }
  }
}

trait PostgresColMap extends HasColMap {
  override val colMap: PartialFunction[m.Column, String] = {
    case col if col.tpe == "java.sql.Timestamp" => "is.launaskil.models.Timestamp"
    case col if col.tpe == "String" =>
      col.options.find(_.isInstanceOf[ColumnOption.SqlType])
        .map(_.asInstanceOf[ColumnOption.SqlType].typeName).map({
          case "_text" => "List[String]"
          case x => {
            "String"
          }
        }).getOrElse("String")
  }
}

class BaseCodegen(model: m.Model) extends SourceCodeGenerator(model) {
  override def tableName = (dbName: String) => super.tableName(dbName) + "Table"
}

class SharedCodegen(model: m.Model) extends BaseCodegen(model)
  with PostgresColMap
  with OnlyClasses

class ServerCodegen(model: m.Model) extends BaseCodegen(model)
  with PostgresColMap
  with OnlyTables {
}

object Codegen {
  lazy val sharedCodegen = (model: m.Model) => new SharedCodegen(model)
  lazy val serverCodegen = (model: m.Model) => new ServerCodegen(model)
}
