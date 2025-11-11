# 📦 Java Collections Framework - Understanding Guide

> **Master data structures: Lists, Sets, Maps and when to use each**

---

## 🎯 What You'll Understand

- Why collections exist and what problem they solve
- List, Set, and Map - what they are and when to use them
- ArrayList vs LinkedList - the real difference
- HashSet vs TreeSet - speed vs order
- HashMap - the most used collection
- How to pick the right collection for your need

---

## 1️⃣ Why Collections? The Big Picture ⭐⭐⭐

### 🤔 The Problem: Arrays Are Limited

**Before Collections Framework:**

```java
// Arrays have FIXED size!
String[] books = new String[5];  // Only 5 books
books[0] = "1984";
books[1] = "Animal Farm";
// ... what if you need to add 6th book?

// Need bigger array? Manual work!
String[] newBooks = new String[10];  // Create bigger array
for (int i = 0; i < books.length; i++) {
    newBooks[i] = books[i];  // Copy everything manually!
}
books = newBooks;  // Replace reference

// Problems:
// 1. Fixed size - can't grow automatically
// 2. No built-in methods (add, remove, search)
// 3. Can't store primitives efficiently (need wrapper classes)
// 4. Manual management (tedious and error-prone)
```

**Real-World Problem:** Like a **parking lot with fixed spaces**:
- Built for 50 cars
- What if 51st car arrives? Can't park!
- Want more spaces? Tear down, rebuild bigger lot!
- No automatic management

### 💡 The Solution: Collections Framework

**Collections = Dynamic, flexible data structures with built-in operations**

**Benefits:**
1. **Dynamic size** - grows/shrinks automatically
2. **Rich API** - add, remove, search, sort built-in
3. **Type-safe** - generics prevent wrong types
4. **Optimized** - different structures for different needs

**Real-World Analogy:** Like **modern cloud storage**:
- Automatically expands when needed
- Built-in tools (search, organize, share)
- Different storage types (fast access, backup, archive)
- You focus on data, not management!

```java
// With Collections - simple and flexible!
List<String> books = new ArrayList<>();
books.add("1984");
books.add("Animal Farm");
books.add("Homage to Catalonia");  // Just add! Automatic growth!
books.remove("Animal Farm");        // Easy removal
boolean has1984 = books.contains("1984");  // Built-in search
```

---

## 2️⃣ Collections Hierarchy Overview

```
                    Collection Interface
                            |
        ----------------------------------------
        |                   |                  |
      List              Set                Queue
        |                   |                  |
   ArrayList          HashSet            PriorityQueue
   LinkedList         TreeSet            LinkedList
   Vector             LinkedHashSet      ArrayDeque

                    Map Interface (separate!)
                            |
                    --------------------
                    |                  |
                HashMap            TreeMap
                LinkedHashMap      Hashtable
```

**Three Main Types:**

1. **List** - Ordered, allows duplicates (like a shopping list)
2. **Set** - Unordered, no duplicates (like a set of unique items)
3. **Map** - Key-value pairs (like a dictionary)

---

## 3️⃣ List Interface ⭐⭐⭐

### 🤔 What is a List?

**List = Ordered collection that allows duplicates**

