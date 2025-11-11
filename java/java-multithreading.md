# 🧵 Java Multithreading & Concurrency - Understanding Guide

> **Master concurrent programming: Threads, synchronization, and parallel execution**

---

## 🎯 What You'll Understand

- Why we need multiple threads and what problems they solve
- How threads are created and managed
- What goes wrong when threads share data (race conditions, deadlock)
- How to write thread-safe code (synchronization)
- How to manage multiple threads efficiently (Thread Pools)
- Modern approaches to async programming (Callable, Future, CompletableFuture)

---

## 1️⃣ Why Concurrency? The Big Picture ⭐⭐⭐

### 🤔 The Problem: Sequential Execution Is Slow

**Without Multithreading:**

```java
// Sequential - one task at a time
public void processOrders() {
    processOrder1();  // 5 seconds
    processOrder2();  // 5 seconds
    processOrder3();  // 5 seconds
    // Total: 15 seconds
}
```

**Real-World Problem:** Like a **restaurant with ONE cook**:
- Order 1: Cook prepares → Customer waits 5 min
- Order 2: Cook prepares → Customer waits 5 min
- Order 3: Cook prepares → Customer waits 5 min
- Third customer waits 15 minutes!

**Issues with Sequential:**
1. **Waste of resources** - CPU idle while waiting for I/O
2. **Poor responsiveness** - UI freezes during long operations
3. **Slow processing** - can't utilize multiple CPU cores
4. **Bad user experience** - waiting for everything to finish

### 💡 The Solution: Multithreading

**Thread = Independent path of execution within a program**

**With Multithreading:**

```java
// Concurrent - multiple tasks simultaneously
public void processOrders() {
    Thread t1 = new Thread(() -> processOrder1());
    Thread t2 = new Thread(() -> processOrder2());
    Thread t3 = new Thread(() -> processOrder3());
    
    t1.start();
    t2.start();
    t3.start();
    
    // All three orders processed simultaneously!
    // Total: ~5 seconds (instead of 15)
}
```

**Real-World Analogy:** Like a **restaurant with THREE cooks**:
- Cook 1 → Order 1 (5 min)
- Cook 2 → Order 2 (5 min)
- Cook 3 → Order 3 (5 min)
- All done in 5 minutes! Third customer doesn't wait!

**Benefits:**
1. **Better resource utilization** - use multiple CPU cores
2. **Improved responsiveness** - UI stays responsive
3. **Faster processing** - parallel execution
4. **Better user experience** - no waiting

### 🌍 Real-World Examples

**1. Web Server**
```java
// Handle multiple client requests simultaneously
for (Client client : clients) {
    new Thread(() -> handleRequest(client)).start();
    // Each client gets own thread - no waiting!
}
```

**2. GUI Application**
```java
// UI thread stays responsive
button.onClick(() -> {
    new Thread(() -> {
        // Long operation in background
        downloadFile();
    }).start();
    // UI doesn't freeze!
});
```

**3. Data Processing**
```java
// Process large dataset in parallel
List<Data> chunks = splitData(largeDataset, 4);
for (Data chunk : chunks) {
    new Thread(() -> processChunk(chunk)).start();
}
// 4x faster with 4 cores!
```

---

## 2️⃣ Thread Basics ⭐⭐⭐

### 🔍 What is a Thread?

**Thread = Lightweight process, independent execution path**

**Process vs Thread:**

```
Process (Program)
└── Thread 1 (main)
    ├── Thread 2
    ├── Thread 3
    └── Thread 4

Process = Heavy (separate memory space)
Thread = Light (shared memory within process)
```

**Real-World Analogy:** Think of a **company**:
- **Process** = Entire company (separate building, resources)
- **Thread** = Employees in company (share office, resources)
- Threads share company resources (memory, files)
- Threads work independently on different tasks

### 📊 Thread vs Process

| Feature | Process | Thread |
|---------|---------|--------|
| **Memory** | Separate memory space | Shared memory |
| **Communication** | IPC (complex) | Direct (simple) |
| **Creation** | Expensive | Cheap |
| **Context Switch** | Slow | Fast |
| **Independence** | Fully independent | Share process resources |
| **Example** | Different programs | Parts of same program |

---

## 3️⃣ Thread Lifecycle ⭐⭐

