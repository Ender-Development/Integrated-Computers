package io.klebe.ocid.part.aspect.write.computer

import org.cyclops.integrateddynamics.api.part.PartTarget

interface IWriteComputerComponent {
    fun exportBoolean(target: PartTarget, value: Boolean)
}