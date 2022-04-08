package org.zcai.sap.common

import scala.io.Source

class Assembler{
  def assemble(prog: String): Array[Byte] = {
    val source = Source.fromFile(prog)
    var program = List[Byte]()

    for (line <- source.getLines()) {
      val tokens = line.trim.split(" ")
      val instr = tokens(0) match {
        case "LDA" => (Integer.parseInt(OpCode.LDA, 2) << 4) + tokens(1).toInt
        case "ADD" => (Integer.parseInt(OpCode.ADD, 2) << 4) + tokens(1).toInt
        case "SUB" => (Integer.parseInt(OpCode.SUB, 2) << 4) + tokens(1).toInt
        case "OUT" => (Integer.parseInt(OpCode.OUT, 2) << 4)
        case "HLT" => (Integer.parseInt(OpCode.HLT, 2) << 4)
        case "VAL" => tokens(1).toInt
      }

      instr match {
        case (a: Int) => {
          program = a.toByte :: program
        }
        case _ =>
      }
    }
    val result = program.reverse.toArray
    result
  }
}
