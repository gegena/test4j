package org.test4j.json.encoder.single.fixed;

import java.util.ArrayList;

import org.junit.Test;
import org.test4j.json.encoder.EncoderTest;
import org.test4j.json.encoder.single.fixed.FloatEncoder;
import org.test4j.junit.annotations.DataFrom;

public class FloatEncoderTest extends EncoderTest {

	@Test
	@DataFrom("float_data")
	public void testEncodeSingleValue(Float number, String expected) throws Exception {
		FloatEncoder encoder = FloatEncoder.instance;
		this.setUnmarkFeature(encoder);

		encoder.encode(number, writer, new ArrayList<String>());
		String result = writer.toString();
		want.string(result).isEqualTo(expected);
	}

	public static Object[][] float_data() {
		return new Object[][] { { 12.12F, "12.12" },// <br>
				{ 1000f, "1000" },// <br>
				{ 1000.0f, "1000" },// <br>
				{ null, "null" } // <br>
		};
	}
}
