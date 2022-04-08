package org.zcai.sap.common

import java.io.{File, FileWriter, IOException}
import scala.io.Source

class Assembler {
  def assembleSampleProgram(buildDir: String): Unit = {
    val sb = new StringBuilder
    sb ++= "LDA 9\n"
    sb ++= "ADD 10\n"
    sb ++= "ADD 11\n"
    sb ++= "SUB 12\n"
    sb ++= "OUT\n"
    sb ++= "HLT\n"
    sb ++= "VAL 255\n" // 6
    sb ++= "VAL 255\n"
    sb ++= "VAL 255\n"
    sb ++= "VAL 1\n" // 9
    sb ++= "VAL 2\n" // 10
    sb ++= "VAL 3\n" // 11
    sb ++= "VAL 4\n" // 12
    sb ++= "VAL 255\n"
    sb ++= "VAL 255\n"
    sb ++= "VAL 255"

    try {
      val buildFolder = new File(buildDir)
      if (!buildFolder.exists()) {
        buildFolder.mkdir()
      }

      val sourceFile = new File(buildDir + "/sap1.src")

      val fwSrc = new FileWriter(sourceFile.getAbsoluteFile)
      fwSrc.write(sb.toString())
      fwSrc.close()

      val hex = new Assembler().assemble(buildDir + "/sap1.src")
      val hexFile = new File(buildDir + "/sap1.hex.txt")

      val fwHex = new FileWriter(hexFile.getAbsoluteFile)
      for (x <- hex) {
        fwHex.write(Integer.toHexString(x) + "\n")
      }
      fwHex.close()
    } catch {
      case e: IOException =>
        println("IO exception while assembling the program")
        System.exit(1)
    }
  }

  def assemble(prog: String): Array[Int] = {
    val source = Source.fromFile(prog)
    var program = List[Int]()

    for (line <- source.getLines()) {
      val tokens = line.trim.split(" ")
      val instr = tokens(0) match {
        case "LDA" => (Integer.parseInt(OpCode.LDA, 2) << 4) + tokens(1).toInt
        case "ADD" => (Integer.parseInt(OpCode.ADD, 2) << 4) + tokens(1).toInt
        case "SUB" => (Integer.parseInt(OpCode.SUB, 2) << 4) + tokens(1).toInt
        case "OUT" => Integer.parseInt(OpCode.OUT, 2) << 4
        case "HLT" => Integer.parseInt(OpCode.HLT, 2) << 4
        case "VAL" => tokens(1).toInt
      }

      instr match {
        case a: Int => program = a :: program
      }
    }
    val result = program.reverse.toArray
    result
  }
}
