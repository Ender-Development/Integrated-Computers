package io.klebe.ocid.compat.oc

import io.klebe.ocid.compat.part.IPartTypeComputerCompat
import li.cil.oc.api.driver.NamedBlock
import li.cil.oc.api.machine.Arguments
import li.cil.oc.api.machine.Context
import li.cil.oc.api.Network
import li.cil.oc.api.network.ManagedPeripheral
import li.cil.oc.api.network.Visibility
import li.cil.oc.api.prefab.AbstractManagedEnvironment
import net.minecraft.util.EnumFacing
import org.cyclops.cyclopscore.datastructure.DimPos
import org.cyclops.integrateddynamics.api.part.PartPos
import org.cyclops.integrateddynamics.core.tileentity.TileMultipartTicking

class Environment(val tileEntity: TileMultipartTicking, val part: IPartTypeComputerCompat, val side: EnumFacing) :
    AbstractManagedEnvironment(),
    ManagedPeripheral,
    NamedBlock {

    private class HelpMethod(val parentEnvironment: Environment) : LuaMethode {
        override val name: String = "help"
        override val description: String = "Gets a help message"
        override val usage: String = "help() | help(methodName: string)"
        override fun invoke(environment: Environment, context: Context, arguments: Arguments): Array<Any> {
            return when (arguments.count()) {
                0 -> arrayOf(
                    "Help\n" +
                    "  Available methods:\n" +
                    parentEnvironment.part.luaMethods.joinToString("\n") { "    ${it.name} - ${it.description}" } +
                    "\n" +
                    "  Tip: Try help(\"help\") for help about help!"
                )
                1 -> {
                    val methodName: String = arguments.checkString(0)

                    fun helpString(method: LuaMethode): String {
                        return "Help for ${method.name}\n" +
                        "  ${method.description}\n" +
                        "  Usage: ${method.usage}"
                    }

                    if(methodName == name) {
                        return arrayOf(helpString(this))
                    }

                    return parentEnvironment.part.luaMethods.find { it.name == methodName }?.let {
                        arrayOf(helpString(it))
                    } ?: throw IllegalArgumentException("No method with the name $methodName found")

                }
                else -> throw IllegalArgumentException("Expected 0 or 1 arguments")
            }
        }
    }

    val position: PartPos = PartPos.of(tileEntity.partContainer.position, side)

    override fun methods(): Array<String> = part.luaMethods.map { it.name }.toMutableList().let {
        it.add("help")
        return it.toTypedArray()
    }

    override fun invoke(method: String, context: Context, arguments: Arguments): Array<Any> {
        return when (method) {
            "help" -> HelpMethod(this).invoke(this, context, arguments)
            else -> part.luaMethods.find { it.name == method }?.invoke(this, context, arguments)
                ?: throw IllegalArgumentException("No method with the name $method found")
        }
    }

    override fun preferredName(): String = part.preferredName
    override fun priority(): Int = 0 // ¯\_(ツ)_/¯

    init{
        setNode(Network.newNode(this, Visibility.Network).create());
    }

}