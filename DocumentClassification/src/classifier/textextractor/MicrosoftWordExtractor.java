/**
 * 
 */
package classifier.textextractor;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.HeaderStories;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/**
 * @author PAUL
 *
 */
public class MicrosoftWordExtractor implements TextExtractor
{	

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
        // This is the document that you want to read using Java.
        String fileName = 
        		"C:\\Users\\PAUL\\Documents\\OnlyFolderToUse\\PossibleWorkWithRethesh\\AccidentalClaimReportsSample\\AllReports\\RapidData Reports GT.docx";
        
        TextExtractor textExtractor = new MicrosoftWordExtractor();
        System.out.println(textExtractor.readMyDocument(fileName));
    }
	
	/**
	 * 
	 * @param fileName
	 */
	public String readMyDocument(String fileName)
    {
		try
		{
			return readTextFromHWPFDoc(fileName);
		}
		catch (Exception e)
		{
			// Check to see if this document is a 2007+ .docx document
			// and handle it accordingly
			if (e instanceof OfficeXmlFileException)
			{
				try
				{
					return readTextFromXWPFDoc(fileName);
				}
				catch (Exception e1)
				{					
					e1.printStackTrace();
					return null;
				}
			}
			
			// Else return empty string
			e.printStackTrace();
			return null;
		}    
    }  
 
    /**
     * 
     * @param doc
     * @throws Exception
     */
    private static String readTextFromHWPFDoc(String fileName) throws Exception
    {        
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
        HWPFDocument hwpfDoc = new HWPFDocument(fs);                     
    	
        WordExtractor wordExtractor = new WordExtractor(hwpfDoc);
 
        // Get the total number of paragraphs
        String[] paragraphs = wordExtractor.getParagraphText();        
        // Get the text from all the paragraphs in the word document
        StringBuffer concatenatedParas = new StringBuffer();
        for (int i = 0; i < paragraphs.length; i++)
        {                        
            concatenatedParas.append(paragraphs[i].toString());
            concatenatedParas.append(System.getProperty("line.separator"));
        }
        
        wordExtractor.close();
        
        return concatenatedParas.toString();
    }
    
    /**
     * 
     * @param fileName
     * @return
     * @throws Exception
     */
    private static String readTextFromXWPFDoc(String fileName) throws Exception
    {
    	XWPFDocument xwpfDoc = new XWPFDocument(new FileInputStream(fileName));		

		// Get the text from all the paragraphs in the word document
    	List<XWPFParagraph> paragraphList =  xwpfDoc.getParagraphs();
    	StringBuffer concatenatedParas = new StringBuffer();
    	for (XWPFParagraph paragraph: paragraphList){
    		concatenatedParas.append(paragraph.getText());
    		concatenatedParas.append(System.getProperty("line.separator"));
    	}

    	return concatenatedParas.toString();
    }
    
    /**
     * 
     * @param doc
     * @param pageNumber
     */
    @SuppressWarnings("unused")
	private static String readHeader(HWPFDocument doc, int pageNumber)
    {
        HeaderStories headerStore = new HeaderStories( doc);
        return headerStore.getHeader(pageNumber); 
    }
 
    /**
     * 
     * @param doc
     * @param pageNumber
     */
    @SuppressWarnings("unused")
	private static String readFooter(HWPFDocument doc, int pageNumber)
    {
        HeaderStories headerStore = new HeaderStories( doc);
        return headerStore.getFooter(pageNumber); 
    }
 
    /**
     * 
     * @param doc
     */
    @SuppressWarnings("unused")
	private void readDocumentSummary(HWPFDocument doc)
    {
        DocumentSummaryInformation summaryInfo=doc.getDocumentSummaryInformation();
        String category = summaryInfo.getCategory();
        String company = summaryInfo.getCompany();
        int lineCount=summaryInfo.getLineCount();
        int sectionCount=summaryInfo.getSectionCount();
        int slideCount=summaryInfo.getSlideCount();
 
        System.out.println("---------------------------");
        System.out.println("Category: "+category);
        System.out.println("Company: "+company);
        System.out.println("Line Count: "+lineCount);
        System.out.println("Section Count: "+sectionCount);
        System.out.println("Slide Count: "+slideCount);
    }

}
