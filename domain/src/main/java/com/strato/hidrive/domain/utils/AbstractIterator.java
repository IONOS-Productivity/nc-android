package com.strato.hidrive.domain.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractIterator<T> implements Iterator<T> {
	private State state;
	private T next;

	protected AbstractIterator() {
		this.state = State.NOT_READY;
	}

	protected abstract T computeNext();

	protected final T endOfData() {
		this.state = State.DONE;
		return null;
	}

	public final boolean hasNext() {
		Preconditions.checkState(this.state != State.FAILED);
		switch (this.state) {
			case DONE:
				return false;
			case READY:
				return true;
			default:
				return this.tryToComputeNext();
		}
	}

	private boolean tryToComputeNext() {
		this.state = State.FAILED;
		this.next = this.computeNext();
		if (this.state != State.DONE) {
			this.state = State.READY;
			return true;
		} else {
			return false;
		}
	}

	public final T next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		} else {
			this.state = State.NOT_READY;
			T result = this.next;
			this.next = null;
			return result;
		}
	}

	public final void remove() {
		throw new UnsupportedOperationException();
	}

	private enum State {
		READY,
		NOT_READY,
		DONE,
		FAILED,
	}
}
