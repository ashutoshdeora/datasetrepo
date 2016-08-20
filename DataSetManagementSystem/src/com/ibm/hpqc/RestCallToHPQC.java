package com.ibm.hpqc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RestCallToHPQC {
	private RestConnector con;

	public static final String HOST = "hpqcprod";
	public static final String PORT = "8080";

	public static final String USERNAME = "a099996";
	public static final String PASSWORD = "MYname_3880";

	public static final String DOMAIN = "DEFAULT";
	public static final String PROJECT = "Unity_RentalProject";

	public RestCallToHPQC() {
		con = RestConnector.getInstance();
	}

	public void callRestFromHPQCForDefect(String defectId) throws Exception {

		String serverURL = "http://" + HOST + ":" + PORT + "/qcbin";
		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverURL, DOMAIN, PROJECT);
		String authenticationPoint = isAuthenticated();
		Response loginResponse = login(authenticationPoint, USERNAME, PASSWORD);
		Iterable<String> newCookies = loginResponse.getResponseHeaders().get("Set-Cookie");
		String cookieString = null;
		for (String cookie : newCookies) {
			cookieString = cookie;
			break;

		}
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		requestHeaders.put("Set-Cookie", cookieString);

		String qcsessionurl = con.buildUrl("rest/site-session");
		Response resp = con.httpPost(qcsessionurl, null, requestHeaders);
		System.out.println(resp);
		Iterable<String> QCSessionCookies = resp.getResponseHeaders().get("Set-Cookie");
		String QCSessioncookieString = null;
		for (String cookie : QCSessionCookies) {
			if (cookie.contains("QCSession")) {
				QCSessioncookieString = cookie;
				break;
			}

		}

		requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		String requesCookie = QCSessioncookieString + ";" + cookieString;
		requestHeaders.put("Cookie", requesCookie);
		String urlOfResourceWeWantToRead = con.buildUrl("rest/domains/DEFAULT/projects/Unity_RentalProject/defects/"+defectId);
		Response serverResponse = con.httpGet(urlOfResourceWeWantToRead, null, requestHeaders);
		readString(serverResponse.toString());

	}

	/**
	 * @param loginUrl
	 *            to authenticate at
	 * @param username
	 * @param password
	 * @return true on operation success, false otherwise
	 * @throws Exception
	 * 
	 *             Logging in to our system is standard http login (basic
	 *             authentication), where one must store the returned cookies
	 *             for further use.
	 */
	public Response login(String loginUrl, String username, String password) throws Exception {

		// create a string that lookes like:
		// "Basic ((username:password)<as bytes>)<64encoded>"
		byte[] credBytes = (username + ":" + password).getBytes();
		String credEncodedString = "Basic " + Base64Encoder.encode(credBytes);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Authorization", credEncodedString);
		returnAuthentication(map);
		Response response = con.httpGet(loginUrl, null, map);
		return response;
	}

	public Map<String, String> returnAuthentication(Map<String, String> userData) {
		return userData;
	}

	/**
	 * @return null if authenticated.<br>
	 *         a url to authenticate against if not authenticated.
	 * @throws Exception
	 */
	public String isAuthenticated() throws Exception {

		String isAuthenticateUrl = con.buildUrl("rest/is-authenticated");
		String ret;
		Response response = con.httpGet(isAuthenticateUrl, null, null);
		Iterable<String> authenticationHeader = response.getResponseHeaders().get("WWW-Authenticate");
		String newUrl = authenticationHeader.iterator().next().split("=")[1];
		newUrl = newUrl.replace("\"", "");
		newUrl += "/authenticate";
		ret = newUrl;
		return ret;
	}

	public void readString(String xml) throws Exception, Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		Document document = builder.parse(is);
		// Iterating through the nodes and extracting the data.
		NodeList nodeList = document.getDocumentElement().getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				// fields
				System.out.println(node.getNodeName());
				System.out.println(node.getNodeValue());
				NodeList childNodes = node.getChildNodes();
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node cNode = childNodes.item(j);
					// field
					System.out.println(cNode.getNodeName());
					System.out.println(cNode.getNodeValue());
					for (int k = 0; k < cNode.getAttributes().getLength(); k++) {
						System.out.print(cNode.getAttributes().item(k).getNodeValue());
						System.out.println();
						// field attribute and field value
						if (cNode.getAttributes().item(k).getNodeValue().equalsIgnoreCase("dev-comments")
								|| cNode.getAttributes().item(k).getNodeValue().equalsIgnoreCase("description")) {

						} else {
							NodeList list = cNode.getChildNodes();
							for (int ll = 0; ll < list.getLength(); ll++) {
								Node cnNode = list.item(ll);
								NodeList cwn = cnNode.getChildNodes();
								for (int jl = 0; jl < cwn.getLength(); jl++) {
									System.out.print(cwn.item(jl).getNodeValue());
									System.out.println();
								}
								System.out.println();
							}
						}
					}

				}
			}

		}

	}
}
