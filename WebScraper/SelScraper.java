import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Carlo Jacques
 *
 */
public class SelScraper {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 */
	
	private static FileWriter file;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, JSONException {
		// List for data
		List<String> timeList = new ArrayList<String>();
		List<String> attackList = new ArrayList<String>();
		List<String> attackTypeList = new ArrayList<String>();
		List<String> attackCountryList = new ArrayList<String>();
		List<String> targetCountryList = new ArrayList<String>();

		// Initializing JSONObject object
		JSONObject jsonObject = new JSONObject();
		
		// Initializing Web Scraper
		System.setProperty("webdriver.chrome.driver", "/Users/macuser/Desktop/Barry/FALL 2022/CS-372/WebScraper/chromedriver.exe");	
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://threatmap.bitdefender.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		
		
		
		// Getting data from https://threatmap.bitdefender.com/
		for(;;) {
			//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds((long) 0.3));
			//String table = driver.findElement(By.cssSelector("table[class='table_data']")).getText();
			//String time = driver.findElement(By.cssSelector("table[class='table_data']")).getText();
			String time = driver.findElement(By.xpath("/html/body/footer/div[2]/div/div[2]/table/tbody/tr[1]/td[1]")).getText();
			String attack = driver.findElement(By.xpath("/html/body/footer/div[2]/div/div[2]/table/tbody/tr[1]/td[2]")).getText();
			String attackType = driver.findElement(By.xpath("/html/body/footer/div[2]/div/div[2]/table/tbody/tr[1]/td[3]")).getText();
			String attackCountry = driver.findElement(By.xpath("/html/body/footer/div[2]/div/div[2]/table/tbody/tr[1]/td[4]")).getText();
			String targetCountry = driver.findElement(By.xpath("/html/body/footer/div[2]/div/div[2]/table/tbody/tr[1]/td[5]")).getText();
			
			// Writing data
			FileWriter data = new FileWriter("data.csv", true);
			data.write(time + "," + attack + "," + attackType + "," + attackCountry + "," + targetCountry + "\n");
			data.close();
			
			timeList.add(time);
			attackList.add(attack);
			attackTypeList.add(attackType);
			attackCountryList.add(attackCountry);
			targetCountryList.add(targetCountry);
			
			jsonObject.put("time", timeList);
			jsonObject.put("attack", attackList);
			jsonObject.put("attackType", attackTypeList);
			jsonObject.put("attackCountry", attackCountryList);
			jsonObject.put("targetCountry", targetCountryList);

			// etc.
			
			//final String json = jsonObject.toString(); // <-- JSON string
			try {
				//Construct a FileWriter given file name
				file = new FileWriter("data.json");
				file.write(jsonObject.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					file.flush();
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			//timeList.add(time);
			//attackList.add(attack);
			//System.out.println(attackList);
		}
	}

}

