package com.play.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTutorial {
	public static void main(String[] args) {
		String pattern = ".*/s2/c([0-9]*)-([^?^/]*)/b([0-9,]*)/a([0-9,]*)-s([0-9]*)-v([0-2]*)-p([0-9]*)-price([,0-9]*)-d([0-2]*)-f([0-9]*)-m([0-1]*)-rt([0-1]*)-pid([,0-9]*)-mid([0-9]*)-k([^?^/]*)/([0-9]*).*$";
		String path = "http://www.yhd.com/ctg/s2/c34844-0/b/a100564,119295-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/20/";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(path);
		if(m.find()) {
			for(int i = 1; i <= m.groupCount(); ++i) {
				System.out.println("$" + i + " : " + m.group(i));
			}
		}
		
	}
}
