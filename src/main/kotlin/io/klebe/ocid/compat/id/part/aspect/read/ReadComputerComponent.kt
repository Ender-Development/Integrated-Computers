package io.klebe.ocid.compat.id.part.aspect.read

import net.minecraft.nbt.NBTTagCompound
import org.cyclops.integrateddynamics.api.part.PartPos
import org.cyclops.integrateddynamics.api.part.PartTarget
import kotlin.reflect.KClass

object ReadComputerComponent : IReadComputerComponent {

    val states: MutableMap<PartPos, IReadComputerComponent.State> = mutableMapOf()

    override fun provideBoolean(target: PartPos, value: Boolean) {
        states[target] = IReadComputerComponent.State.of(value)
    }

    override fun provideNumber(target: PartPos, value: Number) {
        states[target] = IReadComputerComponent.State.of(value)
    }

    override fun provideString(target: PartPos, value: String) {
        states[target] = IReadComputerComponent.State.of(value)
    }

    override fun provideNil(target: PartPos) {
        states.remove(target)
    }

    override operator fun get(target: PartPos): IReadComputerComponent.State? = states[target]
}