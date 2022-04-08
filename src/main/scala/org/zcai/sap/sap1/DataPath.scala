package org.zcai.sap.sap1

import chisel3._
import chisel3.util._

class DataToControlIO(width: Int, memSizeLog: Int) extends Bundle {
  val instruction: UInt = Output(UInt(width.W))
}

class DataPathIO(width: Int, memSizeLog: Int) extends Bundle {
  val pcVal: UInt = Output(UInt(memSizeLog.W))
  val outVal: UInt = Output(UInt(width.W))
  val data: DataToControlIO = new DataToControlIO(width, memSizeLog)
  val control: ControlToDataIO = Flipped(new ControlToDataIO(width, memSizeLog))
}

class DataPath(width: Int = 8, memSizeLog: Int = 4) extends Module {
  val io: DataPathIO = IO(new DataPathIO(width, memSizeLog))

  val pc: UInt = RegInit(0.U(width.W))
  val out: UInt = RegInit(0.U(width.W))
  val mem: Memory = Module(new Memory(width, memSizeLog))

  when(io.control.pcEnable) {
    pc := pc + 1.U
  }

  // FIXME
  mem.io.write := false.B
  mem.io.addrInst := 0.U
  mem.io.addrData := 0.U
  mem.io.dataIn := 0.U
  io.data.instruction := mem.io.instOut

  io.pcVal := pc
  io.outVal := out
}
