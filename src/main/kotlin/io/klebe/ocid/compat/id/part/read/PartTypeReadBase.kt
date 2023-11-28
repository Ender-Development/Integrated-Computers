package io.klebe.ocid.compat.id.part.read

import io.klebe.ocid.OCID
import org.cyclops.cyclopscore.init.ModBase
import org.cyclops.integrateddynamics.api.part.PartRenderPosition
import org.cyclops.integrateddynamics.api.part.read.IPartStateReader
import org.cyclops.integrateddynamics.api.part.read.IPartTypeReader
import org.cyclops.integrateddynamics.api.part.write.IPartStateWriter
import org.cyclops.integrateddynamics.api.part.write.IPartTypeWriter

abstract class PartTypeReadBase<
        P : IPartTypeReader<P, S>?,
        S : IPartStateReader<P>?
        > (name: String) : org.cyclops.integrateddynamics.core.part.read.PartTypeReadBase<P, S>(name, PartRenderPosition(
            5/16f, 5/16f, 8/16f, 8/16f
        )) {
    override fun getMod(): ModBase = OCID
}