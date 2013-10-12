package pl.com.bottega.testrefactoring;

public enum InsurancePlanType {
	/** od kosztow ambulatoryjnych i wizyt w rodzinnym */
	AMBULATORY,

	/** od kosztow hospitalizacji i wizyt u specjalistow */
	HOSPITALIZATION,

	/**
	 * specjalne, tylko dla mlodych ludzi ktorych nie stac na lepsze
	 * ubezpieczenie
	 */
	CATASTROPHIC;
}