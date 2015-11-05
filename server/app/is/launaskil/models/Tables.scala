package is.launaskil.models
// AUTO-GENERATED Slick data model. DO NOT CHANGE.
trait Tables extends DriverExtensions {
  lazy val profile = com.github.olafurpg.slick.PostgresDriver
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = AppUserTable.schema ++ SchemaVersionTable.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema


  /** GetResult implicit for fetching AppUserRow objects using plain SQL queries */
  implicit def GetResultAppUserRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[List[String]]): GR[AppUserRow] = GR{
    prs => import prs._
    AppUserRow.tupled((<<[Int], <<?[String], <<[List[String]]))
  }
  /** Table description of table app_user. Objects of this class serve as prototypes for rows in queries. */
  class AppUserTable(_tableTag: Tag) extends Table[AppUserRow](_tableTag, "app_user") {
    def * = (id, username, profiles) <> (AppUserRow.tupled, AppUserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), username, Rep.Some(profiles)).shaped.<>({r=>import r._; _1.map(_=> AppUserRow.tupled((_1.get, _2, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username SqlType(varchar), Length(256,true), Default(None) */
    val username: Rep[Option[String]] = column[Option[String]]("username", O.Length(256,varying=true), O.Default(None))
    /** Database column profiles SqlType(_text), Length(2147483647,false) */
    val profiles: Rep[List[String]] = column[List[String]]("profiles", O.Length(2147483647,varying=false))
  }
  /** Collection-like TableQuery object for table AppUserTable */
  lazy val AppUserTable = new TableQuery(tag => new AppUserTable(tag))


  /** GetResult implicit for fetching SchemaVersionRow objects using plain SQL queries */
  implicit def GetResultSchemaVersionRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[Epoch], e4: GR[Boolean]): GR[SchemaVersionRow] = GR{
    prs => import prs._
    SchemaVersionRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[String], <<[String], <<?[Int], <<[String], <<[Epoch], <<[Int], <<[Boolean]))
  }
  /** Table description of table schema_version. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class SchemaVersionTable(_tableTag: Tag) extends Table[SchemaVersionRow](_tableTag, "schema_version") {
    def * = (versionRank, installedRank, version, description, `type`, script, checksum, installedBy, installedOn, executionTime, success) <> (SchemaVersionRow.tupled, SchemaVersionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(versionRank), Rep.Some(installedRank), Rep.Some(version), Rep.Some(description), Rep.Some(`type`), Rep.Some(script), checksum, Rep.Some(installedBy), Rep.Some(installedOn), Rep.Some(executionTime), Rep.Some(success)).shaped.<>({r=>import r._; _1.map(_=> SchemaVersionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get, _9.get, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column version_rank SqlType(int4) */
    val versionRank: Rep[Int] = column[Int]("version_rank")
    /** Database column installed_rank SqlType(int4) */
    val installedRank: Rep[Int] = column[Int]("installed_rank")
    /** Database column version SqlType(varchar), PrimaryKey, Length(50,true) */
    val version: Rep[String] = column[String]("version", O.PrimaryKey, O.Length(50,varying=true))
    /** Database column description SqlType(varchar), Length(200,true) */
    val description: Rep[String] = column[String]("description", O.Length(200,varying=true))
    /** Database column type SqlType(varchar), Length(20,true)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[String] = column[String]("type", O.Length(20,varying=true))
    /** Database column script SqlType(varchar), Length(1000,true) */
    val script: Rep[String] = column[String]("script", O.Length(1000,varying=true))
    /** Database column checksum SqlType(int4), Default(None) */
    val checksum: Rep[Option[Int]] = column[Option[Int]]("checksum", O.Default(None))
    /** Database column installed_by SqlType(varchar), Length(100,true) */
    val installedBy: Rep[String] = column[String]("installed_by", O.Length(100,varying=true))
    /** Database column installed_on SqlType(timestamp) */
    val installedOn: Rep[Epoch] = column[Epoch]("installed_on")
    /** Database column execution_time SqlType(int4) */
    val executionTime: Rep[Int] = column[Int]("execution_time")
    /** Database column success SqlType(bool) */
    val success: Rep[Boolean] = column[Boolean]("success")

    /** Index over (installedRank) (database name schema_version_ir_idx) */
    val index1 = index("schema_version_ir_idx", installedRank)
    /** Index over (success) (database name schema_version_s_idx) */
    val index2 = index("schema_version_s_idx", success)
    /** Index over (versionRank) (database name schema_version_vr_idx) */
    val index3 = index("schema_version_vr_idx", versionRank)
  }
  /** Collection-like TableQuery object for table SchemaVersionTable */
  lazy val SchemaVersionTable = new TableQuery(tag => new SchemaVersionTable(tag))
}
