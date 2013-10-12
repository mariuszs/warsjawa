

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

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
import pl.com.bottega.testrefactoring.QualifiedProduct.QualifiedProductStatus;

public class InsuranceProductQualifierTest {

	@Test
	public void smokeTest() {
		PatientFeatures patientFeatures = new PatientFeatures();
		Patient patient = new Patient();
		patient.setName("John Doe");
		patient.setBirthDate(new DateTime(1990, 5, 12, 0, 0));
		patientFeatures.setPatient(patient);
		PatientHospitalizationHistory hospitalization = new PatientHospitalizationHistory();
		hospitalization.setHospitalizationDaysLastYear(5);
		patientFeatures.setHospitalizationHistory(hospitalization);
		PatientInsuranceHistory insuranceHistory = new PatientInsuranceHistory();
		insuranceHistory.setInsurancePlan(InsurancePlanType.AMBULATORY);
		insuranceHistory.setFirstInsuredDate(new DateTime(2008, 0, 1, 0, 0));
		insuranceHistory.setActiveInsuranceStartDate(new DateTime(2012, 5, 1, 0, 0));
		insuranceHistory.setActiveInsuranceEndingDate(new DateTime(2013, 0, 1, 0, 0));
		insuranceHistory.setCurrentFunds(new Money(new BigDecimal(2000), Currency.getInstance("USD")));
		patientFeatures.setInsuranceHistory(insuranceHistory);
		List<InsuranceProduct> products = null;
		try {
			products = InsuranceProductsTestFixtureLoaded.loadFromXml("bug3532-products.xml");
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
		InsuranceProductQualifier qualifier = new InsuranceProductQualifier(/* params */);
		QualificationResult result = qualifier.qualify(patientFeatures, products);
		List<QualifiedProduct> qualifiedProducts = result.getQualifiedProducts();
		Assert.assertEquals(2, qualifiedProducts);
		QualifiedProduct qualifiedProduct1 = qualifiedProducts.get(0);
		Assert.assertEquals(QualifiedProductStatus.AVAILABLE, qualifiedProduct1.getStatus());
		Assert.assertEquals(products.get(0), qualifiedProduct1.getProduct());
		QualifiedProduct qualifiedProduct2 = qualifiedProducts.get(1);
		Assert.assertEquals(QualifiedProductStatus.AVAILABLE, qualifiedProduct2.getStatus());
		Assert.assertEquals(products.get(1), qualifiedProduct2.getProduct());
	}

	@Test
	public void testAgeRestriction1() {
		// patient
		PatientFeatures patientFeatures = new PatientFeatures();
		Patient patient = new Patient();
		patient.setName("John Doe");
		patient.setBirthDate(new DateTime(1990, 5, 12, 0, 0));
		patientFeatures.setPatient(patient);
		patientFeatures.setHospitalizationHistory(new PatientHospitalizationHistory());
		patientFeatures.setInsuranceHistory(new PatientInsuranceHistory());
		// product
		InsuranceProduct product = new InsuranceProduct();
		product.setMaximumPersonAge(35);
		// qualify
		InsuranceProductQualifier qualifier = new InsuranceProductQualifier(/* params */);
		QualificationResult result = qualifier.qualify(patientFeatures, asList(product));
		// it should qualify
		List<QualifiedProduct> qualifiedProducts = result.getQualifiedProducts();
		Assert.assertEquals(1, qualifiedProducts.size());
		Assert.assertEquals(product, qualifiedProducts.get(0).getProduct());
	}

	@Test
	public void testAgeRestriction2() {
		// patient
		PatientFeatures patientFeatures = new PatientFeatures();
		Patient patient = new Patient();
		patient.setName("John Doe");
		patient.setBirthDate(new DateTime(1990, 5, 12, 0, 0));
		patientFeatures.setPatient(patient);
		PatientHospitalizationHistory hospitalization = new PatientHospitalizationHistory();
		hospitalization.setHospitalizationDaysLastYear(5);
		patientFeatures.setHospitalizationHistory(hospitalization);
		PatientInsuranceHistory insuranceHistory = new PatientInsuranceHistory();
		insuranceHistory.setInsurancePlan(InsurancePlanType.AMBULATORY);
		// product
		InsuranceProduct product = new InsuranceProduct();
		product.setMaximumPersonAge(20);
		// qualify
		InsuranceProductQualifier qualifier = new InsuranceProductQualifier(/* params */);
		QualificationResult result = qualifier.qualify(patientFeatures, asList(product));
		// it shouldnt qualify
		Assert.assertEquals(0, result.getQualifiedProducts().size());
	}
}
