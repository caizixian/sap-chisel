package org.zcai.sap.sap1

import chisel3._

class DataToControlIO(width: Int, memSizeLog: Int) extends Bundle {
  val instruction: UInt = Output(UInt(width.W))
}

class DataPathIO(width: Int, memSizeLog: Int) extends Bundle {
  val pcVal: UInt = Output(UInt(memSizeLog.W))
  val outVal: UInt = Output(UInt(width.W))
  val data: DataToControlIO = new DataToControlIO(width, memSizeLog)
  val control: ControlToDataIO = Flipped(new ControlToDataIO(width, memSizeLog))
  val mem: MemoryBundle = Flipped(new MemoryBundle(width, memSizeLog))
}

class DataPath(width: Int = 8, memSizeLog: Int = 4) extends Module {
  val io: DataPathIO = IO(new DataPathIO(width, memSizeLog))

  val pc: UInt = RegInit(0.U(width.W))
  val out: UInt = RegInit(0.U(width.W))
  val accum: UInt = RegInit(0.U(width.W))

  val alu: ALU = Module(new ALU(width))

  when(io.control.pcEnable) {
    pc := pc + 1.U
  }

  alu.io.op := io.control.aluOp
  alu.io.x := accum
  alu.io.y := io.mem.valueData

  when(io.control.accumEnable) {
    when(io.control.memToAccum) {
      accum := io.mem.valueData
    } otherwise {
      accum := alu.io.out
    }
  }

  when(io.control.outEnable) {
    out := accum
  }

  io.mem.addrInst := pc
  io.mem.addrData := io.control.addrData
  io.data.instruction := io.mem.valueInst

  io.pcVal := pc
  io.outVal := out

  // printf(p"pc = ${pc}, accum = ${accum}, out = ${out}, addrData = ${io.control.addrData}, valueData = ${io.mem.valueData}\n")
}
