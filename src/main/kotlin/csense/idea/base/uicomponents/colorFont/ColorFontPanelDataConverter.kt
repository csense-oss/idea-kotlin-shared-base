@file:Suppress("unused")

package csense.idea.base.uicomponents.colorFont

import com.intellij.util.xmlb.*
import csense.idea.base.uicomponents.colorFont.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class ColorFontPanelDataConverter : Converter<ColorFontPanelData>() {

    private val serializer: KSerializer<ColorFontPanelData> by lazy {
        ColorFontPanelData.serializer()
    }
    private val json = Json
    override fun fromString(value: String): ColorFontPanelData {
        return json.decodeFromString(deserializer = serializer, string = value)
    }

    override fun toString(value: ColorFontPanelData): String {
        return json.encodeToString(serializer = serializer, value = value)
    }
}
