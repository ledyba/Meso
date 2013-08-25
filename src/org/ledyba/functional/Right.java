package org.ledyba.functional;

public class Right<E, A> extends Either<E, A> {

	private final A spirit;
	public Right(A answer) {
		this.spirit = answer;
	}
	@Override
	boolean isLeft() {
		return false;
	}
	@Override
	boolean isRight() {
		return true;
	}
	@Override
	E getLeftOr(E default_) {
		return default_;
	}
	@Override
	E getLeft() {
		throw new IllegalStateException("You cannot get left value from right.");
	}
	@Override
	A getRightOr(A default_) {
		return spirit;
	}
	@Override
	A getRight() {
		return spirit;
	}

}
