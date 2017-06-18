package digital;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class MappDigital {
	WebDriver driver;
	private static final String URL = "http://timvroom.com/selenium/playground/";
	private static final int DEFAULT_TIMEOUT = 2;
	
	@BeforeClass(description = "Set up")
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	
	@Test(description = "Grab page title and place title text in answer slot #1", priority = 1)
	public void GrabPageTitleToSlot1() {
		final By BY_ANSWER = By.id("answer1");
		final String PAGE_TITLE = "Selenium Playground";
		
		driver.get(URL);
		String pageTitle = driver.getTitle();
		assertEquals(pageTitle, PAGE_TITLE);
		setSlotValue(BY_ANSWER, PAGE_TITLE);
	}
	
	@Test(description = "Fill out name section of form to be Kilgore Trout", priority = 2)
	public void FillOutNameSection() {
		final By BY_NAME = By.id("name");
		final String NAME_CONTENT = "Kilgore Trout";
		
		WebElement nameInput = driver.findElement(BY_NAME);
		nameInput.clear();
		nameInput.sendKeys(NAME_CONTENT);
		assert( (new WebDriverWait(driver, DEFAULT_TIMEOUT)).until(ExpectedConditions.attributeContains(BY_NAME, "value", NAME_CONTENT)));
	}
	
	@Test(description = "Set occupation on form to Sci-Fi Author", priority = 3)
	public void SetOccupation() {
		final By BY_OCCUPATION = By.id("occupation");
		final String OCCUPATION_CONTENT = "scifiauthor";
		
		Select occupationDropdown = new Select(driver.findElement(BY_OCCUPATION));
		occupationDropdown.selectByValue(OCCUPATION_CONTENT);
		assert( (new WebDriverWait(driver, DEFAULT_TIMEOUT)).until(ExpectedConditions.attributeContains(BY_OCCUPATION, "value", OCCUPATION_CONTENT)));
	}
	
	@Test(description = "Count number of blue_boxes on page after form and enter into answer box #4", priority = 4)
	public void CountBlueBoxes() {
		final By BY_ANSWER = By.id("answer4");
		
		int elementSize = driver.findElements(By.className("bluebox")).size();
		assert(elementSize>=1);
		setSlotValue(BY_ANSWER, Integer.toString(elementSize));
	}
	
	@Test(description = "Click link that says 'click me'", priority = 5)
	public void ClickLink() {
		final String CLICK_ME_TEXT = "click me";
		final By BY_CLICK_ME = By.linkText(CLICK_ME_TEXT);
		
		WebElement clickMeLink = driver.findElement(BY_CLICK_ME);
		clickMeLink.click();
	}
	
	@Test(description = "Find red box on its page find class applied to it, and enter into answer box #6", priority = 6)
	public void FindRedBoxClass() {
		final By BY_RED_BOX = By.id("redbox");
		final By BY_ANSWER = By.id("answer6");
		
		WebElement redBox = driver.findElement(BY_RED_BOX);
		assert(redBox.getText().contains("Red Box "));
		String className = redBox.getAttribute("className");		
		setSlotValue(BY_ANSWER, className);
	}
	
	@Test(description = "Run JavaScript function as: ran_this_js_function() from your Selenium script", priority = 7)
	public void RunJavaScript() {
		if (driver instanceof JavascriptExecutor) {
		    ((JavascriptExecutor)driver).executeScript("ran_this_js_function();");
		} else {
		    throw new IllegalStateException("This driver does not support JavaScript!");
		}
	}
	
	@Test(description = "Run JavaScript function as: got_return_from_js_function() from your Selenium script, take returned value and place it in answer slot #8", priority = 8)
	public void RunJavaScriptGetValue() {
		final String NAME_OF_FUNCTION = "return got_return_from_js_function()";
		final By BY_ANSWER = By.id("answer8");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object result = js.executeScript(NAME_OF_FUNCTION);
		assert(!result.toString().equals(""));	
		setSlotValue(BY_ANSWER, result.toString());
	}
	
	@Test(description = "Mark radio button on form for written book? to Yes", priority = 9)
	public void MarkRadioButton() {
		final By BY_WROTE_BOOK_RADIO = By.name("wrotebook");
		WebElement writtenRadio = driver.findElement(BY_WROTE_BOOK_RADIO);
		writtenRadio.click();
		assert(writtenRadio.isSelected());
	}
	
	@Test(description = "Get the text from the Red Box and place it in answer slot #10", priority = 10)
	public void GetRedBoxText() {
		final By BY_RED_BOX = By.id("redbox");
		final By BY_ANSWER = By.id("answer10");
		
		WebElement redBox = driver.findElement(BY_RED_BOX);
		assert(redBox.getText().contains("Red Box "));
		String redBoxText = redBox.getText();		
		setSlotValue(BY_ANSWER, redBoxText);
	}
	
	@Test(description = "Which box is on top? orange or green -- place correct color in answer slot #11", priority = 11)
	public void WhichBoxOnTop() {
		final By BY_TOP_BOX = By.xpath("//*[contains(text(),'Boxes to check arrangement of')]//following-sibling::span[1]");
		final By BY_ANSWER = By.id("answer11");
		
		WebElement topBox = driver.findElement(BY_TOP_BOX);
		String topBoxText = topBox.getText();
		assert(topBoxText.contains(" Box"));
		setSlotValue(BY_ANSWER, topBoxText.replace(" Box", ""));
	}
	
	@Test(description = "Set browser width to 850 and height to 650", priority = 12)
	public void ResizeBrowser() {
		final int browserWidth = 850;
		final int browserHeight = 650;
		
		Dimension dimension = new Dimension(browserWidth-2, browserHeight-2);	//new Dimension(browserWidth, browserHeight) not working properly
		driver.manage().window().setSize(dimension);
		
		assert(driver.manage().window().getSize().getWidth()==browserWidth);
		assert(driver.manage().window().getSize().getHeight()==browserHeight);
	}
	
	@Test(description = "Type into answer slot 13 yes or no depending on whether item by id of ishere is on the page", priority = 13)
	public void YesNoIfOnPageIsthere() {
		final By BY_ANSWER = By.id("answer13");
		final By BY_ELEMENT = By.cssSelector("div[id=ishere]");

		Boolean isPresent = false;
		try{
			if(driver.findElement(BY_ELEMENT)!=null) isPresent = true;
		} catch (Exception e){ }
		String isPresentAnswer = "no";
		if(isPresent) isPresentAnswer = "yes";
		setSlotValue(BY_ANSWER, isPresentAnswer);
	}
	
	@Test(description = "Type into answer slot 14 yes or no depending on whether item with id of purplebox is visible", priority = 14)
	public void YesNoIfOnPageVisiblePurplebox() {
		final By BY_ANSWER = By.id("answer14");
		final By BY_ELEMENT_NOT_VISIBLE = By.xpath("//div[@id='purplebox' and contains(@style, 'display: none')]");
		
		Boolean isNonVisible = false;
		try{
			if(driver.findElement(BY_ELEMENT_NOT_VISIBLE)!=null) isNonVisible = true;
		} catch (Exception e){ }
			
		String isVisibleAnswer = "yes";
		if(isNonVisible) isVisibleAnswer = "no";
		setSlotValue(BY_ANSWER, isVisibleAnswer);
	}
	
	@Test(description = "Waiting game: click the link with link text of 'click then wait' a random wait will occur (up to 10 seconds) and then a new link will get added with link text of 'click after wait' - click this new link within 500 ms of it appearing to pass this test", priority = 15)
	public void WaitingGame() {
		final By BY_CLICK_THEN_WAIT = By.xpath("//*[text()='click then wait']");
		final By BY_CLICK_AFTER_WAIT = By.xpath("//*[text()='click after wait']");
		
		driver.findElement(BY_CLICK_THEN_WAIT).click();
		new WebDriverWait(driver, 12, 200).until(ExpectedConditions.elementToBeClickable(BY_CLICK_AFTER_WAIT)).click();
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText().replaceAll("[^\\d]", "");
		int alertValue = Integer.parseInt(alertText.toString());
		assert(alertValue<=500);
	}
	
	@Test(description = "Click OK on the confirm after completing task 15", priority = 16)
	public void AcceptAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
		assert( new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.not(ExpectedConditions.alertIsPresent())) );
	}
	
	@Test(description = "Click the submit button on the form", priority = 17)
	public void ClickSubmitButton() {
		final By BY_SUBMIT_QUERY = By.id("submitbutton");
		
		WebElement submitQuery = driver.findElement(BY_SUBMIT_QUERY);
		submitQuery.click();
	}
	
	
	@AfterClass(description = "Close browser")
	public void tearDown(){
		driver.quit();
	}

	
	void setSlotValue(By byAnswer, String result){
		WebElement answerSlot = (new WebDriverWait(driver, DEFAULT_TIMEOUT))
				.until(ExpectedConditions.presenceOfElementLocated(byAnswer));
		answerSlot.clear();
		answerSlot.sendKeys(result.toString());
		assert( (new WebDriverWait(driver, DEFAULT_TIMEOUT)).until(ExpectedConditions.attributeContains(byAnswer, "value", result.toString())));
	}

}