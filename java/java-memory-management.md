# 🧠 Java Memory Management - Understanding Guide

> **Master how Java manages memory: Stack, Heap, Garbage Collection, and optimization**

---

## 🎯 What You'll Understand

- How Java stores different types of data (Stack vs Heap)
- How memory is organized and managed
- How Java automatically cleans up unused objects (Garbage Collection)
- Why String handling is special (String Pool)
- How to write memory-efficient code

---

## 1️⃣ Why Memory Management Matters ⭐⭐

### 🤔 The Problem: Manual Memory Management (Before Java)

In languages like C/C++:

```c
// C/C++ - manual memory management
int* ptr = (int*)malloc(sizeof(int) * 100);  // Allocate
*ptr = 42;
// ... use the memory ...
free(ptr);  // Must manually free! Forget this = memory leak!

// Problems:
// 1. Forget to free() → Memory leak (memory never released)
// 2. Free too early → Dangling pointer (crashes)
// 3. Free twice → Corruption (crashes)
// 4. Complex to track in large programs
```

**Real-World Problem:** Like renting apartments:
- Rent apartment (allocate memory)
- Use apartment
- **Must remember to return keys** (free memory)
- Forget to return keys → Apartments pile up, never available again!
- Return keys too early → Someone still living there (crash!)

**Issues with Manual Management:**
1. Memory leaks (forgot to free)
2. Dangling pointers (freed too early)
3. Double-free errors
4. Complexity and bugs
5. Productivity killer (spend time on memory, not logic)

### 💡 The Solution: Automatic Memory Management

**Java's Approach:**
1. **Automatic allocation** - just create objects with `new`
2. **Automatic cleanup** - Garbage Collector removes unused objects
3. **No manual free()** - developer doesn't manage memory directly

**Real-World Analogy:** Think of a **hotel**:
- Check in (create object) - automatic room assignment
- Use room (use object)
- Check out (object no longer used)
- **Housekeeping automatically cleans** (Garbage Collector)
- You never manage rooms yourself!

```java
// Java - automatic memory management
Book book = new Book("1984");  // Allocate - automatic
// ... use book ...
book = null;  // No longer needed
// Garbage Collector automatically frees memory!
// No manual free() needed!
```

---

## 2️⃣ Stack vs Heap Memory ⭐⭐⭐

### 🤔 The Problem: Different Data Lifetimes

**Different types of data have different lifetimes:**

1. **Short-lived data:** Method variables (live only during method execution)
2. **Long-lived data:** Objects (live until no longer referenced)

**If stored together:** Chaos! How to know what to keep and what to delete?

**Real-World Problem:** Like storing different items together:
- Groceries (short-lived - days)
- Furniture (long-lived - years)
- **If mixed in same storage:** How do you know what to throw away?

### 💡 The Solution: Two Different Memory Areas

**Java divides memory into:**
1. **Stack** - Fast, temporary storage (method-level)
2. **Heap** - Slower, long-term storage (object-level)

---

### 🏗️ Stack Memory

**Stack = Last-In-First-Out (LIFO) storage for method execution**

**Real-World Analogy:** Think of a **stack of plates**:
- Add plate on top (method call)
- Remove plate from top (method returns)
- Can only access top plate
- Each plate has its own space

Another analogy: **Book stack on desk**
- Current book on top (current method)
- Finish book, remove it (method returns)
- Previous book now accessible (previous method)

**What's Stored in Stack:**
1. **Method calls** (execution context)
2. **Local variables** (primitives)
3. **References to objects** (not the objects themselves!)

```java
public void calculateTotal() {
    int price = 100;        // Stored in Stack
    int quantity = 5;       // Stored in Stack
    int total = price * quantity;  // Stored in Stack
    
    Book book = new Book("1984");  // Reference in Stack, Object in Heap
}
// When method ends, Stack memory is automatically cleared!
```

**Stack Memory Visualization:**

```
Stack (LIFO)
┌────────────────┐
│ calculateTotal │
│  price = 100   │
│  quantity = 5  │
│  total = 500   │
│  book (ref)    │───────→ [Book Object in Heap]
└────────────────┘

Method returns → Everything popped off!
```

