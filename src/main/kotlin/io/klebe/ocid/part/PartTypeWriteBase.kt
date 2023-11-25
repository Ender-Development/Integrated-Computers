package io.klebe.ocid.part

import io.klebe.ocid.OCID
import org.cyclops.cyclopscore.init.ModBase
import org.cyclops.integrateddynamics.api.part.PartRenderPosition
import org.cyclops.integrateddynamics.api.part.write.IPartStateWriter
import org.cyclops.integrateddynamics.api.part.write.IPartTypeWriter

abstract class PartTypeWriteBase<
        P : IPartTypeWriter<P, S>?,
        S : IPartStateWriter<P>?
        > (name: String)
    : org.cyclops.integrateddynamics.core.part.write.PartTypeWriteBase<P, S>(name) {
    override fun getMod(): ModBase = OCID


}