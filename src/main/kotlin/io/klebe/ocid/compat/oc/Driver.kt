package io.klebe.ocid.compat.oc

import io.klebe.ocid.compat.part.IPartTypeComputerCompat
import li.cil.oc.api.driver.DriverBlock
import li.cil.oc.api.network.ManagedEnvironment
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.cyclops.integrateddynamics.api.part.IPartType
import org.cyclops.integrateddynamics.block.BlockCable
import org.cyclops.integrateddynamics.core.tileentity.TileMultipartTicking

object Driver : DriverBlock {
    override fun worksWith(world: World, pos: BlockPos, facing: EnumFacing): Boolean {
        val blockState = world.getBlockState(pos)
        val tileEntity = world.getTileEntity(pos)

        if (blockState.block == BlockCable.getInstance() && tileEntity is TileMultipartTicking) {
            return tileEntity.partContainer?.parts?.get(facing.opposite) is IPartTypeComputerCompat
        }

        return false
    }

    override fun createEnvironment(world: World, pos: BlockPos, facing: EnumFacing): ManagedEnvironment? {
        val blockState = world.getBlockState(pos)
        val tileEntity = world.getTileEntity(pos)

        if (blockState.block == BlockCable.getInstance() && tileEntity is TileMultipartTicking) {
            val possiblePart: IPartType<*, *>? = tileEntity.partContainer?.parts?.get(facing.opposite)
            return possiblePart?.let { part ->
                if (part is IPartTypeComputerCompat) {
                    Environment(tileEntity, part, facing.opposite)
                } else null
            }
        }

        return null
    }
}