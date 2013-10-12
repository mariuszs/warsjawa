
import static pl.com.bottega.testrefactoring.InsurancePlanGoalType.COVER_TREATMENT_EXPENSES;
import static pl.com.bottega.testrefactoring.InsurancePlanGoalType.VACCINATIONS;
import static pl.com.bottega.testrefactoring.InsurancePlanType.AMBULATORY;
import static pl.com.bottega.testrefactoring.InsurancePlanType.CATASTROPHIC;
import static pl.com.bottega.testrefactoring.InsurancePlanType.HOSPITALIZATION;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Assert;
import org.junit.Test;

import pl.com.bottega.testrefactoring.InsurancePlanGoalType;
import pl.com.bottega.testrefactoring.InsurancePlanType;
import pl.com.bottega.testrefactoring.InsuranceProduct;
import pl.com.bottega.testrefactoring.InsuranceProductQualifier;
import pl.com.bottega.testrefactoring.Money;
import pl.com.bottega.testrefactoring.Patient;
import pl.com.bottega.testrefactoring.PatientFeatures;
import pl.com.bottega.testrefactoring.PatientHospitalizationHistory;
import pl.com.bottega.testrefactoring.PatientInsuranceHistory;
import pl.com.bottega.testrefactoring.QualificationResult;
import pl.com.bottega.testrefactoring.QualifiedProduct;

public class InsuranceProductQualifierTest {

	private static final Currency EUR = Currency.getInstance("EUR");
	 PatientFeatures patientFeatures;
	List<InsuranceProduct> products = new ArrayList<InsuranceProduct>();
	private QualificationResult result;
	
	@Test
	public void smokeTest() throws Exception {
		// given
		patientFeatures(
				patientBornOn("1990-06-12"),
				createHospitalizationHistoryDaysInHospital(5),
				magicInsuranceHistory(AMBULATORY, money(2000, EUR)));

		productOfTypeForMaxAge(CATASTROPHIC, 25);
		productOfTypeForMaxAge(AMBULATORY, 45, VACCINATIONS);
		productOfTypeForMaxAge(HOSPITALIZATION, 45, COVER_TREATMENT_EXPENSES);

		// when
		qualify();

		// then
		assertContainsOnlyThoseAvailableProductsInOrder(result, products.get(0), products.get(1));
	}


	private void qualify() {
		InsuranceProductQualifier qualifier = new InsuranceProductQualifier(/* params */);
		this.result = qualifier.qualify(patientFeatures, products);
	}

	
	private Money money(int i, Currency eur2) {
		// TODO Auto-generated method stub
		return null;
	}

	private InsuranceProduct productOfTypeForMaxAge(InsurancePlanType planType, int maximumPersonAge,
			InsurancePlanGoalType... goals) {
		InsuranceProduct product = new InsuranceProduct();
		product.setPlanType(planType);
		product.setMaximumPersonAge(maximumPersonAge);
		product.setGoals(Arrays.asList(goals));
		return product;
	}

	private void patientFeatures(Patient patient,
			PatientHospitalizationHistory hospitalizationHistory, PatientInsuranceHistory insuranceHistory) {
		this.patientFeatures = new PatientFeatures();
		patientFeatures.setPatient(patient);
		patientFeatures.setHospitalizationHistory(hospitalizationHistory);
		patientFeatures.setInsuranceHistory(insuranceHistory);
	}

	/**
	 * To jest SPECYFICZNA historia hospitalizacji wiec nie da sie stworzyc
	 * dobrej metody tworzacej chyba ze bedzie maiala strasznie duzo parametrow.
	 * Stad nazwa - magic ... (Na tym etapie nie lepszego nie zrobimy).
	 */
	private PatientInsuranceHistory magicInsuranceHistory(InsurancePlanType insurancePlanType, Money currentFunds) {
		PatientInsuranceHistory insuranceHistory = new PatientInsuranceHistory();
		insuranceHistory.setFirstInsuredDate(new DateTime(2008, 1, 1, 0, 0));
		insuranceHistory.setActiveInsuranceStartDate(new DateTime(2012, 6, 1, 0, 0));
		insuranceHistory.setActiveInsuranceEndingDate(new DateTime(2013, 1, 1, 0, 0));
		insuranceHistory.setInsurancePlan(insurancePlanType);
		insuranceHistory.setCurrentFunds(currentFunds);
		return insuranceHistory;
	}

	private PatientHospitalizationHistory createHospitalizationHistoryDaysInHospital(int numberOfDaysInHospital) {
		PatientHospitalizationHistory hospitalization = new PatientHospitalizationHistory();
		hospitalization.setHospitalizationDaysLastYear(numberOfDaysInHospital);
		return hospitalization;
	}

	private Patient patientBornOn(String birthDate) throws ParseException {
		Patient patient = new Patient();
		patient.setName("John Doe");
		patient.setBirthDate(new DateTime(birthDate));
		return patient;
	}

	private void assertContainsOnlyThoseAvailableProductsInOrder(QualificationResult result,
			InsuranceProduct... expectedProducts) {
		List<QualifiedProduct> actualProducts = result.getQualifiedProducts();
		Assert.assertEquals(expectedProducts.length, actualProducts.size());
		// TODO sprawdz, ze products i expectedProducts zawieraja dokladnie te
		// same produkty (ze statusem AVAILABLE)
	}
}
