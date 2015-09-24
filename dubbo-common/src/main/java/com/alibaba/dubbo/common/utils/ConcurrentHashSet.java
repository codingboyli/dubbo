/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.common.utils;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E>, java.io.Serializable {

	private static final long serialVersionUID = -8672117787651310382L;

	private static final Object PRESENT = new Object();

	private final Set<E> set;
	
	public ConcurrentHashSet(){
		set = Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>());
	}

    public ConcurrentHashSet(int initialCapacity){
       set = Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>());
    }

	/**
	 * Returns an iterator over the elements in this set. The elements are
	 * returned in no particular order.
	 * 
	 * @return an Iterator over the elements in this set
	 * @see ConcurrentModificationException
	 */
	public Iterator<E> iterator() {
		return set.iterator();
	}

	/**
	 * Returns the number of elements in this set (its cardinality).
	 * 
	 * @return the number of elements in this set (its cardinality)
	 */
	public int size() {
		return set.size();
	}

	/**
	 * Returns <tt>true</tt> if this set contains no elements.
	 * 
	 * @return <tt>true</tt> if this set contains no elements
	 */
	public boolean isEmpty() {
		return set.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this set contains the specified element. More
	 * formally, returns <tt>true</tt> if and only if this set contains an
	 * element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 * 
	 * @param o
	 *            element whose presence in this set is to be tested
	 * @return <tt>true</tt> if this set contains the specified element
	 */
	public boolean contains(Object o) {
		return set.contains(o);
	}

	/**
	 * Adds the specified element to this set if it is not already present. More
	 * formally, adds the specified element <tt>e</tt> to this set if this set
	 * contains no element <tt>e2</tt> such that
	 * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>. If this
	 * set already contains the element, the call leaves the set unchanged and
	 * returns <tt>false</tt>.
	 * 
	 * @param e
	 *            element to be added to this set
	 * @return <tt>true</tt> if this set did not already contain the specified
	 *         element
	 */
	public boolean add(E e) {
		return set.add(e);
	}

	/**
	 * Removes the specified element from this set if it is present. More
	 * formally, removes an element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if this
	 * set contains such an element. Returns <tt>true</tt> if this set contained
	 * the element (or equivalently, if this set changed as a result of the
	 * call). (This set will not contain the element once the call returns.)
	 * 
	 * @param o
	 *            object to be removed from this set, if present
	 * @return <tt>true</tt> if the set contained the specified element
	 */
	public boolean remove(Object o) {
		return set.remove(o);
	}

	/**
	 * Removes all of the elements from this set. The set will be empty after
	 * this call returns.
	 */
	public void clear() {
		set.clear();
	}

}