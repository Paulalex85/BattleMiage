package Battle.BattleMiage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("properties")
@Component
public class Param {
	@Value("${rest.base.url}")
	 private String url;
	 
	 public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Value("${team.name}")
	 private String name;
	 
	 @Value("${team.password}")
	 private String password;
	 
	 public void print(){
		 System.out.println(url);
		 System.out.println(name);
		 System.out.println(password);
	 }
}
