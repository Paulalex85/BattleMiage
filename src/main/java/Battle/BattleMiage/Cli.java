package Battle.BattleMiage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;



public class Cli {
 private static final Logger log = Logger.getLogger(Cli.class.getName());
 private String[] args = null;
 private Options options = new Options();
 
 @Value("${rest.base.url}")
 private String url;
 
 @Value("${team.name}")
 private String name;
 
 @Value("${team.password}")
 private String password;
 
 /*public Cli(String[] args) {

  this.args = args;

  options.addOption("h", "help", false, "show help.");
  options.addOption("v", "var", true, "Here you can set parameter .");
  options.addOption("p", "pong", false, "Print pong .");
  options.addOption("config", "config", false, "Display all configs.");

 }*/

 public void parse() throws FileNotFoundException {
  CommandLineParser parser = new BasicParser();

  CommandLine cmd = null;
  try {
   cmd = parser.parse(options, args);

   if (cmd.hasOption("h"))
    help();

   if (cmd.hasOption("v")) {
    log.log(Level.INFO, "Using cli argument -v=" + cmd.getOptionValue("v"));
    // Whatever you want to do with the setting goes here
   }
   if (cmd.hasOption("p")) {
	    System.out.println("pong");
	   }
   if (cmd.hasOption("config")) {
	   FileReader f = new FileReader(new File("properties"));
		try {
			int i = 0;
			while((i = f.read()) != -1){
				System.out.print((char)i);
			}
		    f.close();    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
   
	   System.out.println(url);
	   System.out.println(name);
	   System.out.println(password);
	   }
   else {
    log.log(Level.SEVERE, "Missing v option");
    help();
   }

  } catch (ParseException e) {
   log.log(Level.SEVERE, "Failed to parse comand line properties", e);
   help();
  }
 }

 private void help() {
  // This prints out some help
  HelpFormatter formater = new HelpFormatter();

  formater.printHelp("Main", options);
  System.exit(0);
 }
}
