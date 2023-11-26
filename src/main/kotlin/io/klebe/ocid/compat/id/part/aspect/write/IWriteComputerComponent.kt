package io.klebe.ocid.compat.id.part.aspect.write

import net.minecraft.nbt.NBTTagCompound
import org.cyclops.integrateddynamics.api.part.PartTarget

interface IWriteComputerComponent {
    fun exportBoolean(target: PartTarget, value: Boolean)
    fun exportNumber(target: PartTarget, value: Number)
    fun exportString(target: PartTarget, value: String)
    fun exportNBT(target: PartTarget, value: NBTTagCompound)
}