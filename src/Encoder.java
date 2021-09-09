import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
public class Encoder 
{
	private HashMap dictionary = new HashMap<String,Integer>(256);
	private ArrayList wordPieces = new ArrayList<String>();
	private void initializeDictionary()
	{
		for(int i = 0; i<256; i++)
		{
			dictionary.put("" + (char)i,i);
		}
	}
	private boolean inDictionary(String chars)
	{
		return (dictionary.containsKey(chars));
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
	
	 public static String toBinary(int x, int len)
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
	public void encode()
	{
		initializeDictionary();
		
	}
	
}
