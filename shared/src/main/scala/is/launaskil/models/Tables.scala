package is.launaskil.models

/** Entity class storing rows of table AppUserTable
 *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
 *  @param createdAt Database column created_at SqlType(timestamp), Default(None)
 *  @param username Database column username SqlType(varchar), Length(256,true), Default(None)
 *  @param profiles Database column profiles SqlType(_text), Length(2147483647,false) */
case class AppUserRow(id: Int, createdAt: Option[is.launaskil.models.Timestamp] = None, username: Option[String] = None, profiles: List[String])




/** Entity class storing rows of table SchemaVersionTable
 *  @param versionRank Database column version_rank SqlType(int4)
 *  @param installedRank Database column installed_rank SqlType(int4)
 *  @param version Database column version SqlType(varchar), PrimaryKey, Length(50,true)
 *  @param description Database column description SqlType(varchar), Length(200,true)
 *  @param `type` Database column type SqlType(varchar), Length(20,true)
 *  @param script Database column script SqlType(varchar), Length(1000,true)
 *  @param checksum Database column checksum SqlType(int4), Default(None)
 *  @param installedBy Database column installed_by SqlType(varchar), Length(100,true)
 *  @param installedOn Database column installed_on SqlType(timestamp)
 *  @param executionTime Database column execution_time SqlType(int4)
 *  @param success Database column success SqlType(bool) */
case class SchemaVersionRow(versionRank: Int, installedRank: Int, version: String, description: String, `type`: String, script: String, checksum: Option[Int] = None, installedBy: String, installedOn: is.launaskil.models.Timestamp, executionTime: Int, success: Boolean)




