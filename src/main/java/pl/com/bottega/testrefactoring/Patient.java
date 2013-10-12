package pl.com.bottega.testrefactoring;

import org.joda.time.DateTime;

/**
 * DTO for Patient demographic information. Patient is identified by patient
 * card number.
 */
public class Patient {

	private DateTime birthDate;
	private String name;

	public void setBirthDate(DateTime birthDate) {
		this.birthDate = birthDate;
	}

	public void setName(String name) {
		this.name = name;
	}

}
