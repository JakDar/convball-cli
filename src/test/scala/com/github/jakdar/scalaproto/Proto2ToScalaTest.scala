package com.github.jakdar.scalaproto

import org.scalatest.flatspec.AnyFlatSpec
import com.softwaremill.diffx.scalatest.DiffMatcher._
import org.scalatest.matchers.should.Matchers

class Proto2ToScalaTest extends AnyFlatSpec with Matchers {

  "proto to scala" should "work in basic case " in {

    val example  = """
            message Ala{
                required string ala =1 ;
                repeated int32 ola = 2;
                optional int64 ula = 3;
            }



            message Ola {
                optional string ala =1 ;
                repeated int32 ola = 2;
                optional bool ula = 3;
            }


           enum AlaMakota {
            ALA_MAKOTA = 1;
            OLA_MAPSA = 2;
           }
""".trim()
    val result   = Application.protoToScala(example)
    val expected = """|case class Ala (
                      |    ala: String,
                      |    ola: List[Int],
                      |    ula: Option[Long])
                      |
                      |case class Ola (
                      |    ala: Option[String],
                      |    ola: List[Int],
                      |    ula: Option[Boolean])
                      |
                      |sealed trait AlaMakota
                      |
                      |object AlaMakota {
                      |    case object AlaMakota
                      |    case object OlaMapsa
                      |}""".stripMargin

    result.trim() should matchTo(expected.trim())

  }

}