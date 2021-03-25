package com.ot.pages_elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.ot.base.OT_BASE;

public class OT_Home_Page_Elements_Methods extends OT_BASE {

	public By link_SignIn_BillPay_Button = By.xpath("//span[@class='user-message']");

	public By link_SignIn_Button = By.xpath("//input[@id='password']");

	public void click_SignIn_BillPay_Button() {
		click(link_SignIn_BillPay_Button, "Sign In/BillPay Button");
	}

	public void click_SignIn_Button() {
		click(link_SignIn_Button, "Sign In Button");
	}

}
