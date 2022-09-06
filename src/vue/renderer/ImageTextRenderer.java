package vue.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/** Inserer le texte et l'image dans la combobox
 * @author ESCUDERO Thomas
 * @version 1.0
 */

public class ImageTextRenderer extends JLabel implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object val,
			int index, boolean selected, boolean focused) {
		// obtenir les valeurs
		ImageNText it = (ImageNText)val;

		// mettre a� jour les valeurs
		setIcon(it.getImage());
		setText(it.getName());

		if(selected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setFont(list.getFont());

		return this;

	}
}
