package Huffman_implementation;

import java.util.HashMap;

public class EncodedOutputData {
    public byte[] compressedMessage;
    public HashMap<Byte, String> codeMap;
    public EncodedOutputData(byte[] compressedMessage, HashMap<Byte, String> codeMap){
        this.compressedMessage = compressedMessage;
        this.codeMap = codeMap;
    }
}
