package Battle.BattleMiage;
import org.springframework.context.ApplicationContext;
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
    public static void main( String[] args ) throws FileNotFoundException 
    {
    	
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BattleMain.class);
        Param tests = context.getBean(Param.class);
       // tests.print();
        
    	ApacheRest rest = new ApacheRest();
    	String data = rest.Request_GET(
    			"http://challengemiage.codeandplay.date/epic-ws/epic/player/getIdEquipe/"
    			+ tests.getName() 
    			+ "/" 
    			+ tests.getPassword()
    			);
    	//https://stackoverflow.com/questions/11874919/parsing-json-string-in-java		
    }
    
    
    
}
