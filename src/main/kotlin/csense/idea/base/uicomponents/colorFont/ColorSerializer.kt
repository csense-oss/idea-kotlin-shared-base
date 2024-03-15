package csense.idea.base.uicomponents.colorFont

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.awt.Color

class ColorSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "AwtColor",
        kind = PrimitiveKind.INT
    )

    override fun deserialize(decoder: Decoder): Color {
        return Color(/* rgb = */ decoder.decodeInt())
    }

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeInt(value = value.rgb)
    }
}