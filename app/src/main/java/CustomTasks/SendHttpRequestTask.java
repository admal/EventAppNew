package CustomTasks;

import android.os.AsyncTask;
import android.util.Log;

import http.MethodParameters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 30.11.15.
 */
public class SendHttpRequestTask extends AsyncTask<ArrayList<MethodParameters>, Void, String> {

    @Override
    protected String doInBackground(ArrayList<MethodParameters>... params) {
        String data = sendHttpRequest(params[0]);
        return data;
    }

    private String sendHttpRequest(ArrayList<MethodParameters> parameters) {
        String url = parameters.get(0).getName();
        String method = parameters.get(0).getValue();
        String buffer = "";

        try {
            HttpURLConnection conn = (HttpURLConnection)(new URL(url).openConnection());

            conn.setRequestMethod(method);
            parameters.remove(0);
            List<MethodParameters> updatet = parameters;

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(updatet));
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
            InputStream is = conn.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            buffer = total.toString();
//            byte[] b = new byte[1024];
//            while (is.read(b) != -1)
//            {
//                buffer += new String(b);
//            }
//            conn.disconnect();


        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("json", buffer);
        return buffer;
    }

    @Override
    protected void onPostExecute(String result) {

    }

    private String getQuery(List<MethodParameters> params)
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (MethodParameters pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            try {
                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result.append("=");
            try {
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }
}
