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
    	ApacheRest rest = new ApacheRest();
    	String data = rest.Request_GET("https://gturnquist-quoters.cfapps.io/api/random");
    	System.out.println(data);
    	//https://stackoverflow.com/questions/11874919/parsing-json-string-in-java
    	
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BattleMain.class);
        Param tests = context.getBean(Param.class);
        tests.print();
			
    }
}
