package Battle.BattleMiage;
import org.springframework.context.annotation.*;

import java.io.*;
/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan(basePackages = {"Battle.BattleMiage"})
public class BattleMain 
{
	//http://challengemiage.codeandplay.date/public-ihm/api.html
    public static void main( String[] args ) throws FileNotFoundException 
    {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BattleMain.class);
        Param tests = context.getBean(Param.class);
        
        System.out.println("Que des numéros 10 dans ma team");
        //args = -e N vs bot difficulté N ou -m vs team
        if(new String("-e").equals(args[0])) { // match vs bot
        	System.out.println("Partie vs bot niveau :" + args[1]);
        	NewGame partie = new NewGame(tests, args[1].toString());
        }
        else {
        	System.out.println("Partie vs team");
        	NewGame partie = new NewGame(tests);
        }
    }
}
