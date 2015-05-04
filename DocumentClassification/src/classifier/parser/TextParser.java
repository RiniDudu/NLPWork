package classifier.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;

/**
 * 
 * @author PAUL
 *
 */
class TextParser
{
	private static String PARSER_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		List<GrammaticalStructure> listOfSentenseParses =
				parseTextAndGetGrammar(
						"C:\\Users\\PAUL\\Documents\\OnlyFolderToUse\\PossibleWorkWithRethesh\\AccidentalClaimReportsSample\\"
						+ "AllReportsExtracted\\Report 1 - CA EDEX.txt");

		for (GrammaticalStructure grammaticalStructure : listOfSentenseParses)
		{
			@SuppressWarnings("rawtypes")
			Collection tdl = grammaticalStructure.typedDependenciesCCprocessed();
			System.out.println(tdl);
			System.out.println();
		}
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static List<GrammaticalStructure> parseTextAndGetGrammar(String filename)
	{
		return demoDP(PARSER_MODEL, filename);
	}

	/**
	 * Turning a file into tokens and then parse trees.
	 * 
	 * @param pathToLexicalizedParser
	 * @param filename
	 */
	public static List<GrammaticalStructure> demoDP(String pathToLexicalizedParser, String filename)
	{   
		LexicalizedParser lexicalizedParser = LexicalizedParser.loadModel(pathToLexicalizedParser);

		// Penn tree bank
		TreebankLanguagePack tlp = lexicalizedParser.treebankLanguagePack();
		GrammaticalStructureFactory gsf = null;
		if (tlp.supportsGrammaticalStructures())
		{
			gsf = tlp.grammaticalStructureFactory();
		}
		// Create a tokenizer and pass it to DocumentPreprocessor
		List<GrammaticalStructure> listOfSentenceParses = new ArrayList<GrammaticalStructure>();
		for (List<HasWord> sentence : new DocumentPreprocessor(filename))
		{
			Tree parse = lexicalizedParser.apply(sentence);
			parse.pennPrint();
			System.out.println();

			if (gsf != null)
			{
				GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
				listOfSentenceParses.add(gs);
			}
		}

		return listOfSentenceParses;
	}

	/**
	 * 
	 */
	private TextParser()
	{
	}

}
