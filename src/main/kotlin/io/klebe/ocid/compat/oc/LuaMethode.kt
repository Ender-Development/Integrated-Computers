package io.klebe.ocid.compat.oc

import li.cil.oc.api.machine.Arguments
import li.cil.oc.api.machine.Context

interface LuaMethode {
    val name: String
    val description: String
    val usage: String
    fun invoke(environment: Environment, context: Context, arguments: Arguments): Array<Any>
}