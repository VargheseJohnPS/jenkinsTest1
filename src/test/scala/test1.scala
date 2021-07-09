import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.util.Random

class test1 extends Simulation {

  val httpProtocol = http
    .baseUrl("http://demostore.gatling.io")
  //.acceptHeader("*/*")
  //.acceptEncodingHeader("gzip, deflate")
  //.acceptLanguageHeader("en-US,en;q=0.5")
  //.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:87.0) Gecko/20100101 Firefox/87.0")

  //	val headers_0 = Map(
  //		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
  //		"Cache-Control" -> "max-age=0",
  //		"If-Modified-Since" -> "Tue, 15 Jun 2021 11:08:45 GMT",
  //		"If-None-Match" -> """"f84c0952b7b6757b738d6eceec859c73"""",
  //		"Upgrade-Insecure-Requests" -> "1")


  val Demo2 = Iterator.continually(Map("email" -> (Random. alphanumeric.filter(_.isLetterOrDigit).take (8).mkString.toLowerCase())))


  val scn = scenario("DemoStoreGatling")
    .exec(http("Homepage")
      .get("/")
      .check(css("#_csrf", "content").saveAs("csrfValue")))
    .exec(http("loginGet")
      .get("/login")
      .check(regex("_csrf\" value=\"([^\"]*)").saveAs("csrf")))
    .exec(http("loginPost")
      .post("/login")
      .formParam("_csrf", "${csrf}")
      .formParam("username", "user1")
      .formParam("password", "pass"))



  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}