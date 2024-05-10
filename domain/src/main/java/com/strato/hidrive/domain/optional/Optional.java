package com.strato.hidrive.domain.optional;
/*
 * Copyright (C) 2011 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import androidx.annotation.Nullable;

import com.strato.hidrive.domain.utils.AbstractIterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import io.reactivex.annotations.Beta;

import static com.strato.hidrive.domain.utils.Preconditions.checkNotNull;

/**
 * An immutable object that may contain a non-null reference to another object. Each instance of
 * this type either contains a non-null reference, or contains nothing (in which case we say that
 * the reference is "absent"); it is never said to "contain {@code
 * null}".
 * <p/>
 * <p>A non-null {@code Optional<T>} reference can be used as a replacement for a nullable {@code T}
 * reference. It allows you to represent "a {@code T} that must be present" and a
 * "a {@code T} that might be absent" as two distinct types in your program, which can aid clarity.
 * <p/>
 * <p>Some uses of this class include
 * <p/>
 * <ul>
 * <li>As a method return type, as an alternative to returning {@code null} to indicate that no
 * value was available
 * <li>To distinguish between "unknown" (for example, not present in a map) and "known to have no
 * value" (present in the map, with value {@code Optional.absent()})
 * <li>To wrap nullable references for storage in a collection that does not support {@code null}
 * (though there are
 * <a href="https://github.com/google/guava/wiki/LivingWithNullHostileCollections">several other
 * approaches to this</a> that should be considered first)
 * </ul>
 * <p/>
 * <p>A common alternative to using this class is to find or create a suitable
 * <a href="http://en.wikipedia.org/wiki/Null_Object_pattern">null object</a> for the type in
 * question.
 * <p/>
 * <p>This class is not intended as a direct analogue of any existing "option" or "maybe" construct
 * from other programming environments, though it may bear some similarities.
 * <p/>
 * <p><b>Comparison to {@code java.util.Optional} (JDK 8 and higher):</b> A new {@code Optional}
 * class was added for Java 8. The two classes are extremely similar, but incompatible (they cannot
 * share a common supertype). <i>All</i> known differences are listed either here or with the
 * relevant methods below.
 * <p/>
 * <ul>
 * <li>This class is serializable; {@code java.util.Optional} is not.
 * <li>{@code java.util.Optional} has the additional methods {@code ifPresent}, {@code filter},
 * {@code flatMap}, and {@code orElseThrow}.
 * <li>{@code java.util} offers the primitive-specialized versions {@code OptionalInt}, {@code
 * OptionalLong} and {@code OptionalDouble}, the use of which is recommended; Guava does not
 * have these.
 * </ul>
 * <p/>
 * <p><b>There are no plans to deprecate this class in the foreseeable future.</b> However, we do
 * gently recommend that you prefer the new, standard Java class whenever possible.
 * <p/>
 * <p>See the Guava User Guide article on
 * <a href="https://github.com/google/guava/wiki/UsingAndAvoidingNullExplained#optional">using
 * {@code Optional}</a>.
 *
 * @param <T> the type of instance that can be contained. {@code Optional} is naturally covariant on
 *            this type, so it is safe to cast an {@code Optional<T>} to {@code
 *            Optional<S>} for any supertype {@code S} of {@code T}.
 * @author Kurt Alfred Kluever
 * @author Kevin Bourrillion
 * @since 10.0
 */

public abstract class Optional<T> implements Serializable {
	/**
	 * Returns an {@code Optional} instance with no contained reference.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> this method is equivalent to Java 8's
	 * {@code Optional.empty}.
	 */
	public static <T> Optional<T> absent() {
		return Absent.withType();
	}

	/**
	 * Returns an {@code Optional} instance containing the given non-null reference. To have {@code
	 * null} treated as {@link #absent}, use {@link #fromNullable} instead.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> no differences.
	 *
	 * @throws NullPointerException if {@code reference} is null
	 */
	public static <T> Optional<T> of(T reference) {
		return new Present<>(checkNotNull(reference));
	}

