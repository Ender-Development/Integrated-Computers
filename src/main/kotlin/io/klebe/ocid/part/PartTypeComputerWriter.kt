package io.klebe.ocid.part

import com.google.common.collect.Lists
import io.klebe.ocid.oc.AbstractLuaMethode
import io.klebe.ocid.oc.Environment
import io.klebe.ocid.oc.LuaMethode
import io.klebe.ocid.part.aspect.*
import io.klebe.ocid.part.aspect.write.AspectWriteBuilders
import io.klebe.ocid.part.aspect.write.computer.WriteComputerComponent
import li.cil.oc.api.machine.Arguments
import li.cil.oc.api.machine.Context
import org.cyclops.integrateddynamics.api.part.IPartState
import org.cyclops.integrateddynamics.core.part.aspect.AspectRegistry
import org.cyclops.integrateddynamics.core.part.write.PartStateWriterBase


import java.util.Collections

object PartTypeComputerWriter :
    PartTypeWriteBase<PartTypeComputerWriter, PartStateWriterBase<PartTypeComputerWriter>>( "computer_writer"),
    IPartTypeComputerCompat
{

    init {
        AspectRegistry.getInstance().register(this, listOf(
            Aspects.Write.Computer.BOOLEAN
        ))
    }

    public override fun constructDefaultState(): PartStateWriterBase<PartTypeComputerWriter>? {
        return PartStateWriterBase<PartTypeComputerWriter>(Aspects.REGISTRY.getAspects(this).size)
    }
    override fun getConsumptionRate(state: PartStateWriterBase<PartTypeComputerWriter>?): Int = 0 // TODO
    override val preferredName = "Computer Writer"

    @JvmStatic
    val GET_LUA_METHODE =  object : AbstractLuaMethode("get", "Gets the value omitted value from the writer", "get(): any") {
        override fun invoke(environment: Environment, context: Context, arguments: Arguments): Array<Any> {
            return WriteComputerComponent.states[environment.position]?.let { arrayOf(it) } ?: arrayOf()
        }
    }

    override val luaMethods: Collection<LuaMethode> = Collections.singletonList(GET_LUA_METHODE)

}