### 🔄 Thread States

```
NEW → RUNNABLE ⇄ RUNNING ⇄ BLOCKED/WAITING → TERMINATED
```

**1. NEW**
- Thread created but not started
- `Thread t = new Thread()`

**2. RUNNABLE**
- Thread ready to run
- Waiting for CPU time
- `t.start()` called

**3. RUNNING**
- Thread executing
- Has CPU time

**4. BLOCKED/WAITING**
- Thread waiting for resource
- Sleeping or waiting for I/O
- `sleep()`, `wait()`, `join()`

**5. TERMINATED**
- Thread finished execution
- Cannot be restarted

**Real-World Analogy:** Think of **restaurant order**:
- **NEW:** Order written but not sent to kitchen
- **RUNNABLE:** Order in kitchen queue
- **RUNNING:** Cook actively preparing order
- **BLOCKED:** Cook waiting for ingredient from storage
- **TERMINATED:** Order completed and served

### 📊 State Transitions

```
Thread t = new Thread(task);     // NEW

t.start();                        // NEW → RUNNABLE
                                  // RUNNABLE → RUNNING (scheduler)

Thread.sleep(1000);               // RUNNING → BLOCKED
                                  // After 1s: BLOCKED → RUNNABLE

task completes                    // RUNNING → TERMINATED
```

---

## 4️⃣ Creating Threads ⭐⭐⭐

### 🔧 Method 1: Extend Thread Class

```java
// Extend Thread class
public class DownloadThread extends Thread {
    private String filename;
    
    public DownloadThread(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void run() {
        // This code runs in separate thread
        System.out.println("Downloading " + filename + "...");
        // Simulate download
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(filename + " downloaded!");
    }
}

// Usage
DownloadThread t1 = new DownloadThread("file1.pdf");
DownloadThread t2 = new DownloadThread("file2.pdf");

t1.start();  // Start thread 1
t2.start();  // Start thread 2

// Both download simultaneously!
```

**Problem with this approach:**
- Java doesn't support multiple inheritance
- If your class already extends something, can't extend Thread
- Not flexible!

---

### 🔧 Method 2: Implement Runnable Interface (Better!)

```java
// Implement Runnable interface
public class DownloadTask implements Runnable {
    private String filename;
    
    public DownloadTask(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void run() {
        System.out.println("Downloading " + filename + "...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(filename + " downloaded!");
    }
}

// Usage
Thread t1 = new Thread(new DownloadTask("file1.pdf"));
Thread t2 = new Thread(new DownloadTask("file2.pdf"));

t1.start();
t2.start();
```

**Why Runnable is better:**
- Can implement multiple interfaces
- Separates task from thread execution
- More flexible and maintainable
- **Use this approach!**

---

### 🔧 Method 3: Lambda Expression (Modern, Best!)

```java
// Lambda - cleanest syntax!
Thread t1 = new Thread(() -> {
    System.out.println("Downloading file1...");
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("file1 downloaded!");
});

Thread t2 = new Thread(() -> {
    System.out.println("Downloading file2...");
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("file2 downloaded!");
});

t1.start();
t2.start();
```

**Comparison:**

| Method | Pros | Cons | When to use |
|--------|------|------|-------------|
| **Extend Thread** | Simple | No multiple inheritance | Simple cases only |
| **Implement Runnable** | Flexible, reusable | More verbose | Most cases |
| **Lambda** | Concise, clean | Limited to simple tasks | Modern code |

---

## 5️⃣ Thread Methods ⭐⭐

### 🔧 sleep() - Pause Thread

```java
// Thread.sleep(milliseconds)
Thread t = new Thread(() -> {
    System.out.println("Starting...");
    
    try {
        Thread.sleep(2000);  // Sleep 2 seconds
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    System.out.println("Done after 2 seconds!");
});

t.start();
```

**Real-World Analogy:** Like a **cook taking a break**:
- Cook starts preparing
- Takes 5-minute break (sleep)
- Resumes work after break

**Use cases:**
- Simulate delays
- Polling with intervals
- Rate limiting

---

### 🔧 join() - Wait for Thread to Finish

