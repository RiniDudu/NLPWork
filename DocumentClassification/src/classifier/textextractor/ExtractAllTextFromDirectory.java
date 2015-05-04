/**
 * 
 */
package classifier.textextractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author PAUL
 *
 */
public class ExtractAllTextFromDirectory
{
	// Text Extractor Instance
	TextExtractor textExtractor;

	/**
	 * 
	 */
	public ExtractAllTextFromDirectory()
	{
	}

	/**
	 * 
	 * @param inputDirectory
	 */
	public static void extractTextAndStoreAsPlainTextFile(String inputDirectory, String outputDirectory)
	{
		TextExtractor pdfTextExtractor = new PDFExtractor(); 
        TextExtractor wordTextExtractor = new MicrosoftWordExtractor();
        
        File path = new File(inputDirectory);
        File [] files = path.listFiles();
        for (int i = 0; i < files.length; i++)
        {        	
            if (files[i].isFile())
            {
            	try {
            		String fileCanonicalPath = files[i].getCanonicalPath();
            		String fileName = files[i].getName();
                	String extractedText = null;
                	String outputFilePath = null;
                	
					if (fileCanonicalPath.endsWith("pdf"))
					{
						outputFilePath = outputDirectory + "\\" + fileName.replaceAll("pdf", "txt");
						extractedText = pdfTextExtractor.readMyDocument(fileCanonicalPath);
					}
					else if (fileCanonicalPath.endsWith("doc"))
					{
						outputFilePath = outputDirectory + "\\" + fileName.replaceAll("doc", "txt");
						extractedText = wordTextExtractor.readMyDocument(fileCanonicalPath);
					}
					else if (fileCanonicalPath.endsWith("docx"))
					{
						outputFilePath = outputDirectory + "\\" + fileName.replaceAll("docx", "txt");
						extractedText = wordTextExtractor.readMyDocument(fileCanonicalPath);
					}
					
					// Write the extracted text to the output file					
					File outputFile = new File(outputFilePath);
					FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
					// if file doesnt exists, then create it
					if (!outputFile.exists()) 
					{
						outputFile.createNewFile();
					}
					// get the content in bytes
					byte[] contentInBytes = extractedText.getBytes();
					fileOutputStream.write(contentInBytes);
					fileOutputStream.flush();
					fileOutputStream.close();
				}
            	catch (IOException e)
            	{									
				}                
            }
        }
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// This is the directory that contains the documents you want to extract
        String inputDirectory = 
        		"C:\\Users\\PAUL\\Documents\\OnlyFolderToUse\\PossibleWorkWithRethesh\\AccidentalClaimReportsSample\\AllReportsOriginal";
        // This is the directory that contains the extracted text documents in plain text
        String outputDirectory = 
        		"C:\\Users\\PAUL\\Documents\\OnlyFolderToUse\\PossibleWorkWithRethesh\\AccidentalClaimReportsSample\\AllReportsExtracted";
        
        extractTextAndStoreAsPlainTextFile(inputDirectory, outputDirectory);
	}

}