**Stack Characteristics:**

| Feature | Stack |
|---------|-------|
| **Size** | Small (typically 1 MB) |
| **Speed** | Very fast |
| **Stores** | Primitives, references, method calls |
| **Lifetime** | Until method returns |
| **Management** | Automatic (LIFO) |
| **Allocation** | Compile-time |
| **Thread** | Each thread has own stack |
| **Error** | StackOverflowError (too many method calls) |

---

### 🏢 Heap Memory

**Heap = Large, shared storage for all objects**

**Real-World Analogy:** Think of a **warehouse**:
- Large space for storing items
- Items can stay indefinitely
- Multiple people can access same items
- Need someone to clean up abandoned items (Garbage Collector)

Another analogy: **Public library**
- Books (objects) stored centrally
- Anyone can access any book (shared)
- Librarians remove old, unused books (GC)

**What's Stored in Heap:**
1. **All objects** created with `new`
2. **Instance variables** (object fields)
3. **Arrays**

```java
public class Library {
    public void createBook() {
        // Stack: method frame + local variables
        String title = "1984";  // Reference in Stack
        
        // Heap: actual object
        Book book = new Book(title);  // Object in Heap
        
        // Heap: array
        Book[] books = new Book[10];  // Array in Heap
    }
}
```

**Heap Memory Visualization:**

```
Heap (Shared by all threads)
┌──────────────────────────────┐
│ [Book object]                │
│  title = "1984"              │
│  author = "Orwell"           │
│  pages = 328                 │
├──────────────────────────────┤
│ [Book[] array]               │
│  [ref] [ref] [ref] ...       │
├──────────────────────────────┤
│ [String object "1984"]       │
└──────────────────────────────┘

Garbage Collector cleans unused objects!
```

**Heap Characteristics:**

| Feature | Heap |
|---------|------|
| **Size** | Large (GBs) |
| **Speed** | Slower than Stack |
| **Stores** | Objects, arrays |
| **Lifetime** | Until Garbage Collected |
| **Management** | Garbage Collector |
| **Allocation** | Runtime |
| **Thread** | Shared by all threads |
| **Error** | OutOfMemoryError (heap full) |

---

### 📊 Stack vs Heap Detailed Comparison

| Feature | Stack | Heap |
|---------|-------|------|
| **Purpose** | Method execution | Object storage |
| **Storage** | Primitives, references | Objects, arrays |
| **Size** | Small (~1 MB) | Large (GBs) |
| **Speed** | Very fast | Relatively slower |
| **Access** | LIFO (ordered) | Random access |
| **Lifetime** | Method duration | Until GC |
| **Thread** | One per thread | Shared |
| **Allocation** | Automatic | Automatic via `new` |
| **Deallocation** | Automatic (pop) | Garbage Collector |
| **Fragmentation** | No | Yes (can fragment) |
| **Error** | StackOverflowError | OutOfMemoryError |

---

### 🔍 Complete Example: Stack vs Heap

```java
public class Library {
    // Class variable - Heap
    private String libraryName = "City Library";
    
    public void borrowBook() {
        // Stack: method frame
        
        // Stack: primitive local variables
        int bookId = 123;
        double fee = 5.50;
        boolean available = true;
        
        // Stack: reference, Heap: object
        Book book = new Book("1984", "Orwell");
        
        // Stack: reference, Heap: array + objects
        Book[] books = new Book[3];
        books[0] = new Book("1984", "Orwell");
        books[1] = new Book("Animal Farm", "Orwell");
        
        // Call another method - new stack frame
        calculateFee(bookId, fee);
        
    }  // Method ends → Stack frame cleared
    
    private void calculateFee(int id, double baseFee) {
        // New Stack frame on top
        double lateFee = baseFee * 1.5;
        System.out.println(lateFee);
    }  // Method ends → This stack frame cleared
}
```

**Memory Layout:**

