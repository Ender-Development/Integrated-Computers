package io.klebe.ocid.compat.id.part.aspect.read

import net.minecraft.nbt.NBTTagCompound
import org.cyclops.integrateddynamics.api.part.PartPos
import org.cyclops.integrateddynamics.api.part.PartTarget

interface IReadComputerComponent {
    fun provideBoolean(target: PartPos, value: Boolean)
    fun provideNumber(target: PartPos, value: Number)
    fun provideString(target: PartPos, value: String)
    fun provideNil(target: PartPos)

    operator fun get(target: PartPos): State?

    data class State(val type: Type, val value: Any) {
        enum class Type {
            BOOLEAN, NUMBER, STRING;

            val id: Byte
                get() = when (this) {
                    BOOLEAN -> BOOLEAN_ID
                    NUMBER -> NUMBER_ID
                    STRING -> STRING_ID
                }

            companion object {
                fun ofID(id: Byte) = when (id) {
                    BOOLEAN_ID -> BOOLEAN
                    NUMBER_ID-> NUMBER
                    STRING_ID -> STRING
                    else -> throw IllegalArgumentException("Unknown id $id")
                }

                // 0 Reserved
                const val BOOLEAN_ID = 1.toByte()
                const val NUMBER_ID = 2.toByte()
                const val STRING_ID = 3.toByte()
            }
        }

        companion object {
            fun of(value: Boolean) = State(Type.BOOLEAN, value)
            fun of(value: Number) = State(Type.NUMBER, value)
            fun of(value: String) = State(Type.STRING, value)
        }

        fun tryBoolean() = if (type == Type.BOOLEAN) value as Boolean else null
        fun tryNumber() = if (type == Type.NUMBER) value as Number else null
        fun tryString() = if (type == Type.STRING) value as String else null

        fun isBoolean() = type == Type.BOOLEAN
        fun isNumber() = type == Type.NUMBER
        fun isString() = type == Type.STRING
    }
}