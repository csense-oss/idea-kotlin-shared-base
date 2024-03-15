@file:Suppress("unused")
@file:JvmName("ColorFontPanel")

package csense.idea.base.uicomponents.colorFont

import com.intellij.openapi.editor.markup.*
import com.intellij.openapi.ui.*
import com.intellij.ui.*
import com.intellij.uiDesigner.core.*
import com.intellij.util.xmlb.annotations.*
import csense.idea.base.uicomponents.colorFont.*
import csense.kotlin.extensions.*
import org.intellij.lang.annotations.*
import java.awt.*
import java.beans.*
import javax.swing.*
import javax.swing.border.*

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

    private val effectTypeBox: ComboBox<EffectType> = ComboBox()

    init {
        effectTypeBox.model = EnumComboBoxModel(type())
        setupGui()
    }

    private fun setupGui() {
        val panel1 = JPanel()
        val panel2 = JPanel()
        val hSpacer1 = Spacer()
        val hSpacer2 = Spacer()
        this.layout = GridLayoutManager(1, 1, Insets(0, 0, 0, 0), -1, -1)
        panel1.layout = GridLayoutManager(6, 2, Insets(0, 0, 0, 0), -1, -1)
        panel2.layout = GridLayoutManager(1, 4, Insets(0, 0, 0, 0), -1, -1)
        boldBox.text = "Bold"
        panel2.add(
            boldBox, GridConstraints(
                0, 1, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )

        italicBox.text = "Italic"
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
        foregroundBox.text = "Foreground"
        panel1.add(
            foregroundBox, GridConstraints(
                1, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )
        backgroundBox.text = "Background"
        panel1.add(
            backgroundBox, GridConstraints(
                2, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )
        errorStripeBox.text = "Error stripe mark"
        panel1.add(
            errorStripeBox, GridConstraints(
                3, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null
            )
        )
        effectsBox.text = "Effects"
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
                1, 1, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null
            )
        )
        this.add(
            panel1, GridConstraints(
                0, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null
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
        effectTypeBox.model.selectedItem = effectType
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

    private fun selectedEffectsType(): EffectType? =
        effectTypeBox.model.selectedItem as? EffectType

    private fun selectedFontTypes(): Int {
        var result = Font.PLAIN
        if (italicBox.isSelected) {
            result = result.or(Font.ITALIC)
        }
        if (boldBox.isSelected) {
            result = result.or(Font.BOLD)
        }
        return result
    }
}