```java
Thread t1 = new Thread(() -> {
    System.out.println("Task 1 starting...");
    try { Thread.sleep(2000); } catch (InterruptedException e) {}
    System.out.println("Task 1 done!");
});

Thread t2 = new Thread(() -> {
    System.out.println("Task 2 starting...");
    try { Thread.sleep(1000); } catch (InterruptedException e) {}
    System.out.println("Task 2 done!");
});

t1.start();
t2.start();

try {
    t1.join();  // Wait for t1 to finish
    t2.join();  // Wait for t2 to finish
} catch (InterruptedException e) {
    e.printStackTrace();
}

System.out.println("All tasks completed!");
```

**Real-World Analogy:** Like **waiting for all cooks to finish**:
- Three cooks preparing dishes
- Can't serve until ALL dishes ready
- Wait (join) for each cook to finish

**Output:**
```
Task 1 starting...
Task 2 starting...
Task 2 done!  (after 1 sec)
Task 1 done!  (after 2 sec)
All tasks completed!  (after both finish)
```

---

### 🔧 Thread Priority & Daemon Threads

```java
// Thread Priority (1-10, default 5)
Thread t1 = new Thread(task1);
t1.setPriority(Thread.MAX_PRIORITY);  // 10 - high priority
t1.start();

Thread t2 = new Thread(task2);
t2.setPriority(Thread.MIN_PRIORITY);  // 1 - low priority
t2.start();

// Daemon Threads (background threads)
Thread daemon = new Thread(() -> {
    while (true) {
        // Background task (logging, monitoring)
    }
});
daemon.setDaemon(true);  // Mark as daemon
daemon.start();

// When main thread ends, daemon threads auto-terminate!
```

**Real-World Analogy:** 
- **Priority:** VIP customer vs regular customer in restaurant
- **Daemon:** Janitor cleaning in background (stops when restaurant closes)

---

## 6️⃣ Thread Safety Problems ⭐⭐⭐

### 🤔 The Problem: Race Conditions

**Race Condition = Multiple threads accessing shared data simultaneously**

```java
// Shared counter - UNSAFE!
public class Counter {
    private int count = 0;
    
    public void increment() {
        count++;  // NOT atomic! Three steps internally:
                  // 1. Read count
                  // 2. Add 1
                  // 3. Write count
    }
    
    public int getCount() {
        return count;
    }
}

// Two threads incrementing
Counter counter = new Counter();

Thread t1 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        counter.increment();
    }
});

Thread t2 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        counter.increment();
    }
});

t1.start();
t2.start();

t1.join();
t2.join();

System.out.println(counter.getCount());  // Expected: 2000
                                         // Actual: 1850? 1920? (varies!)
```

**What went wrong?**

```
count++ is actually 3 operations:

Thread 1              Thread 2
Read count (0)
                      Read count (0)
Add 1 (1)
                      Add 1 (1)
Write (1)
                      Write (1)  ← Overwrites Thread 1's write!

Result: count = 1 (should be 2!)
```

**Real-World Analogy:** Think of **bank account**:
- You check balance: $100
- Wife checks balance: $100
- You deposit $50: $150
- Wife deposits $50: $150 (should be $200!)
- **Lost update!**

---

### 🚨 Common Thread Safety Problems

**1. Race Condition**
- Multiple threads modify shared data
- Unpredictable results

**2. Deadlock**
- Two threads waiting for each other
- Both stuck forever!

```java
// Deadlock example
Object lock1 = new Object();
Object lock2 = new Object();

Thread t1 = new Thread(() -> {
    synchronized(lock1) {
        System.out.println("T1: Locked lock1");
        
        synchronized(lock2) {
            System.out.println("T1: Locked lock2");
        }
    }
});

Thread t2 = new Thread(() -> {
    synchronized(lock2) {
        System.out.println("T2: Locked lock2");
        
        synchronized(lock1) {  // Waiting for T1 to release lock1
            System.out.println("T2: Locked lock1");
        }
    }
});

t1.start();
t2.start();

// Deadlock! T1 has lock1, waiting for lock2
//           T2 has lock2, waiting for lock1
```

**Real-World Analogy:** Two people at **narrow doorway**:
- Person A: Enters halfway, waits for Person B to move
- Person B: Enters halfway, waits for Person A to move
- **Both stuck!**

**3. Starvation**
- Thread never gets CPU time
- Low priority or unlucky scheduling

---

