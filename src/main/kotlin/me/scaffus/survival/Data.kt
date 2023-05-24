package me.scaffus.survival

class Data(private val plugin: Survival) {
    private var prefix: String? = null

    init {
        prefix = plugin.config.getString("prefix")
    }


    fun getPrefix(): String? {
        return prefix
    }
}