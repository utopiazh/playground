package com.play.net;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.Enumeration;

public class ListInternetInterface {
	// http://stackoverflow.com/questions/8462498/how-to-determine-internet-network-interface-in-java
	
	public static void main(String[] args) {
		try {
			// iterate over the network interfaces known to java
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();
			OUTER: for (NetworkInterface interface_ : Collections
					.list(interfaces)) {
				// we shouldn't care about loopback addresses

				if (interface_.isLoopback())
					continue;

				// if you don't expect the interface to be up you can skip this
				// though it would question the usability of the rest of the
				// code
				if (!interface_.isUp())
					continue;

				// iterate over the addresses associated with the interface
				Enumeration<InetAddress> addresses = interface_
						.getInetAddresses();
				for (InetAddress address : Collections.list(addresses)) {
					// look only for ipv4 addresses
					if (address instanceof Inet6Address)
						continue;

					// use a timeout big enough for your needs
					if (!address.isReachable(3000))
						continue;

					// java 7's try-with-resources statement, so that
					// we close the socket immediately after use
					try (SocketChannel socket = SocketChannel.open()) {
						// again, use a big enough timeout
						socket.socket().setSoTimeout(3000);

						// bind the socket to your local interface
						socket.bind(new InetSocketAddress(address, 12306));

						// try to connect to *somewhere*
						socket.connect(new InetSocketAddress("google.com", 80));
					} catch (IOException ex) {
						ex.printStackTrace();
						continue;
					}

					System.out.format("ni: %s, ia: %s\n", interface_, address);

					// stops at the first *working* solution
					break OUTER;
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
