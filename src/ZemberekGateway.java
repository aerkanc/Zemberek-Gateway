import leblebi.spelling.SingleWordSpellChecker;
import zemberek.morphology.parser.MorphParse;
import zemberek.morphology.parser.tr.TurkishWordParserGenerator;
import zemberek.tokenizer.SentenceBoundaryDetector;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

import py4j.GatewayServer;
public class ZemberekGateway {

    public TurkishWordParserGenerator parser;

    public HashMap<String, Object> parse(String word){
        List<MorphParse> parses = parser.parse(word);
        MorphParse parse = parses.get(0);
        HashMap<String, Object> response = new HashMap<String,Object> ();
        response.put("root",parse.dictionaryItem.root.toString());
        response.put("kind",parse.dictionaryItem.primaryPos.shortForm);
        HashMap<String,Integer> adds = new HashMap<String,Integer>();
        for(MorphParse.SuffixData sd : parse.inflectionalGroups.get(0).suffixList){
            Integer value = adds.get(sd.toString());
            if(value == null){
                adds.put(sd.toString(),1);
            }else{
                adds.put(sd.toString(),value+1);
            }
        }
        response.put("adds",adds);
        return response;
    }

    public List<String> splitSentences(String t){
        SentenceBoundaryDetector detector = new SimpleSentenceBoundaryDetector();
        List<String> sentences = detector.getSentences(t);
        return sentences;
    }

    public void simpleDecodeTest() {
        SingleWordSpellChecker spellChecker = new SingleWordSpellChecker(1);
        String vocabulary = "elma";
        spellChecker.addWord(vocabulary);
        Assert.assertTrue(spellChecker.decode(vocabulary).contains(vocabulary));
        check1Distance(spellChecker, "elma");

        spellChecker.addWord("armut");
        spellChecker.addWord("ayva");
        check1Distance(spellChecker, "armut");
    }

    public static void main(String[] args) {
        ZemberekGateway zemberekGateway = new ZemberekGateway();
        try{
            zemberekGateway.parser = TurkishWordParserGenerator.createWithDefaults();
        }catch(IOException ex){
            System.out.println("Zemberek parser couldn't initialize");
        }
        GatewayServer gatewayServer = new GatewayServer(zemberekGateway);
        gatewayServer.start();
        System.out.println("Gateway Server Started");
//        System.out.println(zemberekGateway.parse("vermiyorum").formatLong());
//        System.out.println(zemberekGateway.parse("önermiyorum").formatLong());
//        System.out.println(zemberekGateway.parse("seviyorum").formatLong());
//        System.out.println(tres);
    }
}