**Real-World Analogy:** Think of a **shopping list**:
- Items in specific order (1st item, 2nd item, 3rd item)
- Can have duplicates (milk, bread, milk again)
- Can access by position (what's the 3rd item?)

Another analogy: **Queue at ticket counter**
- People in line (ordered)
- Same person can join multiple times (duplicates allowed)
- Can identify by position (5th person in line)

**Key Characteristics:**
- **Ordered** - maintains insertion order
- **Indexed** - access by position (0, 1, 2...)
- **Duplicates** - allows duplicate elements

```java
List<String> books = new ArrayList<>();
books.add("1984");           // Index 0
books.add("Animal Farm");    // Index 1
books.add("1984");           // Index 2 - duplicate allowed!

System.out.println(books.get(0));  // "1984" - access by index
System.out.println(books.size());  // 3
```

---

## 4️⃣ ArrayList vs LinkedList ⭐⭐⭐

### 🤔 The Problem: Different Access Patterns

**Different scenarios need different optimizations:**

**Scenario 1: Frequent Reading**
```java
// Read book at position 500 frequently
Book book = bookList.get(500);  // Need fast random access!
```

**Scenario 2: Frequent Adding/Removing**
```java
// Add book at beginning frequently
bookList.add(0, newBook);  // Need fast insertion!
```

**Can't optimize for both! Need two different implementations.**

### 💡 ArrayList - Fast Random Access

**ArrayList = Dynamic array (resizable array)**

**Real-World Analogy:** Think of **apartment building with numbered units**:
- Each apartment has address (index)
- Direct access (go straight to apartment 501)
- Adding in middle is hard (shift everyone's apartment)

Another analogy: **Book on shelf with page numbers**
- Jump directly to page 237 (fast random access)
- Insert page in middle means renumbering all pages after (slow)

**How It Works Internally:**

```
ArrayList = Array under the hood
┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 0 │ 1 │ 2 │ 3 │ 4 │ 5 │ 6 │ 7 │ 8 │ 9 │
└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘

Elements stored contiguously in memory
Direct index access: array[5] → instant!
```

**Adding to End (Fast):**
```
Before: [A][B][C][ ][ ]
After:  [A][B][C][D][ ]  ✅ Just add at end!
```

**Adding to Middle (Slow):**
```
Before: [A][B][C][D][E]
Insert X at index 2:
Step 1: Shift everything right
        [A][B][ ][C][D][E]
Step 2: Insert X
        [A][B][X][C][D][E]  ⚠️ Shift operation is costly!
```

```java
// ArrayList - best for frequent reads
List<Book> books = new ArrayList<>();

// Fast operations
books.add(new Book("1984"));           // Add at end - O(1)
Book book = books.get(500);            // Random access - O(1)
books.contains(someBook);              // Search - O(n)

// Slow operations
books.add(0, new Book("New"));         // Add at start - O(n) shift
books.remove(0);                       // Remove from start - O(n) shift
```

---

### 💡 LinkedList - Fast Insertion/Deletion

**LinkedList = Chain of nodes, each pointing to next**

**Real-World Analogy:** Think of a **treasure hunt**:
- Each clue points to next location
- To get to 5th clue, must follow chain (1→2→3→4→5)
- Add new clue? Just change one pointer!

Another analogy: **Train with connected carriages**
- Each carriage linked to next
- Want to reach carriage 10? Walk through all previous
- Add new carriage? Just hook it up!

**How It Works Internally:**

```
LinkedList = Chain of Nodes
┌─────┐    ┌─────┐    ┌─────┐    ┌─────┐
│ [A] │───→│ [B] │───→│ [C] │───→│ [D] │
│next │    │next │    │next │    │next │
└─────┘    └─────┘    └─────┘    └─────┘

Each node has data + reference to next
No index, must traverse from start!
```

**Adding to Middle (Fast):**
```
Before: [A]→[B]→[D]
Insert C between B and D:
Step 1: Create new node C
Step 2: B.next = C
Step 3: C.next = D
After:  [A]→[B]→[C]→[D]  ✅ Just update pointers!
```

**Accessing Middle (Slow):**
```
Want element at index 1000?
Start → 1 → 2 → 3 → ... → 1000  ⚠️ Must traverse!
```

```java
// LinkedList - best for frequent add/remove
List<Book> books = new LinkedList<>();

// Fast operations
books.add(0, new Book("New"));         // Add at start - O(1)
books.addFirst(new Book("First"));     // Add at start - O(1)
books.removeLast();                    // Remove last - O(1)

// Slow operations
Book book = books.get(500);            // Random access - O(n) traverse!
books.contains(someBook);              // Search - O(n) traverse
```

---

### 📊 ArrayList vs LinkedList Comparison

| Feature | ArrayList | LinkedList |
|---------|-----------|------------|
| **Internal** | Dynamic array | Doubly-linked nodes |
| **Random Access** | ✅ Fast O(1) | ❌ Slow O(n) |
| **Add at End** | ✅ Fast O(1)* | ✅ Fast O(1) |
| **Add at Start** | ❌ Slow O(n) | ✅ Fast O(1) |
| **Add in Middle** | ❌ Slow O(n) | ⚠️ Slow O(n)** |
| **Remove at End** | ✅ Fast O(1) | ✅ Fast O(1) |
| **Remove at Start** | ❌ Slow O(n) | ✅ Fast O(1) |
| **Memory** | Less (just data) | More (data + pointers) |
| **Cache Performance** | Better (contiguous) | Worse (scattered) |

*Amortized O(1) - occasionally needs to resize  
**O(n) to find position, then O(1) to insert

### ⚖️ When to Use Which?

**✅ Use ArrayList When:**
1. **Frequent random access** (get by index)
2. **Mostly reading** data
3. **Adding at end** mostly
4. **Memory efficiency** matters
5. **Don't know size** upfront (default choice!)

```java
// Good use cases for ArrayList
List<Book> catalog = new ArrayList<>();        // Browse by index
List<Student> students = new ArrayList<>();    // Access by position
List<Product> products = new ArrayList<>();    // Search, display
```

**✅ Use LinkedList When:**
1. **Frequent insertion/deletion** at start/end
2. **Queue/Deque** implementation needed
3. **Rarely access by index**
4. **Don't care about random access speed**

```java
// Good use cases for LinkedList
Queue<Task> taskQueue = new LinkedList<>();    // Add/remove from ends
Deque<Page> browserHistory = new LinkedList<>();  // Navigate back/forward
```

**Real-World Decision:** 

**ArrayList = Photo album**
- Numbered pages (indexed)
- Flip to any page instantly
- Adding page in middle is hassle

**LinkedList = Chain link fence**
- Connected links
- Add new link anywhere easily
- To reach specific link, follow chain

---

### 🚨 Common Mistakes

**Mistake 1: Using LinkedList for Random Access**
```java
// Bad - LinkedList with frequent get()
LinkedList<Book> books = new LinkedList<>();
for (int i = 0; i < books.size(); i++) {
    Book book = books.get(i);  // O(n) each time! Very slow!
}

// Good - use ArrayList
ArrayList<Book> books = new ArrayList<>();
for (int i = 0; i < books.size(); i++) {
    Book book = books.get(i);  // O(1) each time! Fast!
}

// Or use iterator for LinkedList
for (Book book : books) {  // Iterator - O(n) total, not O(n²)
    // Process book
}
```

**Mistake 2: Premature Optimization**
```java
// Don't do this without profiling!
LinkedList<Book> books = new LinkedList<>();  // "Because I heard it's faster for insert"

// Reality: ArrayList is fine for 99% of cases!
ArrayList<Book> books = new ArrayList<>();
```

---

## 5️⃣ Set Interface ⭐⭐⭐

### 🤔 What is a Set?

**Set = Collection with NO duplicates**

**Real-World Analogy:** Think of **unique items**:
- Your **fingerprint** (no two people have same)
- **Student IDs** in school (each unique)
- **Email addresses** in database (no duplicates)

Another analogy: **Guest list for exclusive event**
- Each person invited once
- Try to add same person again? Ignored!
- Don't care about order

**Key Characteristics:**
- **No duplicates** - each element unique
- **Unordered** (usually) - no guaranteed order
- **No index** - can't access by position

```java
Set<String> usernames = new HashSet<>();
usernames.add("alice");    // Added
usernames.add("bob");      // Added
usernames.add("alice");    // Ignored! Duplicate!

System.out.println(usernames.size());  // 2, not 3!
```

---

## 6️⃣ HashSet vs TreeSet ⭐⭐

### 🤔 The Problem: Speed vs Order

**Two different needs:**

**Need 1: Super Fast Operations**
```java
// Check if million users contains "alice"
if (users.contains("alice")) {  // Need this VERY fast!
    // Do something
}
```

**Need 2: Sorted Order**
```java
// Display users alphabetically
for (String user : users) {  // Need sorted output!
    System.out.println(user);
}
```

**Can't have both! Different implementations.**

---

### 💡 HashSet - Lightning Fast, No Order

**HashSet = Hash table implementation (unordered, fast)**

**Real-World Analogy:** Think of **library book locations**:
- Hash function = Library catalog system
- Book ID → Directly tells you: "Shelf 5, Row 3"
- No need to search every shelf!
- Books not in alphabetical order, but INSTANT lookup!

Another analogy: **Apartment mail slots**
- Apartment number → Direct slot
- No searching, straight to mailbox
- Not in alphabetical order by name

**How HashSet Works:**

```
Hash Function converts object → number (hash code)

"alice" → hash function → 12
"bob"   → hash function → 5
"charlie" → hash function → 18

Internal Array (Hash Table):
┌────┬────┬────┬────┬────┬────┬────┬────┐
│    │    │    │    │    │bob │    │    │
└────┴────┴────┴────┴────┴────┴────┴────┘
  0    1    2    3    4    5    6    7   ...
                            ↑
                         index 5

┌────┬────┬────┬────┬────┬────┬────┬────┐
│    │    │    │    │    │    │    │    │
└────┴────┴────┴────┴────┴────┴────┴────┘
  8    9   10   11   12   13   14   15   ...
                      ↑
                  index 12
                  "alice"

Direct access by hash code → O(1) super fast!
```

```java
Set<String> usernames = new HashSet<>();

// All operations O(1) - constant time!
usernames.add("alice");          // O(1)
usernames.add("bob");            // O(1)
usernames.contains("alice");     // O(1) - instant!
usernames.remove("bob");         // O(1)

// But unordered!
for (String user : usernames) {
    System.out.println(user);
}
// Output order: unpredictable! (bob, alice) or (alice, bob)?
```

**Characteristics:**
- **Speed:** O(1) for add, remove, contains
- **Order:** None - unpredictable
- **Null:** Allows one null element
- **Use case:** When speed matters, order doesn't

---

### 💡 TreeSet - Sorted Order, Slower

**TreeSet = Red-Black Tree implementation (sorted, slower)**

**Real-World Analogy:** Think of **dictionary**:
- Words in alphabetical order
- Binary search to find word (fast, but not instant)
- Always sorted
- Finding word takes log(n) time

Another analogy: **Organized filing cabinet**
- Files alphabetically sorted
- Easy to find, but takes time to maintain order
- Adding new file means inserting in right place

**How TreeSet Works:**

```
Binary Search Tree (Balanced)

              "dave"
             /      \
         "bob"      "frank"
          /  \         \
     "alice" "charlie" "george"

Maintains sorted order (left < parent < right)
Finding element: traverse tree - O(log n)
```

```java
Set<String> usernames = new TreeSet<>();

// All operations O(log n) - slower than HashSet
usernames.add("dave");           // O(log n)
usernames.add("alice");          // O(log n)
usernames.add("frank");          // O(log n)
usernames.contains("alice");     // O(log n)

// But sorted order guaranteed!
for (String user : usernames) {
    System.out.println(user);
}
// Output: alice, dave, frank (alphabetical!)
```

**Characteristics:**
- **Speed:** O(log n) for add, remove, contains
- **Order:** Sorted (natural order or custom comparator)
- **Null:** No null allowed (needs comparison)
- **Use case:** When sorted order needed

---

### 📊 HashSet vs TreeSet Comparison

| Feature | HashSet | TreeSet |
|---------|---------|---------|
| **Internal** | Hash Table | Red-Black Tree |
| **Add** | O(1) | O(log n) |
| **Remove** | O(1) | O(log n) |
| **Contains** | O(1) | O(log n) |
| **Order** | ❌ None | ✅ Sorted |
| **Null** | ✅ One null | ❌ No null |
| **Performance** | Fastest | Slower |
| **Memory** | Less | More (tree structure) |
| **When to use** | Speed matters | Order matters |

### ⚖️ When to Use Which?

**✅ Use HashSet When:**
1. **Speed is priority** (most common case)
2. **Don't care about order**
3. **Just need uniqueness**
4. **Large dataset** with frequent lookups

```java
// Good use cases for HashSet
Set<String> uniqueEmails = new HashSet<>();       // Fast duplicate check
Set<Integer> visitedPages = new HashSet<>();      // Fast lookup
Set<String> keywords = new HashSet<>();           // Fast contains()
```

**✅ Use TreeSet When:**
1. **Need sorted output**
2. **Want natural ordering**
3. **Need range operations** (subSet, headSet, tailSet)
4. **Don't mind slightly slower operations**

```java
// Good use cases for TreeSet
Set<String> sortedNames = new TreeSet<>();        // Alphabetical output
Set<Integer> scores = new TreeSet<>();            // Sorted scores
Set<LocalDate> dates = new TreeSet<>();           // Chronological order
```

**Real-World Decision:**

**HashSet = Messy drawer**
- Throw items in anywhere (fast!)
- Find item instantly (hash lookup)
- But not organized

**TreeSet = Organized shelf**
- Items arranged in order
- Takes time to maintain order
- But easy to browse sorted

---

## 7️⃣ Map Interface ⭐⭐⭐

### 🤔 What is a Map?

**Map = Key-Value pairs (like a dictionary)**

**Not a Collection! Separate hierarchy.**

**Real-World Analogy:** Think of a **phone book**:
- Name (key) → Phone number (value)
- Unique names (no duplicate keys)
- Fast lookup by name

Another analogy: **Dictionary**
- Word (key) → Definition (value)
- Each word appears once
- Quick word lookup

**Key Characteristics:**
- **Key-Value pairs** - each key maps to one value
- **Unique keys** - no duplicate keys
- **Fast lookup** - find value by key
- **No order** (HashMap) or **Sorted** (TreeMap)

```java
Map<String, Book> catalog = new HashMap<>();

// Put key-value pairs
catalog.put("ISBN123", new Book("1984", "Orwell"));
catalog.put("ISBN456", new Book("Animal Farm", "Orwell"));

// Get value by key
Book book = catalog.get("ISBN123");  // Fast lookup!

// Key must be unique
catalog.put("ISBN123", new Book("Different Book"));  // Replaces old value!
```

---

## 8️⃣ HashMap ⭐⭐⭐

**HashMap = Hash table implementation of Map**

**Most commonly used Map!**

**Real-World Analogy:** Think of **student lockers**:
- Student ID (key) → Locker number (value)
- ID directly maps to locker (hash function)
- Instant access to your locker
- No searching through all lockers!

**How HashMap Works:**

```
Hash Function: Key → Hash Code → Index

"ISBN123" → hash() → 45 → bucket[45] → Book("1984")
"ISBN456" → hash() → 12 → bucket[12] → Book("Animal Farm")

Internal Array of Buckets:
┌─────┬─────┬─────┬─────┬─────┬─────┐
│     │     │Entry│     │Entry│     │
│     │     │ K:V │     │ K:V │     │
└─────┴─────┴─────┴─────┴─────┴─────┘
   0    1    12    13    45    46   ...
```

```java
// HashMap - super fast key-value storage
Map<String, Book> catalog = new HashMap<>();

// O(1) operations
catalog.put("ISBN123", new Book("1984"));        // O(1)
Book book = catalog.get("ISBN123");              // O(1) - instant!
catalog.remove("ISBN123");                       // O(1)
boolean exists = catalog.containsKey("ISBN123"); // O(1)

// Common operations
catalog.size();                    // Number of entries
catalog.isEmpty();                 // Is map empty?
catalog.containsKey("ISBN123");    // Has this key?
catalog.containsValue(someBook);   // Has this value? (slower O(n))

// Iterate over entries
for (Map.Entry<String, Book> entry : catalog.entrySet()) {
    String isbn = entry.getKey();
    Book book = entry.getValue();
    System.out.println(isbn + " -> " + book.getTitle());
}

// Iterate over keys
for (String isbn : catalog.keySet()) {
    System.out.println(isbn);
}

// Iterate over values
for (Book book : catalog.values()) {
    System.out.println(book.getTitle());
}
```

---

### 🎯 HashMap Common Patterns

**Pattern 1: Counting Occurrences**
```java
// Count word frequencies
Map<String, Integer> wordCount = new HashMap<>();
String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};

for (String word : words) {
    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
}

// Result: {apple=3, banana=2, cherry=1}
```

**Pattern 2: Caching/Memoization**
```java
// Cache expensive computation results
Map<String, String> cache = new HashMap<>();

public String processData(String input) {
    if (cache.containsKey(input)) {
        return cache.get(input);  // Return cached result
    }
    
    String result = expensiveComputation(input);
    cache.put(input, result);  // Cache for next time
    return result;
}
```

**Pattern 3: Grouping Data**
```java
// Group books by author
Map<String, List<Book>> booksByAuthor = new HashMap<>();

for (Book book : allBooks) {
    String author = book.getAuthor();
    
    if (!booksByAuthor.containsKey(author)) {
        booksByAuthor.put(author, new ArrayList<>());
    }
    
    booksByAuthor.get(author).add(book);
}

// Or use computeIfAbsent (Java 8+)
for (Book book : allBooks) {
    booksByAuthor.computeIfAbsent(book.getAuthor(), k -> new ArrayList<>())
                 .add(book);
}
```

**Pattern 4: Configuration/Settings**
```java
// Store application settings
Map<String, String> config = new HashMap<>();
config.put("database.url", "jdbc:mysql://localhost:3306/db");
config.put("database.user", "admin");
config.put("max.connections", "100");

String dbUrl = config.get("database.url");
```

---

### 📊 HashMap Characteristics

| Feature | Details |
|---------|---------|
| **Performance** | O(1) average for get/put |
| **Order** | No guaranteed order |
| **Null Keys** | One null key allowed |
| **Null Values** | Multiple null values allowed |
| **Thread-Safe** | No (use ConcurrentHashMap) |
| **Duplicates** | Keys unique, values can duplicate |
| **When to use** | Fast key-value lookup (most common) |

---

### 🔧 TreeMap - Sorted Map

**TreeMap = Red-Black Tree implementation (sorted by keys)**

```java
Map<String, Book> catalog = new TreeMap<>();
catalog.put("ISBN999", new Book("Zebra Book"));
catalog.put("ISBN123", new Book("Apple Book"));
catalog.put("ISBN456", new Book("Banana Book"));

// Keys automatically sorted!
for (String isbn : catalog.keySet()) {
    System.out.println(isbn);
}
// Output: ISBN123, ISBN456, ISBN999 (sorted!)
```

**When to use TreeMap:**
- Need keys in sorted order
- Need range operations (subMap, headMap, tailMap)
- Don't mind O(log n) instead of O(1)

---

## 9️⃣ Choosing the Right Collection ⭐⭐⭐

### 🎯 Decision Tree

```
Need Key-Value Pairs?
│
├─ YES → Use Map
│   │
│   ├─ Need sorted keys? → TreeMap
│   └─ Just need fast lookup? → HashMap ✅ (most common)
│
└─ NO → Need uniqueness?
    │
    ├─ YES → Use Set
    │   │
    │   ├─ Need sorted order? → TreeSet
    │   └─ Just need fast operations? → HashSet ✅ (most common)
    │
    └─ NO → Use List
        │
        ├─ Frequent add/remove at start/end? → LinkedList
        └─ Everything else? → ArrayList ✅ (default choice)
```

---

### 📊 Complete Comparison Table

| Collection | Ordered | Duplicates | Indexed | Speed | Use Case |
|------------|---------|------------|---------|-------|----------|
| **ArrayList** | ✅ Yes | ✅ Yes | ✅ Yes | Fast reads | Default list |
| **LinkedList** | ✅ Yes | ✅ Yes | ✅ Yes | Fast insert/delete | Queue/Deque |
| **HashSet** | ❌ No | ❌ No | ❌ No | Fastest | Unique items |
| **TreeSet** | ✅ Sorted | ❌ No | ❌ No | Slower | Sorted unique |
| **HashMap** | ❌ No | Keys: No, Values: Yes | ❌ No | Fastest | Key-value pairs |
| **TreeMap** | ✅ Sorted | Keys: No, Values: Yes | ❌ No | Slower | Sorted key-value |

---

### 🎯 Real-World Use Cases

**ArrayList**
```java
List<Student> students = new ArrayList<>();       // Class roster
List<Product> products = new ArrayList<>();       // Product catalog
List<String> logs = new ArrayList<>();            // Log messages
```

**LinkedList**
```java
Queue<Task> taskQueue = new LinkedList<>();       // Task queue
Deque<String> history = new LinkedList<>();       // Browser history
```

**HashSet**
```java
Set<String> usernames = new HashSet<>();          // Unique usernames
Set<Integer> visitedIds = new HashSet<>();        // Track visited items
Set<String> tags = new HashSet<>();               // Unique tags
```

**TreeSet**
```java
Set<String> sortedNames = new TreeSet<>();        // Alphabetical names
Set<Integer> leaderboard = new TreeSet<>();       // Sorted scores
```

**HashMap**
```java
Map<String, User> userMap = new HashMap<>();      // User lookup by ID
Map<String, Integer> inventory = new HashMap<>(); // Product quantities
Map<String, String> config = new HashMap<>();     // App settings
```

**TreeMap**
```java
Map<LocalDate, Event> calendar = new TreeMap<>(); // Chronological events
Map<Integer, String> sortedData = new TreeMap<>();// Sorted key-value
```

---

## 🔟 Best Practices ⭐⭐

### ✅ DO:

**1. Program to Interface, Not Implementation**
```java
// Good - flexible, can change implementation
List<Book> books = new ArrayList<>();
Set<String> tags = new HashSet<>();
Map<String, User> users = new HashMap<>();

// Bad - tied to specific implementation
ArrayList<Book> books = new ArrayList<>();  // What if need LinkedList later?
```

**2. Specify Initial Capacity for Large Collections**
```java
// Good - avoid resizing
List<Book> books = new ArrayList<>(10000);
Map<String, User> users = new HashMap<>(10000);

// Default starts small, resizes multiple times (costly!)
```

**3. Use Generics**
```java
// Good - type safe
List<Book> books = new ArrayList<>();
books.add(new Book("1984"));

// Bad - no type safety, requires casting
List books = new ArrayList();  // Raw type!
books.add(new Book("1984"));
Book book = (Book) books.get(0);  // Cast needed
```

**4. Choose Right Collection**
```java
// Need unique? Use Set
Set<String> uniqueEmails = new HashSet<>();

// Need key-value? Use Map
Map<String, Book> catalog = new HashMap<>();

// Need ordered? Use List
List<Task> todoList = new ArrayList<>();
```

### ❌ DON'T:

**1. Don't Mix Collection Types Unnecessarily**
```java
// Bad - using LinkedList when ArrayList would be better
List<Book> books = new LinkedList<>();
for (int i = 0; i < books.size(); i++) {
    Book book = books.get(i);  // O(n) each time! Very slow!
}
```

**2. Don't Use Vector or Hashtable**
```java
// Bad - legacy, synchronized (slow)
Vector<Book> books = new Vector<>();
Hashtable<String, Book> map = new Hashtable<>();

// Good - modern alternatives
List<Book> books = new ArrayList<>();
Map<String, Book> map = new HashMap<>();
// If need thread-safety: ConcurrentHashMap, Collections.synchronizedList()
```

**3. Don't Modify Collection While Iterating**
```java
// Bad - ConcurrentModificationException!
List<String> items = new ArrayList<>();
for (String item : items) {
    if (condition) {
        items.remove(item);  // Error!
    }
}

// Good - use Iterator
Iterator<String> it = items.iterator();
while (it.hasNext()) {
    String item = it.next();
    if (condition) {
        it.remove();  // Safe
    }
}

// Or collect and remove later
List<String> toRemove = new ArrayList<>();
for (String item : items) {
    if (condition) {
        toRemove.add(item);
    }
}
items.removeAll(toRemove);
```

---

## 💡 Quick Reference

### When to Use What

```java
// Default list (90% of cases)
List<Book> books = new ArrayList<>();

// Unique items, fast lookup
Set<String> emails = new HashSet<>();

// Unique items, sorted
Set<String> names = new TreeSet<>();

// Key-value lookup (most common map)
Map<String, Book> catalog = new HashMap<>();

// Sorted key-value
Map<LocalDate, Event> calendar = new TreeMap<>();

// Queue operations
Queue<Task> queue = new LinkedList<>();
```

### Common Operations

```java
// List
list.add(item);
list.get(index);
list.remove(index);
list.contains(item);
list.size();

// Set
set.add(item);
set.remove(item);
set.contains(item);
set.size();

// Map
map.put(key, value);
map.get(key);
map.remove(key);
map.containsKey(key);
map.size();
```

---

## ✅ Self-Check Questions

1. What's the difference between List and Set?
2. When would you use LinkedList over ArrayList?
3. What's the time complexity of get() in ArrayList vs LinkedList?
4. What's the difference between HashSet and TreeSet?
5. Can a Set have duplicate elements?
6. What's the difference between a Set and a Map?
7. What does O(1) mean for HashMap operations?
8. Can HashMap have duplicate keys? Duplicate values?
9. When would you use TreeMap instead of HashMap?
10. Why is ArrayList the default choice for most lists?

---

## 🎯 Key Takeaways

### Must Understand:

1. **ArrayList** - Default list, fast reads, slow inserts (except end)
2. **LinkedList** - Fast inserts/deletes, slow random access
3. **HashSet** - Fast unique items, no order
4. **TreeSet** - Sorted unique items, slower
5. **HashMap** - Fast key-value pairs, most common map
6. **TreeMap** - Sorted key-value pairs

### Think in Real-World Terms:
- ArrayList = Apartment building (numbered units)
- LinkedList = Train carriages (linked chain)
- HashSet = Unique guest list (no duplicates, no order)
- TreeSet = Sorted class roster (alphabetical)
- HashMap = Phone book (name → number)
- TreeMap = Dictionary (alphabetical words)

### Default Choices:
- **List?** → ArrayList
- **Set?** → HashSet
- **Map?** → HashMap
- **Need sorted?** → TreeSet or TreeMap
- **Queue?** → LinkedList

---

**Previous:** Group 2 - Memory Management  
**Next:** Group 4 - Multithreading & Concurrency  
**Study Time:** 3-4 hours | **Review:** 45 minutes

💡 **Practice Tip:** Create a small library system that uses all collections: ArrayList for books, HashSet for unique ISBNs, HashMap for book catalog lookup. Implement search, add, remove operations and measure performance differences!