package org.zcai.sap.sap1

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

class MemorySpec extends AnyFreeSpec with ChiselScalatestTester {
  "Read initially zero" in {
    test(new Memory(8, 4, "")) { dut =>
      dut.io.addrData.poke(0.U)
      dut.io.valueData.expect(0.U)
    }
  }
}
