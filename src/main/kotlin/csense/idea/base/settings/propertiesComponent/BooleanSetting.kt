@file:Suppress("unused")

package csense.idea.base.settings.propertiesComponent

import com.intellij.ide.util.*
import kotlin.reflect.*


class BooleanSetting(
    private val backend: PropertiesComponent,
    private val settingsNamePrefix: String,
    private val postfixName: String = "",
    private val defaultValue: Boolean = true
) {
    private fun nameFor(property: KProperty<*>): String {
        return settingsNamePrefix + property.name + postfixName
    }
    
    operator fun getValue(prop: Any, property: KProperty<*>): Boolean {
        return backend.getBoolean(
            /* name = */ nameFor(property),
            /* defaultValue = */defaultValue
        )
    }

    operator fun setValue(prop: Any, property: KProperty<*>, newValue: Boolean) {
        backend.setValue(
            /* p0 = */ nameFor(property),
            /* p1 = */ newValue,
            /* p2 = */ defaultValue
        )
    }

}