package com.play.string;

import java.util.regex.Matcher;

public class RegexTest {
	public static void main(String[] args) {
//		String template = "test $TEST $TEST";

		//String template = "test $CLASS_NAME_HOLDER";
		String template ="--class-name \n$CLASS_NAME_HOLDER\n\n--query\n$SQOOP_QUERY_HOLDER\n\n-m\n1";
//		String replacement = Matcher.quoteReplacement("$TEST");
		String replacement = Matcher.quoteReplacement("$CLASS_NAME_HOLDER");
		String replacement2 = Matcher.quoteReplacement("$SQOOP_QUERY_HOLDER");
		String str = template.replaceAll(replacement, "test").replaceAll(replacement2, "$sqoop");
		System.out.println(str);
	}
}
