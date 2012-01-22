package br.edu.ifpb.solicit.support;

public class MailServer {
	private String host;
	private int port;
	private boolean secure;

	public MailServer() {}
	
	public MailServer(String host, int port, boolean secure) {
		super();
		this.host = host;
		this.port = port;
		this.secure = secure;
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
}
