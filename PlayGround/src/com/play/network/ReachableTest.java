package com.play.network;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReachableTest {

	public static void main(String[] args) {
		String ip = "192.168.1.100";
		int port = 6546;
		boolean hostReachable = false;
		boolean portReachable = false;
		try {
			InetAddress host = InetAddress.getByName(ip);
			if(host.isReachable(1000)) {
				hostReachable = true;
				Socket sock = new Socket(ip, port);
				portReachable = true;
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			System.out.println("Failed to connect.");
			e.printStackTrace();
	    }catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Host " + ip + " reachable " + hostReachable + ", port " + port + " reachable " + portReachable);
	}
}
