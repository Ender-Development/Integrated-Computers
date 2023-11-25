package io.klebe.ocid

import io.klebe.ocid.part.PartTypeComputerWriter
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.cyclops.cyclopscore.client.key.IKeyRegistry
import org.cyclops.cyclopscore.init.ModBase
import org.cyclops.cyclopscore.network.PacketHandler
import org.cyclops.cyclopscore.proxy.CommonProxyComponent
import org.cyclops.cyclopscore.proxy.ICommonProxy
import org.cyclops.integrateddynamics.IntegratedDynamics
import org.cyclops.integrateddynamics.api.part.IPartTypeRegistry


open class CommonProxy : CommonProxyComponent() {
    open fun preInit(e: FMLPreInitializationEvent) {
        OCID.log.info("preInit")
        IntegratedDynamics._instance.registryManager.getRegistry(IPartTypeRegistry::class.java)?.let { registry ->
            registry.register(PartTypeComputerWriter)
        }
    }

    open fun init(e: FMLInitializationEvent) {
        OCID.log.info("init")
    }

    open fun postInit(e: FMLPostInitializationEvent) {
        OCID.log.info("postInit")
    }

    override fun getMod(): ModBase = OCID

}