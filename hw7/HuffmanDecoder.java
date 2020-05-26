public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie decodeTrie = (BinaryTrie) or.readObject();
        Integer count = (Integer) or.readObject();
        BitSequence textSeq = (BitSequence) or.readObject();
        char[] symbols = new char[count];
        for (int i = 0; i < count; i += 1) {
            Match match = decodeTrie.longestPrefixMatch(textSeq);
            symbols[i] = match.getSymbol();
            textSeq = textSeq.allButFirstNBits(match.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], symbols);
    }
}