```
STACK (Thread-specific)           HEAP (Shared)
┌──────────────────────┐         ┌─────────────────────────┐
│ calculateFee()       │         │ Library object          │
│  id = 123            │         │  libraryName = "City..."│
│  baseFee = 5.50      │         ├─────────────────────────┤
│  lateFee = 8.25      │         │ Book object             │
├──────────────────────┤         │  title = "1984"         │
│ borrowBook()         │         │  author = "Orwell"      │
│  bookId = 123        │         ├─────────────────────────┤
│  fee = 5.50          │         │ Book[] array            │
│  available = true    │         │  [ref][ref][null]       │
│  book ────────────────────────→├─────────────────────────┤
│  books ───────────────────────→│ Book object             │
└──────────────────────┘         │  title = "Animal Farm"  │
                                 └─────────────────────────┘
```

---

### 🎯 Practical Rules

**Stack:**
- Primitives (`int`, `double`, `boolean`, etc.)
- Method local variables
- References to objects (the pointer, not the object)

**Heap:**
- All objects (created with `new`)
- Instance variables (object fields)
- Arrays (even primitive arrays!)

```java
// Examples
int x = 5;                    // x in Stack
Integer y = new Integer(5);   // y reference in Stack, Integer object in Heap

int[] arr = new int[10];      // arr reference in Stack, array in Heap
String s = "Hello";           // s reference in Stack, String object in Heap

Book book = new Book();       // book reference in Stack, Book object in Heap
```

---

### 🚨 Common Errors

**StackOverflowError**
```java
// Too many method calls (infinite recursion)
public void recursiveMethod() {
    recursiveMethod();  // Calls itself forever
}
// Stack frames pile up → StackOverflowError!
```

**Real-World Analogy:** Like stacking plates endlessly - eventually tower falls!

**OutOfMemoryError: Java Heap Space**
```java
// Creating too many objects
List<Book> books = new ArrayList<>();
while (true) {
    books.add(new Book("Title", "Author"));  // Infinite objects!
}
// Heap runs out → OutOfMemoryError!
```

**Real-World Analogy:** Like filling a warehouse until no space left!

---

## 3️⃣ Garbage Collection (GC) ⭐⭐⭐

### 🤔 The Problem: Memory Leaks

**Without automatic cleanup:**

```java
public void processBooks() {
    for (int i = 0; i < 1000000; i++) {
        Book book = new Book("Title " + i);
        // Process book
        // In C/C++, must manually free() each book
        // Forget to free? Memory leak!
    }
}
// In C: 1,000,000 books still in memory (leak!)
// In Java: Garbage Collector automatically cleans up!
```

**Real-World Problem:** Like a **restaurant**:
- Customers use tables (create objects)
- Customers leave (objects no longer needed)
- **Without cleanup:** Dirty tables pile up, restaurant becomes unusable!
- **Need:** Waiters to clean tables (Garbage Collector)

### 💡 The Solution: Garbage Collection

**Garbage Collection = Automatic process that finds and removes unused objects**

**Real-World Analogy:** Think of **city garbage collection**:
- You throw away trash (mark objects as unused)
- Garbage truck comes periodically (GC runs)
- Picks up trash (removes unused objects)
- Streets stay clean (memory freed)

Another analogy: **Library book cleanup**
- Books on shelves (objects in heap)
- Damaged/outdated books (unreachable objects)
- Librarian periodically removes them (GC)
- Makes space for new books (free memory)

---

### 🔍 How Garbage Collection Works

**Key Concept: Reachability**

**An object is garbage when it's no longer reachable from any living thread**

```java
public void example() {
    // Create object - reachable (book1 references it)
    Book book1 = new Book("1984");
    
    // Create another object - reachable (book2 references it)
    Book book2 = new Book("Animal Farm");
    
    // book1 now unreachable - eligible for GC
    book1 = null;
    
    // book2 reassigned - old object unreachable - eligible for GC
    book2 = new Book("Homage to Catalonia");
    
    // At this point: 2 objects eligible for garbage collection
}  // Method ends - book2 goes out of scope - third object eligible for GC
```

**Reachability Chain:**

```
Root References (Starting Points)
├── Local variables in Stack
├── Static variables
├── Active threads
└── JNI references

If object has path to root → REACHABLE (keep)
If no path to root → UNREACHABLE (garbage, remove!)
```

