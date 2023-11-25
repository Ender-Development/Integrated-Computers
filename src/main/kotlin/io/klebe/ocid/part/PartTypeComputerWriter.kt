package io.klebe.ocid.part

import com.google.common.collect.Lists
import io.klebe.ocid.part.aspect.*
import org.cyclops.integrateddynamics.core.part.aspect.AspectRegistry
import org.cyclops.integrateddynamics.core.part.write.PartStateWriterBase

object PartTypeComputerWriter
    : PartTypeWriteBase<PartTypeComputerWriter, PartStateWriterBase<PartTypeComputerWriter>>( "computer_writer") {

    init {
        AspectRegistry.getInstance().register(this, listOf(
            Aspects.Write.Computer.BOOLEAN
        ))
    }

    public override fun constructDefaultState(): PartStateWriterBase<PartTypeComputerWriter>? {
        return PartStateWriterBase<PartTypeComputerWriter>(Aspects.REGISTRY.getAspects(this).size)
    }
    override fun getConsumptionRate(state: PartStateWriterBase<PartTypeComputerWriter>?): Int = 0 // TODO

}