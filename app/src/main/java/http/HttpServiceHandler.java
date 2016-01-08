package http;

import java.util.List;

/**
 * Created by adam on 30.11.15.
 */
public class HttpServiceHandler
{
    public static final String POST = "POST";
    public static final String GET = "GET";
    public HttpServiceHandler()
    {

    }

    public String makeServiceCall(String url, String method)
    {
        return this.makeServiceCall(url, method, null);
    }

    private String makeServiceCall(String url, String method, List<String> params)
    {
        if(method == POST)
        {

        }
        else if(method == GET)
        {

        }
        return "";
    }

}
