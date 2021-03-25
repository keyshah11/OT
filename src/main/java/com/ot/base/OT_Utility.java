package com.ot.base;

import org.testng.Assert;



import org.apache.log4j.Logger;

public class OT_Utility extends OT_BASE {
	Logger log = Logger.getLogger(OT_Utility.class);
	

	public void Verify_Page_Title(String actualPageTitle, String expectedPageTitle, String pageName)
			throws InterruptedException {
		try {
			if (actualPageTitle.equalsIgnoreCase(expectedPageTitle)) {
				System.out.println("PASSED : " + pageName + " Title Verified");
			} else {
				System.out.println("ELSE Failed : " + pageName + " Title Not Verified");
				System.out.println("Actual Page Title is " + actualPageTitle);
				System.out.println("Expected Page Title is " + expectedPageTitle);
				Assert.fail("ELSE ASSERT FAILED : " + pageName + " Title Not Verified");
			}

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("CATCH FAILED : " + pageName + " Title Not Verified");
			System.out.println("Actual Page Title is " + actualPageTitle);
			System.out.println("Expected Page Title is " + expectedPageTitle);
			Assert.fail("CATCH ASSERT FAILED : " + pageName + " Title Not Verified");
		}
	}

	public void Verify_Page_Title_Contains(String actualPageTitle, String expectedPageTitle, String pageName)
			throws InterruptedException {
		try {
			if (actualPageTitle.contains(expectedPageTitle)) {
				System.out.println("PASSED : " + pageName + " Title Verified");
			} else {
				System.out.println("ELSE Failed : " + pageName + " Title Not Verified");
				System.out.println("Actual Page Title is " + actualPageTitle);
				System.out.println("Expected Page Title is " + expectedPageTitle);
				log.error("Not Found");
				Assert.fail("ELSE ASSERT FAILED : " + pageName + " Title Not Verified");
			}

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("CATCH FAILED : " + pageName + " Title Not Verified");
			System.out.println("Actual Page Title is " + actualPageTitle);
			System.out.println("Expected Page Title is " + expectedPageTitle);
			Assert.fail("CATCH ASSERT FAILED : " + pageName + " Title Not Verified");
		}
	}

}
