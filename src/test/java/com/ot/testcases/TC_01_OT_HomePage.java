package com.ot.testcases;
import org.testng.annotations.Test;

import com.ot.base.OT_BASE;
import com.ot.base.OT_Utility;

public class TC_01_OT_HomePage extends OT_BASE {

	OT_Utility ot_utility = new OT_Utility();

	@Test(groups = { "smoke" }, priority = 1, enabled = false)
	public void TC_Verify_HomePage_Title() throws Exception {
		ot_utility.Verify_Page_Title(driver.getTitle(), prop.getProperty("home_page_title"), "Home Page");
	}

}
