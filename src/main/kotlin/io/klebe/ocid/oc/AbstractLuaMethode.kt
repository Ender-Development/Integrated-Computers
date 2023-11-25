package io.klebe.ocid.oc

abstract class AbstractLuaMethode(
    override val name: String,
    override val description: String,
    override val usage: String
) : LuaMethode {
    override fun toString(): String {
        return "LuaMethode(name='$name', description='$description', usage='$usage')"
    }
}