## 7️⃣ Synchronization ⭐⭐⭐

### 🔧 Solution 1: synchronized Keyword

**synchronized = Only one thread at a time can execute this code**

```java
// Thread-safe counter
public class Counter {
    private int count = 0;
    
    // Synchronized method - only one thread at a time
    public synchronized void increment() {
        count++;  // Safe now!
    }
    
    public synchronized int getCount() {
        return count;
    }
}

// Now works correctly!
Counter counter = new Counter();

Thread t1 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        counter.increment();
    }
});

Thread t2 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        counter.increment();
    }
});

t1.start();
t2.start();
t1.join();
t2.join();

System.out.println(counter.getCount());  // Always 2000!
```

**Real-World Analogy:** Like a **single-person bathroom**:
- Lock door when entering
- Only one person at a time
- Others wait outside
- Unlock when done

### 🔧 Synchronized Block (More Flexible)

```java
public class BankAccount {
    private double balance = 0;
    private final Object lock = new Object();
    
    public void deposit(double amount) {
        synchronized(lock) {  // Synchronize only critical section
            balance += amount;
        }
        // Other code here (not synchronized)
    }
    
    public void withdraw(double amount) {
        synchronized(lock) {
            if (balance >= amount) {
                balance -= amount;
            }
        }
    }
}
```

**Why use synchronized block?**
- More granular control
- Synchronize only critical section
- Better performance

---

### 📊 Synchronization Comparison

| Approach | Pros | Cons |
|----------|------|------|
| **synchronized method** | Simple, clean | Entire method locked |
| **synchronized block** | Flexible, better performance | More verbose |
| **Locks (ReentrantLock)** | Advanced features | Complex |

---

### ⚖️ Synchronization Best Practices

**✅ DO:**

1. **Synchronize Only Critical Sections**
```java
public void processData() {
    // Not synchronized - can run in parallel
    prepareData();
    
    synchronized(lock) {
        // Only this needs protection
        updateSharedData();
    }
    
    // Not synchronized
    logResults();
}
```

2. **Use Same Lock for Related Data**
```java
private final Object lock = new Object();

public void updateBoth() {
    synchronized(lock) {
        field1 = newValue1;
        field2 = newValue2;  // Same lock for consistency
    }
}
```

**❌ DON'T:**

1. **Don't Over-Synchronize**
```java
// Bad - synchronizing everything
public synchronized void doSimpleRead() {
    return someField;  // No need to synchronize read!
}
```

2. **Don't Hold Locks Too Long**
```java
// Bad - holding lock during slow I/O
public synchronized void saveToDatabase() {
    database.save(data);  // Slow! Blocks all threads!
}

// Good - release lock before I/O
public void saveToDatabase() {
    Data copy;
    synchronized(lock) {
        copy = data.clone();  // Quick copy
    }
    database.save(copy);  // Slow I/O without lock
}
```

---

## 8️⃣ Thread Pools ⭐⭐⭐

### 🤔 The Problem: Creating Too Many Threads

```java
// Creating new thread for each request - BAD!
for (int i = 0; i < 10000; i++) {
    new Thread(() -> handleRequest()).start();
}

// Problems:
// 1. Thread creation is expensive
// 2. Too many threads = memory exhaustion
// 3. Context switching overhead
// 4. No control over resources
```

**Real-World Problem:** Like **hiring new employee for each order**:
- Customer 1 arrives → Hire cook 1
- Customer 2 arrives → Hire cook 2
- ... (10,000 customers = 10,000 cooks!)
- **Restaurant overrun! Chaos!**

### 💡 The Solution: Thread Pool

**Thread Pool = Reusable pool of worker threads**

**Real-World Analogy:** Like **restaurant with permanent staff**:
- Restaurant has 10 cooks (thread pool)
- Orders come in (tasks)
- Cooks process orders (threads execute tasks)
- Cooks don't quit after each order (reused!)
- Queue for waiting orders

```java
// Create thread pool with 10 threads
ExecutorService executor = Executors.newFixedThreadPool(10);

// Submit tasks
for (int i = 0; i < 10000; i++) {
    executor.submit(() -> {
        handleRequest();
    });
}

// Shutdown when done
executor.shutdown();
```