**Example Visualization:**

```
ROOTS                    HEAP
┌──────────┐            ┌──────────────┐
│ book1    │───────────→│ Book("1984") │ ← Reachable (keep)
└──────────┘            └──────────────┘

┌──────────┐            ┌──────────────────┐
│ book2    │───────────→│ Book("Homage")   │ ← Reachable (keep)
└──────────┘            └──────────────────┘

(No reference)          ┌────────────────────┐
                        │ Book("Animal Farm")│ ← Unreachable (garbage!)
                        └────────────────────┘
```

---

### 🔧 Garbage Collection Process

**Three Main Steps:**

**1. Mark Phase**
- Start from root references
- Mark all reachable objects as "alive"
- Objects not marked = garbage

**2. Sweep Phase (Optional)**
- Go through heap
- Remove unmarked (dead) objects
- Free their memory

**3. Compact Phase (Optional)**
- Move living objects together
- Eliminate fragmentation
- Makes allocation faster

**Real-World Analogy:** Think of **organizing a closet**:
1. **Mark:** Tag clothes you still wear (alive objects)
2. **Sweep:** Remove untagged clothes (dead objects)
3. **Compact:** Reorganize remaining clothes neatly (defragmentation)

---

### 🏗️ Generational Garbage Collection

**Key Observation:** Most objects die young!

```java
public void processRequest() {
    // Temporary objects - die immediately
    String temp = "Processing...";
    List<String> logs = new ArrayList<>();
    // These likely garbage when method returns
}

// Long-lived objects
private static final Map<String, Book> cache = new HashMap<>();
// This lives entire application lifetime
```

**Real-World Analogy:** Like **restaurant patrons**:
- **Young Generation:** Quick lunch customers (in/out fast) - 90% of people
- **Old Generation:** All-day café workers (stay long) - 10% of people

**Check young generation frequently (most garbage there!)**
**Check old generation rarely (few objects die there)**

---

### 📊 Heap Generations

```
Heap Memory Layout
┌────────────────────────────────────────────┐
│ Young Generation (Small, frequent GC)      │
│  ┌──────────┬───────┬───────┐             │
│  │  Eden    │ S0    │ S1    │             │
│  │ (new)    │(survivors)     │             │
│  └──────────┴───────┴───────┘             │
├────────────────────────────────────────────┤
│ Old Generation (Large, rare GC)            │
│  ┌───────────────────────────────────────┐ │
│  │  Long-lived objects                   │ │
│  └───────────────────────────────────────┘ │
├────────────────────────────────────────────┤
│ Permanent/Metaspace (Class metadata)       │
└────────────────────────────────────────────┘
```

**How It Works:**

1. **New objects → Eden space** (in Young Generation)
2. **Eden fills up → Minor GC** (fast, collects young garbage)
3. **Survivors → Survivor Space** (S0 or S1)
4. **Multiple survivals → Old Generation** (promoted)
5. **Old fills up → Major GC** (slow, collects old garbage)

**Real-World Analogy:** Like **employee retention**:
- **Eden:** New hires (most quit quickly)
- **Survivor:** Employees who stayed few months (some leave, some stay)
- **Old:** Long-term employees (rarely leave)

---

### 🎯 Types of Garbage Collectors

| GC Type | When | Duration | Use Case |
|---------|------|----------|----------|
| **Minor GC** | Young Gen fills | Milliseconds | Frequent, fast |
| **Major GC** | Old Gen fills | Seconds | Rare, slow |
| **Full GC** | Entire heap | Seconds | Very rare, very slow |

**Real-World Analogy:** Like **house cleaning**:
- **Minor GC:** Daily tidying (quick, frequent)
- **Major GC:** Monthly deep cleaning (slow, occasional)
- **Full GC:** Spring cleaning everything (very slow, rare)

---

### 🔧 Making Objects Eligible for GC

**1. Nullify Reference**
```java
Book book = new Book("1984");
book = null;  // Object eligible for GC
```

**2. Reassign Reference**
```java
Book book = new Book("1984");
book = new Book("Animal Farm");  // First object eligible for GC
```

