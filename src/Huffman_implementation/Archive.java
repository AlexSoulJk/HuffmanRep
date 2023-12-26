package Huffman_implementation;
import java.util.HashMap;

public class Archive {
    public String compressedData;
    public String originalFileName;
    public HashMap<String, Byte> recoveryMap;
    int originalLength;
    int dataOffset;
}