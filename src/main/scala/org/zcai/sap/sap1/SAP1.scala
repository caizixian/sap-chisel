package org.zcai.sap.sap1

import chisel3.Module

import chisel3._
import chisel3.util._

class SAP1IO(width: Int, memSizeLog: Int) extends Bundle {
  val outVal: UInt = Output(UInt(width.W))
}

class SAP1(width: Int, memSizeLog: Int) extends Module {
  val io: SAP1IO = IO(new SAP1IO(width, memSizeLog))

  val controlPath: ControlPath = Module(new ControlPath(width, memSizeLog))
  val dataPath: DataPath = Module(new DataPath(width, memSizeLog))

  dataPath.io.control <> controlPath.io.control
  dataPath.io.data <> controlPath.io.data
  io.outVal := dataPath.io.outVal
}


object SAP1 extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new SAP1(8, 4), Array("--target-dir", "build"))
}