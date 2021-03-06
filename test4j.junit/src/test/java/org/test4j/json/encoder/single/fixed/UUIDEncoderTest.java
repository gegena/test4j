package org.test4j.json.encoder.single.fixed;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.test4j.json.encoder.EncoderTest;
import org.test4j.json.encoder.single.fixed.UUIDEncoder;
import org.test4j.json.helper.JSONFeature;

public class UUIDEncoderTest extends EncoderTest {

	@Test
	public void testEncodeSingleValue() throws Exception {
		UUID uuid = UUID.randomUUID();
		UUIDEncoder encoder = UUIDEncoder.instance;
		encoder.setFeatures(JSONFeature.UseSingleQuote);

		encoder.encode(uuid, writer, new ArrayList<String>());
		String result = writer.toString();
		want.string(result).start("{#class:'UUID',#value:'").end("'}");
	}
}
