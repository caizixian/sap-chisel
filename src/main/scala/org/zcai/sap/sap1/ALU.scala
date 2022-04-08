package org.zcai.sap.sap1

import chisel3._
import chisel3.experimental.ChiselEnum
import chisel3.util._

object ALUOp extends ChiselEnum {
  val add, sub = Value
}

class ALUBundle(width: Int) extends Bundle {
  val op: ALUOp.Type = Input(ALUOp())
  val x: UInt = Input(UInt(width.W))
  val y: UInt = Input(UInt(width.W))
  val out: UInt = Output(UInt(width.W))
}

class ALU(width: Int) extends Module {
  val io: ALUBundle = IO(new ALUBundle(width))

  io.out := 0.U

  switch(io.op) {
    is(ALUOp.add) {
      io.out := io.x + io.y
    }

    is(ALUOp.sub) {
      io.out := io.x - io.y
    }
  }
}
