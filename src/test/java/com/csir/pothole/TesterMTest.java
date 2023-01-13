package com.csir.pothole;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.csir.pothole.service.TestersM;

public class TesterMTest {

	@Test
	public void testCalcu() {
		TestersM calculator = new TestersM();
        assertThat(calculator.getValue()).isEqualTo(0);
	}
}
