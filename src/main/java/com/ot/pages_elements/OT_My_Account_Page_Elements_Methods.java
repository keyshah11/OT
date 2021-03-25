package com.ot.pages_elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.ot.base.OT_BASE;

public class OT_My_Account_Page_Elements_Methods extends OT_BASE {

	OT_Home_Page_Elements_Methods OT_Home_Page_Elements = new OT_Home_Page_Elements_Methods();

	public By txtbox_EmailAddress = By.id("email");

	public By txtbox_Password = By.xpath("//input[@id='password']");

	public By btn_SignIn_Submit = By.xpath("//span[@class='MuiButton-label']");

	public By btn_Account_Icon = By.xpath(
			"//button[@aria-label='account of current user']//span[@class='MuiIconButton-label']//*[local-name()='svg']");

	public By btn_Logout = By.xpath("//*[@id=\"user-menu\"]/div[3]/ul/div[2]/li/div[2]");

	public By txt_email_address = By.xpath("//*[@id=\"user-menu\"]/div[3]/ul/div[1]/div/div[2]");
	
	public By txt_invalid_credentials_validation = By.xpath("//span[@class='color5 font14']");

	public By txt_invalid_username_validation = By.id("SI_UsernameRequiredError");

	public By txt_invalid_password_validation = By.id("SI_PasswordRequiredError");

	public By btn_Register = By.id("MA_RegisterButton");

	public void click_SignIn_Button() throws InterruptedException {
		click(btn_SignIn_Submit, "Sign In Button");
		waitsleep(3);
	}

	public void click_Regiser_Button() throws InterruptedException {
		click(btn_Register, "Register Button");
		waitsleep(3);
	}

	public void send_Email_text(String Username) {
		sendKeys(txtbox_EmailAddress, Username, "Username Textbox");
	}

	public void send_Password_text(String Password) {
		sendKeys(txtbox_Password, Password, "Password Textbox");
	}

	public void click_Account_Icon_Button() {
		click(btn_Account_Icon, "Account Icon Button");
	}

	public boolean isivisile_Account_Icon_Button() {
		boolean flag;
		flag  = isDisplayed(btn_Account_Icon);
		System.out.println(flag);
		return flag;
	}

	public void click_logout_Button() {
		click(btn_Logout, "Logout Button");
	}
	
	public String gettext_Email_Address() {
		click_Account_Icon_Button();
		return getText(txt_email_address, "Email address text");
		

	}

	public String gettext_logout_Button() {
		return getText(btn_Logout, "Logout Button");

	}

	public String gettext_Validation_Message() {
		return getText(txt_invalid_credentials_validation, "Invalid Credentials Validation Message");

	}

	public String gettext_Username_Validation_Message() {
		return getText(txt_invalid_username_validation, "Invalid Username Validation Message");

	}

	public String gettext_Password_Validation_Message() {
		return getText(txt_invalid_password_validation, "Invalid Password Validation Message");

	}

	public void Successful_Untied_Login() throws InterruptedException {

		OT_Home_Page_Elements.click_SignIn_Button();
		send_Email_text(System.getProperty("userName"));
		send_Password_text(System.getProperty("password"));
		click_SignIn_Button();
	}

	public void Successful_Tied_Login() throws InterruptedException {

		OT_Home_Page_Elements.click_SignIn_Button();
		send_Email_text(System.getProperty("userName"));
		send_Password_text(System.getProperty("password"));
		click_SignIn_Button();
	}

}
