package Persistance


import org.specs2.mutable._
import org.specs2.specification.{Step, Fragments}
import Model.ModelMocks

trait BeforeAllAfterAll extends Specification {
  override def map(fragments: => Fragments) =
    Step(before) ^ fragments ^ Step(after)

  def before()

  def after()
  }

class TestMongoXml extends Specification with ModelMocks with BeforeAfter{

  def before() = println("Doing setup \n")

  def after(){

    println("\nDone. Cleanup")
    MongoXml.deleteAll()            // as the last test is deleteall, this line is not necessary for this situation,
                                    // but it may be as tests grow
  }


  "MongoXml members" in {
    val name = "aaa"
    val bname = "rrr"
    val lastName = "bbbb"
    val phone = "3333"
    val phone1 = "1111"
    val c = mockContact(name, lastName, Set(phone) )
    val d = mockContact(name, lastName, Set(phone1) )
    val e = mockContact(bname, lastName, Set(phone) )

    "save(contact)" should {

      "insert new contact " in {

        val res = MongoXml.save(c)
        val res1 = MongoXml.save(d)
        val res2 = MongoXml.save(e)
        val res3 = MongoXml.find(name)

        "succeeds" in {
          res must beGreaterThan(0)
          res1 must beGreaterThan(0)
          res2 must beGreaterThan(0)
          println(" records in the collection : " + MongoConnector.coll.count())
          MongoConnector.coll.count() == 2

          val phoneFromDB = res3.phone
          println("phoneFromDb:       " + phoneFromDB)
          println("phone :  " + phone + "phone1 :  "  + phone1 )

          Set(phone) ++ Set(phone1) must beEqualTo(phoneFromDB).ignoreSpace



        }
      }
    }

    "find(name)" should {

      "find a contact with given name " in {

        val res = MongoXml.find(name)

        "succeeds " in {
          res must not be null

          "if returned name is same " in {
            name must beEqualTo(res.name).ignoreSpace.ignoreCase

          }

        }
      }
    }

    "delete(name)" should {

      "delete the contact with given name" in{

         val res = MongoXml.delete(name)

        "succeeds" in {

          res > 0

        }
      }
    }


    }

    "deleteall()"  should {

      "delete all the documents in the collection " in {

        val res = MongoXml.deleteAll()

        "succeeds"  in {
          res == 0
        }
      }


    }


}




