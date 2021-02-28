package server;

import helpers.RelayInOut;
import helpers.SSLContex;

import java.net.*;
import javax.net.ssl.*;
import java.io.*;


public class SSLServer extends SSLContex implements Runnable {

	private ServerSocket ss;
	private final int tunnelPort;
	private final int localPort;
	private final String host;

	private final Thread thread;
	private volatile boolean exit = false;

    public SSLServer(String key , String trust, char[] storepass, char[] keypass, int localPort, int tunnelPort, String host) {
    	super(key, trust, storepass, keypass);
      	this.tunnelPort = tunnelPort;
      	this.localPort = localPort;
      	this.host = host;
		initSSLServerSocket();
      	thread = new Thread(this);
      	thread.start();
		System.out.println("SSL Server started and " +
				"listening on port " + this.localPort + " " + "and host " + this.host + " " +
				"relaying to port " + this.tunnelPort);
    }

    public void initSSLServerSocket() {
    	try {
           SSLServerSocketFactory ssf = (getSSLContext()).getServerSocketFactory();
           ss = ssf.createServerSocket(this.localPort);
           ((SSLServerSocket)ss).setNeedClientAuth(true);
       	} catch(Exception e) {
       		System.err.println(e.getMessage());
    	}
    }

    public void createStreams(SSLSocket incoming) throws IOException {
    	Socket s = new Socket(this.host , this.tunnelPort);

    	DataInputStream localIn = new DataInputStream(new BufferedInputStream(s.getInputStream()));
	   	DataOutputStream localOut = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
	   	DataInputStream tunnelIn  = new DataInputStream(new BufferedInputStream(incoming.getInputStream()));
	   	DataOutputStream tunnelOut = new DataOutputStream(new BufferedOutputStream(incoming.getOutputStream()));

	   	new RelayInOut(tunnelIn, localOut,"In-App");
	   	new RelayInOut(localIn, tunnelOut,"App-Out");
    }

	// Start listening on tunnel port
	@Override
	public void run() {
		while(!exit) {
			try {
				SSLSocket incomingSocket = (SSLSocket) ss.accept();
				incomingSocket.setSoTimeout(30 * 1000);
				System.out.println("Connection from " + incomingSocket);
				System.out.println("Timeout setting for socket is " + incomingSocket.getSoTimeout());
				createStreams(incomingSocket);
			} catch(IOException e) {
				System.err.println(e);
			}
		}
	}

	public void stop() { exit = true; }

	public void close() {
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void print_usage() {
		System.out.println("USAGE (parameters): [keystore-path] [truststore-path] " +
				"[keystore-password] [truststore-password] [local-port] [tunnel-port] [listening-host]");
		System.out.println();
	}

	public static void main(String[] args) {
		if(args.length != 7) {
			print_usage();
		} else {
			SSLServer server = new SSLServer(args[0], args[1], args[2].toCharArray(),
					args[3].toCharArray(), Integer.parseInt(args[4]), Integer.parseInt(args[5]), args[6]);
		}
	}
}
