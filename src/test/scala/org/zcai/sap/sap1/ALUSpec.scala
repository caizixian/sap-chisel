package org.zcai.sap.sap1

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

class ALUSpec extends AnyFreeSpec with ChiselScalatestTester {
  "Add should work" in {
    test(new ALU(4)) { dut =>
      dut.io.op.poke(ALUOp.add)
      dut.io.x.poke(2.U)
      dut.io.y.poke(3.U)
      dut.io.out.expect(5.U)
    }
  }

  "Sub should work" in {
    test(new ALU(4)) { dut =>
      dut.io.op.poke(ALUOp.sub)
      dut.io.x.poke(3.U)
      dut.io.y.poke(2.U)
      dut.io.out.expect(1.U)
    }
  }
}
