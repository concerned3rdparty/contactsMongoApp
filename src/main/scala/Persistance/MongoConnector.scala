package Persistance

import com.mongodb.casbah.MongoClient


object MongoConnector{

  val mongoClient = MongoClient("localhost", 27017)
  val db = mongoClient("contacts")
  val coll = db("contacts")

}