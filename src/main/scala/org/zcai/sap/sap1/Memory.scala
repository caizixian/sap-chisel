package org.zcai.sap.sap1

import chisel3._
import chisel3.util.experimental.loadMemoryFromFileInline

class MemoryBundle(width: Int, sizeLog: Int) extends Bundle {
  val addrInst: UInt = Input(UInt(sizeLog.W))
  val addrData: UInt = Input(UInt(sizeLog.W))
  val valueInst: UInt = Output(UInt(width.W))
  val valueData: UInt = Output(UInt(width.W))
}

class Memory(width: Int = 8, sizeLog: Int = 4, memoryFile: String) extends Module {
  val io: MemoryBundle = IO(new MemoryBundle(width, sizeLog))
  val mem: Mem[UInt] = Mem(1 << sizeLog, UInt(width.W))

  // dual-port asynchronous read
  io.valueData := mem.read(io.addrData)
  io.valueInst := mem.read(io.addrInst)

  // Initialize memory
  if (memoryFile.trim().nonEmpty) {
    loadMemoryFromFileInline(mem, memoryFile)
  }
}
