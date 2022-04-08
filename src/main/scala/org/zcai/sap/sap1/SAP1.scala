package org.zcai.sap.sap1

import chisel3._
import org.zcai.sap.common.Assembler

class SAP1IO(width: Int, memSizeLog: Int) extends Bundle {
  val outVal: UInt = Output(UInt(width.W))
}

class SAP1(width: Int, memSizeLog: Int, memoryFile: String = "") extends Module {
  val io: SAP1IO = IO(new SAP1IO(width, memSizeLog))

  val controlPath: ControlPath = Module(new ControlPath(width, memSizeLog))
  val dataPath: DataPath = Module(new DataPath(width, memSizeLog))
  val memory: Memory = Module(new Memory(width, memSizeLog, memoryFile))

  dataPath.io.control <> controlPath.io.control
  dataPath.io.data <> controlPath.io.data
  dataPath.io.mem <> memory.io
  io.outVal := dataPath.io.outVal
}

object SAP1 extends App {
  new Assembler().assembleSampleProgram("build")
  (new chisel3.stage.ChiselStage).emitVerilog(new SAP1(8, 4, "build/sap1.hex"), Array("--target-dir", "build"))
}
