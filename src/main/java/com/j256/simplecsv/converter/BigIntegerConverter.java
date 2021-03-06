package com.j256.simplecsv.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;

import com.j256.simplecsv.processor.ColumnInfo;
import com.j256.simplecsv.processor.ParseError;

/**
 * Converter for the Java BigInteger type.
 * 
 * @author graywatson
 */
public class BigIntegerConverter implements Converter<BigInteger, DecimalFormat> {

	private static final BigIntegerConverter singleton = new BigIntegerConverter();

	/**
	 * Get singleton for class.
	 */
	public static BigIntegerConverter getSingleton() {
		return singleton;
	}

	@Override
	public DecimalFormat configure(String format, long flags, ColumnInfo<BigInteger> fieldInfo) {
		if (format == null) {
			return null;
		} else {
			DecimalFormat decimalFormat = new DecimalFormat(format);
			decimalFormat.setParseBigDecimal(true);
			decimalFormat.setParseIntegerOnly(true);
			return decimalFormat;
		}
	}

	@Override
	public boolean isNeedsQuotes(DecimalFormat decimalFormat) {
		return true;
	}

	@Override
	public boolean isAlwaysTrimInput() {
		return true;
	}

	@Override
	public String javaToString(ColumnInfo<BigInteger> columnInfo, BigInteger value) {
		DecimalFormat decimalFormat = (DecimalFormat) columnInfo.getConfigInfo();
		if (value == null) {
			return null;
		} else if (decimalFormat == null) {
			return value.toString();
		} else {
			return decimalFormat.format(value);
		}
	}

	@Override
	public BigInteger stringToJava(String line, int lineNumber, int linePos, ColumnInfo<BigInteger> columnInfo,
			String value, ParseError parseError) throws ParseException {
		DecimalFormat decimalFormat = (DecimalFormat) columnInfo.getConfigInfo();
		if (value.isEmpty()) {
			return null;
		} else if (decimalFormat == null) {
			return new BigInteger(value);
		} else {
			BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(value);
			return bigDecimal.toBigInteger();
		}
	}
}
