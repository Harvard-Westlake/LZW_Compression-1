import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
public class Encoder 
{
	private HashMap dictionary = new HashMap<String,Integer>(256);
	private ArrayList entryValueArray = new ArrayList<Integer>();
	private String fileName;
	private String binaryMasterString;
	private String text;
	public Encoder(String uncompressedFile)
	{
		initializeDictionary();
		fileName = uncompressedFile;
		text = this.getText(uncompressedFile);
		entryValueArray = this.toEntryArray(text);
		binaryMasterString = this.createBinaryMasterString(entryValueArray);
	}
	private void initializeDictionary()
	{
		for(int i = 0; i<256; i++)
		{
			dictionary.put("" + (char)i,i);
		}
	}
	public String getText(String filename)
	{
		String code = "";
		 try (BufferedReader br = new BufferedReader(new FileReader(filename)))
	        {
	            String line;
	            while ((line = br.readLine()) != null) {
	                code += line;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 return code;
	}
	
	 public String toBinary(int x, int len)
	    {
	        if (len > 0)
	        {
	            return String.format("%" + len + "s", Integer.toBinaryString(x)).replaceAll(" ", "0");
	        }
	 
	        return null;
	    }
	    
	public ArrayList<Integer> toEntryArray(String text)
	{
		ArrayList entryValueList = new ArrayList<Integer>();
		String textForLoop = text;
		String c = "";
		int i = 0;
		for(int j = 0; j < text.length() - 1; j++)
		{
		
		String cn = c + textForLoop.charAt(j+1);
		if(dictionary.containsKey(cn))
		{
			c = cn;
			entryValueList.add(dictionary.get(cn));
		}
		else
		{
		entryValueList.add(dictionary.get(cn.substring(0,cn.length() - 1)));
		dictionary.put(cn, 256+i);
		i++;
		c = cn.substring(cn.length()-1);
		}
		}
		return entryValueList;
	}
	
	public static int log(int x) 
	{
	    return (int) (Math.log(x) / Math.log(2) + 1e-10);
	}
	
	public String createBinaryMasterString(ArrayList<Integer> entryValues)
	{
		String binaryRepresentation = "";
		for(int i = 0; i < entryValues.size(); i++)
		{
			binaryRepresentation += toBinary(entryValues.get(i), Encoder.log(dictionary.size()));
		}
		while(binaryRepresentation.length()%8>0)
		{
			binaryRepresentation += "0";
		}
		return binaryRepresentation;
	}
	
	public static byte toByte(String s)
	{
		return ((byte) Integer.parseInt(s, 2));
	}
	
	public ArrayList<Integer> getEntryValueArray()
	{
		return entryValueArray;
	}
	
	public String getBinaryMasterString()
	{
		return binaryMasterString;
	}
	
	public String getText()
	{
		return text;
	}
	
	public static void main(String [] args)
	{
		Encoder e = new Encoder("/Users/tylerdonovan/eclipse-workspace/LZW_Compression/src/lzw-file1.txt");
		String masterString = e.getBinaryMasterString();
		byte[] byteRepresentation = new byte[masterString.length()/8];
		int j = 0;
		for(int i = 0; i < masterString.length() - 8; i += 8)
		{
			byteRepresentation[j] = toByte(masterString.substring(i, i+8));
			j++;
		}
		
	}
	
}
