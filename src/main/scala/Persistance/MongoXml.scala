package Persistance

import com.mongodb.casbah.Imports._
import Model.Contact

trait Persistance {

  def find(name: String ): Contact /* List[Contact]*/
  def save(contact: Contact ): Int /*Try[Boolean]*/
  def delete(name : String ): Int/*Try[Boolean]*/
  def deleteAll(): Int
}

object MongoXml extends Persistance{

  override def find(name : String): Contact  ={

    println("searching for the records with the name field: " + name )
    println("Number of contact records in collection :  " + MongoConnector.coll.count())
    val query = MongoDBObject("name" -> name)

    val result = MongoConnector.coll.findOne( query )

    result match {
      case Some(x) => {
        println("Found a contact! \n" + x.get("name") )
        println("contact info of the person found " + x.get("name").toString, x.get("lastName").toString, x.get("phone").toString )
        Contact(x("name").toString, x("lastName").toString,  x.getAs[List[String]]("phone").getOrElse(Set()).toSet)
      }
      case None => Contact("","",Set(""))
    }


    }


  override def save(c : Contact ):Int = {
    println("number of documents in the collection :  " + MongoConnector.coll.count() )

    val query = MongoDBObject("name" -> c.name ,
      "lastName" -> c.lastName
    )
    val update = $addToSet("phone" -> c.phone.head)
    val result = MongoConnector.coll.update( query, update, upsert=true )

    println( s" ${result.getN} contact upserted: " )

    result.getN
  }

  override def delete(name : String): Int={

    println(s"There are ${ MongoConnector.coll.count()}  records in the collection before delete operation ")

    val query = MongoDBObject("name" -> name)
    val result = MongoConnector.coll.remove( query )

    result.getN match {
      case x if x > 0 =>  println( s" 1 contact with the name ${name}  deleted"  )
      case _ => println("No such record to delete" )
    }

    println( "number of documents left in the collection :  " + MongoConnector.coll.count() )
    result.getN
  }

  override def deleteAll(): Int ={

    println("deleting all records from mongo collection")
    MongoConnector.coll.drop()
    println("number of documents left in the collection :  " + MongoConnector.coll.count() )
    MongoConnector.coll.count()
  }


}
