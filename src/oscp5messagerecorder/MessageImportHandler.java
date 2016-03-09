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

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The <code>MessageImportHandler</code> class is used to extract recorded oscP5
 * messages from xml files.
 *
 * @author Christopher Wells <cwellsny@nycap.rr.com>
 */
public class MessageImportHandler extends DefaultHandler {

    private final ArrayList<String[]> messages;

    /**
     * Initializes a <code>MessageImportHandler</code> with an empty ArrayList
     * of messages.
     */
    public MessageImportHandler() {
        this.messages = new ArrayList();
    }

    /**
     * Returns the messages extracted from the input file.
     *
     * @return The messages extracted from the input file.
     */
    public ArrayList<String[]> getMessages() {
        return this.messages;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "message":
                String channel = attributes.getValue("channel");
                String time = attributes.getValue("time");
                String value = attributes.getValue("value");
                this.messages.add(new String[]{channel, time, value});
                break;
        }
    }
}
