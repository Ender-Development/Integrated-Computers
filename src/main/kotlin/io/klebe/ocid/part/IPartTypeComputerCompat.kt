package io.klebe.ocid.part

import io.klebe.ocid.oc.LuaMethode

interface IPartTypeComputerCompat {
    val preferredName: String
    val luaMethods: Collection<LuaMethode>
}