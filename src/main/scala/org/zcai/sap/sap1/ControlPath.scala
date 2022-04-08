package org.zcai.sap.sap1

import chisel3._
import chisel3.util._

class ControlToDataIO(width: Int, memSizeLog: Int) extends Bundle {
  val pcEnable: Bool = Output(Bool())
}

class ControlIO(width: Int, memSizeLog: Int) extends Bundle {
  val control: ControlToDataIO = new ControlToDataIO(width, memSizeLog)
  val data: DataToControlIO = Flipped(new DataToControlIO(width, memSizeLog))
}

class ControlPath(width: Int, memSizeLog: Int) extends Module {
  val io: ControlIO = IO(new ControlIO(width, memSizeLog))

  // FIXME
  io.control.pcEnable := false.B
}
