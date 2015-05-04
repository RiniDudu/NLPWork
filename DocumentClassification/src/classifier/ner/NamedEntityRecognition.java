package classifier.ner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;


/** This is a demo of calling CRFClassifier programmatically.
 *  <p>
 *  Usage: {@code java -mx400m -cp "*" NERDemo [serializedClassifier [fileName]] }
 *  <p>
 *  If arguments aren't specified, they default to
 *  classifiers/english.all.3class.distsim.crf.ser.gz and some hardcoded sample text.
 *  If run with arguments, it shows some of the ways to get k-best labelings and
 *  probabilities out with CRFClassifier. If run without arguments, it shows some of
 *  the alternative output formats that you can get.
 *  <p>
 *  To use CRFClassifier from the command line:
 *  </p><blockquote>
 *  {@code java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier [classifier] -textFile [file] }
 *  </blockquote><p>
 *  Or if the file is already tokenized and one word per line, perhaps in
 *  a tab-separated value format with extra columns for part-of-speech tag,
 *  etc., use the version below (note the 's' instead of the 'x'):
 *  </p><blockquote>
 *  {@code java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier [classifier] -testFile [file] }
 *  </blockquote>
 *
 *  @author Jenny Finkel
 *  @author Christopher Manning
 */

public class NamedEntityRecognition
{
	private static String NER_CLASSIFIER = "classifiers/english.all.3class.distsim.crf.ser.gz";

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		List<List<CoreLabel>> coreLabels =
				getNamedEntitiesFromFileDefaultClassifier("C:\\Users\\PAUL\\Documents\\OnlyFolderToUse\\PossibleWorkWithRethesh\\AccidentalClaimReportsSample\\"
						+ "AllReportsExtracted\\Report 1 - CA EDEX.txt");


		for (List<CoreLabel> sentence : coreLabels)
		{
			System.out.println("SENTENCE IS --> " + sentence.toString());
			System.out.print("ENTITIES ARE --> ");
			for (CoreLabel word : sentence)
			{
				if (!word.get(CoreAnnotations.AnswerAnnotation.class).equals("O"))
				{
					System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
				}
			}
			System.out.println();
		}

	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static List<List<CoreLabel>> getNamedEntitiesFromFileDefaultClassifier(String filename)
	{
		return getNamedEntitiesFromFile(NER_CLASSIFIER, filename);
	}

	/**
	 * 
	 * @param pathToClassifier
	 * @param filename
	 * @return
	 */
	public static List<List<CoreLabel>> getNamedEntitiesFromFile(String pathToClassifier, String filename)
	{
		AbstractSequenceClassifier<CoreLabel> classifier = null;
		String fileContents = null;

		try 
		{
			classifier = CRFClassifier.getClassifier(pathToClassifier);
			fileContents = IOUtils.slurpFile(filename);
		} catch (ClassCastException e)
		{
			e.printStackTrace();
			return new ArrayList<List<CoreLabel>>();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			return new ArrayList<List<CoreLabel>>();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			return new ArrayList<List<CoreLabel>>();
		}

		return classifier.classify(fileContents);
	}

	/**
	 * 
	 */
	private NamedEntityRecognition()
	{
	}
}
