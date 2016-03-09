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
import java.util.ArrayList;

/**
 * The <code>Main</code> class is used to run the program.
 *
 * @author Christopher Wells <cwellsny@nycap.rr.com>
 */
public class Main {

    /**
     * Runs the program using the information given via the command line
     * arguments.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        final int port;
        final String outputFileLocation;
        final ArrayList<String> channels;
        final int recordInterval = 5;

        if (args.length == 0 || (args.length == 1 && args[0].equals("--help"))) {
            usage();
        } else if (args[0].equals("record")) {
            if (args.length >= 4) {
                port = Integer.parseInt(args[1]);
                outputFileLocation = args[2];
                File outputFile = new File(outputFileLocation);

                // Get all of the wanted channels
                channels = new ArrayList<>();
                for (int i = 3; i < args.length; i++) {
                    channels.add(args[i]);
                }

                // Start recording the messages
                OscP5Recorder oscP5Recorder = new OscP5Recorder(port, outputFile, channels, recordInterval);
            } else {
                System.err.println("Invalid number of parameters for recording. Try running with the '--help' flag for usage information.");
                System.exit(1);
            }
        } else {
            usage();
        }

    }

    /**
     * Prints information on the usage of the program.
     */
    public static void usage() {
        System.out.println("Usage: java -jar OscP5MessageRecorder.jar record PORT OUTPUTFILE [CHANNELS]");
        System.out.println("");
        System.out.println("Examples:");
        System.out.println("\tjava -jar OscP5MessageRecorder.jar record 5000 test.xml /muse/elements/blink");
        System.out.println("\tjava -jar OscP5MessageRecorder.jar record 4999 test2.xml /muse/elements/blink /muse/elements/jaw_clench");
    }

}