**3. Object Goes Out of Scope**
```java
public void method() {
    Book book = new Book("1984");
}  // book goes out of scope, object eligible for GC
```

**4. Island of Isolation**
```java
class Node {
    Node next;
}

Node node1 = new Node();
Node node2 = new Node();
node1.next = node2;
node2.next = node1;

node1 = null;
node2 = null;
// Both objects reference each other but unreachable from roots
// Both eligible for GC (island of isolation)
```

---

### ⚖️ Garbage Collection Best Practices

**✅ DO:**

1. **Nullify Large Objects When Done**
```java
byte[] largeArray = new byte[1000000];
// ... use array ...
largeArray = null;  // Help GC reclaim memory sooner
```

2. **Close Resources Promptly**
```java
try (FileReader file = new FileReader("data.txt")) {
    // Use file
}  // Auto-closed, eligible for GC
```

3. **Avoid Creating Unnecessary Objects**
```java
// Bad - creates many objects
for (int i = 0; i < 1000000; i++) {
    String s = new String("hello");  // Don't do this!
}

// Good - reuse
String s = "hello";  // String pool
for (int i = 0; i < 1000000; i++) {
    // Use s
}
```

4. **Use StringBuilder for String Concatenation in Loops**
```java
// Bad - creates many String objects
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i;  // Creates new String each time!
}

// Good - one StringBuilder object
StringBuilder result = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    result.append(i);  // Modifies same object
}
```

**❌ DON'T:**

1. **Don't Call System.gc()**
```java
System.gc();  // Suggestion, not command. JVM knows better!
```

2. **Don't Hold Onto References Unnecessarily**
```java
// Bad - holds all objects in memory
List<Book> allBooks = new ArrayList<>();
for (int i = 0; i < 1000000; i++) {
    allBooks.add(new Book("Title " + i));
    // All 1M objects in memory!
}

// Good - process and release
for (int i = 0; i < 1000000; i++) {
    Book book = new Book("Title " + i);
    processBook(book);
    // book eligible for GC after each iteration
}
```

3. **Don't Create Objects in Loops Unnecessarily**
```java
// Bad
for (int i = 0; i < 1000; i++) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // Creates 1000 SimpleDateFormat objects!
}

// Good
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
for (int i = 0; i < 1000; i++) {
    // Reuse same object
}
```

---

### 🚨 Common Misconceptions

**❌ Wrong:** "Setting object to null forces immediate garbage collection"  
**✅ Reality:** Makes it eligible for GC. Actual collection happens when GC runs.

**❌ Wrong:** "finalize() method always runs before GC"  
**✅ Reality:** finalize() is unreliable and deprecated. Use try-with-resources instead.

**❌ Wrong:** "Garbage collection prevents all memory leaks"  
**✅ Reality:** Still possible if you hold unnecessary references (logical leaks).

---

## 4️⃣ String Pool ⭐⭐

### 🤔 The Problem: String Duplication

**Strings are used EVERYWHERE in programs:**

```java
String name1 = new String("John");
String name2 = new String("John");
String name3 = new String("John");
// Three identical "John" strings in memory - wasteful!

// In real applications:
// - Error messages
// - Log statements
// - Configuration values
// - User names, addresses, etc.
// Many duplicates!
```

**Real-World Problem:** Like a **library** storing 100 copies of the same book:
- Wastes shelf space
- Expensive (buy 100 copies)
- Inefficient (only need 1 copy, share it)

### 💡 The Solution: String Pool (Interning)

**String Pool = Special memory area in heap that stores unique String literals**

**Instead of duplicates, share one copy!**

**Real-World Analogy:** Think of **phone directory**:
- Many people have same phone company
- Instead of storing "Verizon" 1000 times...
- Store "Verizon" once, everyone references it!

Another analogy: **Library shared copies**
- 100 students need same textbook
- Library buys 1 copy (master)
- Students reference the same copy
- Saves money and space!

---

### 🔧 How String Pool Works

