package io.klebe.ocid.compat.id.part.read

import io.klebe.ocid.compat.id.part.aspect.Aspects
import io.klebe.ocid.compat.id.part.aspect.read.ReadComputerComponent
import io.klebe.ocid.compat.oc.AbstractLuaMethode
import io.klebe.ocid.compat.oc.Environment
import io.klebe.ocid.compat.part.IComputerPart
import li.cil.oc.api.machine.Arguments
import li.cil.oc.api.machine.Context
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.common.ticket.AABBTicket
import org.cyclops.integrateddynamics.core.part.aspect.AspectRegistry
import org.cyclops.integrateddynamics.core.part.read.PartStateReaderBase

object PartTypeComputerReader :
    PartTypeReadBase<PartTypeComputerReader, PartStateReaderBase<PartTypeComputerReader>>("computer_reader"),
    IComputerPart
{


    init {
        AspectRegistry.getInstance().register(this, listOf(
            Aspects.Read.Computer.BOOLEAN,
            Aspects.Read.Computer.DOUBLE,
            Aspects.Read.Computer.LONG,
            Aspects.Read.Computer.STRING,
            Aspects.Read.Computer.IS_NIL,
        ))
    }

    override fun constructDefaultState(): PartStateReaderBase<PartTypeComputerReader> =
        PartStateReaderBase<PartTypeComputerReader>()

    val PROVIDE_LUA_METHODE = object : AbstractLuaMethode(
        "provide",
        "Provides the value to the reader",
        "provide(value: boolean | number | string | nil): nil"
    ) {
        override fun invoke(environment: Environment, context: Context, arguments: Arguments): Array<Any> {
            environment.position
            if (arguments.count() > 1) {
                throw IllegalArgumentException("Expected 0 or 1 arguments")
            }

            if(arguments.isBoolean(0))
                ReadComputerComponent.provideBoolean(environment.position, arguments.checkBoolean(0))
            else if(arguments.isDouble(0))
                ReadComputerComponent.provideNumber(environment.position, arguments.checkDouble(0))
            else if(arguments.isString(0))
                ReadComputerComponent.provideString(environment.position, arguments.checkString(0))
            else if(arguments.count() == 0)
                ReadComputerComponent.provideNil(environment.position)
            else
                throw IllegalArgumentException("Expected boolean, number, string or nil")
            return arrayOf()
        }
    }

    val WHAT_PROVIDED_LUA_METHODE = object : AbstractLuaMethode(
        "whatProvided",
        "Returns the value provided to the reader",
        "whatProvided(): boolean | number | string | nil"
    ) {
        override fun invoke(environment: Environment, context: Context, arguments: Arguments): Array<Any> =
            ReadComputerComponent.states[environment.position]?.let { arrayOf(it.value) } ?: arrayOf()
    }

    override val preferredName = "computer_reader"
    override val luaMethods = listOf(
        PROVIDE_LUA_METHODE,
        WHAT_PROVIDED_LUA_METHODE
    )
}
