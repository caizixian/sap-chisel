package org.zcai.sap.sap1

import chisel3._

class MemoryBundle(width: Int, sizeLog: Int) extends Bundle {
  val write: Bool = Input(Bool())
  val addr: UInt = Input(UInt(sizeLog.W))
  val dataIn: UInt = Input(UInt(width.W))
  val dataOut: UInt = Output(UInt(width.W))
}

class Memory(width: Int = 8, sizeLog: Int = 4) extends Module {
  val io: MemoryBundle = IO(new MemoryBundle(width, sizeLog))
  val mem: Mem[UInt] = Mem(1 << sizeLog, UInt(width.W))

  // asynchronous read
  io.dataOut := mem.read(io.addr)
  // synchronous write
  when(io.write) {
    mem.write(io.addr, io.dataIn)
  }
}
