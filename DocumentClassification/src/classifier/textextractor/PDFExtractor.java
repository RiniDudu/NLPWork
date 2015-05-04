/**
 * 
 */
package classifier.textextractor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * @author PAUL
 *
 */
public class PDFExtractor implements TextExtractor
{

	/**
	 * 
	 */
	public PDFExtractor()
	{
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{		
		// This is the document that you want to read using Java.
        String fileName = 
        		"C:\\Users\\PAUL\\Documents\\OnlyFolderToUse\\PossibleWorkWithRethesh\\AccidentalClaimReportsSample\\AllReports\\253757 - Comp Report.pdf";
        
        TextExtractor textExtractor = new PDFExtractor();
        System.out.println(textExtractor.readMyDocument(fileName));
	}

	/**
	 * 
	 * @param fileName
	 */
	public String readMyDocument(String fileName)
    {
		PDFParser parser;
		COSDocument cosDoc;
		PDFTextStripper pdfStripper;
		PDDocument pdDoc;
		
		try
		{
			parser = new PDFParser(new FileInputStream(fileName));
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
	        pdfStripper.setEndPage(5);
	        String parsedText = pdfStripper.getText(pdDoc);
	        return parsedText;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
    }  	
}
