/* Doublebox.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Sat Oct 14 12:59:39     2006, Created by henrichen
}}IS_NOTE

Copyright (C) 2006 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package org.zkoss.zul;

import org.zkoss.zk.ui.WrongValueException;

import org.zkoss.zul.mesg.MZul;
import org.zkoss.zul.impl.FormatInputElement;

/**
 * An edit box for holding an float point value (double).
 *
 * @author henrichen
 */
public class Doublebox extends FormatInputElement {
	public Doublebox() {
		setCols(11);
	}
	public Doublebox(double value) throws WrongValueException {
		this();
		setValue(value);
	}
	public Doublebox(Double value) throws WrongValueException {
		this();
		setValue(value);
	}

	/** Returns the value (in Double), might be null unless
	 * a constraint stops it.
	 * @exception WrongValueException if user entered a wrong value
	 */
	public Double getValue() throws WrongValueException {
		return (Double)getRawValue();
	}
	/** Returns the value in double. If null, zero is returned.
	 */
	public double doubleValue() throws WrongValueException {
		final Object val = getRawValue();
		return val != null ? ((Double)val).doubleValue(): 0.0;
	}
	/** Returns the value in integer. If null, zero is returned.
	 */
	public int intValue() throws WrongValueException {
		final Object val = getRawValue();
		return val != null ? ((Double)val).intValue(): 0;
	}
	/** Returns the value in long. If null, zero is returned.
	 */
	public long longValue() throws WrongValueException {
		final Object val = getRawValue();
		return val != null ? ((Double)val).longValue(): 0;
	}
	/** Returns the value in short. If null, zero is returned.
	 */
	public short shortValue() throws WrongValueException {
		final Object val = getRawValue();
		return val != null ? ((Double)val).shortValue(): 0;
	}
	/** Sets the value (in Double).
	 * @exception WrongValueException if value is wrong
	 */
	public void setValue(Double value) throws WrongValueException {
		validate(value);
		setRawValue(value);
	}
	/** Sets the value (in double)
	 * @exception WrongValueException if value is wrong
	 */
	public void setValue(double value) throws WrongValueException {
		setValue(new Double(value));
	}

	//-- super --//
	protected Object coerceFromString(String value) throws WrongValueException {
		final String val = toNumberOnly(value);
		if (val == null || val.length() == 0)
			return null;

		try {
			int j = val.indexOf('%'); //toNumberOnly translates Locale-dependent
			double v =  j == 0 ? 0.0 : Double.parseDouble(j < 0 ? val: val.substring(0, j));
			if (j <= 0) return new Double(v);

			for (final int len = val.length(); j < len && v != 0; ++j)
				if (val.charAt(j) == '%') v /= 100;
				else throw new WrongValueException(this, MZul.NUMBER_REQUIRED, value);
			return new Double(v);
		} catch (NumberFormatException ex) {
			throw new WrongValueException(this, MZul.NUMBER_REQUIRED, value);
		}
	}
	protected String coerceToString(Object value) {
		return formatNumber(value, "0.##########");
	}
}
