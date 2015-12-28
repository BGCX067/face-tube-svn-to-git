package com.dnb.webmash.facetube.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.dnb.webmash.facetube.shared.FBUser;
import com.dnb.webmash.facetube.shared.Facebook;
import com.dnb.webmash.facetube.shared.Session;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;
import com.restfb.types.User;

public class FBOAuth implements Filter {

    public void init(FilterConfig fc) throws ServletException {
    }

    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
    	HttpServletRequest req=null;
        HttpServletResponse res=null;
		try {
			req = (HttpServletRequest) sr;
			res = (HttpServletResponse) sr1;
		} catch (ClassCastException e) {
			throw new RuntimeException("Fatal error trying to cast ServletRequest and Response to HTTP");
		}
        String code = sr.getParameter("code");
        if (!code.trim().isEmpty()) {
            String authURL = Facebook.getAuthURL(code);
            URL url = new URL(authURL);
            try {
                String result = readURL(url);
                String accessToken = null;
                Integer expires = null;
                String[] pairs = result.split("&");
                for (String pair : pairs) {
                    String[] kv = pair.split("=");
                    if (kv.length != 2) {
                        throw new RuntimeException("Unexpected auth response");
                    } else {
                        if (kv[0].equals("access_token")) {
                            accessToken = kv[1];
                        }
                        if (kv[0].equals("expires")) {
                            expires = Integer.valueOf(kv[1]);
                        }
                    }
                }
                if (accessToken != null && expires != null) {
                	FBUser usr = FBOAuth.authFacebookLogin(accessToken, expires);
                	if (usr!=null){
                		//**** IMPORTANT STUFF HERE ****
                		req.getSession().setAttribute("user", usr); //STORE USER TO SESSION 
                		res.addCookie(new Cookie(Common.appName,usr.getUserID()));//STORE A COOKIE WITH THE USER ID
                		res.sendRedirect("/");//TODO SET THIS TO THE MAIN GWT APP 
                	} else res.sendRedirect("/fail");
                } else {
                    throw new RuntimeException("Access token and expires not found");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
//HELPERS
    /**
     * @param accessToken
     * @param expires
     * @return The unique facebook userID of the logged in user or null if auth fails
     */
    public static FBUser authFacebookLogin(String accessToken, int expires) {
        try {
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
            
            User user;
			try {
				user = facebookClient.fetchObject("me", User.class);
			} catch (FacebookException e) {
				// TODO: handle exception
				return null;
			}
            if (user==null)return null;
            
            Session session = new Session(accessToken,expires,new Date());
            FBUser usr = new FBUser(user.toString(), user.getId(), session);
            
            StorageServices.jdoANDcacheObject(usr);//TODO IMPLEMENT THIS
            return usr;
            
        } catch (Throwable ex) {
            return null;
        }
    }
    
    private String readURL(URL url) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = url.openStream();
        int r;
        while ((r = is.read()) != -1) {
            baos.write(r);
        }
        return new String(baos.toByteArray());
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
