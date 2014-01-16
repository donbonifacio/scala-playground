package controllers

import scala.xml._
import java.net._
import scala.io.Source
import scala.xml._
import play.api._
import play.api.mvc._
import play.api.libs._
import play.api.libs.concurrent._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def simple_async = Action {
    val promiseOfInt = Akka.future { 1234 }
    Async {
      promiseOfInt.map(i => Ok("Got result: " + i))
    }
  }

  def getInvoiceIndirection(id: Long) = Action {
    val url = "http://scala-playground.heroku.com/invoices/" + id
    val promise = Akka.future {
      Source.fromURL(new URL(url)).mkString
    }
    Async {
      promise.map(xml => Ok(xml))
    }
  }

  def getInvoice(id: Long) = Action {
    Ok(
        <invoice>
          <id>{id}</id>
          <status>final</status>
          <sequence_number>draft</sequence_number>
          <date>19/10/2009</date>
          <due_date>19/10/2009</due_date>
          <reference>123456</reference>
          <observations>Computer Processed</observations>
          <permalink>http://link/to/document</permalink>
          <retention>5.0</retention>
          <client>
          <name>Bruce Norris</name>
          <code>Code</code>
          <email>foo@bar.com</email>
          <address>Badgad</address>
          <postal_code>120213920139</postal_code>
          <country>Germany</country>
          <fiscal_id>12</fiscal_id>
          <website>www.brucenorris.com</website>
          <phone>2313423424</phone>
          <fax>2313423425</fax>
          <observations>Computer Processed</observations>
          </client>
          <currency>Euro</currency>
          <items type="array">
          <item>
          <id>19</id>
          <name>Product 1</name>
          <description>Cleaning product</description>
          <unit_price>10.0</unit_price>
          <quantity>1.0</quantity>
          <unit>unit</unit>
          <tax>
          <name></name>
          <value>0.0</value>
          </tax>
          <discount>10.0</discount>
          </item>
          <item>
          <id>20</id>
          <name>Product 2</name>
          <description>Product for beauty</description>
          <unit_price>5.0</unit_price>
          <quantity>1.0</quantity>
          <unit>unit</unit>
          <tax>
          <id>19</id>
          <name>IVA20</name>
          <value>20.0</value>
          </tax>
          <discount>0.0</discount>
          </item>
          </items>
          <sum>15.0</sum>
          <discount>1.0</discount>
          <before_taxes>14.0</before_taxes>
          <taxes>1.0</taxes>
          <total>14.3</total>
          <mb_reference>
          <reference>123 456 789</reference>
          <entity>12345</entity>
          <value>5000</value>
          </mb_reference>
        </invoice>
      )
  }

}
