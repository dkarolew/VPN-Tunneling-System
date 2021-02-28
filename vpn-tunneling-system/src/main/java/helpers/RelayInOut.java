package helpers;

import java.io.*;


public class RelayInOut extends Thread {

	private final DataInputStream in;
	private final DataOutputStream out;
	private final String name;

	public RelayInOut(DataInputStream in, DataOutputStream out, String name) {
		super(name);
		this.name = name;
		this.in = in;
		this.out = out;
		setDaemon(true);
		this.start();
	}

	public RelayInOut(DataInputStream in, DataOutputStream out) {
	     this.name = getName();
	     this.in = in;
	     this.out = out;
	     setDaemon(true);
	     this.start();
	}

	public void run() {
		int size;
		byte[] buffer = new byte[4096];
		try {
			while(true) {
				size = in.read(buffer);
	       		if(size > 0 ) {
	           		System.out.println(name + " receive from in connection" + size);
		   			out.write(buffer,0, size);
		   			out.flush();
		   			System.out.println(name  + " finish forwarding to out connection");
	       		} else if (size == -1) {
					System.out.println(name + " connection closed");
					out.close();
					return;
				}
			}
	    } catch(Exception e) {
	       System.err.println(name + e);
	       closeall();
		}
	}

	public void closeall() {
	     try {
	        if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		 } catch(IOException e) {
		    System.err.println(e);
		 }
	}
}
