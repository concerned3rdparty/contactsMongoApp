# contactsXmlMongo
import xml contact elements to mongodb 

NOTE: CLICK ON README.TXT TO READ IN A BETTER FORMAT 

I've used casbah instead of reactiveMongo with iteratees and enumerators , as it does not deal with realtime data streams.

libraryDependencies ++= Seq(
  "org.mongodb" %% "casbah" % "2.8.2",
  "org.specs2" %% "specs2" % "2.3.12" % Test,
  "org.mockito" % "mockito-core" % "1.9.5" % Test
)

I've used intellij and its sbt plugin, configured to use scala 2_10 with jdk1.7. Import project as an sbt project and it will
be ready to run.

It supports upsert, find, delete, deleteall,exit operations. Persistence operations are tested with specs2 which uses 
mockito for mock objects.

some println statements are used just to track flow of the state from console output.

Usage of console commands from the root dir of the project :

Note: A mongodb instance has to be already running at "localhost", 27017 before running the app. I hardcoded this values in the  mongoConnector object,there you can change the line "val mongoClient = MongoClient("localhost", 27017)" accordingly.

sbt "run load xmlFile\c.cml"  // load an xml file to a mongodb collection
sbt "run load xmlFile"        // load all xml files under xmlFile directory to to a mongodb collection
sbt "run find ahmet"          // searchs for a contact with the name field ahmet in a mongodb collection
sbt "run delete ahmet"        // delete a contact with the name field ahmet from a mongodb collection
sbt "run deleteall"          // delete all contacts from collection from a mongodb collection

you can also use intellij's edit configuration menu to input arguments like "load xmlFile\c.xml" and then run the app.

to run tests from the root dir of the project : sbt test

persistance tests are handled but for now it does not test loadXml operations.

Note:

xml documents has to be in the format below since i did not have much time to test various input types. Each line has one contact 
element.

<contact><name>Gündüzbey</name><lastName>Güngörbey</lastName><phone>+90 333 7223337</phone></contact>
<contact><name>Münir</name><lastName>Mürit</lastName><phone>+90 333 9968694</phone></contact>
<contact><name>Rabiye</name><lastName>Radim</lastName><phone>+90 333 7661132</phone></contact>


do not use a file like this since first two lines is not in the format <elementname>....</elementname>. 

<?xml version="1.0" encoding="UTF-8" ?>
<contacts>
<contact><name>Yesügey</name><lastName>Yeşil</lastName><phone>+90 333 9695395</phone></contact>
<contact><name>Firdest</name><lastName>Firdevis</lastName><phone>+90 333 4234638</phone></contact>
 

