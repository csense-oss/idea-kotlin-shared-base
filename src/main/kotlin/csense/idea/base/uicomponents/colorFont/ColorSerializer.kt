package csense.idea.base.uicomponents.colorFont


import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.awt.*

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