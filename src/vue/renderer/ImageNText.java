package vue.renderer;

import javax.swing.Icon;

/** Classe pour le type img + name
 * @author ESCUDERO Thomas
 * @version 1.0
 */
public class ImageNText {
        private Icon img;
        private String name;

        public ImageNText(Icon img, String name) {
                this.img = img;
                this.name = name;
        }

        public Icon getImage() {
                return this.img;
        }

        public void setImage(Icon img) {
                this.img = img;
        }

        public String getName() {
                return this.name;
        }

        public void setName(String name) {
                this.name = name;
        }
}