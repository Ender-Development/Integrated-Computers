package io.klebe.ocid.compat.oc

import io.klebe.ocid.compat.id.part.aspect.read.IReadComputerComponent
import io.klebe.ocid.compat.id.part.aspect.read.ReadComputerComponent
import io.klebe.ocid.compat.id.part.aspect.write.WriteComputerComponent
import io.klebe.ocid.compat.part.IComputerPart
import li.cil.oc.api.driver.NamedBlock
import li.cil.oc.api.machine.Arguments
import li.cil.oc.api.machine.Context
import li.cil.oc.api.Network
import li.cil.oc.api.network.ManagedPeripheral
import li.cil.oc.api.network.Node
import li.cil.oc.api.network.Visibility
import li.cil.oc.api.prefab.AbstractManagedEnvironment
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import org.cyclops.integrateddynamics.api.part.PartPos
import org.cyclops.integrateddynamics.core.tileentity.TileMultipartTicking

class Environment(val tileEntity: TileMultipartTicking, val part: IComputerPart, val side: EnumFacing) :
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

    override fun save(nbt: NBTTagCompound) {
        super.save(nbt)
        ReadComputerComponent.states[position]?.let { state ->
            nbt.setTag("computer_reader_state", NBTTagCompound().apply {
                this.setByte("type", state.type.id)
                this.setString("value", state.value.toString());
            })
        }
    }

    override fun load(nbt: NBTTagCompound) {
        super.load(nbt)
        nbt.getCompoundTag("computer_reader_state").let { stateTag ->
            stateTag.getByte("type").let { typeId ->
                val type = IReadComputerComponent.State.Type.ofID(typeId)
                val rawValue = stateTag.getString("value")

                val value: IReadComputerComponent.State = when (type) {
                    IReadComputerComponent.State.Type.BOOLEAN -> IReadComputerComponent.State.of(rawValue.toBoolean())
                    IReadComputerComponent.State.Type.NUMBER -> {
                        if (rawValue.toLong().toString() == rawValue) {
                            IReadComputerComponent.State.of(rawValue.toLong())
                        } else {
                            IReadComputerComponent.State.of(rawValue.toDouble())
                        }
                    }
                    IReadComputerComponent.State.Type.STRING -> IReadComputerComponent.State.of(rawValue)
                }

                ReadComputerComponent.states[position] = value
            }
        }
    }

    override fun onDisconnect(node: Node?) {
        super.onDisconnect(node)
        ReadComputerComponent.states.remove(position)
    }

    init{
        setNode(Network.newNode(this, Visibility.Network).create());
    }

}