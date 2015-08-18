/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.pagefactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all of the
 * WebElement fields and 2) List<WebElement> fields that have
 * {@literal @AndroidFindBy}, {@literal @AndroidFindBys}, or
 * {@literal @iOSFindBy/@iOSFindBys} annotation with a proxy that locates the
 * elements using the passed in ElementLocatorFactory.
 * 
 * Please pay attention: fields of {@link WebElement}, {@link RemoteWebElement},
 * {@link MobileElement}, {@link AndroidElement} and {@link IOSElement} are allowed 
 * to use with this decorator
 */
public class AppiumFieldDecorator implements FieldDecorator {

    private final static Map<Class<? extends SearchContext>, Class<? extends WebElement>> elementRuleMap =
            new HashMap<Class<? extends SearchContext>, Class<? extends WebElement>>(){
                private static final long serialVersionUID = 1L;
                {
                    put(AndroidDriver.class, AndroidElement.class);
                    put(AndroidElement.class, AndroidElement.class);
                    put(IOSDriver.class, IOSElement.class);
                    put(IOSElement.class, IOSElement.class);
                }
            };
	
	private final AppiumElementLocatorFactory factory;
    private final SearchContext context;
	public static long DEFAULT_IMPLICITLY_WAIT_TIMEOUT = 1;

	public static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;

	public AppiumFieldDecorator(SearchContext context, long implicitlyWaitTimeOut, TimeUnit timeUnit) {
        this.context = context;
		factory = new AppiumElementLocatorFactory(this.context, new TimeOutDuration(implicitlyWaitTimeOut, timeUnit));
	}

    public AppiumFieldDecorator(SearchContext context, TimeOutDuration timeOutDuration) {
        this.context = context;
        factory = new AppiumElementLocatorFactory(this.context, timeOutDuration);
    }
	
	public AppiumFieldDecorator(SearchContext context) {
        this.context = context;
		factory = new AppiumElementLocatorFactory(this.context);
	}

	public Object decorate(ClassLoader ignored, Field field) {
		ElementLocator locator;
		if (AppiumElementUtils.isDecoratableElement(field)) {
			locator = factory.createLocator(field);
			return proxyForLocator(locator);
		}
		if (AppiumElementUtils.isDecoratableList(field)) {
			locator = factory.createLocator(field);
			return proxyForListLocator(locator);
		}
		if(AppiumElementUtils.isAndroidElement(field) || AppiumElementUtils.isIOSElement(field)) {
			MobileElement element = (MobileElement) proxyForLocator(factory.createLocator(field.getType()));
			PageFactory.initElements(new AppiumElementLocatorFactory(element), element);
			return element;
		}
		return null;
	}

    private Class<?> getTypeForProxy(){
        Class<?> contextClass = context.getClass();
        Iterable<Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>>> rules = elementRuleMap.entrySet();
        Iterator<Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>>> iterator = rules.iterator();
        while (iterator.hasNext()){ //it will return MobileElement subclass when here is something
            //that extends AppiumDriver or MobileElement
            Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>> e = iterator.next();
            if (e.getKey().isAssignableFrom(contextClass))
                return e.getValue();
        } //it is compatible with desktop browser. So at this case it returns RemoteWebElement.class
        return RemoteWebElement.class;
    }

	private Object proxyForLocator(ElementLocator locator) {
		ElementInterceptor elementInterceptor = new ElementInterceptor(locator);
		return ProxyFactory.getEnhancedProxy(getTypeForProxy(), elementInterceptor);
	}
	
	@SuppressWarnings("unchecked")
	private List<WebElement> proxyForListLocator(
			ElementLocator locator) {
		ElementListInterceptor elementInterceptor = new ElementListInterceptor(locator);
		return ProxyFactory.getEnhancedProxy(ArrayList.class, elementInterceptor);
	}
}
