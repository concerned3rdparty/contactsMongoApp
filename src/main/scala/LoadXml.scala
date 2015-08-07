import Persistance.{MongoXml, MongoConnector}
import com.mongodb.casbah.Imports._
import scala.io.Source
import scala.util.{Failure, Success, Try}
import scala.xml.XML
import Model.Contact

object LoadXml {

  def readXml(file: String): Unit = {
    println("reading   " + file)
    Try(Source.fromFile(file ).getLines) match {

      case Success(lines) => lines.foreach(contactXml => {

        val lineXml = XML.loadString(contactXml)
        val a = fromXml(lineXml)

        println(a.name)
        println(a.lastName)
        println(a.phone)

        val query = MongoDBObject(
          "name" -> a.name,
          "lastName" -> a.lastName
        )

        val result = MongoXml.save(a)
        println("count= " + MongoConnector.coll.count())
        println(result)

      }
      )
      case Failure(f) => println(f)

    }

  }

  def fromXml(node: scala.xml.Node): Contact ={

    val name = (node \\ "name").text
    val lastName = (node \\ "lastName").text
    val phone = (node \\ "phone").text
    Contact(name,lastName,Set(phone))

  }

}