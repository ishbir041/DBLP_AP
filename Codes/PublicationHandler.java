//***************************************
//          Author tag
//Raunak Sinha-2015075
//Ishbir Walia-2015041
//****************************************

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

public class PublicationHandler extends DefaultHandler{
	

	boolean bAuthor=false;
	boolean bTitle=false;
	boolean bPages=false;
	boolean bYear=false;
	boolean bVolume=false;
	boolean bJournal=false;
	boolean bUrl=false;
	
	String tempchar;	
	boolean resultFound = false;
	boolean isEntity=false;
	
	Map<String,Integer> publi;
	Data tempart;
	String auth;
	Entity ent;
	String []authsplit;
	
	PublicationHandler(Entity en)
	{
		publi=new HashMap<String,Integer>();
		tempart=new Data();
		tempchar=new String("");
		ent=en;
	}
	
	
	
	
	public void startElement(String uri,String localName, String qName, Attributes attributes) throws SAXException 
	{
			      if(qName.equalsIgnoreCase("www")){
			    	  String tmmp=attributes.getValue("key");
			    	  if(tmmp.contains("homepages/"))
			    	  {
			    		  isEntity=true;
			    	  }
			    	  else
			    	  {
			    		  isEntity=false;
			    	  }
			    	  
			      } 
			      else if (qName.equalsIgnoreCase("author")) 
			      {
			         bAuthor = true;
			      } 
			      else if (qName.equalsIgnoreCase("title")) {
				         bTitle = true;
				  } 
			      else if (qName.equalsIgnoreCase("pages")) {
				         bPages = true;
				  }
				  else if (qName.equalsIgnoreCase("year")) {
				         bYear = true;
				  }
				  else if (qName.equalsIgnoreCase("volume")) {
					         bVolume = true;
				  }
				  else if (qName.equalsIgnoreCase("journal")  || qName.equalsIgnoreCase("booktitle")) {
					         bJournal = true;
				  }
				  else if (qName.equalsIgnoreCase("url")) {
					         bUrl = true;
				  }
	}

	@Override
	public void endElement(String uri,String localName, String qName) throws SAXException
	{
			      
		if (qName.equalsIgnoreCase("article") ||  qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("incollection") || qName.equalsIgnoreCase("phdthesis") || qName.equalsIgnoreCase("proceedings") || qName.equalsIgnoreCase("book")|| qName.equalsIgnoreCase("masterthesis")) 
		{
			 tempart=new Data();
	         tempchar=new String("");
	    	 resultFound=false;
	         isEntity=false;
	    }
	    else if(qName.equalsIgnoreCase("www"))
	    {
	    	  	tempart=new Data();
			    resultFound=false;
			    tempchar=new String("");
			    isEntity=false;
			  
	    	  
	    }
	    else if(bAuthor)
	    {
	    	  if(isEntity==false)
	    	  {  
	    		  if(tempchar!=null)
	    		  {
	    			  insertPubli(tempchar);
	    			  tempart.setAuthor(tempchar);
	    		  }
	    	  }
	    	  tempchar=new String("");
	    	  bAuthor=false;
	    	  
	     }
	     else if(bTitle && isEntity==false)
	     {
	    	  tempart.setTitle(tempchar);
	    	  
	    	  tempchar=new String("");
	    	  bTitle=false;
	     }
	     else if(bPages && isEntity==false)
	     {
	    	  tempart.setPages(tempchar);
	    	  
	    	  tempchar=new String("");
	    	  bPages=false;
	     }
	     else if(bYear && isEntity==false)
	     {
	    	  tempart.setYear(tempchar);
	    	  
	    	  tempchar=new String("");
	    	  bYear=false;
	     }
	     else if(bVolume && isEntity==false)
	     {
	    	  tempart.setVolume(tempchar);
	    	  
	    	  tempchar=new String("");
	    	  bVolume=false;
	     }
	     else if(bJournal && isEntity==false)
	     {
	    	  tempart.setJournal(tempchar);
	    	  
	    	  tempchar=new String("");
	    	  bJournal=false;
	     }
	     else if(bUrl && isEntity==false)
	     {
	    	  tempart.setUrl(tempchar);
	    	  
	    	  tempchar=new String("");
	    	  bUrl=false;
	     }
	     else
	     {
	    	  tempchar=new String("");
	     }
	   
	}

	@Override
	public void characters(char ch[],int start, int length) throws SAXException
	{
		tempchar += new String(ch, start, length);
	}
	
	
	public void insertPubli(String au)  //increases or add elements in a hashmap
	{
		String main=ent.getMap().get(au);
		if(main!=null)
		{
			Integer j=publi.get(au);
			if(j==null)
			{
				j=0;
			}
			publi.put(main, j+1);
		}
		else
		{
			Integer j=publi.get(au);
			if(j==null)
			{
				j=0;
			}
			publi.put(au, j+1);
		}
	}
	
	public Map<String,Integer> getList()
	{
		return publi;
	}
	

}
