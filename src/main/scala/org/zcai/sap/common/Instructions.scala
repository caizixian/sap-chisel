package org.zcai.sap.common

import chisel3._
import chisel3.util._

object OpCode {
  val LDA = "0000"
  val ADD = "0001"
  val SUB = "0010"
  val OUT = "1110"
  val HLT = "1111"
}

object Instructions {
  def LDA: BitPat = BitPat("b" + OpCode.LDA + "????")

  def ADD: BitPat = BitPat("b" + OpCode.ADD + "????")

  def SUB: BitPat = BitPat("b" + OpCode.SUB + "????")

  def OUT: BitPat = BitPat("b" + OpCode.OUT + "????")

  def HLT: BitPat = BitPat("b" + OpCode.HLT + "????")
}
