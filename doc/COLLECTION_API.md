# API Lab Discussion
# Collections API Discussion

## Names and NetIDs
Andre Wang (jw542),
Donghan Park (dp239),
Samy Boutouis (sb590),
Felix Jiang (fj32)

### What is the purpose of each interface implemented by LinkedList?
LinkedList implements: Serializable, Cloneable, Iterable<E>, Collection<E>, Deque<E>, List<E>, Queue<E>

* Serializable: Signals to programmers that a linked list can be serialized and deserialized
* Cloneable: makes it legal for the Object.clone() method to make a field-for-field copy of instances of that class
* Iterable: allows the object to be the target of the "for-each loop" statement
* Collection: allows passing and manipulation of collections where generality id desired
* Deque: Support for treating the linkedlist as a "double ended queue" - insertion and removal on both ends of the linked list.
* List: Methods in the List class can be called on the Linked List. Allows this linked list to be treated as an ordered collection. The user can access elements by their integer index (position in the list), and search for elements in the list.
* Queue: allows insertion and removal operations in a typically first-in first-out (FIFO) manner and helps hold elements before processing.

### What is the purpose of each superclass of HashMap?
Direct superclass: Abstract Map
Second (and final) superclass: Object

* Abstract Map: Provides a skeletal implementation of the Map interface (so an interface has no implementation, but this AbstractMap class saves all subclasses some time by providing some implementation for some of the methods of the Map interface)
* Object: Root of the class hierarchy that implements common methods of all Java objects such as hashCode() or clone()

### How many different implementations are there for a Set?

Three: HashSet, TreeSet, LinkedHashSet

### What methods are common to all collections?

add(), addAll(), clear(), contains(), containsALl, equals(), hashCode(), isEmpty(), iterator(), parallelStream(), remove(), removeAll(), removeIf(), retainAll(), size(), spliterator(), stream(), toArray()

### What methods are common to all Queues?
Methods common to all Queues are the ones defined in the Queue interface. Anything that is a Queue must implement the Queue interface, and therefore it is guaranteed that all Queues have the methods in the Queue interface. However, the Queue interface also has superinterfaces of Collections and Iterable, so all methods defined in those interfaces also are common to all Queues (but not included here).

* add(E e)
    * Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restrictions, returning true upon success and throwing an IllegalStateException if no space is currently available.
* E	element()
    * Retrieves, but does not remove, the head of this queue.
* boolean	offer(E e)
    * Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restrictions.
* E	peek()
    * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
* E	poll()
    * Retrieves and removes the head of this queue, or returns null if this queue is empty.
* E	remove()
    * Retrieves and removes the head of this queue.

### What is the purpose of the collection utility classes?
The collections utility class provides static methods that operate on objects that implement the Collection interface, such as sorting or copying a collection.

It essentially contains polymorphic algorithms that operate on collections/wrappers, returning a new collection backed by another specific collection.