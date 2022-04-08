package org.zcai.sap.sap1

import chisel3._
import chisel3.util._

class DataPathBundle(width: Int, memSizeLog: Int) extends Bundle {
  val pcEnable: Bool = Input(Bool())
  val pcVal: UInt = Output(UInt(memSizeLog.W))
}

class DataPath(width: Int = 8, memSizeLog: Int = 4) extends Module {
  val io: DataPathBundle = IO(new DataPathBundle(width, memSizeLog))

  val pc: UInt = RegInit(0.U(width.W))
  when (io.pcEnable) {
    pc := pc + 1.U
  }
  io.pcVal := pc
}
