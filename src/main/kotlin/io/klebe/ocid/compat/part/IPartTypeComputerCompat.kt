package io.klebe.ocid.compat.part

import io.klebe.ocid.compat.oc.LuaMethode

interface IPartTypeComputerCompat {
    val preferredName: String
    val luaMethods: Collection<LuaMethode>
}