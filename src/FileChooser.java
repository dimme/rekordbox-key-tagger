import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Author: Dimitrios Vlastaras
 * Date..: 2013-03-13
 * Time..: 15:05
 */
public class FileChooser extends JFileChooser {

    /**
     * The constructor
     */
    public FileChooser() {
        super();
        this.setCurrentDirectory(new java.io.File("."));
        this.setDialogTitle("Choose Rekordbox playlist file...");
        this.setAcceptAllFileFilterUsed(false);
        this.setFileFilter(new RekordboxXMLFile());
        this.setApproveButtonText("Select");
    }

    /**
     * Private class for filtering the correct file type
     */
    private class RekordboxXMLFile extends FileFilter {

        // Type of file that should be display in JFileChooser
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
        }

        // Set description for the type of file that should be display
        public String getDescription() {
            return ".xml Rekordbox Playlist";
        }
    }
}

