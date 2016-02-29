package com.play.net.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;

public class EasyFtp {
	
	private static Logger log = Logger.getLogger(EasyFtp.class);
	
	private String ip;
	private int port;
	private String user;
	private String passwd;
	
	private boolean checksum = true;
	private Joiner joiner;
	
	private FTPClient ftp;
	private FileType remoteType;
	
	public EasyFtp(String ip, int port, String user, String passwd) {
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.passwd = passwd;
		joiner = Joiner.on('/');	
		ftp = new FTPClient(); 
		remoteType = FileType.DIRECOTRY;
	}
	
	/**
	 * Upload file
	 * @param src: source directory or file
	 * @param dest: destination directory
	 * @return
	 */
	public boolean upload(String src, String dest) {
		boolean success = false;
				
		try {
			if(!connect(dest)) {
				return false;
			}

			uploadInternal(src, dest);
			
			disconnect();
			
			success = true;
		} catch (SocketException e) {
			log.error(e);			
		} catch (IOException e) {
			log.error(e);
		}
		return success;
	}
	
	/**
	 * Download file
	 * @param src: source directory or file
	 * @param dest: destination direcotry
	 * @return
	 */
	public boolean download(String src, String dest) {
		boolean success = false;
		
		try {
			if(!connect(dest)) {
				return false;
			}

			downloadInternal(src, dest, remoteType);
			
			disconnect();
			
			success = true;
		} catch (SocketException e) {
			log.error(e);			
		} catch (IOException e) {
			log.error(e);
		}
		return success;
	}	
	


	private boolean connect(String remote) throws SocketException, IOException {
		ftp.connect(ip, port);
		if(!ftp.login(user, passwd)) {
			log.error("Failed to login, " + toString());
			ftp.logout();
			ftp.disconnect();
			return false;
		}
		
		int reply = ftp.getReplyCode();			 
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return false;
		}

		//enter passive mode
		ftp.enterLocalPassiveMode();
		log.info("Remote system is " + ftp.getSystemType());
		
		String cwd = remote;
		remoteType = getFileType(remote);
		if(remoteType != null && remoteType.equals(FileType.FILE)) {
			cwd = getParent(remote);
		}
		//change current directory
		ftp.changeWorkingDirectory(cwd);
		System.out.println("Current directory is " + ftp.printWorkingDirectory());
		
		return true;
	}
	
	private void disconnect() throws IOException {
		ftp.logout();
		ftp.disconnect();
	}
	
	private void uploadInternal(String src, String dest) throws IOException {
		File srcFile = new File(src);
		String name = srcFile.getName();
		String newDest = joiner.join(dest, name);
		if(srcFile.isDirectory()) {
					
			ftp.makeDirectory(newDest);
			ftp.changeWorkingDirectory(newDest);

			File[] files = srcFile.listFiles();
			if(files != null) {
				for(File file : files) {
					uploadInternal(file.getAbsolutePath(), dest);
				}
			}
		} else {
			InputStream input = new FileInputStream(srcFile);
			ftp.storeFile(newDest, input);
			input.close();			
		}		
	}
	
	private FileType getFileType(String path) throws IOException {
		// Judge where the src is directory or file		
		String parent = getParent(path);	
		String name = getName(path);
		FTPFile[] files = ftp.listFiles(parent);		
		if(files == null) {
			log.error("List parent failed, src: " + path);
			return null;
		} else {
			for(FTPFile file : files) {
				if(file.getName().equals(name)) {		
					if(file.isFile()) {						
						return FileType.FILE;
					} else if(file.isDirectory()) {						
						return FileType.DIRECOTRY;						
					} else {
						log.error("Ignored " + file.getType());
						return null;
					}
				}				
			}
		}
		return null;
	}
	
	private void downloadInternal(String src, String dest, FileType type) throws IOException {		
		String name = getName(src);
		String newDest =  joiner.join(dest, name);

		switch (type) { 
		case FILE:
			OutputStream output = new FileOutputStream(newDest);
			ftp.retrieveFile(src, output);
			output.close();
			return;
		case DIRECOTRY:
			File newDestDir = new File(newDest);
			newDestDir.mkdirs();
			FTPFile[] subs = ftp.listFiles(src);
			if(subs != null) {
				for(FTPFile sub : subs) {
					String newSrc = joiner.join(src, sub.getName());
					if(sub.isFile()) {
						downloadInternal(newSrc, newDest, FileType.FILE);
					} else if(sub.isDirectory()) {
						downloadInternal(newSrc, newDest, FileType.DIRECOTRY);
					} else {
						log.error("Ignored " + sub.getType());
					}
				}
			}
			return;
		default:
			break;
		}		
		
	}
	
	private String getName(String path) {
		File file = new File(path);
		return file.getName();
	}

	private String getParent(String path) {
		File file = new File(path);
		return file.getParent();		
	}

	@Override
	public String toString() {
		return "EasyFtp [ip=" + ip + ", port=" + port + ", user=" + user
				+ ", passwd=" + passwd + ", checksum=" + checksum + "]";
	}
	
	private static enum FileType {
		UNKNOWN,
		FILE,
		DIRECOTRY
	}
}
