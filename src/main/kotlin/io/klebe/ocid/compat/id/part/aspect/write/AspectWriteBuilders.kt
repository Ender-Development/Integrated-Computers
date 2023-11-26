package io.klebe.ocid.compat.id.part.aspect.write

import io.klebe.ocid.OCID
import io.klebe.ocid.compat.id.evaluate.variable.ValueNumber
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.cyclops.integrateddynamics.api.evaluate.variable.IValue
import org.cyclops.integrateddynamics.api.evaluate.variable.IValueType
import org.cyclops.integrateddynamics.api.part.PartTarget
import org.cyclops.integrateddynamics.api.part.aspect.property.IAspectProperties
import org.cyclops.integrateddynamics.api.part.write.IPartStateWriter
import org.cyclops.integrateddynamics.api.part.write.IPartTypeWriter
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypes
import org.cyclops.integrateddynamics.core.part.aspect.build.AspectBuilder
import org.cyclops.integrateddynamics.core.part.aspect.build.IAspectValuePropagator
import org.cyclops.integrateddynamics.core.part.aspect.build.IAspectWriteActivator
import org.cyclops.integrateddynamics.core.part.aspect.build.IAspectWriteDeactivator
import org.cyclops.integrateddynamics.part.aspect.write.AspectWriteBuilders as CyclopsAspectWriteBuilders
import org.apache.commons.lang3.tuple.Triple as ApacheTriple

object AspectWriteBuilders {

    object Computer {

        @Suppress("UNUSED_PARAMETER")
        object EventHandler : IAspectWriteDeactivator, IAspectWriteActivator {

            private fun <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> onTileEntityTarget(
                tileEntity: TileEntity,
                partType: P,
                target: PartTarget,
                partState: S,
                activation: Boolean,
            ){

            }

            private fun <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> onBlockTarget(
                blockState: IBlockState,
                partType: P,
                target: PartTarget,
                partState: S,
                activation: Boolean,
            ){
                val targetPos: BlockPos = target.target.pos.blockPos
                val world: World = target.target.pos.world!!
                val myPos: BlockPos = target.center.pos.blockPos!!

                blockState.neighborChanged(
                    world,
                    targetPos,
                    world.getBlockState(myPos).block,
                    myPos
                )
            }

            private inline fun  <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> alwaysAnd(
                partType: P,
                target: PartTarget,
                partState: S,
                activation: Boolean,
                crossinline block: ()->Unit
            ){
                // GitHub Copilot:
                // q: is an intellisense error here?
                // a: yes, but it's a bug in intellisense, not in the code
                // q: how do you know?
                // a: because it compiles
                // q: how can i fix it?
                // a: you can't, you just have to live with it
                // q: where comes the error from?
                // a: the error comes from the fact that the compiler can't infer the type of the lambda
                // q: why can't the compiler infer the type of the lambda?
                // a: because the compiler is stupid
                // q: 'target.target.pos.world' intellisense means 'target.target.pos.world' is not accessible
                // q: "Cannot access class 'net.minecraft.world.World'. Check your module classpath for missing or conflicting dependencies"
                // a: that's a bug in intellisense, not in the code
                // q: i know but why does it happen?
                // a: because the compiler is stupid
                // q: that doesn't get me any further
                // a: i know
                // ¯\_(ツ)_/¯
                val world: World = target.target.pos.world!!
                val pos: BlockPos = target.target.pos.blockPos!!

                world.getBlockState(pos).let{bs ->
                    if (bs.block != Blocks.AIR) {
                        onBlockTarget(bs, partType, target, partState, activation)
                    }
                }

                world.getTileEntity(pos)?.let {te -> onTileEntityTarget(te, partType, target, partState, activation) }

                block()
            }

            override fun <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> onDeactivate(
                partType: P,
                target: PartTarget,
                p2: S
            ) = alwaysAnd(partType, target, p2, false) {
                OCID.log.info(this.javaClass.name + "::onDeactivate called") // TODO
                WriteComputerComponent.states.remove(target.center)
            }

            override fun <P : IPartTypeWriter<P, S>?, S : IPartStateWriter<P>?> onActivate(
                partType: P,
                target: PartTarget,
                p2: S
            ) = alwaysAnd(partType, target, p2, true) {
                OCID.log.info(this.javaClass.name + "::onActivate called") // TODO
            }
        }

        val WRITE_COMPUTER_COMPONENT: IWriteComputerComponent = WriteComputerComponent

        val DEACTIVATOR: IAspectWriteDeactivator = EventHandler
        val ACTIVATOR: IAspectWriteActivator = EventHandler

        val KIND = "computer"

        val PROP_BOOLEAN_SET =
            IAspectValuePropagator<ApacheTriple<PartTarget, IAspectProperties, Boolean>, Unit> { input ->
                WRITE_COMPUTER_COMPONENT.exportBoolean(input.left, input.right)
            }

        val PROP_NUMBER_SET =
            IAspectValuePropagator<ApacheTriple<PartTarget, IAspectProperties, Number>, Unit> { input ->
                WRITE_COMPUTER_COMPONENT.exportNumber(input.left, input.right)
            }

        val PROP_STRING_SET =
            IAspectValuePropagator<ApacheTriple<PartTarget, IAspectProperties, String>, Unit> { input ->
                WRITE_COMPUTER_COMPONENT.exportString(input.left, input.right)
            }

        val PROP_NBT_SET =
            IAspectValuePropagator<ApacheTriple<PartTarget, IAspectProperties, NBTTagCompound>, Unit> { input ->
                WRITE_COMPUTER_COMPONENT.exportNBT(input.left, input.right)
            }

        private fun <V: IValue, T: IValueType<V>, O> AspectBuilder<V, T, O>.common(): AspectBuilder<V, T, O> = this
                .appendKind(KIND)
                .appendDeactivator(DEACTIVATOR)
                .appendActivator(ACTIVATOR)

        val BUILDER_BOOLEAN
            = CyclopsAspectWriteBuilders.BUILDER_BOOLEAN
                .handle(CyclopsAspectWriteBuilders.PROP_GET_BOOLEAN)
                .handle(PROP_BOOLEAN_SET)
                .common()

        val BUILDER_NUMBER
            = CyclopsAspectWriteBuilders.getValue(AspectBuilder.forWriteType(ValueTypes.CATEGORY_NUMBER))
                .handle { input ->
                    ApacheTriple.of(input.left,  input.middle, ValueNumber.of(input.right).getRawValue())
                }
                .handle(PROP_NUMBER_SET)
                .common()

        val BUILDER_STRING
            = CyclopsAspectWriteBuilders.BUILDER_STRING
                .handle(CyclopsAspectWriteBuilders.PROP_GET_STRING)
                .handle(PROP_STRING_SET)
                .common()

        val BUILDER_NBT
            = CyclopsAspectWriteBuilders.BUILDER_NBT
                .handle(CyclopsAspectWriteBuilders.PROP_GET_NBT)
                .handle(PROP_NBT_SET)
                .common()
    }
}