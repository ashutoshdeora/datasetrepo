package com.ibm.hpqc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.model.DefectBean;

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

	public static void main(String[] args) {
		try {
			new RestCallToHPQC().callRestFromHPQCForDefectValidation("14525", USERNAME, PASSWORD);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public List<DefectBean> callRestFromHPQCForDefects(List<String> defectIds, String username, String password) throws Exception {
		String serverURL = "http://" + HOST + ":" + PORT + "/qcbin";
		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverURL, DOMAIN, PROJECT);
		String authenticationPoint = isAuthenticated();
		Response loginResponse = login(authenticationPoint, username, password);
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
		List<DefectBean> defectBeans = new ArrayList<DefectBean>();
		for (String def : defectIds) {
			String urlOfResourceWeWantToRead = con.buildUrl("rest/domains/DEFAULT/projects/Unity_RentalProject/defects/" + def);
			Response serverResponse = con.httpGet(urlOfResourceWeWantToRead, null, requestHeaders);
			DefectBean bean = new DefectBean();
			if (serverResponse.getStatusCode() == HttpURLConnection.HTTP_OK) {
				bean = readString(serverResponse.toString());
			}
			defectBeans.add(bean);
		}
		return defectBeans;
	}

	private boolean callRestFromHPQCForDefectValidation(String defectId, String username, String password) throws Exception {
		String serverURL = "http://" + HOST + ":" + PORT + "/qcbin";
		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverURL, DOMAIN, PROJECT);
		String authenticationPoint = isAuthenticated();
		Response loginResponse = login(authenticationPoint, username, password);
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
		String urlOfResourceWeWantToRead = con.buildUrl("rest/domains/DEFAULT/projects/Unity_RentalProject/defects/" + defectId);
		Response serverResponse = con.httpGet(urlOfResourceWeWantToRead, null, requestHeaders);
		DefectBean bean = new DefectBean();
		if (serverResponse.getStatusCode() == HttpURLConnection.HTTP_OK) {
			bean = readString(serverResponse.toString());
		}
		if (bean.getHPQCID() != null && bean.getHPQCID().length() > 0) {
			return true;
		} else {
			return false;
		}

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
	private Response login(String loginUrl, String username, String password) throws Exception {

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

	/**
	 * @return true if logout successful
	 * @throws Exception
	 *             close session on server and clean session cookies on client
	 */
	public boolean logout() throws Exception {

		// note the get operation logs us out by setting authentication cookies
		// to:
		// LWSSO_COOKIE_KEY="" via server response header Set-Cookie
		Response response = con.httpGet(con.buildUrl("authentication-point/logout"), null, null);
		return (response.getStatusCode() == HttpURLConnection.HTTP_OK);

	}

	private Map<String, String> returnAuthentication(Map<String, String> userData) {
		return userData;
	}

	/**
	 * @return null if authenticated.<br>
	 *         a url to authenticate against if not authenticated.
	 * @throws Exception
	 */
	private String isAuthenticated() throws Exception {

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

	private DefectBean readString(String xml) throws Exception, Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		Document document = builder.parse(is);
		// Iterating through the nodes and extracting the data.
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		Map<String, String> nodeMap = new HashMap<String, String>();
		String key = null;
		String value = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				NodeList childNodes = node.getChildNodes();
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node cNode = childNodes.item(j);
					for (int k = 0; k < cNode.getAttributes().getLength(); k++) {
						System.out.print(cNode.getAttributes().item(k).getNodeValue());
						if (cNode.getAttributes().item(k).getNodeValue().equalsIgnoreCase("dev-comments")
								|| cNode.getAttributes().item(k).getNodeValue().equalsIgnoreCase("description")) {

						} else {
							key = cNode.getAttributes().item(k).getNodeValue();
							NodeList list = cNode.getChildNodes();
							for (int ll = 0; ll < list.getLength(); ll++) {
								Node cnNode = list.item(ll);
								NodeList cwn = cnNode.getChildNodes();
								for (int jl = 0; jl < cwn.getLength(); jl++) {
									value = cwn.item(jl).getNodeValue();
									System.out.print(cwn.item(jl).getNodeValue());
								}
								System.out.println();
							}
						}
					}
					nodeMap.put(key, value);
				}
			}
		}
		DefectBean temp = new DefectBean();
		temp.setHPQCID(nodeMap.get("id"));
		temp.setDefectseverity(nodeMap.get("severity"));
		temp.setDefectstatus(nodeMap.get("status"));
		temp.setDefectOwner(nodeMap.get("owner"));
		temp.setDefectCause(nodeMap.get("user-02"));
		temp.setDefectName(nodeMap.get("name"));
		temp.setDefectPriority(nodeMap.get("priority"));
		temp.setDefectCreationDate(nodeMap.get("creation-time"));
		temp.setDefectLastModifies(nodeMap.get("last-modified"));
		temp.setDefectClosingDate(nodeMap.get("closing-date"));
		temp.setDefectRaisedBy(nodeMap.get("detected-by"));

		return temp;

	}
}