**Benefits:**
1. **Reuse threads** - no creation overhead
2. **Controlled resources** - fixed number of threads
3. **Queue management** - automatic task queuing
4. **Better performance** - less context switching

---

### 🔧 Types of Thread Pools

**1. Fixed Thread Pool**
```java
// Fixed number of threads
ExecutorService executor = Executors.newFixedThreadPool(10);

// Use case: Known number of concurrent operations
// Example: Web server with max 100 connections
```

**2. Cached Thread Pool**
```java
// Creates threads as needed, reuses idle threads
ExecutorService executor = Executors.newCachedThreadPool();

// Use case: Many short-lived tasks
// Example: Quick async operations
```

**3. Single Thread Executor**
```java
// Only one thread
ExecutorService executor = Executors.newSingleThreadExecutor();

// Use case: Sequential execution with queuing
// Example: Processing events in order
```

**4. Scheduled Thread Pool**
```java
// Schedule tasks with delays
ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

// Run after delay
executor.schedule(() -> {
    System.out.println("Runs after 5 seconds");
}, 5, TimeUnit.SECONDS);

// Run periodically
executor.scheduleAtFixedRate(() -> {
    System.out.println("Runs every 10 seconds");
}, 0, 10, TimeUnit.SECONDS);

// Use case: Periodic tasks (monitoring, cleanup)
```

---

### 🎯 Thread Pool Example

```java
public class LibrarySystem {
    // Thread pool for processing book requests
    private ExecutorService executor = Executors.newFixedThreadPool(5);
    
    public void processBorrowRequests(List<BorrowRequest> requests) {
        for (BorrowRequest request : requests) {
            // Submit task to thread pool
            executor.submit(() -> {
                System.out.println("Processing: " + request.getUserId());
                
                // Check availability
                if (isBookAvailable(request.getBookId())) {
                    // Process borrow
                    processBorrow(request);
                    System.out.println("Borrowed: " + request.getBookId());
                } else {
                    System.out.println("Unavailable: " + request.getBookId());
                }
            });
        }
    }
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
```

---

### 📊 Executor Comparison

| Type | Threads | Use Case |
|------|---------|----------|
| **FixedThreadPool** | Fixed number | CPU-bound tasks, controlled concurrency |
| **CachedThreadPool** | Grows as needed | Many short tasks |
| **SingleThreadExecutor** | 1 | Sequential processing |
| **ScheduledThreadPool** | Fixed number | Periodic/delayed tasks |

---

## 9️⃣ Callable & Future ⭐⭐

### 🤔 The Problem: Runnable Can't Return Value

```java
// Runnable - no return value!
Runnable task = () -> {
    int result = compute();
    // How to return result? ❌
};

// Can't get result!
```

**Real-World Problem:** Like **sending cook to buy ingredients**:
- Cook goes to store (thread)
- Buys ingredients
- **How do you get ingredients back?**

### 💡 The Solution: Callable & Future

**Callable = Like Runnable but can return value**
**Future = Placeholder for future result**

```java
// Callable - CAN return value!
Callable<Integer> task = () -> {
    Thread.sleep(2000);  // Simulate work
    return 42;  // Return result!
};

// Submit and get Future
ExecutorService executor = Executors.newFixedThreadPool(1);
Future<Integer> future = executor.submit(task);

// Do other work while task runs...
System.out.println("Task submitted, doing other work...");

// Get result (blocks if not ready)
try {
    Integer result = future.get();  // Blocks until result ready
    System.out.println("Result: " + result);
} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
}

executor.shutdown();
```

**Real-World Analogy:** Like **ordering pizza**:
- Order pizza (submit task)
- Get receipt/ticket (Future)
- Do other things while pizza being made
- When ready, use ticket to get pizza (future.get())

---

### 🔧 Future Methods

```java
Future<String> future = executor.submit(task);

// Check if done
if (future.isDone()) {
    String result = future.get();
}

// Cancel task
future.cancel(true);  // true = interrupt if running

// Check if cancelled
if (future.isCancelled()) {
    System.out.println("Task was cancelled");
}

// Get with timeout
try {
    String result = future.get(5, TimeUnit.SECONDS);
} catch (TimeoutException e) {
    System.out.println("Task taking too long!");
    future.cancel(true);
}
```

---

### 🎯 Practical Example: Parallel Data Processing

