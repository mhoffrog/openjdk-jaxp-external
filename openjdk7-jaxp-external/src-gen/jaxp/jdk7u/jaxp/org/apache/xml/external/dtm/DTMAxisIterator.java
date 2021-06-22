/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id: DTMAxisIterator.java,v 1.2.4.1 2005/09/15 08:14:52 suresh_emailid Exp $
 */
package jdk7u.jaxp.org.apache.xml.external.dtm;

/**
 * This class iterates over a single XPath Axis, and returns node handles.
 */
public interface DTMAxisIterator extends Cloneable
{

  /** Specifies the end of the iteration, and is the same as DTM.NULL.  */
  public static final int END = DTM.NULL;

  /**
   * Get the next node in the iteration.
   *
   * @return The next node handle in the iteration, or END.
   */
  public int next();


  /**
   * Resets the iterator to the last start node.
   *
   * @return A DTMAxisIterator, which may or may not be the same as this
   *         iterator.
   */
  public DTMAxisIterator reset();

  /**
   * @return the number of nodes in this iterator.  This may be an expensive
   * operation when called the first time.
   */
  public int getLast();

  /**
   * @return The position of the current node in the set, as defined by XPath.
   */
  public int getPosition();

  /**
   * Remembers the current node for the next call to gotoMark().
   */
  public void setMark();

  /**
   * Restores the current node remembered by setMark().
   */
  public void gotoMark();

  /**
   * Set start to END should 'close' the iterator,
   * i.e. subsequent call to next() should return END.
   *
   * @param node Sets the root of the iteration.
   *
   * @return A DTMAxisIterator set to the start of the iteration.
   */
  public DTMAxisIterator setStartNode(int node);

  /**
   * Get start to END should 'close' the iterator,
   * i.e. subsequent call to next() should return END.
   *
   * @return The root node of the iteration.
   */
  public int getStartNode();

  /**
   * @return true if this iterator has a reversed axis, else false.
   */
  public boolean isReverse();

  /**
   * @return a deep copy of this iterator. The clone should not be reset
   * from its current position.
   */
  public DTMAxisIterator cloneIterator();

  /**
   * Set if restartable.
   */
  public void setRestartable(boolean isRestartable);

  /**
   * Return the node at the given position.
   *
   * @param position The position
   * @return The node at the given position.
   */
  public int getNodeByPosition(int position);
}
