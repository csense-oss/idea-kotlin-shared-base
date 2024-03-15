@file:Suppress("unused")
@file:UseSerializers(ColorSerializer::class)

package csense.idea.base.uicomponents.colorFont

import com.intellij.openapi.editor.markup.*
import com.intellij.util.xmlb.annotations.*
import kotlinx.serialization.*
import org.intellij.lang.annotations.*
import java.awt.*

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class ColorFontPanelData(

    var foregroundColor: Color? = null,

    var backgroundColor: Color? = null,

    var errorStripeColor: Color? = null,

    var effectColor: Color? = null,

    var effectType: EffectType? = null,

    @MagicConstant(flags = [Font.PLAIN.toLong(), Font.BOLD.toLong(), Font.ITALIC.toLong()])
    var fontType: Int = Font.PLAIN
)

fun ColorFontPanelData.toTextAttributes(): TextAttributes = TextAttributes(
    /* foregroundColor = */ foregroundColor,
    /* backgroundColor = */ backgroundColor,
    /* effectColor = */ effectColor,
    /* effectType = */ effectType,
    /* fontType = */  fontType
)
