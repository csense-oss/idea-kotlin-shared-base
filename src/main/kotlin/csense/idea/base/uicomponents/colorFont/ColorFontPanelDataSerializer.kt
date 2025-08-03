package csense.idea.base.uicomponents.colorFont

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

class ColorFontPanelDataSerializer : KSerializer<ColorFontPanelData> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(
            "ColorFontPanelData",
            kind = kotlinx.serialization.descriptors.PrimitiveKind.STRING
        )

    private val json: Json = Json.Default

    override fun serialize(
        encoder: Encoder,
        value: ColorFontPanelData
    ) {
        encoder.encodeString(json.encodeToString(value))
    }

    override fun deserialize(decoder: Decoder): ColorFontPanelData {
        val input: String = decoder.decodeString()
        return json.decodeFromString<ColorFontPanelData>(input)
    }

}