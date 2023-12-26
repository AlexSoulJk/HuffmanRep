package Huffman_implementation;

import Huffman_implementation.Tree.Leaf;
import Huffman_implementation.Tree.Node;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Encoder {

    public static EncodedOutputData encode(byte[] data) {
        HashMap<Byte, String> codeMap = new HashMap<Byte, String>();
        HashMap<Byte, Integer> weightMap = calculateWeightMap(data);
        PriorityQueue<Node> weightQueue = buildWeightQueue(weightMap);
        Node root = buildHuffmanTree(weightQueue);

        root.fillCodeMap("", codeMap);

        String compressed = compressData(data, codeMap);
        compressed = padWithZeros(compressed);

        return new EncodedOutputData(convertToByteArray(compressed), codeMap);
    }

    private static String padWithZeros(String compressed) {
        int delta = 8 - compressed.length() % 8;

        for (int counter = 0; counter < delta; counter++) {
            compressed += "0";
        }

        return String.format("%8s", Integer.toBinaryString(delta & 0xff)).replace(" ", "0") + compressed;
    }
    private static Node buildHuffmanTree(PriorityQueue<Node> queue) {
        while (queue.size() > 1) {
            Node node1 = queue.poll();
            Node node2 = queue.poll();

            Node node = new Node(node1.frequency + node2.frequency);
            node.right = node1;
            node.left = node2;

            queue.add(node);
        }

        return queue.poll();
    }
    private static PriorityQueue<Node> buildWeightQueue(HashMap<Byte, Integer> map) {
        return map.entrySet().stream()
                .map(entry -> {
                    Byte symbol = entry.getKey();
                    Integer weight = entry.getValue();
                    return new Leaf(symbol, weight);
                })
                .collect(Collectors.toCollection(PriorityQueue::new));
    }
    private static HashMap<Byte, Integer> calculateWeightMap(byte[] data) {
        HashMap<Byte, Integer> map = new HashMap<>();

        IntStream.range(0, data.length)
                .forEach(i -> map.merge(data[i], 1, Integer::sum));

        return map;
    }
    private static String compressData(byte[] data, HashMap<Byte, String> codeMap) {
        StringBuilder compressed = new StringBuilder();

        IntStream.range(0, data.length)
                .forEach(i -> compressed.append(codeMap.get(data[i])));

        return compressed.toString();
    }

    private static byte[] convertToByteArray(String compressed) {
        StringBuilder compressedString = new StringBuilder(compressed);
        byte[] result = new byte[compressedString.length() / 8];

        IntStream.range(0, result.length)
                .forEach(i -> result[i] = (byte) Integer.parseInt(
                        compressedString.substring(i * 8, (i + 1) * 8), 2));
        return result;
    }
}
