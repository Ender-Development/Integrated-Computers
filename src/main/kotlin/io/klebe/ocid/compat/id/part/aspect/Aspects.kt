package io.klebe.ocid.compat.id.part.aspect

import io.klebe.ocid.OCID
import io.klebe.ocid.compat.id.part.aspect.read.AspectReadBuilders
import io.klebe.ocid.compat.id.part.aspect.write.AspectWriteBuilders
import org.cyclops.integrateddynamics.IntegratedDynamics
import org.cyclops.integrateddynamics.api.evaluate.variable.IValue
import org.cyclops.integrateddynamics.api.evaluate.variable.IValueType
import org.cyclops.integrateddynamics.api.part.aspect.IAspectRegistry
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeBoolean.ValueBoolean
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeDouble.ValueDouble
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeLong.ValueLong
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeString.ValueString
import org.cyclops.integrateddynamics.core.part.aspect.build.AspectBuilder

object Aspects {
    internal fun <V: IValue, T: IValueType<V>, O> AspectBuilder<V, T, O>.appendDebugPrinter() = this
        .handle{
            OCID.log.info("[ASPECT-DEBUG-PRINT] $it")
            it
        }

    val REGISTRY = IntegratedDynamics._instance.registryManager.getRegistry<IAspectRegistry?>(IAspectRegistry:: class.java)

    object Read {
        object Computer {
            val BOOLEAN = AspectReadBuilders.Computer.BUILDER_BOOLEAN
                .handle { input -> AspectReadBuilders.Computer.READ_COMPUTER_COMPONENT[input.left.center]?.tryBoolean() }
                .handle { input -> ValueBoolean.of(input ?: false) }
                .buildRead()
            val DOUBLE = AspectReadBuilders.Computer.BUILDER_DOUBLE
                .handle { input -> AspectReadBuilders.Computer.READ_COMPUTER_COMPONENT[input.left.center]?.tryNumber()?.toDouble() }
                .handle { input -> ValueDouble.of(input ?: 0.0) }
                .buildRead()
            val LONG = AspectReadBuilders.Computer.BUILDER_LONG
                .handle { input -> AspectReadBuilders.Computer.READ_COMPUTER_COMPONENT[input.left.center]?.tryNumber()?.toLong() }
                .handle { input -> ValueLong.of(input ?: 0) }
                .buildRead()
            val STRING = AspectReadBuilders.Computer.BUILDER_STRING
                .handle { input -> AspectReadBuilders.Computer.READ_COMPUTER_COMPONENT[input.left.center]?.tryString() }
                .handle { input -> ValueString.of(input ?: "") }
                .buildRead()
            val IS_NIL = AspectReadBuilders.Computer.BUILDER_BOOLEAN
                .handle { input -> AspectReadBuilders.Computer.READ_COMPUTER_COMPONENT[input.left.center] == null }
                .handle { input -> ValueBoolean.of(input) }
                .buildRead()
        }
    }

    object Write {
        object Computer {
            val BOOLEAN = AspectWriteBuilders.Computer.BUILDER_BOOLEAN.buildWrite()
            val NUMBER = AspectWriteBuilders.Computer.BUILDER_NUMBER.buildWrite()
            val STRING = AspectWriteBuilders.Computer.BUILDER_STRING.buildWrite()
            val NBT = AspectWriteBuilders.Computer.BUILDER_NBT.buildWrite()
        }
    }
}