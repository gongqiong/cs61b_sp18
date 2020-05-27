import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> res = new HashMap<>();
        for (char c : inputSymbols) {
            if (res.containsKey(c)) {
                res.put(c, res.get(c) + 1);
            } else {
                res.put(c, 1);
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        char[] inputSymbols = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        BinaryTrie decodeTrie = new BinaryTrie(frequencyTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(decodeTrie);
        Integer count = inputSymbols.length;
        ow.writeObject(count);
        Map<Character, BitSequence> lookUpTable = decodeTrie.buildLookupTable();
        List<BitSequence> bitSequenceList = new ArrayList<>();
        for (char c : inputSymbols) {
            BitSequence seq = lookUpTable.get(c);
            bitSequenceList.add(seq);
        }
        BitSequence hugSeq = BitSequence.assemble(bitSequenceList);
        ow.writeObject(hugSeq);
    }
}