```java
public class DataProcessor {
    private ExecutorService executor = Executors.newFixedThreadPool(4);
    
    public List<ProcessedData> processAll(List<RawData> dataList) {
        List<Future<ProcessedData>> futures = new ArrayList<>();
        
        // Submit all tasks
        for (RawData data : dataList) {
            Callable<ProcessedData> task = () -> {
                // Expensive processing
                return expensiveProcess(data);
            };
            
            Future<ProcessedData> future = executor.submit(task);
            futures.add(future);
        }
        
        // Collect all results
        List<ProcessedData> results = new ArrayList<>();
        for (Future<ProcessedData> future : futures) {
            try {
                ProcessedData result = future.get();  // Wait for result
                results.add(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        return results;
    }
}
```

---

## 🔟 CompletableFuture ⭐⭐

### 🤔 The Problem: Future is Limited

```java
Future<String> future = executor.submit(task);

// Problems:
// 1. Blocking - get() blocks until done
// 2. No chaining - can't chain operations
// 3. No combining - can't combine multiple futures
// 4. Manual exception handling
```

### 💡 The Solution: CompletableFuture (Java 8+)

**CompletableFuture = Future with superpowers!**

**Features:**
- Non-blocking
- Chainable operations
- Combine multiple futures
- Better exception handling
- Callbacks

```java
// Simple async operation
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Runs in separate thread
    return "Hello";
});

// Non-blocking - add callback
future.thenAccept(result -> {
    System.out.println("Result: " + result);
});

// Continue with other work - not blocked!
```

---

### 🔧 CompletableFuture Chaining

```java
CompletableFuture.supplyAsync(() -> {
    // Step 1: Fetch user
    System.out.println("Fetching user...");
    return "User123";
    
}).thenApply(userId -> {
    // Step 2: Fetch user details
    System.out.println("Fetching details for: " + userId);
    return new UserDetails(userId, "John", "john@email.com");
    
}).thenApply(details -> {
    // Step 3: Process details
    System.out.println("Processing details for: " + details.getName());
    return details.getEmail();
    
}).thenAccept(email -> {
    // Step 4: Send email
    System.out.println("Sending email to: " + email);
    
}).exceptionally(ex -> {
    // Handle any exception in the chain
    System.out.println("Error: " + ex.getMessage());
    return null;
});

// Main thread continues - not blocked!
```

**Real-World Analogy:** Like **restaurant order pipeline**:
1. Take order (supplyAsync)
2. Prepare food (thenApply)
3. Add garnish (thenApply)
4. Serve (thenAccept)
5. Handle issues (exceptionally)

Each step happens when previous completes!

---

### 🔧 Combining Multiple CompletableFutures

```java
// Run multiple tasks in parallel
CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
    return "Result1";
});

CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
    return "Result2";
});

CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
    return "Result3";
});

// Wait for ALL to complete
CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);

allTasks.thenRun(() -> {
    // All tasks done!
    try {
        String r1 = task1.get();
        String r2 = task2.get();
        String r3 = task3.get();
        System.out.println("All results: " + r1 + ", " + r2 + ", " + r3);
    } catch (Exception e) {
        e.printStackTrace();
    }
});

// Wait for ANY to complete
CompletableFuture<Object> anyTask = CompletableFuture.anyOf(task1, task2, task3);

anyTask.thenAccept(result -> {
    System.out.println("First to finish: " + result);
});
```

---

### 🎯 Real-World Example: Library Book Search

