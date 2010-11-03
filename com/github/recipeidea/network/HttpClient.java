package com.github.recipeidea.network;

import java.io.InputStream;

import javax.microedition.io.HttpConnection;

import com.github.recipeidea.log.Logger;

public class HttpClient {

	private HttpConnectionFactory factory;

	protected Logger log = Logger.getLogger(getClass());

	public HttpClient(HttpConnectionFactory hFactory) {
		factory = hFactory;
	}

	public StringBuffer doGet(String url) throws Exception {
		return doGet(url, null, factory);
	}

	/*
	 * Get Method Connection
	 * 
	 * @url Url to Server
	 * 
	 * @parameterField is an adding parameter to url
	 */
	public StringBuffer doGet(String url, String parameterField,
			HttpConnectionFactory hFactory) throws Exception {
		return doGet(url + parameterField, hFactory);
	}

	/*
	 * GET Method Connection
	 * 
	 * @url url to Server
	 * 
	 * @hFactory HttpConnection
	 */
	public StringBuffer doGet(String url, HttpConnectionFactory hFactory)
			throws Exception {
		HttpConnection connection = null;
		StringBuffer stringBuffer = new StringBuffer();

		try {

			if (url == null || url.equals("") || url.length() == 0) {
				log.debug(getClass().getName() + " : Url Not Initialized");
				return null;
			}

			connection = hFactory.getHttpConnection(url);

			switch (connection.getResponseCode()) {
			case HttpConnection.HTTP_OK: {
				InputStream inputStream = connection.openInputStream();
				int c;
				while ((c = inputStream.read()) != -1) {
					stringBuffer.append((char) c);
				}
				inputStream.close();
				break;
			}
			case HttpConnection.HTTP_TEMP_REDIRECT:
			case HttpConnection.HTTP_MOVED_TEMP:
			case HttpConnection.HTTP_MOVED_PERM: {
				url = connection.getHeaderField("Location");
				stringBuffer = doGet(url, hFactory);
				break;
			}
			default:
				break;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (connection != null)
				connection.close();
		}
		return stringBuffer;
	}
}
