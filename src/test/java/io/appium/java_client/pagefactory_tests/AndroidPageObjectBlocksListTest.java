package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory_tests.blocks.AndroidPageBlock;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AndroidPageObjectBlocksListTest {

	private WebDriver driver;

	List<AndroidPageBlock> content;
	
	@SuppressWarnings("rawtypes")
	@Before
	public void setUp() throws Exception {
	    File appDir = new File("src/test/java/io/appium/java_client");
	    File app = new File(appDir, "ApiDemos-debug.apk");
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
	    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

	    //This time out is set because test can be run on slow Android SDK emulator
		PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void findByElementsTest() {
		Assert.assertNotEquals(0, content.get(0).textVieWs.size());
	}

	@Test
	public void findByElementTest() {
		Assert.assertNotEquals(null, content.get(0).textView.getAttribute("text"));
	}


	@Test
	public void androidFindByElementsTest(){
		Assert.assertNotEquals(0, content.get(0).androidTextViews.size());
	}

	@Test
	public void androidFindByElementTest(){
		Assert.assertNotEquals(null, content.get(0).androidTextView.getAttribute("text"));
	}

	@Test
	public void checkThatElementsWereNotFoundByIOSUIAutomator(){
		Assert.assertEquals(0, content.get(0).iosTextViews.size());
	}

	@Test
	public void checkThatElementWasNotFoundByIOSUIAutomator(){
		NoSuchElementException nsee = null;
		try{
			content.get(0).iosTextView.getAttribute("text");
		}
		catch (Exception e){
			nsee = (NoSuchElementException) e;
		}
		Assert.assertNotNull(nsee);
	}

	@Test
	public void androidOrIOSFindByElementsTest(){
		Assert.assertNotEquals(0, content.get(0).androidOriOsTextViews.size());
	}

	@Test
	public void androidOrIOSFindByElementTest(){
		Assert.assertNotEquals(null, content.get(0).androidOriOsTextView.getAttribute("text"));
	}

	@Test
	public void androidFindByUIAutomatorElementsTest(){
		Assert.assertNotEquals(0, content.get(0).androidUIAutomatorViews.size());
	}

	@Test
	public void androidFindByUIAutomatorElementTest(){
		Assert.assertNotEquals(null, content.get(0).androidUIAutomatorView.getAttribute("text"));
	}

	@Test
	public void areMobileElementsTest(){
		Assert.assertNotEquals(0, content.get(0).mobileElementViews.size());
	}

	@Test
	public void isMobileElementTest(){
		Assert.assertNotEquals(null, content.get(0).mobileElementView.getAttribute("text"));
	}

	@Test
	public void areMobileElements_FindByTest(){
		Assert.assertNotEquals(0, content.get(0).mobiletextVieWs.size());
	}

	@Test
	public void isMobileElement_FindByTest(){
		Assert.assertNotEquals(null, content.get(0).mobiletextVieW.getAttribute("text"));
	}

	@Test
	public void areRemoteElementsTest(){
		Assert.assertNotEquals(0, content.get(0).remoteElementViews.size());
	}

	@Test
	public void isRemoteElementTest(){
		Assert.assertNotEquals(null, content.get(0).remotetextVieW.getAttribute("text"));
	}

	@Test
	public void androidChainSearchElementsTest(){
		Assert.assertNotEquals(0, content.get(0).chainElementViews.size());
	}

	@Test
	public void androidChainSearchElementTest(){
		Assert.assertNotEquals(null, content.get(0).chainElementView.getAttribute("text"));
	}

	@Test
	public void checkThatElementsWereNotFoundByIOSUIAutomator_Chain(){
		Assert.assertEquals(0, content.get(0).iosChainTextViews.size());
	}

	@Test
	public void checkThatElementWasNotFoundByIOSUIAutomator_Chain(){
		NoSuchElementException nsee = null;
		try{
			content.get(0).iosChainTextView.getAttribute("text");
		}
		catch (Exception e){
			nsee = (NoSuchElementException) e;
		}
		Assert.assertNotNull(nsee);
	}

	@Test
	public void androidOrIOSFindByElementsTest_ChainSearches(){
		Assert.assertNotEquals(0, content.get(0).chainAndroidOrIOSUIAutomatorViews.size());
	}

	@Test
	public void androidOrIOSFindByElementTest_ChainSearches(){
		Assert.assertNotEquals(null, content.get(0).chainAndroidOrIOSUIAutomatorView.getAttribute("text"));
	}	
	
	@Test
	public void isAndroidElementTest(){
		Assert.assertNotEquals(null, content.get(0).androidElementView.getAttribute("text"));
	}	
	
	@Test
	public void areAndroidElementsTest(){
		Assert.assertNotEquals(0, content.get(0).androidElementViews.size());
	}		
	
	@Test
	public void findAllElementTest(){
		Assert.assertNotEquals(null, content.get(0).findAllElementView.getAttribute("text"));
	}	
	
	@Test
	public void findAllElementsTest(){
		Assert.assertNotEquals(0, content.get(0).findAllElementViews.size());
	}	
        
	@Test
	public void findByAndroidAnnotationOnlyTest(){
		Assert.assertNotEquals(null, content.get(0).textAndroidId.getAttribute("text"));
	}

    @Test
    public void isTouchableElement(){
        Assert.assertNotEquals(null, content.get(0).touchabletextVieW.getAttribute("text"));
    }

    @Test
    public void areTouchableElements(){
        Assert.assertNotEquals(0, content.get(0).touchabletextVieWs.size());
    }

    @Test
    public void isTheFieldAndroidElement(){
        @SuppressWarnings("unused")
		AndroidElement androidElement = (AndroidElement) content.get(0).mobiletextVieW; //declared as MobileElement
        androidElement = (AndroidElement) content.get(0).androidTextView; //declared as WedElement
        androidElement = (AndroidElement) content.get(0).remotetextVieW;  //declared as RemoteWedElement
        androidElement = (AndroidElement) content.get(0).touchabletextVieW; //declared as TouchABLEElement
    }

    @Test
    public void checkThatTestWillNotBeFailedBecauseOfInvalidFindBy(){
        try {
            Assert.assertNotEquals(null, content.get(0).elementWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy.getAttribute("text"));
        }
        catch (NoSuchElementException ignored){
            return;
        }
        throw new RuntimeException(NoSuchElementException.class.getName() + " has been expected.");
    }

    @Test
    public void checkThatTestWillNotBeFailedBecauseOfInvalidFindBy_List(){
        Assert.assertEquals(0, content.get(0).elementsWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy.size());
    }
}
