package io.klebe.ocid.part.aspect.write.computer

import io.klebe.ocid.OCID
import org.cyclops.cyclopscore.datastructure.DimPos
import org.cyclops.integrateddynamics.api.part.PartTarget

object WriteComputerComponent : IWriteComputerComponent {

    val states: MutableMap<DimPos, Any?> = mutableMapOf()
    override fun exportBoolean(target: PartTarget, value: Boolean) {
        states[target.center.pos] = value
        OCID.log.info("Saving $value to ${target.center.pos}")
    }
}