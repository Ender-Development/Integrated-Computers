package io.klebe.ocid.compat.part

import io.klebe.ocid.compat.oc.LuaMethode

interface IComputerPart {
    val preferredName: String
    val luaMethods: Collection<LuaMethode>
}