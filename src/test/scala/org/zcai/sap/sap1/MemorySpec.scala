package org.zcai.sap.sap1

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec

class MemorySpec extends AnyFreeSpec with ChiselScalatestTester {
  "Read initially zero" in {
    test(new Memory(8, 4)) { dut =>
      dut.io.addr.poke(0.U)
      dut.io.dataOut.expect(0)
    }
  }

  "Write and then read" in {
    test(new Memory(8, 4)) { dut =>
      dut.io.addr.poke(0.U)
      dut.io.write.poke(true.B)
      dut.io.dataIn.poke(42.U)
      dut.io.dataOut.expect(0)
      dut.clock.step()
      dut.io.addr.poke(0.U)
      dut.io.dataOut.expect(42)
    }
  }
}
