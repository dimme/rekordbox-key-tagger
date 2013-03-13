import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import net.miginfocom.swing.MigLayout;

/**
 * Author: Dimitrios Vlastaras
 * Date..: 2013-03-11
 * Time..: 21:22
 */
public class MainWindow extends JFrame implements ActionListener {
    public FileChooser fileChooser;
    public JLabel statusLabel, filePathLabel;
    public JProgressBar progressBar;
    public SelectButton selectButton;
    public RunButton runButton;
    public File xmlFile;

    public MainWindow() {
        // The the title of the main window / frame
        super("Rekordbox Key ID3 Tagger");

        // We don't want the window to be resizable
        this.setResizable(false);


        // Variable to change the sizes of some elements
        Dimension preferredSize = null;

        // Get the container to add elements
        Container container = getContentPane();

        // Set it to MiG layout
        container.setLayout(new MigLayout());

        // Create the file chooser
        fileChooser = new FileChooser();

        // Add the file text and select button
        selectButton = new SelectButton(this);
        filePathLabel = new JLabel("File: No file selected");
        preferredSize = filePathLabel.getPreferredSize();
        preferredSize.width = 250;
        filePathLabel.setPreferredSize(preferredSize);
        container.add(filePathLabel, "span 3");
        container.add(selectButton, "right");

        // Add the run button
        runButton = new RunButton(this);
        container.add(runButton,"right,wrap");

        // Adding some vertical space
        container.add(new JComponent() {
            @Override
            public Dimension getPreferredSize() {
                Dimension dimension = new Dimension();
                dimension.height = 10;
                return dimension;
            }
        },"span,wrap");


        // Add the status label
        statusLabel = new JLabel("Select the location of the exported Rekordbox XML playlist file.");
        preferredSize = statusLabel.getPreferredSize();
        preferredSize.width = filePathLabel.getPreferredSize().width + selectButton.getPreferredSize().width + runButton.getPreferredSize().width;
        statusLabel.setPreferredSize(preferredSize);
        container.add(statusLabel, "left,span,wrap");

        // Add the progressBar
        progressBar = new JProgressBar();
        preferredSize = progressBar.getPreferredSize();
        preferredSize.width = filePathLabel.getPreferredSize().width + selectButton.getPreferredSize().width + runButton.getPreferredSize().width;
        progressBar.setPreferredSize(preferredSize);
        container.add(progressBar, "left,span,wrap");

        // When the window is closed the whoe program should close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Pack the window
        this.pack();

        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Move the window to the middle of the screen
        this.setLocation((screenSize.width - this.getSize().width) / 2, (screenSize.height - this.getSize().height) / 2);

        // Show the window
        this.setVisible(true);
    }

    /**
     * This gets called when an action happens
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        // Check whether the "Select" or "Run" button was pushed
        if ("select".equals(actionEvent.getActionCommand())) {
            int userChoice = fileChooser.showOpenDialog(this);

            // Check whether the user picked a file or canceled
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                xmlFile = fileChooser.getSelectedFile();
                filePathLabel.setText("File: " + xmlFile.getName());
                runButton.setEnabled(true);
            } else {
                xmlFile = null;
                filePathLabel.setText("File: No file selected");
                runButton.setEnabled(false);
            }
        } else {
            selectButton.setEnabled(false);
            runButton.setEnabled(false);

            progressBar.setStringPainted(true);

            // It should never be null but it doesn't hurt checking
            if (xmlFile == null){
                statusLabel.setText("Error: xmlFile is null");
            } else {
                // Start parsing thread
                (new Thread(new XMLtoMp3(this))).start();
            }
        }
    }
}
