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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MILLIS;
import java.util.ArrayList;
import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;

/**
 * The <code>OscP5Recorder</code> class is used to record oscP5 messages and
 * store them in xml files.
 *
 * @author Christopher Wells <cwellsny@nycap.rr.com>
 */
public class OscP5Recorder implements OscEventListener {

    private final int port;
    private final File outputFile;
    private final ArrayList<String> channels;
    private final int recordInterval;

    private int recordCount;

    private final ArrayList<String[]> messages;
    private final OscP5 server;
    private final LocalTime startTime;

    /**
     * Instantiates a <code>OscP5Recorder</code> with the given port and output
     * file.
     *
     * @param port The port of the oscP5 server.
     * @param outputFile The output file that the oscP5 messages will be written
     * to.
     * @param channels The channels that will be recorded.
     * @param recordInterval The number of messages that will be recorded at a
     * time.
     */
    public OscP5Recorder(int port, File outputFile, ArrayList<String> channels, int recordInterval) {
        this.port = port;
        this.outputFile = outputFile;
        this.channels = channels;
        this.recordCount = 0;
        this.recordInterval = recordInterval;
        this.messages = new ArrayList<>();
        this.startTime = LocalTime.now();
        this.server = new OscP5(this, this.port);
    }

    /**
     * Saves the recorded messages to the xml file. Uses the following format:
     * <br><br>
     * <code>channel</code> - The channel of the message.
     * <br><br>
     * <code>time</code> - The time of the message since the start point in
     * milliseconds.
     * <br><br>
     * <code>value</code> - The value of the message.
     */
    private void saveMessages() {
        // Construct the contents of the file
        String fileContents = "";
        fileContents += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        fileContents += "<oscp5messages>\n";
        for (String[] message : this.messages) {
            fileContents += String.format("<message channel=\"%s\" time=\"%s\" value=\"%s\" />\n", message[0], message[1], message[2]);
        }
        fileContents += "</oscp5messages>\n";

        // Write to the file
        BufferedWriter out = null;
        try {
            FileWriter fileWriter = new FileWriter(this.outputFile);
            out = new BufferedWriter(fileWriter);
            out.write(fileContents);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * Records the wanted oscP5 messages as they are received.
     *
     * @param om The received oscP5 message.
     */
    @Override
    public void oscEvent(OscMessage om) {
        for (String channel : this.channels) {
            if (om.checkAddress(channel)) {
                this.messages.add(new String[]{channel, Long.toString(this.startTime.until(LocalTime.now(), MILLIS)), Integer.toString(om.get(0).intValue())});
                if (this.recordCount == this.recordInterval) {
                    this.recordCount = 0;
                    this.saveMessages();
                } else {
                    this.recordCount++;
                }
            }
        }
    }
}
