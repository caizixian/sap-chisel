package org.zcai.sap.sap1

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec
import org.zcai.sap.common.Assembler

class SAP1Spec extends AnyFreeSpec with ChiselScalatestTester {

  "Test with real program" in {
    new Assembler().assembleSampleProgram("build")
    test(new SAP1(8, 4, "build/sap1.hex.txt")) { dut =>
      dut.io.outVal.expect(0.U) // cycle 0
      dut.clock.step()
      dut.io.outVal.expect(0.U) // cycle 1
      dut.clock.step()
      dut.io.outVal.expect(0.U) // cycle 2
      dut.clock.step()
      dut.io.outVal.expect(0.U) // cycle 3
      dut.clock.step()
      dut.io.outVal.expect(0.U) // cycle 4
      dut.clock.step()
      dut.io.outVal.expect(2.U) // cycle 5
      dut.clock.step()
      dut.io.outVal.expect(2.U) // cycle 6
      dut.clock.step()
    }
  }
}
