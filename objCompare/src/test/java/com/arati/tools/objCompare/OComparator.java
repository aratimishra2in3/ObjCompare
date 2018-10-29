package com.arati.tools.objCompare;


	import java.util.ArrayList;
	import java.util.Collection;
	import java.util.List;

	import junit.framework.AssertionFailedError;
	import ognl.DefaultMemberAccess;
	import ognl.Ognl;
	import ognl.OgnlContext;
	import ognl.OgnlException;

	import org.unitils.core.UnitilsException;
	import org.unitils.reflectionassert.ReflectionAssert;
	import org.unitils.reflectionassert.ReflectionComparatorMode;

	public class OComparator {
	public static ArrayList<AssertionFailedError> errorList = new ArrayList<AssertionFailedError>();
	public static List<String> messageList = null;
		/**
		 * Does a deep comparison of two Objects. Results are added as JUnit
		 * failures and will reflect in testNG Result and MAUI result
		 * 
		 * @param expected
		 * @param actual
		 * @throws Exception
		 */
		public void deepCompare(Object expected, Object actual) throws Exception {
			messageList = new ArrayList<String>();
			if (expected == null)
				throw new Exception("expected Object is null");
			if (actual == null)
				throw new Exception("actual Object is null");
			if (expected.getClass().equals(actual.getClass()))
				try {
					ReflectionAssert.assertReflectionEquals(expected, actual);
				} catch (AssertionFailedError e) {
					messageList.add(e.getMessage());
				}
		}

		/**
		 * Additional deepCompare method where it does a comparison of two objects
		 * of same type with additional comparison rules defined in
		 * ReflectionComparatorMode.class
		 * 
		 * @param expected
		 * @param actual
		 * @param modes
		 * @return 
		 * @throws Exception
		 */
		public List<String> deepCompare(Object expected, Object actual,
				ReflectionComparatorMode... modes) throws Exception {
			messageList = new ArrayList<String>();
			if (expected == null)
				throw new Exception("expected Object is null");
			if (actual == null)
				throw new Exception("actual Object is null");
			if (expected.getClass().equals(actual.getClass()))
				try {
					ReflectionAssert.assertReflectionEquals(expected, actual, modes);
				} catch (AssertionFailedError e) {
					messageList.add(e.getMessage());
				}
			return messageList;
		}

		/**
		 * Additional deepCompare method where it does a comparison of two objects
		 * of same type with additional comparison rules defined in
		 * ReflectionComparatorMode.class for only mentioned parameters
		 * 
		 * @param expected
		 * @param actual
		 * @param modes
		 * @throws Exception
		 */
		public void deepCompare(String ognlNotation, Object expectedPropertyValue,
				Object actualObject, ReflectionComparatorMode... modes)
				throws Exception {
			messageList = new ArrayList<String>();
			if (actualObject == null)
				throw new Exception("actual Object is null");
			try {
				ReflectionAssert.assertPropertyReflectionEquals(ognlNotation,
						expectedPropertyValue, actualObject, modes);
			} catch (AssertionFailedError e) {
					messageList.add(e.getMessage());
			}
		}
		
		/**
		 * Additional deepCompare method where it does a comparison of two objects
		 * of same type with additional comparison rules defined in
		 * ReflectionComparatorMode.class for only mentioned parameters as List
		 * 
		 * @param expected
		 * @param actual
		 * @param modes
		 * @return 
		 * @throws Exception
		 */
		public List<String> deepCompare(Collection<String> ognlNotations, Object expectedObject,
				Object actualObject, ReflectionComparatorMode... modes)
				throws Exception {
			messageList = new ArrayList<String>();
			if (expectedObject == null)
				throw new Exception("expected property value is null");
			if (actualObject == null)
				throw new Exception("actual Object is null");
			if (expectedObject.getClass().equals(actualObject.getClass()))
			for(String ognlNotation : ognlNotations){
				AssertionFailedError error = null;;
				try {
					ReflectionAssert.assertPropertyReflectionEquals(ognlNotation,
							getProperty(expectedObject, ognlNotation), actualObject, modes);
				} catch (AssertionFailedError e) {
					error = e;
					errorList.add(error);
				}
				if(error == null){
					messageList.add('\n'+"Field values matched. Expected value "+getProperty(expectedObject, ognlNotation)+" Actual value "+getProperty(actualObject, ognlNotation));
				}
			}
			else
				throw new Exception("Types of objects Don't match");
			
			if(!errorList.isEmpty()){
				for(AssertionFailedError error: errorList){
					messageList.add(error.getMessage());
				}
			}
			return messageList;
		}
		
		/**
	     * Evaluates the given OGNL expression, and returns the corresponding property value from the given object.
	     *
	     * @param object         The object on which the expression is evaluated
	     * @param ognlExpression The OGNL expression that is evaluated
	     * @return The value for the given OGNL expression
	     */
	    protected static Object getProperty(Object object, String ognlExpression) {
	        try {
	            OgnlContext ognlContext = new OgnlContext();
	            ognlContext.setMemberAccess(new DefaultMemberAccess(true));
	            Object ognlExprObj = Ognl.parseExpression(ognlExpression);
	            return Ognl.getValue(ognlExprObj, ognlContext, object);
	        } catch (OgnlException e) {
	            throw new UnitilsException("Failed to get property value using OGNL expression " + ognlExpression, e);
	        }
	    }

	}



