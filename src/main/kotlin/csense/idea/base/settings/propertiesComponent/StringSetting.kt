@file:Suppress("unused")

package csense.idea.base.settings.propertiesComponent

import com.intellij.ide.util.*
import kotlin.reflect.*


class StringSetting(
    private val backend: PropertiesComponent,
    private val settingsNamePrefix: String,
    private val postfixName: String = "",
    private val defaultValue: String = ""
) {

    private fun nameFor(property: KProperty<*>): String {
        return settingsNamePrefix + property.name + postfixName
    }

    operator fun getValue(prop: Any, property: KProperty<*>): String {
        return backend.getValue(
            /* name = */ nameFor(property),
            /* defaultValue = */ defaultValue
        )
    }

    operator fun setValue(prop: Any, property: KProperty<*>, newValue: String) {
        backend.setValue(
            /* name = */ nameFor(property),
            /* newValue = */ newValue,
            /* defaultValue = */ defaultValue
        )
    }
}