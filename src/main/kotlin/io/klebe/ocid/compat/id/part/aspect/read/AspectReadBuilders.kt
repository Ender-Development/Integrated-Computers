package io.klebe.ocid.compat.id.part.aspect.read

import io.klebe.ocid.compat.id.part.aspect.read.AspectReadBuilders.Computer.common
import io.klebe.ocid.compat.id.part.aspect.write.AspectWriteBuilders
import io.klebe.ocid.compat.id.part.aspect.write.IWriteComputerComponent
import io.klebe.ocid.compat.id.part.aspect.write.WriteComputerComponent
import org.cyclops.integrateddynamics.api.evaluate.variable.IValue
import org.cyclops.integrateddynamics.api.evaluate.variable.IValueType
import org.cyclops.integrateddynamics.core.part.aspect.build.AspectBuilder
import org.cyclops.integrateddynamics.core.part.aspect.build.IAspectWriteActivator
import org.cyclops.integrateddynamics.core.part.aspect.build.IAspectWriteDeactivator
import org.cyclops.integrateddynamics.part.aspect.read.AspectReadBuilders  as CyclopsAspectReadBuilders
import org.apache.commons.lang3.tuple.Triple as ApacheTriple

object AspectReadBuilders {
    object Computer {

        val READ_COMPUTER_COMPONENT: IReadComputerComponent = ReadComputerComponent

        val KIND = "computer"

        private fun <V: IValue, T: IValueType<V>, O> AspectBuilder<V, T, O>.common(): AspectBuilder<V, T, O> = this
            .appendKind(KIND)

        val BUILDER_BOOLEAN = CyclopsAspectReadBuilders.BUILDER_BOOLEAN.common()
        val BUILDER_DOUBLE = CyclopsAspectReadBuilders.BUILDER_DOUBLE.common()
        val BUILDER_LONG = CyclopsAspectReadBuilders.BUILDER_LONG.common()
        val BUILDER_STRING = CyclopsAspectReadBuilders.BUILDER_STRING.common()
    }
}