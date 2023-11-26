package io.klebe.ocid.compat.id.evaluate.variable

import org.cyclops.integrateddynamics.api.evaluate.variable.IValue
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueBase
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeDouble.ValueDouble
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeInteger.ValueInteger
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeLong.ValueLong
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypes

class ValueNumber private constructor(val value: Number) : ValueBase(ValueTypes.CATEGORY_NUMBER) {
    companion object {
        fun of(value: Number): ValueNumber = ValueNumber(value)
        fun of(value: IValue): ValueNumber = when (value) {
            is ValueInteger -> ValueNumber(value.rawValue)
            is ValueLong -> ValueNumber(value.rawValue)
            is ValueDouble -> ValueNumber(value.rawValue)
            else -> throw IllegalArgumentException("Cannot convert $value to a ValueNumber")
        }
    }

    fun getRawValue(): Number = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ValueNumber) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "ValueNumber(value=$value)"
    }

}
