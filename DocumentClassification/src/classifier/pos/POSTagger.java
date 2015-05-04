package classifier.pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * 
 * @author PAUL
 *
 */
class POSTagger
{

	private POSTagger()
	{

	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
	{    

		List<List<TaggedWord>> listOfTaggedSentences = getPartsOfSpeech("C:\\Users\\PAUL\\Documents\\OnlyFolderToUse\\PossibleWorkWithRethesh\\AccidentalClaimReportsSample\\"
				+ "AllReportsExtracted\\Report 1 - CA EDEX.txt");
		for (List<TaggedWord> tSentence : listOfTaggedSentences)
		{
			System.out.println(Sentence.listToString(tSentence, false));
		}
	}

	/**
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static List<List<TaggedWord>> getPartsOfSpeech(String filename) throws Exception
	{
		MaxentTagger tagger = new MaxentTagger("models\\english-caseless-left3words-distsim.tagger");
		List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(filename)));
		List<List<TaggedWord>> listOfTaggedSentences = new ArrayList<List<TaggedWord>>();
		for (List<HasWord> sentence : sentences)
		{
			listOfTaggedSentences.add(tagger.tagSentence(sentence));	      
		}
		return listOfTaggedSentences;
	}

}
