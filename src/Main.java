import javax.swing.UIManager;

/**
 * Author: Dimitrios Vlastaras
 * Date..: 2013-03-13
 * Time..: 17:41
 */

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        new MainWindow();
    }
}
