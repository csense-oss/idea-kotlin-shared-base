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
    operator fun getValue(prop: Any, property: KProperty<*>): String {
        return backend.getValue(
            /* name = */ settingsNamePrefix + property.name + postfixName,
            /* defaultValue = */
            defaultValue
        )
    }

    operator fun setValue(prop: Any, property: KProperty<*>, newValue: String) {
        backend.setValue(
            /* p0 = */ settingsNamePrefix + property.name + postfixName,
            /* p1 = */ newValue,
            /* p2 = */ defaultValue
        )
    }
}
