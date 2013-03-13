import javax.swing.*;
import javax.xml.transform.sax.SAXSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Dimitrios Vlastaras
 * Date..: 2013-03-13
 * Time..: 15:59
 */
public class SelectButton  extends JButton {


    public SelectButton(ActionListener actionListener) {
        super("Select");

        this.setActionCommand("select");
        this.addActionListener(actionListener);
    }
}