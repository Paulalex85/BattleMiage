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
        
        NewGame partie = new NewGame(tests);
    }
}
