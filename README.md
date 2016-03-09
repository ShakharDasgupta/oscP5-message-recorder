# OscP5 Message Recorder
OscP5 Message Recorder is a Java program which can be used to record and play back oscP5 signals.

## Usage
The program can be run by compiling it into a jar file and running commands like the ones below.

### Recording
```
java -jar OscP5MessageRecorder.jar record PORT OUTPUTFILE [CHANNELS]
java -jar OscP5MessageRecorder.jar record 5000 test.xml /muse/elements/blink
java -jar OscP5MessageRecorder.jar record 4999 test2.xml /muse/elements/blink /muse/elements/jaw_clench
```

### Playing
```
java -jar OscP5MessageRecorder.jar play PORT INPUTFILE
java -jar OscP5MessageRecorder.jar play 5000 test.xml
```

### Help
For further usage information, run the jar file with the `--help` flag.

```
java -jar OscP5MessageRecorder.jar --help
```