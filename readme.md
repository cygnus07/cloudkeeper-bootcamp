synchronized(object) {
    // Only one thread here at a time
}
```

### **2. ReentrantLock (Explicit Lock)**
- **What**: More flexible, feature-rich lock
- **When**: Need timeout, try-lock, or fair queuing
- **Advantage**: Can attempt to acquire lock and do something else if busy
- **Think of it**: A bathroom where you can knock, wait 5 minutes, then leave if still occupied

### **3. ReadWriteLock**
- **What**: Allows multiple readers OR one writer
- **When**: Data is read frequently but written rarely (like a blog - many readers, few writers)
- **Concept**: Multiple people can read a notice board simultaneously, but only one person can write on it (and nobody can read while writing)

### **4. Semaphore**
- **What**: Controls access to limited resources
- **When**: You have N resources (e.g., 5 database connections) and need to limit concurrent access
- **Think of it**: A parking lot with 10 spots - once full, cars must wait for someone to leave

---

## **Deadlock - The Eternal Wait**

Imagine two people:
- Person A holds a pen, needs scissors
- Person B holds scissors, needs a pen
- Both refuse to give up what they have until they get what they need
- **Result**: Both wait forever! ⏳

**Prevention Strategy**: Always acquire resources in the same order. If everyone agrees to always take pen first, then scissors, deadlock can't happen.

---

## **Thread Pools - The Smart Way**

### **Problem with Unlimited Threads**
Creating 10,000 threads for 10,000 tasks:
- Each thread consumes memory (1-2 MB)
- 10,000 threads = 10-20 GB just for threads!
- CPU wastes time switching between threads (context switching)

### **Solution: Thread Pool**
Create a **fixed pool of 10 threads** that reuse themselves:
- Task 1 uses Thread A → completes → Thread A becomes free
- Task 2 reuses Thread A → completes → Thread A becomes free again
- **Result**: 10 threads handle 10,000 tasks efficiently

**Key Concept**: It's like having 10 employees handle 1000 customer requests, rather than hiring and firing 1000 employees.

---

## **ExecutorService - Why It's Better**

**Old way (manual threads)**:
- You create threads manually
- You manage their lifecycle
- You handle errors yourself
- You worry about thread cleanup

**ExecutorService way**:
- Framework manages threads for you
- Handles errors gracefully
- Reuses threads automatically
- You just submit tasks and forget

**Think of it**: Like hiring a manager to handle all employee (thread) management instead of doing it yourself.

---

# 🚀 **2. COMPLETABLE FUTURE - CONCEPTS**

## **The Core Problem: Blocking Operations**

Traditional programming is **blocking**:
```
Call API (waits 3 seconds) → blocks everything
Process data (instant)
Save to DB (waits 2 seconds) → blocks everything
Total: 5+ seconds, your app is frozen
```

**Real-world analogy**: You call a restaurant for delivery, then sit by the phone doing NOTHING until they call back. Waste of time!

---

## **CompletableFuture - The Solution**

**Async programming**:
```
Start API call (doesn't wait) → continues immediately
Start DB query (doesn't wait) → continues immediately
Do other work...
When results ready → combine and process
Total: 3 seconds (both ran in parallel), app never freezes
```

**Real-world analogy**: You order food, then watch TV. When doorbell rings, you pause TV and collect food. No wasted time!

---

## **Key Concepts**

### **1. Non-Blocking**
Your code doesn't wait for slow operations. It continues doing other things.

### **2. Callback/Chaining**
You tell Java: "When this task completes, automatically do the next task." Like setting up dominoes - one triggers the next.

### **3. Composability**
You can chain multiple async operations: fetch user → fetch orders → calculate total → send email. Each step starts when previous completes, automatically.

---

## **When to Use CompletableFuture?**

✅ **Use when**:
- Calling external APIs (network calls are slow)
- Database operations
- File I/O operations
- Any operation where you're waiting (I/O bound)
- When you need to do multiple independent tasks in parallel

❌ **Don't use when**:
- Simple, fast operations (overhead not worth it)
- CPU-intensive calculations (threads won't help much)
- Sequential operations where next depends on previous result (no parallelism benefit)

---

## **Mental Models**

### **1. thenApply vs thenCompose**

**thenApply** = Transform the result
- Like: Get number → multiply by 2
- Input: CompletableFuture<5> → Output: CompletableFuture<10>

**thenCompose** = Chain dependent operations
- Like: Get userId → fetch user details (needs another async call)
- Input: CompletableFuture<userId> → Output: CompletableFuture<User>

**Analogy**: 
- thenApply: You have a cake → cut it into slices (simple transformation)
- thenCompose: You have flour → bake a cake (need to do a whole new process)

### **2. thenCombine vs allOf**

**thenCombine** = Combine TWO futures
- Like: Get temperature + get humidity → combine into weather report

**allOf** = Wait for MANY futures
- Like: Wait for 10 API calls to complete, then proceed

---

## **Error Handling Philosophy**

Traditional code:
```
try {
    result = doSomething();
} catch (Exception e) {
    handle error
}
```

CompletableFuture:
```
doSomethingAsync()
    .thenApply(result → transform)
    .exceptionally(error → fallback)