	/**
	 * If {@code nullableReference} is non-null, returns an {@code Optional} instance containing that
	 * reference; otherwise returns {@link Optional#absent}.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> this method is equivalent to Java 8's
	 * {@code Optional.ofNullable}.
	 */
	public static <T> Optional<T> fromNullable(@Nullable T nullableReference) {
		return (nullableReference == null)
				? Optional.absent()
				: new Present<>(nullableReference);
	}

	Optional() {
	}

	/**
	 * Returns {@code true} if this holder contains a (non-null) instance.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> no differences.
	 */
	public abstract boolean isPresent();

	public abstract T get();

	public abstract T or(T defaultValue);

	public abstract Optional<T> or(Optional<? extends T> secondChoice);

	@Beta
	public abstract T or(Supplier<? extends T> supplier);

	/**
	 * Invokes consumer function with value if present.
	 *
	 * @param consumer consumer function
	 */
	public abstract void ifPresent(Consumer<? super T> consumer);

	/**
	 * Returns the contained instance if it is present; {@code null} otherwise. If the instance is
	 * known to be present, use {@link #get()} instead.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> this method is equivalent to Java 8's
	 * {@code Optional.orElse(null)}.
	 */
	@Nullable
	public abstract T orNull();

	/**
	 * Returns an immutable singleton {@link Set} whose only element is the contained instance if it
	 * is present; an empty immutable {@link Set} otherwise.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> this method has no equivalent in Java 8's
	 * {@code Optional} class. However, this common usage: <pre>   {@code
	 * <p/>
	 *   for (Foo foo : possibleFoo.asSet()) {
	 *     doSomethingWith(foo);
	 *   }}</pre>
	 * <p/>
	 * ... can be replaced with: <pre>   {@code
	 * <p/>
	 *   possibleFoo.ifPresent(foo -> doSomethingWith(foo));}</pre>
	 *
	 * @since 11.0
	 */
	public abstract Set<T> asSet();

	/**
	 * If the instance is present, it is transformed with the given {@link Function}; otherwise,
	 * {@link Optional#absent} is returned.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> this method is similar to Java 8's
	 * {@code Optional.map}, except when {@code function} returns {@code null}. In this case this
	 * method throws an exception, whereas the Java 8 method returns {@code Optional.absent()}.
	 *
	 * @throws NullPointerException if the function returns {@code null}
	 * @since 12.0
	 */
	public abstract <V> Optional<V> transform(Function<? super T, V> function);

	/**
	 * Returns {@code true} if {@code object} is an {@code Optional} instance, and either the
	 * contained references are {@linkplain Object#equals equal} to each other or both are absent.
	 * Note that {@code Optional} instances of differing parameterized types can be equal.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> no differences.
	 */
	@Override
	public abstract boolean equals(@Nullable Object object);

	/**
	 * Returns a hash code for this instance.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> this class leaves the specific choice of
	 * hash code unspecified, unlike the Java 8 equivalent.
	 */
	@Override
	public abstract int hashCode();

	/**
	 * Returns a string representation for this instance.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> this class leaves the specific string
	 * representation unspecified, unlike the Java 8 equivalent.
	 */
	@Override
	public abstract String toString();

	/**
	 * Returns the value of each present instance from the supplied {@code optionals}, in order,
	 * skipping over occurrences of {@link Optional#absent}. Iterators are unmodifiable and are
	 * evaluated lazily.
	 * <p/>
	 * <p><b>Comparison to {@code java.util.Optional}:</b> this method has no equivalent in Java 8's
	 * {@code Optional} class; use
	 * {@code optionals.stream().filter(Optional::isPresent).map(Optional::get)} instead.
	 *
	 * @since 11.0 (generics widened in 13.0)
	 */
	@Beta
	public static <T> Iterable<T> presentInstances(
			final Iterable<? extends Optional<? extends T>> optionals) {
		checkNotNull(optionals);
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new AbstractIterator<T>() {
					private final Iterator<? extends Optional<? extends T>> iterator =
							checkNotNull(optionals.iterator());

					@Override
					protected T computeNext() {
						while (iterator.hasNext()) {
							Optional<? extends T> optional = iterator.next();
							if (optional.isPresent()) {
								return optional.get();
							}
						}
						return endOfData();
					}
				};
			}
		};
	}

	private static final long serialVersionUID = 0;
}