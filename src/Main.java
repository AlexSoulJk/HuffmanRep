import java.io.File;
import java.util.Arrays;

import Huffman_implementation.*;

public class Main {
    public static void main(String[] args) {

        if (args.length < 2)
        {
            System.out.println("Incorrect request!\n" +
                    "Please, use: java Main <encode/decode/info> <inputFile>");
            return;
        }

        switch (args[0]) {
            case "decode" -> {
                Archive Archive = FileManager.readArchive(args[1]);
                FileManager.writeFile(Decoder.decode(Archive.compressedData, Archive.recoveryMap), Archive.originalFileName);
            }
            case "encode" -> {
                EncodedOutputData result = Encoder.encode(FileManager.readFile(args[1]));
                File f = new File(args[1]);
                String originalFileName = f.getName();
                String outputFilePath = originalFileName.substring(0, originalFileName.lastIndexOf('.')) + ".arc";
                FileManager.writeArchive(result.compressedMessage, result.codeMap, originalFileName, outputFilePath);
            }
            case "info" -> {
                Archive Archive = FileManager.readArchive(args[1]);
                FileManager.test(Archive, Decoder.decode(Archive.compressedData, Archive.recoveryMap));
            }
            default ->
                    System.out.println("Invalid mode. Please, use 'encode', 'decode'," +
                            " or 'info'");
        }
    }

}