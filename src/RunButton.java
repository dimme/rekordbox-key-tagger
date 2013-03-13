import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Dimitrios Vlastaras
 * Date..: 2013-03-13
 * Time..: 15:59
 */
public class RunButton extends JButton {


    public RunButton(ActionListener actionListener) {
        super("Run");

        this.setEnabled(false);
        this.setActionCommand("run");
        this.addActionListener(actionListener);
    }
}
