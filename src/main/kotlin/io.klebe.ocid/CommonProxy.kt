package io.klebe.ocid

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

open class CommonProxy {
    open fun preInit(e: FMLPreInitializationEvent) {
        OCID.log.info("preInit")
    }

    open fun init(e: FMLInitializationEvent) {
        OCID.log.info("init")
    }

    open fun postInit(e: FMLPostInitializationEvent) {
        OCID.log.info("postInit")
    }
}