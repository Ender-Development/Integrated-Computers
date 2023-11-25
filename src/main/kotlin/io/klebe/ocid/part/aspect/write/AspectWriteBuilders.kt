package io.klebe.ocid.part.aspect.write

import io.klebe.ocid.OCID
import io.klebe.ocid.part.aspect.write.computer.IWriteComputerComponent
import io.klebe.ocid.part.aspect.write.computer.WriteComputerComponent
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.cyclops.integrateddynamics.api.part.PartTarget
import org.cyclops.integrateddynamics.api.part.aspect.property.IAspectProperties
import org.cyclops.integrateddynamics.api.part.write.IPartStateWriter
import org.cyclops.integrateddynamics.api.part.write.IPartTypeWriter
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeBoolean
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypes
import org.cyclops.integrateddynamics.core.part.aspect.build.AspectBuilder
import org.cyclops.integrateddynamics.core.part.aspect.build.IAspectValuePropagator
import org.cyclops.integrateddynamics.core.part.aspect.build.IAspectWriteActivator
import org.cyclops.integrateddynamics.core.part.aspect.build.IAspectWriteDeactivator
import org.cyclops.integrateddynamics.core.part.aspect.property.AspectProperties
import org.cyclops.integrateddynamics.core.part.aspect.property.AspectPropertyTypeInstance
import java.lang.IllegalStateException

typealias CyclopsAspectWriteBuilders = org.cyclops.integrateddynamics.part.aspect.write.AspectWriteBuilders
typealias ApacheTriple<A, B, C> = org.apache.commons.lang3.tuple.Triple<A, B, C>

object AspectWriteBuilders {

    object Computer {

        object EventHandler : IAspectWriteDeactivator, IAspectWriteActivator {

            private fun <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> onTileEntityTarget(
                tileEntity: TileEntity,
                p0: P,
                target: PartTarget,
                p2: S,
                activation: Boolean,
            ){

            }

            private fun <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> onBlockTarget(
                blockState: IBlockState,
                p0: P,
                target: PartTarget,
                p2: S,
                activation: Boolean,
            ){
                val targetPos: BlockPos = target.target.pos.blockPos
                val world: World = target.target.pos.world!!

                val myPos = when (target.target.side) {
                    EnumFacing.DOWN -> targetPos.up()
                    EnumFacing.UP -> targetPos.down()
                    EnumFacing.NORTH -> targetPos.south()
                    EnumFacing.SOUTH -> targetPos.north()
                    EnumFacing.WEST -> targetPos.east()
                    EnumFacing.EAST -> targetPos.west()
                    else -> throw IllegalStateException("Invalid Side: ${target.target.side}")
                }

                blockState.neighborChanged(
                    world,
                    target.target.pos.blockPos,
                    world.getBlockState(myPos).block,
                    myPos
                )
            }

            private inline fun  <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> alwaysAnd(
                p0: P,
                target: PartTarget,
                p2: S,
                activation: Boolean,
                crossinline block: ()->Unit
            ){
                // q: is an intellisense error here?
                // a: yes, but it's a bug in intellisense, not in the code
                // q: how do you know?
                // a: because it compiles
                val world: World = target.target.pos.world!!
                val pos: BlockPos = target.target.pos.blockPos!!

                world.getBlockState(pos).let{bs ->
                    if (bs.block != Blocks.AIR) {
                        onBlockTarget(bs, p0, target, p2, activation)
                    }
                }

                world.getTileEntity(pos)?.let {te -> onTileEntityTarget(te, p0, target, p2, activation) }

                block()
            }

            override fun <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> onDeactivate(
                p0: P,
                target: PartTarget,
                p2: S
            ) = alwaysAnd(p0, target, p2, false) {
                OCID.log.info(this.javaClass.name + "::onDeactivate called") // TODO
            }

            override fun <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> onActivate(
                p0: P,
                target: PartTarget,
                p2: S
            ) = alwaysAnd(p0, target, p2, true) {
                OCID.log.info(this.javaClass.name + "::onActivate called") // TODO
            }
        }

        val WRITE_COMPUTER_COMPONENT: IWriteComputerComponent = WriteComputerComponent

        val DEACTIVATOR: IAspectWriteDeactivator = EventHandler
        val ACTIVATOR: IAspectWriteActivator = EventHandler

        val KIND = "computer"

        val PROP_EXPORT_BOOLEAN = AspectPropertyTypeInstance(
            ValueTypes.BOOLEAN,
            "aspect.aspecttypes.ocid.boolean.export_boolean")

        val PROP_BOOLEAN_SET =
            IAspectValuePropagator<ApacheTriple<PartTarget, IAspectProperties, Boolean>, Unit> { input ->
                input.middle.getValue(PROP_EXPORT_BOOLEAN)?.rawValue?.let {
                    WRITE_COMPUTER_COMPONENT.exportBoolean(input.left, it)
                }
            }

        val PROPERTIES_COMPUTER = AspectProperties(listOf(
            PROP_EXPORT_BOOLEAN
        ))

        init {
            PROPERTIES_COMPUTER.setValue(PROP_EXPORT_BOOLEAN, ValueTypeBoolean.ValueBoolean.of(false))
        }

        val BUILDER_BOOLEAN
            = CyclopsAspectWriteBuilders.BUILDER_BOOLEAN
                .appendKind(KIND)
                .handle(CyclopsAspectWriteBuilders.PROP_GET_BOOLEAN)
                .appendDeactivator(DEACTIVATOR)
                .appendActivator(ACTIVATOR)
                .withProperties(PROPERTIES_COMPUTER);
    }
}