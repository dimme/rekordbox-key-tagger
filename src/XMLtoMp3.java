import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import java.io.*;
import java.net.URLDecoder;

/**
 * Author: Dimitrios Vlastaras
 * Date..: 2013-03-13
 * Time..: 17:41
 */
public class XMLtoMp3 extends DefaultHandler implements Runnable {

    private MainWindow window;
    private int collectionEntries, parsedEntries, lessEntriesInCollection;

    public XMLtoMp3(MainWindow window) {
        // Set the local variables
        this.window = window;
        collectionEntries = 1;
        parsedEntries = 0;
        lessEntriesInCollection = 0;
    }

    @Override
    public void run() {

        try {
            // Fixing any invalid characters in the XML file
            window.statusLabel.setText("Fixing bad chars, this may take a while...");
            File xmlFile = filteredXMLFile();

            // Get a factory
            SAXParserFactory spf = SAXParserFactory.newInstance();

            // Get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            // Parse the file and also register this class for call backs
            sp.parse(xmlFile, this);

            // Delete the temporary XML file
            xmlFile.delete();

            // Change the status message
            window.statusLabel.setText("All MP3 tracks " + (lessEntriesInCollection > 0 ? "(but " + lessEntriesInCollection + " corrupted) " : "") + "have been updated!");

        } catch(Exception e) {
            window.statusLabel.setText("Error: " + e.getLocalizedMessage());
        }

        window.selectButton.setEnabled(true);
        window.progressBar.setStringPainted(false);
        window.progressBar.setValue(0);
        window.xmlFile = null;
        window.filePathLabel.setText("File: No file selected");
    }

    // Event Handler
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("TRACK") && attributes.getValue("Location") != null && attributes.getValue("Tonality") != null && attributes.getValue("Kind").equals("MP3 File")) {

            parsedEntries++;

            File mp3File = new File(attributes.getValue("Location"));
            String key = attributes.getValue("Tonality");

            updateMp3File(mp3File, key);

            String fileNameInfo;
            try {
                fileNameInfo = URLDecoder.decode(mp3File.getName(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new SAXException(e.getLocalizedMessage());
            }

            if (fileNameInfo.length() > 40)
                fileNameInfo = fileNameInfo.substring(0,40) + "...";

            window.statusLabel.setText("Updating: " + fileNameInfo);
            window.progressBar.setValue((int)(((float)parsedEntries / (float)collectionEntries) * 100));

        } else if (qName.equals("COLLECTION") && attributes.getValue("Entries") != null) {
            collectionEntries = Integer.parseInt(attributes.getValue("Entries")) - lessEntriesInCollection;
        }
    }

    /**
     * Updating the ID3 tag of an mp3 file with a given key
     * @param mp3File
     * @param key
     */
    private void updateMp3File(File mp3File, String key) {
        // TODO: Implement me tomorrow
        System.out.println("Updating yo!");
    }

    /**
     * Replacing nasty characters in naughty XML files
     * @throws IOException
     */
    private File filteredXMLFile() throws IOException {
        File cleanXMLFile = File.createTempFile("dont_delete", ".tmp");
        FileWriter fileWriter = new FileWriter(cleanXMLFile);

        Reader fileReader = new FileReader(window.xmlFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while(bufferedReader.ready()) {
            String line = bufferedReader.readLine();

            if (line.contains("\u0004"))
                lessEntriesInCollection++;
            else
                fileWriter.write(line + "\n");
        }

        fileWriter.close();
        bufferedReader.close();
        fileReader.close();

        return cleanXMLFile;
    }
}
