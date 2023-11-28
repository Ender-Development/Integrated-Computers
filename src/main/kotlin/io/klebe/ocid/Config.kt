package io.klebe.ocid

import net.minecraftforge.common.config.Configuration

class Config(configuration: Configuration) {
    init {
        configuration.load()
    }

    val readerEnabled: Boolean = configuration.getBoolean("readerEnabled", "general", true, "Enable the Computer Reader")

    val writerEnabled: Boolean = configuration.getBoolean("writerEnabled", "general", true, "Enable the Computer Writer")

    init {
        configuration.save()
    }
}