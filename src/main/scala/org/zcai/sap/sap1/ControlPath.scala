package org.zcai.sap.sap1

import chisel3._
import chisel3.experimental.ChiselEnum
import chisel3.util._
import org.zcai.sap.sap1.Instructions._

class ControlToDataIO(width: Int, memSizeLog: Int) extends Bundle {
  val pcEnable: Bool = Output(Bool())
  val accumEnable: Bool = Output(Bool())
  val outEnable: Bool = Output(Bool())
  val memToAccum: Bool = Output(Bool())
  val addrData: UInt = Output(UInt(memSizeLog.W))
  val aluOp: ALUOp.Type = Output(ALUOp())
}

class ControlIO(width: Int, memSizeLog: Int) extends Bundle {
  val control: ControlToDataIO = new ControlToDataIO(width, memSizeLog)
  val data: DataToControlIO = Flipped(new DataToControlIO(width, memSizeLog))
}

object ControlState extends ChiselEnum {
  val run, halted = Value
}

class ControlPath(width: Int, memSizeLog: Int) extends Module {
  val io: ControlIO = IO(new ControlIO(width, memSizeLog))
  val state: ControlState.Type = RegInit(ControlState.run)

  val controlSignals: Seq[Element] = ListLookup(io.data.instruction,
    // format pcEnable (B), accumEnable (B), outEnable(B), memToAccum (B), addrData (U(memSizeLog.W)), aluOp (ALUOp), nextState
    // default
    List(false.B, false.B, false.B, false.B, 0.U, ALUOp.add, ControlState.halted),
    Array(
      LDA -> List(true.B, true.B, false.B, true.B, io.data.instruction(3, 0), ALUOp.add, ControlState.run),
      ADD -> List(true.B, true.B, false.B, false.B, io.data.instruction(3, 0), ALUOp.add, ControlState.run),
      SUB -> List(true.B, true.B, false.B, false.B, io.data.instruction(3, 0), ALUOp.sub, ControlState.run),
      OUT -> List(true.B, false.B, true.B, false.B, 0.U, ALUOp.add, ControlState.run),
      HLT -> List(false.B, false.B, false.B, false.B, 0.U, ALUOp.add, ControlState.halted)
    )
  )

  val pcEnable :: accumEnable :: outEnable :: memToAccum :: addrData :: aluOp :: nextState :: Nil = controlSignals

  io.control.pcEnable := pcEnable
  io.control.accumEnable := accumEnable
  io.control.outEnable := outEnable
  io.control.memToAccum := memToAccum
  io.control.addrData := addrData
  io.control.aluOp := aluOp
  state := nextState
}
