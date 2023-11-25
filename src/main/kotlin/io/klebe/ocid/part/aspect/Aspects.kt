package io.klebe.ocid.part.aspect

import io.klebe.ocid.part.aspect.write.AspectWriteBuilders
import org.cyclops.integrateddynamics.IntegratedDynamics
import org.cyclops.integrateddynamics.api.part.aspect.IAspectRegistry

object Aspects {

    val REGISTRY = IntegratedDynamics._instance.registryManager.getRegistry<IAspectRegistry?>(IAspectRegistry:: class.java)

    object Write {
        object Computer {
            val BOOLEAN: BooleanAspectWrite = AspectWriteBuilders.Computer.BUILDER_BOOLEAN
                .handle(AspectWriteBuilders.Computer.PROP_BOOLEAN_SET)
                .buildWrite()
            /*
            val INTEGER: IntegerAspectWrite
            val DOUBLE: DoubleAspectWrite
            val STRING: StringAspectWrite
            val NBT: NbtAspectWrite*/
        }
    }
}