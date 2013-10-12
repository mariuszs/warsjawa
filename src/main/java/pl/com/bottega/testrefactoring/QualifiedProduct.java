package pl.com.bottega.testrefactoring;

public class QualifiedProduct {

	public static enum QualifiedProductStatus {
		AVAILABLE, RESTRICTED;
	}

	private QualifiedProductStatus status;
	private InsuranceProduct product;
	private int score;

	public QualifiedProduct(QualifiedProductStatus status, InsuranceProduct product, int score) {
		this.status = status;
		this.product = product;
		this.score = score;
	}

	public QualifiedProductStatus getStatus() {
		return status;
	}

	public InsuranceProduct getProduct() {
		return product;
	}

	public int getScore() {
		return score;
	}
}
