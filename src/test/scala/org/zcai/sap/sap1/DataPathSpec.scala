package org.zcai.sap.sap1

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

class DataPathSpec extends AnyFreeSpec with ChiselScalatestTester {
  "PC counting" in {
    test(new DataPath) { dut =>
      dut.io.control.pcEnable.poke(true.B)
      dut.io.pcVal.expect(0.U)
      dut.clock.step()
      dut.io.pcVal.expect(1.U)
      dut.clock.step()
      dut.io.pcVal.expect(2.U)
      dut.io.control.pcEnable.poke(false.B)
      dut.clock.step()
      dut.io.pcVal.expect(2.U)
      dut.clock.step()
      dut.io.pcVal.expect(2.U)
    }
  }
}
