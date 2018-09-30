package com.arati.tools.objCompare;

import org.junit.*;

public class TestObjest {

	@Test
	public void comparisonTest() throws Exception {
		Car honda= new Car();
		honda.number_of_wheels=4;
			Wheel w1 = new Wheel();
			w1.number_of_spokes = 24;
				Spoke s1 = new Spoke();
				s1.length = 5;
				s1.material = "iron";
			w1.spoke = s1;
			honda.wheel = w1;
			
		
		
		Car tesla = new Car();
		tesla.number_of_wheels=4;
			Wheel w2 = new Wheel();
			w2.number_of_spokes = 24;
				Spoke s2 = new Spoke();
				s2.length = 5;
				s2.material = "Steel";
		w2.spoke = s2;
		tesla.wheel = w2;
		OComparator objectcomp =new OComparator();
		objectcomp.deepCompare(tesla, honda);
		System.out.println(objectcomp.messageList);
		Assert.assertFalse(objectcomp.messageList.isEmpty());
		//messageList can be added to any HTML Report
	}
}
