package org.test4j.json.encoder.single.fixed;

import java.io.Writer;

import org.test4j.json.encoder.single.FixedTypeEncoder;
import org.test4j.tools.commons.ClazzHelper;

@SuppressWarnings("rawtypes")
public class ClazzEncoder extends FixedTypeEncoder<Class> {
	public static ClazzEncoder instance = new ClazzEncoder();

	private ClazzEncoder() {
		super(Class.class);
	}

	@Override
	protected void encodeSingleValue(Class target, Writer writer) throws Exception {
		Class type = ClazzHelper.getUnProxyType(target);
		writer.append(quote_Char).append(type.getName()).append(quote_Char);
	}
}
