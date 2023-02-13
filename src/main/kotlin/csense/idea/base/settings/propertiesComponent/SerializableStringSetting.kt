@file:Suppress("unused")

package csense.idea.base.settings.propertiesComponent

import com.intellij.ide.util.*
import kotlin.reflect.*


class SerializableStringSetting<T>(
    backend: PropertiesComponent,
    settingsNamePrefix: String,
    postfixName: String = "",
    defaultValue: String = "",
    private val deserialize: (String) -> T,
    private val serialize: (T) -> String
) {
    private var storage: String by StringSetting(
        backend = backend,
        settingsNamePrefix = settingsNamePrefix,
        postfixName = postfixName,
        defaultValue = defaultValue
    )

    operator fun getValue(prop: Any, property: KProperty<*>): T {
        return deserialize(storage)
    }

    operator fun setValue(prop: Any, property: KProperty<*>, newValue: T) {
        storage = serialize(newValue)
    }
}
