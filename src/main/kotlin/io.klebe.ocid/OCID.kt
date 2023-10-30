package io.klebe.ocid

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.Logger

@Mod(
        modid = OCID.MODID,
        name = OCID.NAME,
        version = OCID.VERSION,
        dependencies = OCID.DEPENDENCIES,
        modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object OCID {
    const val MODID = "ocid"
    const val NAME = "OCID"
    const val VERSION = "0.1.0"
    const val DEPENDENCIES = "required-after:forgelin;required-after:integrateddynamics;required-after:opencomputers"

    private lateinit var logger: Logger

    @SidedProxy(serverSide = "io.klebe.ocid.CommonProxy", clientSide = "io.klebe.ocid.ClientProxy")
    private lateinit var private_proxy: CommonProxy

    val log
        get() = logger

    val proxy
        get() = private_proxy

    @Mod.EventHandler fun preInit(e: FMLPreInitializationEvent) {
        this.logger = e.modLog
        MinecraftForge.EVENT_BUS.register(this.proxy)
        this.proxy.preInit(e)
    }

    @Mod.EventHandler fun init(e: FMLInitializationEvent) = this.proxy.init(e)
    @Mod.EventHandler fun postInit(e: FMLPostInitializationEvent) = this.proxy.postInit(e)
}