package io.klebe.ocid.part.aspect

import org.cyclops.integrateddynamics.api.evaluate.variable.IValue
import org.cyclops.integrateddynamics.api.evaluate.variable.IValueType
import org.cyclops.integrateddynamics.api.part.aspect.IAspectWrite
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeBoolean
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeCategoryAny
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeDouble
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeInteger
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeList
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeLong
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeNbt
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeOperator
import org.cyclops.integrateddynamics.core.evaluate.variable.ValueTypeString

typealias BooleanAspectWrite = IAspectWrite<ValueTypeBoolean.ValueBoolean, ValueTypeBoolean>
typealias IntegerAspectWrite = IAspectWrite<ValueTypeInteger.ValueInteger, ValueTypeInteger>
typealias DoubleAspectWrite = IAspectWrite<ValueTypeDouble.ValueDouble, ValueTypeDouble>
typealias StringAspectWrite = IAspectWrite<ValueTypeString.ValueString, ValueTypeString>
typealias LongAspectWrite = IAspectWrite<ValueTypeLong.ValueLong, ValueTypeLong>
typealias NbtAspectWrite = IAspectWrite<ValueTypeNbt.ValueNbt, ValueTypeNbt>
typealias OperatorAspectWrite = IAspectWrite<ValueTypeOperator.ValueOperator, ValueTypeOperator>

typealias ListAspectWrite = IAspectWrite<ValueTypeList.ValueList<ValueTypeCategoryAny, IValue>, IValueType<ValueTypeList.ValueList<ValueTypeCategoryAny, IValue>>>
