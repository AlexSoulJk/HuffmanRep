package Huffman_implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

public class Decoder {

    static public byte[] decode(String compressed, HashMap<String, Byte> recoveryMap) {
        ArrayList<Byte> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (int index = 0; index < compressed.length(); index++) {
            current.append(compressed.charAt(index));

            if (recoveryMap.containsKey(current.toString())) {
                result.add(recoveryMap.get(current.toString()));
                current.setLength(0);
            }

        }

        return convertListToArray(result);
    }

    static private byte[] convertListToArray(ArrayList<Byte> list) {
        byte[] result = new byte[list.size()];

        IntStream.range(0, result.length)
                .forEach(i -> result[i] = list.get(i));

        return result;
    }
}
