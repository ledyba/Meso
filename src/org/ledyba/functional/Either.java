package org.ledyba.functional;

public abstract class Either<E, A> {
	protected Either() {
	}
	
	abstract boolean isLeft();
	abstract boolean isRight();
	
	abstract E getLeftOr(E default_);
	abstract E getLeft();

	abstract A getRightOr(A default_);
	abstract A getRight();
	
	static public final <E,A> Left<E,A> left(E e){
		return new Left<E,A>(e);
	}
	static public final <E,A> Right<E,A> right(A a){
		return new Right<E,A>(a);
	}
	
	<A2> Either<E,A2> bind( Func<A, Either<E,A2> > f ) {
		if(isLeft()) {
			@SuppressWarnings("unchecked") Either<E,A2> r = (Either<E,A2>)this;
			return r;
		}else{
			return f.apply(getRight());
		}
	}
	<A2> Either<E,A2> fmap( Func<A, A2> f ) {
		if(isLeft()) {
			@SuppressWarnings("unchecked") Either<E,A2> r = (Either<E,A2>)this;
			return r;
		}else{
			return new Right<E,A2>(f.apply(getRight()));
		}
	}
}
