package io.klebe.ocid.compat.id.part.write

import io.klebe.ocid.OCID
import org.cyclops.cyclopscore.init.ModBase
import org.cyclops.integrateddynamics.api.part.PartRenderPosition
import org.cyclops.integrateddynamics.api.part.write.IPartStateWriter
import org.cyclops.integrateddynamics.api.part.write.IPartTypeWriter

abstract class PartTypeWriteBase<
        P : IPartTypeWriter<P, S>?,
        S : IPartStateWriter<P>?
        > (name: String)
    : org.cyclops.integrateddynamics.core.part.write.PartTypeWriteBase<P, S>(name, PartRenderPosition(
    5/16f, 5/16f, 8/16f, 8/16f
)) {
    override fun getMod(): ModBase = OCID


}