@file:Suppress("unused", "UnstableApiUsage")
@file:JvmName("ColorFontPanel")

package csense.idea.base.uicomponents.colorFont

import com.intellij.*
import com.intellij.openapi.editor.markup.*
import com.intellij.openapi.ui.*
import com.intellij.ui.*
import com.intellij.uiDesigner.core.*
import csense.kotlin.extensions.*
import java.awt.*
import java.util.*
import javax.swing.*

class ColorFontPanel : JPanel() {

    private val boldBox: JCheckBox = JCheckBox()
    private val italicBox: JCheckBox = JCheckBox()

    private val foregroundBox: JCheckBox = JCheckBox()
    private val foregroundColorBox: ColorPanel = ColorPanel()

    private val backgroundBox: JCheckBox = JCheckBox()
    private val backgroundColorBox: ColorPanel = ColorPanel()

    private val errorStripeBox: JCheckBox = JCheckBox()
    private val errorStripeColorBox: ColorPanel = ColorPanel()

    private val effectsBox: JCheckBox = JCheckBox()
    private val effectsColorBox: ColorPanel = ColorPanel()

    private val effectTypeBox: ComboBox<OurEffectType> = ComboBox()

    init {
        effectTypeBox.model = EnumComboBoxModel(type())
        setupGui()
    }

