package com.play.debug;

public class FunctionCall {
	
    private static final int CLIENT_CODE_STACK_INDEX;

    /**
     * http://stackoverflow.com/questions/442747/getting-the-name-of-the-current-executing-method
     */
    static {
    	// Finds out the index of "this code" in the returned stack trace - funny but it differs in JDK 1.5 and 1.6
    	int i = 0;
    	for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
    		/*
				System.out.println("**** \t" + ste);
    		 	**** 	java.lang.Thread.getStackTrace(Thread.java:1567)
    		 	**** 	com.play.debug.FunctionCall.<clinit>(FunctionCall.java:9)
			*/
    		i++;
    		if (ste.getClassName().equals(FunctionCall.class.getName())) {
    			break;
    		}
    	}
    	CLIENT_CODE_STACK_INDEX = i;
    }

	public static void printStackTrace() {
		System.out.println("------------------");
		final StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		for(StackTraceElement ste : stes) {
			System.out.println("\t" + ste);
		}
	}
	/**
	 * Get the method name for a depth in call stack. <br />
	 * Utility function
	 * @param depth depth in the call stack (0 means current method, 1 means call method, ...)
	 * @return method name
	 */
	public static String getMethodName(final int depth)
	{
	  final StackTraceElement[] ste = Thread.currentThread().getStackTrace();

	  /* STE is like this:
	   
		[0] java.lang.Thread.getStackTrace(Thread.java:1567)
		[1] com.play.debug.FunctionCall.getMethodName(FunctionCall.java:18)
		[2] com.play.mthread.EmbedSynchronized.syncL1(EmbedSynchronized.java:8)
		[3] com.play.mthread.EmbedSynchronized.main(EmbedSynchronized.java:22)
		
		so starting from the 3rd.
		
		*/
	  if(depth < ste.length - 1 - CLIENT_CODE_STACK_INDEX) {
		  return ste[depth + CLIENT_CODE_STACK_INDEX].getMethodName();
	  } else {
		  return null;
	  }
	}
}
