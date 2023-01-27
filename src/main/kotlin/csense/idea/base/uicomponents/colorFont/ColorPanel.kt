package csense.idea.base.uicomponents.colorFont

import com.intellij.ui.*
import java.awt.*
import javax.swing.*

fun ColorPanel.colorOrNullBy(checkbox: JCheckBox): Color? = when {
    checkbox.isSelected -> this.selectedColor
    else -> null
}