```java
public class LibrarySearchService {
    
    public CompletableFuture<SearchResults> searchBooks(String query) {
        return CompletableFuture.supplyAsync(() -> {
            // Search local catalog
            return searchLocalCatalog(query);
            
        }).thenCompose(localResults -> {
            // If not found locally, search partner libraries
            if (localResults.isEmpty()) {
                return searchPartnerLibraries(query);
            }
            return CompletableFuture.completedFuture(localResults);
            
        }).thenApply(results -> {
            // Enhance results with availability info
            return addAvailabilityInfo(results);
            
        }).thenApply(results -> {
            // Sort by relevance
            return sortByRelevance(results);
            
        }).exceptionally(ex -> {
            // Handle errors
            System.err.println("Search failed: " + ex.getMessage());
            return SearchResults.empty();
        });
    }
    
    // Parallel search across multiple libraries
    public CompletableFuture<SearchResults> searchAllLibraries(String query) {
        CompletableFuture<List<Book>> library1 = CompletableFuture.supplyAsync(
            () -> searchLibrary("Library1", query)
        );
        
        CompletableFuture<List<Book>> library2 = CompletableFuture.supplyAsync(
            () -> searchLibrary("Library2", query)
        );
        
        CompletableFuture<List<Book>> library3 = CompletableFuture.supplyAsync(
            () -> searchLibrary("Library3", query)
        );
        
        // Combine all results
        return CompletableFuture.allOf(library1, library2, library3)
            .thenApply(v -> {
                List<Book> allBooks = new ArrayList<>();
                allBooks.addAll(library1.join());
                allBooks.addAll(library2.join());
                allBooks.addAll(library3.join());
                return new SearchResults(allBooks);
            });
    }
}
```

---

## 💡 Quick Reference

### Creating Threads
```java
// Method 1: Thread class
class MyThread extends Thread {
    public void run() { /* task */ }
}

// Method 2: Runnable (better)
Thread t = new Thread(() -> {
    // task
});
t.start();

// Method 3: ExecutorService (best)
ExecutorService executor = Executors.newFixedThreadPool(10);
executor.submit(() -> {
    // task
});
```

### Thread Safety
```java
// Synchronized method
public synchronized void method() {
    // thread-safe
}

// Synchronized block
synchronized(lock) {
    // thread-safe section
}
```

### Thread Pool
```java
ExecutorService executor = Executors.newFixedThreadPool(10);
executor.submit(task);
executor.shutdown();
```

### Callable & Future
```java
Callable<Integer> task = () -> 42;
Future<Integer> future = executor.submit(task);
Integer result = future.get();  // blocking
```

### CompletableFuture
```java
CompletableFuture.supplyAsync(() -> "data")
    .thenApply(data -> process(data))
    .thenAccept(result -> use(result))
    .exceptionally(ex -> handleError(ex));
```

---

## ✅ Self-Check Questions

1. What's the difference between process and thread?
2. What are the thread states?
3. What's better: extending Thread or implementing Runnable? Why?
4. What's a race condition?
5. What's deadlock and how to prevent it?
6. What does synchronized keyword do?
7. Why use thread pools instead of creating threads manually?
8. What's the difference between Runnable and Callable?
9. What's Future used for?
10. How is CompletableFuture better than Future?

---

## 🎯 Key Takeaways

### Must Understand:

1. **Threads** - Independent execution paths for parallel work
2. **Thread Lifecycle** - NEW → RUNNABLE → RUNNING → TERMINATED
3. **Race Conditions** - Multiple threads accessing shared data unsafely
4. **Synchronization** - synchronized keyword for thread safety
5. **Thread Pools** - Reusable threads for better performance
6. **Callable/Future** - Tasks that return values
7. **CompletableFuture** - Modern async programming

### Think in Real-World Terms:
- Thread = Cook in kitchen (independent worker)
- Race Condition = Bank account update problem
- Deadlock = Two people stuck at doorway
- Synchronization = Single-person bathroom lock
- Thread Pool = Restaurant permanent staff
- Future = Pizza receipt (get later)

### Best Practices:
- Use Runnable/Lambda over extending Thread
- Synchronize only critical sections
- Use thread pools (don't create threads manually)
- Prefer CompletableFuture for modern async code
- Always handle InterruptedException properly

### Common Pitfalls:
- Forgetting to call start() (calling run() directly)
- Not synchronizing shared data
- Over-synchronizing (performance killer)
- Creating too many threads (use pools!)
- Blocking UI thread with long operations

---

**Previous:** Group 3 - Collections Framework  
**Study Time:** 4-5 hours | **Review:** 1 hour

💡 **Practice Tip:** Create a multi-threaded download manager that downloads multiple files simultaneously using thread pools. Add progress tracking and cancellation. This covers threads, synchronization, thread pools, and Callable/Future!

---

## 🎉 Congratulations!

You've completed all Java fundamentals! You now understand:
- ✅ Java Basics & OOP
- ✅ Memory Management
- ✅ Collections Framework
- ✅ Multithreading & Concurrency

**Ready for your interview on November 12th!** 🚀