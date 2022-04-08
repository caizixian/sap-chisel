package org.zcai.sap.common

import chisel3.util._

object Instructions {
  def LDA: BitPat = BitPat("b0000????")

  def ADD: BitPat = BitPat("b0001????")

  def SUB: BitPat = BitPat("b0010????")

  def OUT: BitPat = BitPat("b1110????")

  def HLT: BitPat = BitPat("b1111????")
}
