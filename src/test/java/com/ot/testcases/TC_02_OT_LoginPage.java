package com.ot.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ot.base.OT_BASE;
import com.ot.pages_elements.OT_Home_Page_Elements_Methods;
import com.ot.pages_elements.OT_My_Account_Page_Elements_Methods;

public class TC_02_OT_LoginPage extends OT_BASE {

	OT_Home_Page_Elements_Methods OT_Home_Page_Elements = new OT_Home_Page_Elements_Methods();
	OT_My_Account_Page_Elements_Methods OT_My_Account_Page_Elements = new OT_My_Account_Page_Elements_Methods();

	@Test(groups = { "smoke" }, priority = 1, enabled = true)
	public void TC_Verify_Login_With_Valid_Email_Valid_Password_Untied_Account() throws Exception {
		loadPropertiyFile("login");
		OT_My_Account_Page_Elements.send_Email_text(System.getProperty("userName"));
		OT_My_Account_Page_Elements.send_Password_text(System.getProperty("password"));
		OT_My_Account_Page_Elements.click_SignIn_Button();
		waitsleep(5);
		if (OT_My_Account_Page_Elements.gettext_Email_Address().contains(System.getProperty("userName"))) {
			System.out.println("PASS : User is logged in Successfully");
			OT_My_Account_Page_Elements.click_logout_Button();
		} else {
			System.out.println("FAIL - User is not logged in Successfully");
			System.out.println("Actual text is  " + driver.getCurrentUrl());
			Assert.fail();
		}
	}

	@Test(groups = { "smoke" }, priority = 2, enabled = false)
	public void TC_Verify_Login_With_Valid_Email_Valid_Password_Tied_Account() throws Exception {
		OT_Home_Page_Elements.click_SignIn_Button();
		OT_My_Account_Page_Elements.send_Email_text(System.getProperty("tieduserName"));
		OT_My_Account_Page_Elements.send_Password_text(System.getProperty("tiedpassword"));
		OT_My_Account_Page_Elements.click_SignIn_Button();
		if (OT_My_Account_Page_Elements.gettext_logout_Button().equalsIgnoreCase("Sign Out")) {
			System.out.println("PASS : Tied User is logged in Successfully");
			OT_My_Account_Page_Elements.click_logout_Button();
		} else {
			System.out.println("FAIL : Tied User is not logged in Successfully");
			System.out.println("Actual text is  " + OT_My_Account_Page_Elements.gettext_logout_Button());
			Assert.fail();
		}

	}

	@Test(groups = { "smoke" }, priority = 3, enabled = false)
	public void TC_Verify_Login_With_Valid_Email_INValid_Password() throws Exception {
		OT_Home_Page_Elements.click_SignIn_Button();
		OT_My_Account_Page_Elements.send_Email_text(System.getProperty("userName"));
		OT_My_Account_Page_Elements.send_Password_text(System.getProperty("invalidpassword"));
		OT_My_Account_Page_Elements.click_SignIn_Button();
		if (OT_My_Account_Page_Elements.gettext_Validation_Message()
				.equalsIgnoreCase("Invalid Username or Password.")) {
			System.out.println("Validation message appeared properly");
		} else {
			System.out.println("Validation message is not proper");
			System.out.println("Actual text is  " + OT_My_Account_Page_Elements.gettext_Validation_Message());
			Assert.fail();
		}

	}

	@Test(groups = { "smoke" }, priority = 4, enabled = false)
	public void TC_Verify_Login_With_Blank_Email_Blank_Password() throws Exception {
		OT_Home_Page_Elements.click_SignIn_Button();
		OT_My_Account_Page_Elements.send_Email_text("");
		OT_My_Account_Page_Elements.send_Password_text("");
		OT_My_Account_Page_Elements.click_SignIn_Button();
		if (OT_My_Account_Page_Elements.gettext_Username_Validation_Message().equalsIgnoreCase("Username is required.")
				&& OT_My_Account_Page_Elements.gettext_Password_Validation_Message()
						.equalsIgnoreCase("Password is required.")) {
			System.out.println("Validation message appeared properly");
		} else {
			System.out.println("Validation message is not proper");
			System.out.println("Actual Username Validation is  "
					+ OT_My_Account_Page_Elements.gettext_Username_Validation_Message());
			System.out.println("Actual Password Validation is  "
					+ OT_My_Account_Page_Elements.gettext_Password_Validation_Message());
			Assert.fail();
		}

	}
//
//	@Test(priority = 6, enabled = false)
//	public void closebrowser() throws Exception {
//		Browser_Close();
//	}
}
