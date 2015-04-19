package com.wutnews.assistance;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author czd
 * 
 */
public class BaseConnection {
	public static String getInfo(String url,HttpEntity entity) throws IOException {

		HttpPost post = new HttpPost(url);
		post.setEntity(entity);

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() != 999) {
			HttpEntity entity1 = response.getEntity();
			if (Thread.interrupted())
				throw new IOException("Thread interrupted");
			String info = EntityUtils.toString(entity1, "utf-8");
			return info;
		} else {
			throw new IOException("http connection failed!");
		}
	}
}
