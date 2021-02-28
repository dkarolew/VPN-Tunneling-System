package client;

import helpers.RelayInOut;
import helpers.SSLContex;

import java.net.*;
import javax.net.ssl.*;
import java.io.*;


public class SSLClient extends SSLContex implements Runnable {

	private SSLSocket ss;
	private ServerSocket locals;

	private DataInputStream in, secureIn;
	private DataOutputStream out, secureOut;

	private final String host;
	private final int tunnelPort;
	private final int localPort;

	private final Thread thread;
	private volatile boolean exit = false;

	public SSLClient(String keystorePath, String truststorePath, char[] storepass, char[] keypass,
					 String host, int tunnelPort, int localPort) {
		super(keystorePath, truststorePath, storepass, keypass);
		this.host = host;
		this.tunnelPort = tunnelPort;
		this.localPort = localPort;
		initLocalConnection();
		thread = new Thread(this);
		thread.start();
		System.out.println("SSL Client started and " +
				"listening on port " + this.localPort + " " +
				"relaying to port " + this.tunnelPort + " " + "and host " + this.host);
	}

	public void initLocalConnection() {
		try {
			locals = new ServerSocket(this.localPort);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void initTunnelConnection() {
		try {
			SSLSocketFactory sf = getSSLContext().getSocketFactory();
			ss = (SSLSocket) sf.createSocket(this.host, this.tunnelPort);
			ss.startHandshake();

			SSLSession session = ss.getSession();

			System.out.println("Cipher suite: " + session.getCipherSuite());
			System.out.println("Protocol: " + session.getProtocol());

			secureIn = new DataInputStream(new BufferedInputStream(ss.getInputStream()));
			secureOut = new DataOutputStream(new BufferedOutputStream(ss.getOutputStream()));

			System.out.println("Established remote secured connection");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void beginRelaying() {
		System.out.println("Relaying");
		new RelayInOut(in, secureOut, "App-Out");
		new RelayInOut(secureIn, out, "In-App");
	}

	// Start listening on local port
	@Override
	public void run() {
		while (!exit) {
			try {
				Socket sock = locals.accept();
				initTunnelConnection();
				in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
				beginRelaying();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

	public void stop() { exit = true; }

	public void close() {
		try {
			locals.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void print_usage() {
		System.out.println("USAGE (parameters): [keystore-path] " +
				" [truststore-path] [keystore-password] [truststore-password] [destination-host]" +
				" [tunnel-port] [local-port] ");
		System.out.println();
	}

	public static void main(String[] args) {
		if (args.length != 7) {
			print_usage();
		} else {
			SSLClient client = new SSLClient(args[0], args[1],
					args[2].toCharArray(), args[3].toCharArray(), args[4],
					Integer.parseInt(args[5]), Integer.parseInt(args[6]));
		}
	}
}
