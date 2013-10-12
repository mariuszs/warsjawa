package pl.com.bottega.testrefactoring;

public class PatientFeatures {

	private Patient patient;
	private PatientHospitalizationHistory hospitalizationHistory;
	private PatientInsuranceHistory insuranceHistory;

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setHospitalizationHistory(PatientHospitalizationHistory hospitalizationHistory) {
		this.hospitalizationHistory = hospitalizationHistory;
	}

	public void setInsuranceHistory(PatientInsuranceHistory insuranceHistory) {
		this.insuranceHistory = insuranceHistory;
	}

	public Patient getPatient() {
		return patient;
	}

	public PatientHospitalizationHistory getHospitalizationHistory() {
		return hospitalizationHistory;
	}

	public PatientInsuranceHistory getInsuranceHistory() {
		return insuranceHistory;
	}
}