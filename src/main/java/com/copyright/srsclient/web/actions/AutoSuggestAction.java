package com.copyright.srsclient.web.actions;

import com.opensymphony.xwork2.ActionSupport;

import java.io.*;
import java.net.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
* Return JSON list of possible items to search on based on
* the string provided.
*
* @author LWojtowicz, based on work of MJessop
* @version
*/
public class AutoSuggestAction extends ActionSupport
{
	private static final long serialVersionUID = 1;
    private Logger mLog = Logger.getLogger(getClass());

    private StringBuilder data;
    
	protected HttpSession getHttpSession()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getSession();
	}
	
    public String execute()
    throws IOException
    {
        int items = 10;
        int count = 3;
        
		HttpServletRequest request = ServletActionContext.getRequest();
        String searchTerm = request.getParameter("q");
        mLog.debug("searchTerm: " + searchTerm);
        String baseUrl = request.getParameter("endpoint");
        try
        {
	        items = Integer.valueOf(request.getParameter("items"));
	        count = Integer.valueOf(request.getParameter("count"));
        }
        catch(NumberFormatException nfe)
        {
            mLog.error("Error when reading autosuggest parameters: " + nfe.getMessage());
        }
        
        data = new StringBuilder();
        
        try
        {
            URL url = new URL(buildQuery(baseUrl, URLEncoder.encode(searchTerm, "UTF-8"), items, count));
        	mLog.debug("\nAutosuggest URL: " + url);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            
            int c;
            while ((c = in.read()) > -1)
            {
                data.append((char) c);
            }
            in.close();
            
        }
        catch(UnsupportedEncodingException uee)
        {
        	mLog.error("Unsupported encoding exception: " + uee.getMessage());
        }
        catch(IOException e)
        {
        	mLog.error("Error: " + e.getMessage());
        }
        mLog.debug("data: " + data);
        return "SUCCESS";
    }

    private String buildQuery(String baseUrl, String term, int items, int count)
    {
        StringBuilder query = new StringBuilder(baseUrl);
        
        query.append("/select/?q=\"").append(term).append("\"")
            .append("+AND+count%3A[").append(count).append("+TO+*]")
            .append("&fl=title,count").append("&wt=json")
            .append("&version=2.2").append("&start=0").append("&rows=")
            .append(items).append("&indent=on").append("&sort=count%20desc");
        
        return query.toString();
    }

	public StringBuilder getData() {
		return data;
	}

	public void setData(StringBuilder data) {
		this.data = data;
	}
}