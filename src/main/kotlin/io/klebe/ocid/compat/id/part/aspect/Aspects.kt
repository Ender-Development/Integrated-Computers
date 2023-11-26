package io.klebe.ocid.compat.id.part.aspect

import io.klebe.ocid.compat.id.part.aspect.write.AspectWriteBuilders
import org.cyclops.integrateddynamics.IntegratedDynamics
import org.cyclops.integrateddynamics.api.part.aspect.IAspectRegistry

object Aspects {

    val REGISTRY = IntegratedDynamics._instance.registryManager.getRegistry<IAspectRegistry?>(IAspectRegistry:: class.java)

    object Write {
        object Computer {
            val BOOLEAN = AspectWriteBuilders.Computer.BUILDER_BOOLEAN.buildWrite()
            val NUMBER = AspectWriteBuilders.Computer.BUILDER_NUMBER.buildWrite()
            val STRING = AspectWriteBuilders.Computer.BUILDER_STRING.buildWrite()
            val NBT = AspectWriteBuilders.Computer.BUILDER_NBT.buildWrite()
        }
    }
}