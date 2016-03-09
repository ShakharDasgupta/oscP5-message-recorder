/*
 * The MIT License
 *
 * Copyright 2016 Christopher Wells <cwellsny@nycap.rr.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package oscp5messagerecorder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 * The <code>OscP5Player</code> class is used to send previously recorded oscP5
 * messages to a set port.
 *
 * @author Christopher Wells <cwellsny@nycap.rr.com>
 */
public class OscP5Player {

    private final int port;
    private final File inputFile;

    private ArrayList<String[]> messages;

    /**
     * Initializes a <code>OscP5Player</code> object with the given port and
     * input file.
     *
     * @param port The port that the OscP5Player serves to.
     * @param inputFile The message input file that the OscP5Player reads from.
     */
    public OscP5Player(int port, File inputFile) {
        this.port = port;
        this.inputFile = inputFile;
        this.messages = null;
        this.importMessages();
        for (String[] message : this.messages) {
            System.out.println(String.format("%s : %s : %s", message[0], message[1], message[2]));
        }
    }
    
    /**
     * Imports the messages from the input file.
     */
    private void importMessages() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MessageImportHandler messageImportHandler = new MessageImportHandler();
            saxParser.parse(this.inputFile, messageImportHandler);
            
            this.messages = messageImportHandler.getMessages();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
