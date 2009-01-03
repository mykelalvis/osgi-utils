/**
 * 
 */
package com.joaoassuncao.osgi.log.cmd;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.StringTokenizer;

import org.apache.felix.shell.Command;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author jassuncao
 * @scr.component metatype="no" immediate="true" name="Log4jShellCommand"
 * @scr.service interface="org.apache.felix.shell.Command"
 */
public class Log4jCommand implements Command {

	private static final String COMMAND_NAME = "log4j";
	private static final String COMMAND_DESCRIPTION = "Change log4j settings";
	private static final String COMMAND_USAGE = COMMAND_NAME+" [<loggername> [<level>]]";

	public void execute(String line, PrintStream out, PrintStream err) {
		StringTokenizer st = new StringTokenizer(line, " ");
        // Ignore the command name.
        st.nextToken();
        if (st.hasMoreTokens()==false) {
        	listLoggers(out,err);
        }
        else {
        	String loggerName = st.nextToken();        	
        	if(st.hasMoreTokens()==false)
        		displayLoggerLevel(loggerName, out, err );
        	else {
        		String levelName = st.nextToken();
        		setLoggerLevel(loggerName,levelName, out, err);
        	}        	
        }     
	}	

	private void displayLoggerLevel(String loggerName, PrintStream out, PrintStream err ) {
		if(loggerName.endsWith("*")){
			displayMatchingLoggersLevel(loggerName, out, err);
		}
		else {
			Logger logger = Logger.getRootLogger().getLoggerRepository().exists(loggerName);
			displayLoggerLevel(logger,out,err);
		}		
	}
	
	private void displayMatchingLoggersLevel(String loggerName, PrintStream out, PrintStream err) {
		String loggerPrefix = loggerName.substring(0,loggerName.length()-1);
		out.println("  Level\t\tName");
		Logger rootLogger = Logger.getRootLogger();
		if(rootLogger.getName().startsWith(loggerPrefix))
			printLoggerInfo(rootLogger, out);
		Enumeration<?> enumerator = rootLogger.getLoggerRepository().getCurrentLoggers();				
		while(enumerator.hasMoreElements()){
			Logger logger = (Logger) enumerator.nextElement();
			if(logger.getName().startsWith(loggerPrefix))
				printLoggerInfo(logger, out);
		}
	}

	private void displayLoggerLevel(Logger logger, PrintStream out, PrintStream err ) {
		if(logger==null ){			
			out.println("Logger not found");
			return;
		}
		Level level = logger.getEffectiveLevel();
		out.println(levelToString(level));
	}

	private void setLoggerLevel(String loggerName, String levelName, PrintStream out, PrintStream err) {
		Logger logger = Logger.getRootLogger().getLoggerRepository().getLogger(loggerName);
		Level level = Level.toLevel(levelName);
		logger.setLevel(level);
	}

	public String getName() {
		return COMMAND_NAME;
	}

	public String getShortDescription() {
		return COMMAND_DESCRIPTION;
	}

	public String getUsage() {
		return COMMAND_USAGE;
	}
	
	private void listLoggers(PrintStream out, PrintStream err) {		
		out.println("  Level\t\tName");
		Logger rootLogger = Logger.getRootLogger();
		printLoggerInfo(rootLogger, out);
		Enumeration<?> enumerator = rootLogger.getLoggerRepository().getCurrentLoggers();				
		while(enumerator.hasMoreElements()){
			printLoggerInfo((Logger) enumerator.nextElement(), out);
		}
	}
	
	private void printLoggerInfo(Logger logger, PrintStream out){
		out.println(levelToString(logger.getEffectiveLevel())+"\t\t"+ logger.getName());
	}
	
	private String levelToString(Level level){
		if(level!=null)
			return level.toString();
		else
			return "NOT SET";
	}

}
