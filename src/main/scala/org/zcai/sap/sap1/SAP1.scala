package org.zcai.sap.sap1

import chisel3.Module
import chisel3._
import chisel3.util._
import org.zcai.sap.common.Assembler

import java.io.{File, FileOutputStream, FileWriter}

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
  var sb = new StringBuilder
  sb ++= "LDA 9\n"
  sb ++= "ADD 10\n"
  sb ++= "ADD 11\n"
  sb ++= "SUB 12\n"
  sb ++= "OUT\n"
  sb ++= "HLT\n"
  sb ++= "VAL 255\n" // 6
  sb ++= "VAL 255\n"
  sb ++= "VAL 255\n"
  sb ++= "VAL 1\n" // 9
  sb ++= "VAL 2\n" // 10
  sb ++= "VAL 3\n" // 11
  sb ++= "VAL 4\n" // 12
  sb ++= "VAL 255\n"
  sb ++= "VAL 255\n"
  sb ++= "VAL 255"

  val buildFolder = new File("build")
  if (!buildFolder.exists()) {
    buildFolder.mkdir()
  }

  val sourceFile = new File("build/sap1.src")

  try {
    val fw = new FileWriter(sourceFile.getAbsoluteFile)
    fw.write(sb.toString())
    fw.close()
  }

  val hex = new Assembler().assemble("build/sap1.src")
  val hexFile = new File("build/sap1.hex")

  try {
    val fis = new FileOutputStream(hexFile.getAbsoluteFile)
    fis.write(hex)
    fis.close()
  }

  (new chisel3.stage.ChiselStage).emitVerilog(new SAP1(8, 4, "build/sap1.hex"), Array("--target-dir", "build"))
}
