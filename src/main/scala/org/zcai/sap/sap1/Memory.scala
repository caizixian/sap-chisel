package org.zcai.sap.sap1

import chisel3._

class MemoryBundle(width: Int, sizeLog: Int) extends Bundle {
  val write: Bool = Input(Bool())
  val addrInst: UInt = Input(UInt(sizeLog.W))
  val addrData: UInt = Input(UInt(sizeLog.W))
  val dataIn: UInt = Input(UInt(width.W))
  val dataOut: UInt = Output(UInt(width.W))
  val instOut: UInt = Output(UInt(width.W))
}

class Memory(width: Int = 8, sizeLog: Int = 4) extends Module {
  val io: MemoryBundle = IO(new MemoryBundle(width, sizeLog))
  val mem: Mem[UInt] = Mem(1 << sizeLog, UInt(width.W))

  // dual-port asynchronous read
  io.dataOut := mem.read(io.addrData)
  io.instOut := mem.read(io.addrInst)
  // synchronous write
  when(io.write) {
    mem.write(io.addrData, io.dataIn)
  }
}
