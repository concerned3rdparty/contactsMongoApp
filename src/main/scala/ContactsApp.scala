/**
 * Created by asus kelebek on 1.8.2015.
 *
 *
 *
 */

import LoadXml.readXml
import Persistance.MongoXml
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.io.File




object ContactsApp extends App {


  println(args.length)
  println("load contacts from xml or find contacts by the name field \n ")

  if (args.length > 0) {
    args(0) match {
      case "load" =>  getListOfFiles(args(1))  //ex: load xmlFiles\c.xml  or load xmlFiles for more than one xml file
      case "find" =>  MongoXml.find(args(1)).name   // find name
      case "delete" => MongoXml.delete(args(1))  // delete name
      case "deleteall" => MongoXml.deleteAll()   // deleteall
      case "exit" =>  exit()                     // exit
      case _ => println("Wrong first argument")
    }
  } else {
    println("You need to enter 2 parameters, first one must be either load or find :possible operations\n load filename.xml and find Name")
  }

  def exit() = {
    println("see you")

  }

  def getListOfFiles(dir: String):Unit = {

    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList.map{file => Future(xmlCheck(dir + "\\" + file.getName ))}
    } else if(d.exists && !d.isDirectory){
        xmlCheck(dir)
      } else {
          List[File]()
    }

  }

  def xmlCheck(file :String): Unit ={

    file.split('.').drop(1).lastOption match {
      case Some("xml") => readXml(file)
      case _ => println("not an xml file name,  exiting from app ")

    }

  }


}





