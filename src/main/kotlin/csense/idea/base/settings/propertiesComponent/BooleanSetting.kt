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
    operator fun getValue(prop: Any, property: KProperty<*>): Boolean {
        return backend.getBoolean(
            /* name = */ settingsNamePrefix + property.name + postfixName,
            /* defaultValue = */defaultValue
        )
    }

    operator fun setValue(prop: Any, property: KProperty<*>, newValue: Boolean) {
        backend.setValue(
            /* p0 = */ settingsNamePrefix + property.name + postfixName,
            /* p1 = */ newValue,
            /* p2 = */ defaultValue
        )
    }

}