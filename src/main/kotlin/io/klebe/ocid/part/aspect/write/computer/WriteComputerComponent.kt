package io.klebe.ocid.part.aspect.write.computer

import io.klebe.ocid.OCID
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.cyclops.integrateddynamics.api.part.PartTarget

object WriteComputerComponent : IWriteComputerComponent {
    override fun exportBoolean(target: PartTarget, value: Boolean) {
        OCID.log.info("Write $value to ${target.target.pos}")
    }
}