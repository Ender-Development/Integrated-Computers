package io.klebe.ocid.compat.id.part.aspect.write

import net.minecraft.nbt.NBTTagCompound
import org.cyclops.integrateddynamics.api.part.PartPos
import org.cyclops.integrateddynamics.api.part.PartTarget

object WriteComputerComponent : IWriteComputerComponent {

    val states: MutableMap<PartPos, Any?> = mutableMapOf()

    private fun export(target: PartTarget, value: Any?) {
        states[target.center] = value
    }

    override fun exportBoolean(target: PartTarget, value: Boolean) = export(target, value)
    override fun exportNumber(target: PartTarget, value: Number) = export(target, value)
    override fun exportString(target: PartTarget, value: String) = export(target, value)
    override fun exportNBT(target: PartTarget, value: NBTTagCompound) = export(target, value)
}