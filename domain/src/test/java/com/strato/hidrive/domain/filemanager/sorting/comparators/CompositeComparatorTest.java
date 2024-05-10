package com.strato.hidrive.domain.filemanager.sorting.comparators;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

/**
 * User: Dima Muravyov
 * Date: 03.08.2016
 */
public class CompositeComparatorTest {

	private Object lessValue;
	private Object greaterValue;

	@Before
	public void setUp() {
		this.lessValue = new Object();
		this.greaterValue = new Object();
	}

	@Test
	public void compareReturns0WhenThereAre0Comparators() {
		assertEquals(0, new CompositeComparator().compare(this.lessValue, this.greaterValue));
		assertEquals(0, new CompositeComparator().compare(this.greaterValue, this.lessValue));
		assertEquals(0, new CompositeComparator().compare(this.lessValue, this.lessValue));
	}

	@Test
	public void compareCalls1ExistedComparator() {
		Comparator mockComparator = mock(Comparator.class);
		CompositeComparator compositeComparator = new CompositeComparator(mockComparator);

		compositeComparator.compare(this.lessValue, this.greaterValue);

		verify(mockComparator, times(1)).compare(this.lessValue, this.greaterValue);
	}

	@Test
	public void compareReturnsResultOf1ExistedComparator() {
		Comparator mockComparator = mock(Comparator.class);
		when(mockComparator.compare(this.lessValue, this.greaterValue)).thenReturn(1);
		when(mockComparator.compare(this.greaterValue, this.lessValue)).thenReturn(-1);
		when(mockComparator.compare(this.lessValue, this.lessValue)).thenReturn(0);
		CompositeComparator compositeComparator = new CompositeComparator(mockComparator);

		assertEquals(1, compositeComparator.compare(this.lessValue, this.greaterValue));
		assertEquals(-1, compositeComparator.compare(this.greaterValue, this.lessValue));
		assertEquals(0, compositeComparator.compare(this.lessValue, this.lessValue));
	}

	@Test
	public void compareCallsOnlyFirstComparatorOf2ExistedWhenFirstReturnNonZeroValue() {
		Comparator comparator1 = mock(Comparator.class);
		Comparator comparator2 = mock(Comparator.class);
		CompositeComparator compositeComparator = new CompositeComparator(comparator1, comparator2);

		when(comparator1.compare(any(), any())).thenReturn(1);

		compositeComparator.compare(this.lessValue, this.greaterValue);

		verify(comparator1, times(1)).compare(any(), any());
		verify(comparator2, never()).compare(any(), any());
	}

	@Test
	public void compareCallsSecondComparatorOf2ExistedWhenFirstReturnZeroValue() {
		Comparator comparator1 = mock(Comparator.class);
		Comparator comparator2 = mock(Comparator.class);
		CompositeComparator compositeComparator = new CompositeComparator(comparator1, comparator2);

		when(comparator1.compare(any(), any())).thenReturn(0);

		compositeComparator.compare(this.lessValue, this.lessValue);

		verify(comparator1, times(1)).compare(any(), any());
		verify(comparator2, times(1)).compare(any(), any());
	}

	@Test
	public void compareReturnsResultFromSecondComparatorOf2ExistedWhenFirstReturnZeroValue() {
		Comparator comparator1 = mock(Comparator.class);
		Comparator comparator2 = mock(Comparator.class);
		CompositeComparator compositeComparator = new CompositeComparator(comparator1, comparator2);

		when(comparator1.compare(any(), any())).thenReturn(0);
		when(comparator2.compare(this.lessValue, this.greaterValue)).thenReturn(1);
		when(comparator2.compare(this.greaterValue, this.lessValue)).thenReturn(-1);
		when(comparator2.compare(this.lessValue, this.lessValue)).thenReturn(0);

		assertEquals(1, compositeComparator.compare(this.lessValue, this.greaterValue));
		assertEquals(-1, compositeComparator.compare(this.greaterValue, this.lessValue));
		assertEquals(0, compositeComparator.compare(this.lessValue, this.lessValue));
	}
}
