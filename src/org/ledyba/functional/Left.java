package org.ledyba.functional;

public class Left<E, A> extends Either<E, A> {
	private final E spirit;
	public Left(E error) {
		this.spirit = error;
	}
	@Override
	boolean isLeft() {
		return true;
	}
	@Override
	boolean isRight() {
		return false;
	}
	@Override
	E getLeftOr(E default_) {
		return spirit;
	}
	@Override
	E getLeft() {
		return spirit;
	}
	@Override
	A getRightOr(A default_) {
		return default_;
	}
	@Override
	A getRight() {
		throw new IllegalStateException("You cannot get right value from left.");
	}

}