```java
// String literals → automatically pooled
String s1 = "Hello";  // Created in String Pool
String s2 = "Hello";  // Reuses same object from pool!
String s3 = "Hello";  // Reuses same object from pool!

System.out.println(s1 == s2);  // true (same object!)
System.out.println(s2 == s3);  // true (same object!)

// new String() → NOT pooled (heap)
String s4 = new String("Hello");  // New object in heap
String s5 = new String("Hello");  // Another new object in heap

System.out.println(s1 == s4);  // false (different objects)
System.out.println(s4 == s5);  // false (different objects)

// But content is same
System.out.println(s1.equals(s4));  // true (same content)
```

**Memory Visualization:**

```
String Pool (Heap)              Regular Heap
┌────────────────┐              ┌────────────────┐
│ "Hello" ←──────┼──s1          │ "Hello" ←── s4 │
│         ←──────┼──s2          ├────────────────┤
│         ←──────┼──s3          │ "Hello" ←── s5 │
└────────────────┘              └────────────────┘

Pool: 1 object, 3 references
Heap: 2 separate objects
```

---

### 🔍 String Creation Methods

**Method 1: String Literal (Recommended)**
```java
String s1 = "Hello";
// 1. Check if "Hello" exists in String Pool
// 2. If yes, reuse it
// 3. If no, create in pool
// Always uses pool!
```

**Method 2: new String()**
```java
String s2 = new String("Hello");
// 1. Creates new object in heap (always!)
// 2. "Hello" literal also in pool
// 3. Total: 2 objects created
// Wasteful! Don't use unless necessary!
```

**Method 3: intern() Method**
```java
String s3 = new String("Hello");
String s4 = s3.intern();
// intern() moves string to pool (if not already there)
// s4 now references pooled version

String s1 = "Hello";
System.out.println(s1 == s4);  // true (both from pool)
```

---

### 📊 String Comparison

```java
String s1 = "Hello";
String s2 = "Hello";
String s3 = new String("Hello");

// == compares references (object identity)
System.out.println(s1 == s2);        // true (same pool object)
System.out.println(s1 == s3);        // false (different objects)

// equals() compares content (value)
System.out.println(s1.equals(s2));   // true (same content)
System.out.println(s1.equals(s3));   // true (same content)

// Always use equals() for String comparison!
```

**Real-World Analogy:** Like **twins**:
- **==:** Are they the exact same person? (same object?)
- **equals():** Do they look identical? (same content?)

---

### 🎯 Why String Pool Matters

**1. Memory Efficiency**
```java
// Without pool
String[] usernames = new String[1000];
for (int i = 0; i < 1000; i++) {
    usernames[i] = new String("guest");  // 1000 "guest" objects!
}

// With pool
String[] usernames = new String[1000];
for (int i = 0; i < 1000; i++) {
    usernames[i] = "guest";  // 1 "guest" object, 1000 references!
}
```

**2. Performance (== comparison)**
```java
String s1 = "Hello";
String s2 = "Hello";

// Fast - just pointer comparison
if (s1 == s2) {  // Instant!
    // Same object
}

// Slower - compares each character
if (s1.equals(s2)) {  // Has to check each char
    // Same content
}
```

**3. Security**
- Strings are immutable
- Pooled strings are shared
- Can't be modified (secure)

---

### 🔧 String Concatenation

```java
// Concatenation with + creates new objects
String s1 = "Hello";
String s2 = s1 + " World";  // New String object
String s3 = s2 + "!";       // Another new object

// In loop - very inefficient!
String result = "";
for (int i = 0; i < 1000; i++) {
    result = result + i;  // Creates 1000 new String objects!
}

// Use StringBuilder instead!
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);  // Modifies same object
}
String result = sb.toString();  // One final String
```