    private fun setupGui() {
        val bundle: ResourceBundle =
            DynamicBundle.getResourceBundle(this::class.java.classLoader, "texts/ColorFontPanel")
        val panel1 = JPanel()
        val panel2 = JPanel()
        val hSpacer1 = Spacer()
        val hSpacer2 = Spacer()
        this.layout = GridLayoutManager(1, 1, Insets(0, 0, 0, 0), -1, -1)
        panel1.layout = GridLayoutManager(6, 2, Insets(0, 0, 0, 0), -1, -1)
        panel2.layout = GridLayoutManager(1, 4, Insets(0, 0, 0, 0), -1, -1)
        boldBox.text = bundle.getString("bold")
        panel2.add(
            boldBox, GridConstraints(
                0, 1, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )

        italicBox.text = bundle.getString("italic")
        panel2.add(
            italicBox, GridConstraints(
                0, 2, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )
        panel2.add(
            hSpacer1, GridConstraints(
                0, 3, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_GROW or GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK,
                null, null, null
            )
        )
        panel2.add(
            hSpacer2, GridConstraints(
                0, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_GROW or GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK,
                null, null, null
            )
        )
        panel1.add(
            panel2, GridConstraints(
                0, 0, 1, 2,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null
            )
        )
        foregroundBox.text = bundle.getString("foreground")
        panel1.add(
            foregroundBox, GridConstraints(
                1, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )
        backgroundBox.text = bundle.getString("background")
        panel1.add(
            backgroundBox, GridConstraints(
                2, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )
        errorStripeBox.text = bundle.getString("error_stripe_mark")
        panel1.add(
            errorStripeBox, GridConstraints(
                3, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )
        effectsBox.text = bundle.getString("effects")
        panel1.add(
            effectsBox, GridConstraints(
                4, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )
        effectTypeBox.model = EnumComboBoxModel(type())
        panel1.add(
            effectTypeBox, GridConstraints(
                5, 0, 1, 2,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )
        panel1.add(
            backgroundColorBox, GridConstraints(
                2, 1, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null
            )
        )
        panel1.add(
            errorStripeColorBox, GridConstraints(
                3, 1, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null
            )
        )
        panel1.add(
            effectsColorBox, GridConstraints(
                4, 1, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null
            )
        )
        panel1.add(
            foregroundColorBox, GridConstraints(
                /* row = */ 1, /* column = */ 1, /* rowSpan = */ 1, /* colSpan = */ 1,
                /* anchor = */ GridConstraints.ANCHOR_CENTER, /* fill = */ GridConstraints.FILL_NONE,
                /* HSizePolicy = */ GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                /* VSizePolicy = */ GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                /* minimumSize = */ null, /* preferredSize = */ null, /* maximumSize = */ null
            )
        )
        this.add(
            panel1, GridConstraints(
                /* row = */ 0, /* column = */ 0, /* rowSpan = */ 1, /* colSpan = */ 1,
                /* anchor = */ GridConstraints.ANCHOR_CENTER, /* fill = */ GridConstraints.FILL_BOTH,
                /* HSizePolicy = */ GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                /* VSizePolicy = */ GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                /* minimumSize = */ null, /* preferredSize = */ null, /* maximumSize = */ null
            )
        )
    }


    fun getData(): ColorFontPanelData = ColorFontPanelData(
        foregroundColor = selectedForegroundColor(),
        backgroundColor = selectedBackgroundColor(),
        errorStripeColor = selectedErrorStripeColor(),
        effectColor = selectedEffectsColor(),
        effectType = selectedEffectsType(),
        fontType = selectedFontTypes()
    )

    fun updateWithData(data: ColorFontPanelData) {
        updateForegroundBy(data.foregroundColor)
        updateBackgroundBy(data.backgroundColor)
        updateFontBy(data.fontType)
        updateErrorStripeBy(data.errorStripeColor)
        updateEffectBy(data.effectColor, data.effectType)
    }

    private fun updateFontBy(
        fontType: Int
    ) {
        boldBox.isSelected = fontType.and(Font.BOLD) != 0
        italicBox.isSelected = fontType.and(Font.ITALIC) != 0
    }

    private fun updateForegroundBy(foregroundColor: Color?) {
        updateBoxAndColorBy(checkbox = foregroundBox, colorPanel = foregroundColorBox, color = foregroundColor)
    }

    private fun updateBackgroundBy(background: Color?) {
        updateBoxAndColorBy(checkbox = backgroundBox, colorPanel = backgroundColorBox, color = background)
    }

    private fun updateErrorStripeBy(errorStripeColor: Color?) {
        updateBoxAndColorBy(checkbox = errorStripeBox, colorPanel = errorStripeColorBox, color = errorStripeColor)
    }

    private fun updateEffectBy(effectColor: Color?, effectType: EffectType?) {
        updateBoxAndColorBy(checkbox = effectsBox, colorPanel = effectsColorBox, color = effectColor)
        updateEffectType(effectType)
    }

    private fun updateEffectType(effectType: EffectType?) {
        if (effectType == null) {
            effectTypeBox.model.selectedItem = null
            return
        }
        effectTypeBox.model.selectedItem = OurEffectType.fromEffectType(effectType)
    }


    private fun updateBoxAndColorBy(
        checkbox: JCheckBox,
        colorPanel: ColorPanel,
        color: Color?
    ) {
        checkbox.isSelected = color != null
        colorPanel.selectedColor = color
    }

    private fun selectedForegroundColor(): Color? =
        foregroundColorBox.colorOrNullBy(foregroundBox)

    private fun selectedBackgroundColor(): Color? =
        backgroundColorBox.colorOrNullBy(backgroundBox)

    private fun selectedErrorStripeColor(): Color? =
        errorStripeColorBox.colorOrNullBy(errorStripeBox)


    private fun selectedEffectsColor(): Color? =
        effectsColorBox.colorOrNullBy(effectsBox)

    private fun selectedEffectsType(): EffectType? {
        val our: OurEffectType? = effectTypeBox.model.selectedItem as? OurEffectType
        return our?.toEffectType()
    }

    private fun selectedFontTypes(): Int {
        var result: Int = Font.PLAIN
        if (italicBox.isSelected) {
            result = result.or(Font.ITALIC)
        }
        if (boldBox.isSelected) {
            result = result.or(Font.BOLD)
        }
        return result
    }
}


enum class OurEffectType {
    LINE_UNDERSCORE,
    WAVE_UNDERSCORE,
    BOXED,
    STRIKEOUT,
    BOLD_LINE_UNDERSCORE,
    BOLD_DOTTED_LINE,
    SEARCH_MATCH,
    ROUNDED_BOX,
    SLIGHTLY_WIDER_BOX;


    private val bundle: ResourceBundle = DynamicBundle.getResourceBundle(
        this::class.java.classLoader,
        "texts/ColorFontPanel"
    )

    override fun toString(): String {
        return bundle.getString("EffectType.$name")
    }

    fun toEffectType(): EffectType = when (this) {
        LINE_UNDERSCORE -> EffectType.LINE_UNDERSCORE
        WAVE_UNDERSCORE -> EffectType.WAVE_UNDERSCORE
        BOXED -> EffectType.BOXED
        STRIKEOUT -> EffectType.STRIKEOUT
        BOLD_LINE_UNDERSCORE -> EffectType.BOLD_LINE_UNDERSCORE
        BOLD_DOTTED_LINE -> EffectType.BOLD_DOTTED_LINE
        SEARCH_MATCH -> EffectType.SEARCH_MATCH
        ROUNDED_BOX -> EffectType.ROUNDED_BOX
        SLIGHTLY_WIDER_BOX -> EffectType.SLIGHTLY_WIDER_BOX
    }

    companion object {
        fun fromEffectType(type: EffectType): OurEffectType = when (type) {
            EffectType.LINE_UNDERSCORE -> LINE_UNDERSCORE
            EffectType.WAVE_UNDERSCORE -> WAVE_UNDERSCORE
            EffectType.BOXED -> BOXED
            EffectType.STRIKEOUT -> STRIKEOUT
            EffectType.BOLD_LINE_UNDERSCORE -> BOLD_LINE_UNDERSCORE
            EffectType.BOLD_DOTTED_LINE -> BOLD_DOTTED_LINE
            EffectType.SEARCH_MATCH -> SEARCH_MATCH
            EffectType.ROUNDED_BOX -> ROUNDED_BOX
            EffectType.SLIGHTLY_WIDER_BOX -> SLIGHTLY_WIDER_BOX
        }
    }
}