**Why StringBuilder?**
- String is immutable (can't change)
- Each concatenation creates new String
- StringBuilder is mutable (can change)
- Much faster for multiple operations

**Real-World Analogy:** Like **building a wall**:
- **String concatenation:** Tear down wall, build bigger wall each time (slow!)
- **StringBuilder:** Add bricks to existing wall (fast!)

---

### ⚖️ String Pool Best Practices

**✅ DO:**

1. **Use String Literals**
```java
String s = "Hello";  // Good - uses pool
```

2. **Use equals() for Comparison**
```java
if (s1.equals(s2)) {  // Good - compares content
    // Do something
}
```

3. **Use StringBuilder in Loops**
```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);
}
```

4. **Intern() for Dynamically Created Strings (if many duplicates)**
```java
String dynamic = scanner.nextLine().intern();
// If many users enter same value, intern() saves memory
```

**❌ DON'T:**

1. **Don't Use new String() Unnecessarily**
```java
String s = new String("Hello");  // Bad - creates unnecessary object
```

2. **Don't Use == for String Comparison**
```java
if (s1 == s2) {  // Bad - compares references, not content
    // Unreliable!
}
```

3. **Don't Concatenate Strings in Loop**
```java
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i;  // Bad - very slow!
}
```

---

### 🚨 Common Pitfalls

**Pitfall 1: == vs equals()**
```java
String s1 = "Hello";
String s2 = new String("Hello");

if (s1 == s2) {  // false (different objects)
    System.out.println("Same object");
}

if (s1.equals(s2)) {  // true (same content)
    System.out.println("Same content");
}
```

**Pitfall 2: String Concatenation in Loop**
```java
// This is VERY slow for large loops!
String result = "";
for (int i = 0; i < 10000; i++) {
    result += i;  // Creates 10,000 String objects!
}
```

**Pitfall 3: Assuming All Strings Are Pooled**
```java
String s1 = "Hello";
String s2 = new String("Hello");
// s1 is pooled, s2 is NOT pooled!
```

---

## 💡 Quick Reference

### Stack vs Heap
```java
// Stack
int x = 5;              // Primitive in stack
String ref;             // Reference in stack

// Heap
String obj = new String("Hello");  // Object in heap
int[] arr = new int[10];           // Array in heap
```

### Garbage Collection
```java
// Make eligible for GC
object = null;          // Nullify
object = new Object();  // Reassign
// Method ends          // Out of scope
```

### String Pool
```java
// Pooled
String s1 = "Hello";    // Literal → pool

// Not pooled
String s2 = new String("Hello");  // new → heap

// Move to pool
String s3 = s2.intern();  // Now pooled

// Comparison
s1 == s2      // false (different objects)
s1.equals(s2) // true (same content)
```

### StringBuilder
```java
// Efficient string building
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);
}
String result = sb.toString();
```

---

## ✅ Self-Check Questions

1. What's the difference between Stack and Heap?
2. Where are local variables stored?
3. Where are objects stored?
4. What happens when a method ends?
5. How does Garbage Collection know what to collect?
6. What's the difference between Minor GC and Major GC?
7. What is the String Pool?
8. Why use String literals instead of new String()?
9. What's the difference between == and equals() for Strings?
10. When should you use StringBuilder?

---

## 🎯 Key Takeaways

### Must Understand:

1. **Stack:** Fast, small, method-level (primitives + references)
2. **Heap:** Slower, large, application-level (objects + arrays)
3. **Garbage Collection:** Automatic cleanup of unreachable objects
4. **String Pool:** Reuses String literals to save memory
5. **StringBuilder:** Use for string concatenation in loops

### Think in Real-World Terms:
- Stack = Stack of plates (LIFO)
- Heap = Warehouse (shared storage)
- Garbage Collection = City garbage pickup (automatic cleanup)
- String Pool = Shared library books (reuse instead of duplicate)
- StringBuilder = Building a wall (add bricks vs rebuild)

### Common Mistakes to Avoid:
- Using == to compare Strings (use equals())
- String concatenation in loops (use StringBuilder)
- Creating String with new unnecessarily
- Holding onto object references (prevents GC)
- Calling System.gc() (let JVM decide)

---

**Previous:** Group 1C - Special Concepts  
**Next:** Group 3 - Collections Framework  
**Study Time:** 2-3 hours | **Review:** 30 minutes

💡 **Practice Tip:** Write a program that creates objects in a loop and monitor memory usage. Experiment with nullifying references and observe how garbage collection works. Create Strings with literals vs new and compare with == and equals().