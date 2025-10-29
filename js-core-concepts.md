# 🧠 JavaScript Core Concepts

> **Master Execution Context, Scope, Hoisting & This - Interview Essential**

## 📑 Table of Contents

1. [Global Execution Context & Global Object](#global-execution-context--global-object)
2. [Execution Context in Detail](#execution-context-in-detail)
3. [Scope](#scope)
4. [Hoisting](#hoisting)
5. [This Keyword](#this-keyword)
6. [Interview Questions](#interview-questions)

---

## Global Execution Context & Global Object

### What is Execution Context?

**Execution Context** is the environment where JavaScript code is executed. Think of it as a container that holds:
- Variables
- Functions
- The value of `this`
- Scope chain
- Other information needed to run code

### Types of Execution Context

```
1. Global Execution Context (GEC)
   - Created when script first runs
   - Only ONE per program
   - Creates global object (window in browsers)

2. Function Execution Context (FEC)
   - Created when function is called
   - Can have MANY (one per function call)

3. Eval Execution Context (rare, avoid)
   - Created inside eval() function
```

### Global Execution Context

```javascript
// When this code runs, JS creates:
// 1. Global Object (window in browser, global in Node.js)
// 2. 'this' keyword (points to global object)
// 3. Memory space for variables and functions

var name = 'John';
let age = 30;

function greet() {
    console.log('Hello');
}

// All these become properties of global object (except let/const)
console.log(window.name);    // 'John' (var becomes window property)
console.log(window.age);     // undefined (let doesn't)
console.log(window.greet);   // function
```

### Global Object

**Browser:** `window`  
**Node.js:** `global`  
**Universal (ES11):** `globalThis`

```javascript
// Browser environment
console.log(window);         // Window object
console.log(this);           // Window object (in global scope)
console.log(window === this); // true

// Node.js environment
console.log(global);         // Global object
console.log(globalThis);     // Works in both!

// Global object contains:
window.console;    // console object
window.document;   // document object (browser only)
window.setTimeout; // timer functions
window.alert;      // alert function
// ...and many more built-in APIs
```

### How Global Object is Created

```
Execution Context Creation (2 Phases):

1. CREATION PHASE (Memory Allocation)
   ├── Create global object (window/global)
   ├── Create 'this' (points to global object)
   ├── Setup memory for variables (undefined)
   └── Store function declarations (entire function)

2. EXECUTION PHASE (Code Execution)
   ├── Assign values to variables
   └── Execute functions when called
```

**Example:**

```javascript
console.log(name);    // undefined (not error! hoisted)
console.log(greet);   // function greet() {...}

var name = 'John';

function greet() {
    console.log('Hello');
}

// What happens:
// CREATION PHASE:
// - name: undefined (memory allocated)
// - greet: function greet() {...} (entire function stored)
// 
// EXECUTION PHASE (line by line):
// - console.log(name) → undefined
// - console.log(greet) → function
// - name = 'John' (assignment)
```

### Variable Environment

```javascript
// Global Execution Context
var globalVar = 'I am global';
let globalLet = 'Also global';
const globalConst = 'Still global';

function test() {
    // New Function Execution Context
    var functionVar = 'I am local';
    console.log(globalVar);  // ✅ Can access global
}

console.log(globalVar);      // ✅ 'I am global'
// console.log(functionVar); // ❌ Error: not defined
```

---

## Execution Context in Detail

### Call Stack

JavaScript uses a **Call Stack** to manage execution contexts.

```
CALL STACK (LIFO - Last In, First Out)

│                 │
│   third()       │ ← Currently executing
│   second()      │
│   first()       │
│   Global EC     │ ← Always at bottom
└─────────────────┘
```

**Example:**

```javascript
function first() {
    console.log('First function');
    second();
    console.log('First function end');
}

function second() {
    console.log('Second function');
    third();
    console.log('Second function end');
}

function third() {
    console.log('Third function');
}

first();

// Call Stack visualization:
// 
// Step 1: Global EC
// Step 2: Global EC → first() EC
// Step 3: Global EC → first() EC → second() EC
// Step 4: Global EC → first() EC → second() EC → third() EC
// Step 5: Global EC → first() EC → second() EC (third done)
// Step 6: Global EC → first() EC (second done)
// Step 7: Global EC (first done)

// Output:
// First function
// Second function
// Third function
// Second function end
// First function end
```

### Stack Overflow

```javascript
// Infinite recursion causes stack overflow
function recursive() {
    recursive();  // calls itself forever
}

// recursive();  // ❌ Uncaught RangeError: Maximum call stack size exceeded
```

### Execution Context Components

Each execution context has:

```
Execution Context = {
    1. Variable Environment {
        - Environment Record (variables, functions)
        - Reference to outer environment (scope chain)
    }
    
    2. Lexical Environment {
        - Similar to Variable Environment
        - Used for let, const declarations
    }
    
    3. this binding {
        - Determined at runtime
        - Depends on how function is called
    }
}
```

---

## Scope

**Scope** determines where variables are accessible in your code.

### Types of Scope

```javascript
// 1. GLOBAL SCOPE
var globalVar = 'global';

// 2. FUNCTION SCOPE
function test() {
    var functionVar = 'function scope';
    
    // 3. BLOCK SCOPE (let, const only)
    if (true) {
        let blockVar = 'block scope';
        var notBlockScoped = 'still function scoped';
    }
    
    console.log(notBlockScoped);  // ✅ works (var ignores blocks)
    // console.log(blockVar);     // ❌ Error (let respects blocks)
}

// console.log(functionVar);      // ❌ Error (not in global scope)
```

### Global Scope

Variables accessible everywhere in your code.

```javascript
// Global scope
var globalVar = 'accessible everywhere';
let globalLet = 'also global';
const PI = 3.14159;

function anyFunction() {
    console.log(globalVar);  // ✅ accessible
    console.log(globalLet);  // ✅ accessible
    console.log(PI);         // ✅ accessible
}

// Avoid polluting global scope!
// ❌ BAD
var data = [1, 2, 3];
function helper() { }

// ✅ GOOD - use modules or IIFE
(function() {
    var data = [1, 2, 3];
    function helper() { }
})();
```

### Function Scope

Variables declared inside function are only accessible within that function.

```javascript
function outer() {
    var outerVar = 'I am outer';
    
    function inner() {
        var innerVar = 'I am inner';
        console.log(outerVar);   // ✅ accessible (outer scope)
        console.log(innerVar);   // ✅ accessible (own scope)
    }
    
    inner();
    console.log(outerVar);       // ✅ accessible
    // console.log(innerVar);    // ❌ Error: not defined
}

outer();
// console.log(outerVar);        // ❌ Error: not defined
```

### Block Scope (let, const)

Variables declared with `let` and `const` are block-scoped.

```javascript
// Block = anything between { }

// if block
if (true) {
    let x = 10;
    const y = 20;
    var z = 30;
}
// console.log(x);  // ❌ Error (block scoped)
// console.log(y);  // ❌ Error (block scoped)
console.log(z);     // ✅ 30 (var ignores blocks)

// for loop block
for (let i = 0; i < 3; i++) {
    console.log(i);
}
// console.log(i);  // ❌ Error (i is block scoped)

// Standalone block
{
    let blockVar = 'I am in a block';
    console.log(blockVar);  // ✅ works inside block
}
// console.log(blockVar);   // ❌ Error
```

### Lexical Scope (Static Scope)

Scope is determined by code structure (where functions are written), not where they're called.

```javascript
let globalVar = 'global';

function outer() {
    let outerVar = 'outer';
    
    function inner() {
        let innerVar = 'inner';
        console.log(innerVar);   // own scope
        console.log(outerVar);   // parent scope
        console.log(globalVar);  // global scope
    }
    
    inner();
}

outer();

// Lexical scope chain: inner() → outer() → global
```

**Key Point:** Functions carry their lexical environment with them!

```javascript
function outer() {
    let count = 0;
    
    function inner() {
        count++;
        console.log(count);
    }
    
    return inner;
}

let counter = outer();
counter();  // 1
counter();  // 2
counter();  // 3

// inner() still has access to count even after outer() finished!
// This is a CLOSURE
```

### Scope Chain

When looking for a variable, JavaScript searches:

```
1. Current scope
2. Outer scope
3. Outer's outer scope
4. ...
5. Global scope
6. If not found → ReferenceError
```

**Example:**

```javascript
let a = 'global a';

function first() {
    let b = 'first b';
    
    function second() {
        let c = 'second c';
        
        console.log(a);  // ✅ found in global
        console.log(b);  // ✅ found in first()
        console.log(c);  // ✅ found in second()
        // console.log(d);  // ❌ ReferenceError
    }
    
    second();
}

first();

// Scope chain: second() → first() → global
```

### Scope Visualization

```javascript
// Global Scope {
    var global = 'global';
    
    function outer() {
        // outer() Scope {
            var outerVar = 'outer';
            
            function inner() {
                // inner() Scope {
                    var innerVar = 'inner';
                    
                    // Can access: innerVar, outerVar, global
                // }
            }
            
            // Can access: outerVar, global
            // Cannot access: innerVar
        // }
    }
    
    // Can access: global
    // Cannot access: outerVar, innerVar
// }
```

### Closures (Related to Scope)

A closure is when a function "remembers" its lexical scope even when executed outside that scope.

```javascript
function makeCounter() {
    let count = 0;  // private variable
    
    return function() {
        count++;
        return count;
    };
}

let counter = makeCounter();
console.log(counter());  // 1
console.log(counter());  // 2
console.log(counter());  // 3

// counter() has closure over 'count'
// count is not accessible from outside
```

**Practical Example:**

```javascript
function createGreeter(greeting) {
    return function(name) {
        console.log(greeting + ', ' + name);
    };
}

let sayHi = createGreeter('Hi');
let sayHello = createGreeter('Hello');

sayHi('John');     // Hi, John
sayHello('Jane');  // Hello, Jane

// Each function has its own closure over 'greeting'
```

---

## Hoisting

**Hoisting** is JavaScript's behavior of moving declarations to the top of their scope during compilation.

### What Gets Hoisted?

```javascript
// CODE YOU WRITE:
console.log(myVar);      // undefined
console.log(myLet);      // ReferenceError
console.log(myConst);    // ReferenceError
greet();                 // "Hello" (works!)

var myVar = 'Hello';
let myLet = 'World';
const myConst = 'JS';

function greet() {
    console.log('Hello');
}

// WHAT JAVASCRIPT SEES (conceptually):
var myVar;               // hoisted (initialized as undefined)
function greet() {       // hoisted (entire function)
    console.log('Hello');
}

console.log(myVar);      // undefined
// console.log(myLet);   // ReferenceError (TDZ)
// console.log(myConst); // ReferenceError (TDZ)
greet();                 // "Hello"

myVar = 'Hello';
let myLet = 'World';
const myConst = 'JS';
```

### var Hoisting

```javascript
console.log(x);  // undefined (not error!)
var x = 5;
console.log(x);  // 5

// Hoisted as:
var x;           // declaration hoisted
console.log(x);  // undefined
x = 5;           // assignment stays in place
console.log(x);  // 5
```

### let and const Hoisting

**They ARE hoisted**, but in the **Temporal Dead Zone (TDZ)**.

```javascript
// TDZ starts
console.log(x);  // ReferenceError: Cannot access 'x' before initialization
let x = 5;       // TDZ ends
console.log(x);  // 5

// Same with const
console.log(y);  // ReferenceError
const y = 10;
```

**Temporal Dead Zone:**

```javascript
{
    // TDZ for 'a' starts here
    // console.log(a);  // ReferenceError
    // let a = a + 1;   // ReferenceError
    
    let a = 10;  // TDZ ends, 'a' is initialized
    console.log(a);  // 10
}
```

### Function Hoisting

```javascript
// Function declarations are FULLY hoisted
greet();  // "Hello" - works!

function greet() {
    console.log('Hello');
}

// Function expressions are NOT hoisted
// sayHi();  // TypeError: sayHi is not a function

var sayHi = function() {
    console.log('Hi');
};

// Arrow functions are NOT hoisted
// wave();  // ReferenceError (if let/const) or TypeError (if var)

const wave = () => {
    console.log('Wave');
};
```

### Comparison Table

| Declaration | Hoisted? | Initialized? | TDZ? |
|-------------|----------|--------------|------|
| `var` | ✅ Yes | ✅ undefined | ❌ No |
| `let` | ✅ Yes | ❌ No | ✅ Yes |
| `const` | ✅ Yes | ❌ No | ✅ Yes |
| `function` | ✅ Yes | ✅ Entire function | ❌ No |
| `function expression` | Depends on var/let/const | ❌ No | - |
| `arrow function` | Depends on var/let/const | ❌ No | - |

### Hoisting in Functions

```javascript
function test() {
    console.log(x);  // undefined (var hoisted in function scope)
    
    var x = 10;
    
    if (true) {
        console.log(y);  // ReferenceError (let in TDZ)
        let y = 20;
    }
}

// Hoisted as:
function test() {
    var x;           // hoisted to top of function
    console.log(x);  // undefined
    x = 10;
    
    if (true) {
        // let y; (hoisted but in TDZ)
        console.log(y);
        let y = 20;
    }
}
```

### Common Hoisting Pitfalls

```javascript
// Pitfall 1: Loop with var
for (var i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 1000);
}
// Output: 3, 3, 3 (not 0, 1, 2!)
// Why? Only ONE 'i' exists, shared by all iterations

// Fix with let (block scoped)
for (let i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 1000);
}
// Output: 0, 1, 2 (each iteration has own 'i')

// Pitfall 2: Function expressions
var func = function() {
    console.log('First');
};

func();  // "First"

var func = function() {
    console.log('Second');
};

func();  // "Second"

// Pitfall 3: Conditional function declarations (avoid!)
if (true) {
    function test() {
        console.log('A');
    }
} else {
    function test() {
        console.log('B');
    }
}
// Behavior is inconsistent across browsers!
```

### Best Practices

```javascript
// ✅ GOOD: Declare at the top
function goodExample() {
    let x;
    let y;
    const z = 10;
    
    x = 5;
    y = 15;
    
    console.log(x, y, z);
}

// ✅ GOOD: Use let/const, avoid var
let name = 'John';
const age = 30;

// ❌ BAD: Using before declaration
console.log(value);  // undefined (confusing)
var value = 100;

// ✅ GOOD: Function expressions over declarations in blocks
let greet;
if (condition) {
    greet = function() { console.log('A'); };
} else {
    greet = function() { console.log('B'); };
}
```

---

## This Keyword

**`this`** is a keyword that refers to an object - which object depends on how the function is called.

### This in Different Contexts

```javascript
// 1. GLOBAL CONTEXT
console.log(this);  // Window (browser) or global (Node.js)

function globalFunc() {
    console.log(this);  // Window (non-strict) or undefined (strict)
}

// 2. OBJECT METHOD
let person = {
    name: 'John',
    greet: function() {
        console.log(this);       // person object
        console.log(this.name);  // "John"
    }
};

// 3. CONSTRUCTOR FUNCTION
function Person(name) {
    this.name = name;  // 'this' refers to new instance
}

// 4. ARROW FUNCTION (no own 'this')
let obj = {
    name: 'John',
    regular: function() {
        console.log(this);  // obj
    },
    arrow: () => {
        console.log(this);  // Window (inherits from outer scope)
    }
};

// 5. EVENT HANDLER
button.addEventListener('click', function() {
    console.log(this);  // button element
});
```

### This Rules (Priority Order)

```
1. new binding (constructor)
2. Explicit binding (call, apply, bind)
3. Implicit binding (object method)
4. Default binding (global or undefined)
```

### 1. Default Binding (Global Context)

```javascript
function test() {
    console.log(this);
}

test();  // Window (non-strict) or undefined (strict mode)

// Strict mode
'use strict';
function strictTest() {
    console.log(this);  // undefined
}

strictTest();
```

### 2. Implicit Binding (Object Method)

```javascript
let person = {
    name: 'John',
    greet: function() {
        console.log(this.name);  // 'this' = person
    }
};

person.greet();  // "John"

// Lost context
let greetFunc = person.greet;
greetFunc();  // undefined (this = window, window.name is undefined)

// Nested objects
let obj = {
    name: 'outer',
    inner: {
        name: 'inner',
        greet: function() {
            console.log(this.name);  // 'this' = inner object
        }
    }
};

obj.inner.greet();  // "inner"
```

### 3. Explicit Binding (call, apply, bind)

```javascript
function greet(greeting, punctuation) {
    console.log(greeting + ', ' + this.name + punctuation);
}

let person = { name: 'John' };

// call - arguments passed individually
greet.call(person, 'Hello', '!');  // "Hello, John!"

// apply - arguments passed as array
greet.apply(person, ['Hi', '?']);  // "Hi, John?"

// bind - returns new function with bound 'this'
let boundGreet = greet.bind(person);
boundGreet('Hey', '.');  // "Hey, John."

let boundGreet2 = greet.bind(person, 'Greetings');
boundGreet2('!!!');  // "Greetings, John!!!"
```

**Practical Use:**

```javascript
// Borrowing methods
let person1 = {
    name: 'John',
    greet: function() {
        console.log('Hi, ' + this.name);
    }
};

let person2 = { name: 'Jane' };

person1.greet.call(person2);  // "Hi, Jane"

// Fixing context in callbacks
let obj = {
    name: 'MyObj',
    delayedGreet: function() {
        setTimeout(function() {
            console.log(this.name);  // undefined (lost context)
        }, 1000);
    },
    
    delayedGreetFixed: function() {
        setTimeout(function() {
            console.log(this.name);  // "MyObj"
        }.bind(this), 1000);
    }
};
```

### 4. New Binding (Constructor)

```javascript
function Person(name, age) {
    // 'this' = newly created object
    this.name = name;
    this.age = age;
}

let john = new Person('John', 30);
console.log(john.name);  // "John"
console.log(john.age);   // 30

// What happens with 'new':
// 1. Creates empty object {}
// 2. Sets this = new object
// 3. Links object to constructor's prototype
// 4. Returns this (unless function returns object)
```

### Arrow Functions and This

Arrow functions **DON'T have their own `this`** - they inherit from outer scope.

```javascript
let obj = {
    name: 'John',
    
    regularFunc: function() {
        console.log(this.name);  // "John" (this = obj)
        
        setTimeout(function() {
            console.log(this.name);  // undefined (this = window)
        }, 1000);
    },
    
    arrowFunc: function() {
        console.log(this.name);  // "John" (this = obj)
        
        setTimeout(() => {
            console.log(this.name);  // "John" (inherits this from outer)
        }, 1000);
    }
};

// Arrow function doesn't have own 'this'
let arrowExample = {
    name: 'John',
    greet: () => {
        console.log(this.name);  // undefined (this = global)
    }
};
```

**When to use arrow functions:**

```javascript
// ✅ GOOD: Callbacks
let obj = {
    count: 0,
    increment: function() {
        setInterval(() => {
            this.count++;  // 'this' = obj
            console.log(this.count);
        }, 1000);
    }
};

// ✅ GOOD: Array methods
let person = {
    numbers: [1, 2, 3],
    multiplier: 2,
    
    multiply: function() {
        return this.numbers.map(n => n * this.multiplier);
        // Arrow function inherits 'this' from multiply()
    }
};

// ❌ BAD: Object methods
let bad = {
    name: 'John',
    greet: () => {
        console.log(this.name);  // undefined (arrow has no 'this')
    }
};

// ❌ BAD: Event handlers (when you need element reference)
button.addEventListener('click', () => {
    console.log(this);  // Window, not button!
});
```

### This in Classes (ES6)

```javascript
class Person {
    constructor(name) {
        this.name = name;
    }
    
    greet() {
        console.log(`Hi, I'm ${this.name}`);
    }
    
    delayedGreet() {
        setTimeout(() => {
            console.log(`Hi, I'm ${this.name}`);  // Arrow preserves 'this'
        }, 1000);
    }
}

let john = new Person('John');
john.greet();  // "Hi, I'm John"

// Lost context problem
let greet = john.greet;
greet();  // TypeError: Cannot read property 'name' of undefined

// Fix 1: Bind in constructor
class Person2 {
    constructor(name) {
        this.name = name;
        this.greet = this.greet.bind(this);  // Bind in constructor
    }
    
    greet() {
        console.log(`Hi, I'm ${this.name}`);
    }
}

// Fix 2: Arrow function property (class field)
class Person3 {
    constructor(name) {
        this.name = name;
    }
    
    greet = () => {  // Arrow function field
        console.log(`Hi, I'm ${this.name}`);
    }
}
```

### This in Event Handlers

```javascript
// Regular function - 'this' is element
button.addEventListener('click', function() {
    console.log(this);  // button element
    this.classList.toggle('active');
});

// Arrow function - 'this' is outer scope
button.addEventListener('click', () => {
    console.log(this);  // Window (or component if in React)
});

// Object method as handler
let handler = {
    message: 'Clicked!',
    
    handleClick: function() {
        console.log(this.message);
    }
};

// Lost context
button.addEventListener('click', handler.handleClick);  // undefined

// Fixed with bind
button.addEventListener('click', handler.handleClick.bind(handler));
```

### Common This Pitfalls

```javascript
// Pitfall 1: Lost context in callbacks
let person = {
    name: 'John',
    greet: function() {
        console.log(this.name);
    }
};

setTimeout(person.greet, 1000);  // undefined
// Fix: setTimeout(person.greet.bind(person), 1000);

// Pitfall 2: Method extraction
let obj = {
    name: 'Test',
    method: function() { return this.name; }
};

let extracted = obj.method;
extracted();  // undefined (this = window)

// Pitfall 3: Nested functions
let obj2 = {
    name: 'Outer',
    outer: function() {
        console.log(this.name);  // "Outer"
        
        function inner() {
            console.log(this.name);  // undefined (this = window)
        }
        inner();
    }
};

// Fix 1: Save 'this' reference
let obj3 = {
    name: 'Outer',
    outer: function() {
        let self = this;  // Save reference
        
        function inner() {
            console.log(self.name);  // "Outer"
        }
        inner();
    }
};

// Fix 2: Use arrow function
let obj4 = {
    name: 'Outer',
    outer: function() {
        let inner = () => {
            console.log(this.name);  // "Outer" (inherits this)
        };
        inner();
    }
};
```

### This Summary Table

| Context | This Refers To |
|---------|----------------|
| Global | Window/global/undefined (strict) |
| Function call | Window/undefined (strict) |
| Object method | Object before dot |
| Constructor (new) | Newly created object |
| Arrow function | Inherited from outer scope |
| call/apply/bind | Explicitly specified object |
| Event handler | Element that received event |
| Class method | Instance of class |

---

## Interview Questions

### Execution Context

**Q1: What is the Global Execution Context?**
The default execution context where JavaScript code starts executing. It creates the global object (window/global) and sets up 'this'. Only one GEC exists per program.

**Q2: Explain the phases of execution context creation.**
1. **Creation Phase**: Memory allocated for variables (undefined) and functions (entire function)
2. **Execution Phase**: Code runs line by line, variables get assigned values

**Q3: What is the call stack?**
A data structure that tracks execution contexts. Uses LIFO (Last In, First Out). When a function is called, its context is pushed; when it returns, it's popped.

```javascript
function a() { b(); }
function b() { c(); }
function c() { console.log('Done'); }
a();
// Stack: Global → a() → b() → c() → b() → a() → Global
```

**Q4: What causes a stack overflow?**
Infinite or deep recursion that exceeds the call stack size limit.

```javascript
function recursive() {
    recursive();  // No base case!
}
// recursive(); // RangeError: Maximum call stack size exceeded
```

### Scope

**Q5: What is scope?**
The region of code where a variable is accessible. Types: Global, Function, and Block scope.

**Q6: Difference between function scope and block scope?**
- **Function scope**: Variables accessible throughout function (var)
- **Block scope**: Variables accessible only within {} block (let, const)

**Q7: What is lexical scope?**
Scope determined by where functions are defined, not where they're called. Inner functions can access outer function variables.

**Q8: Explain the scope chain.**
When accessing a variable, JavaScript searches current scope → outer scope → ... → global scope. If not found, throws ReferenceError.

**Q9: What is a closure?**
A function that retains access to its lexical scope even when executed outside that scope.

```javascript
function outer() {
    let count = 0;
    return function inner() {
        return ++count;
    };
}
let counter = outer();
counter(); // 1
counter(); // 2
```

**Q10: Why is var function-scoped?**
Historical design decision in JavaScript. `var` declarations ignore block boundaries but respect function boundaries.

### Hoisting

**Q11: What is hoisting?**
JavaScript's behavior of moving variable and function declarations to the top of their scope during compilation.

**Q12: Are let and const hoisted?**
Yes, but they're in the Temporal Dead Zone (TDZ) until initialization. Accessing them before declaration throws ReferenceError.

**Q13: What is the Temporal Dead Zone?**
The time between entering scope and variable initialization where accessing let/const throws an error.

```javascript
console.log(x); // ReferenceError (TDZ)
let x = 5;
```

**Q14: Difference between function declaration and expression hoisting?**
- **Declaration**: Fully hoisted, can call before declaration
- **Expression**: Only variable hoisted (undefined), cannot call before

```javascript
func1(); // ✅ Works
function func1() {}

func2(); // ❌ TypeError
var func2 = function() {};
```

**Q15: Why does this code output 3, 3, 3?**
```javascript
for (var i = 0; i < 3; i++) {
    setTimeout(() => console.log(i), 1000);
}
```
`var` is function-scoped, so only ONE `i` exists. By the time timeouts run, loop finished and i = 3. Fix: use `let` (block-scoped).

### This Keyword

**Q16: What is 'this' keyword?**
A keyword that refers to an object - which object depends on how the function is called.

**Q17: What are the 4 rules for 'this' binding?**
1. **new binding**: `this` = newly created object
2. **Explicit binding**: `this` = specified with call/apply/bind
3. **Implicit binding**: `this` = object before dot
4. **Default binding**: `this` = global (or undefined in strict mode)

**Q18: How does 'this' work in arrow functions?**
Arrow functions don't have their own `this` - they inherit from the enclosing lexical scope.

```javascript
let obj = {
    name: 'Test',
    regular: function() { console.log(this.name); },  // 'Test'
    arrow: () => { console.log(this.name); }          // undefined
};
```

**Q19: What's the output and why?**
```javascript
let obj = {
    name: 'John',
    greet: function() { console.log(this.name); }
};
setTimeout(obj.greet, 1000);
```
Output: `undefined`  
Why: Method passed to setTimeout loses its context. `this` becomes window/global.  
Fix: `setTimeout(obj.greet.bind(obj), 1000)`

**Q20: Explain call, apply, and bind.**
- **call**: Invokes function with specified `this` and arguments individually
- **apply**: Invokes function with specified `this` and arguments as array
- **bind**: Returns new function with bound `this` (doesn't invoke)

```javascript
func.call(obj, arg1, arg2);
func.apply(obj, [arg1, arg2]);
let bound = func.bind(obj);
```

**Q21: How to fix 'this' in event handlers?**
```javascript
// Problem
button.addEventListener('click', obj.method);  // Lost context

// Fix 1: Arrow function
button.addEventListener('click', () => obj.method());

// Fix 2: Bind
button.addEventListener('click', obj.method.bind(obj));
```

**Q22: What's the output?**
```javascript
let obj = {
    name: 'Outer',
    func: function() {
        console.log(this.name);     // ?
        let inner = () => {
            console.log(this.name);  // ?
        };
        inner();
    }
};
obj.func();
```
Output: "Outer", "Outer"  
Why: Arrow function inherits `this` from `func()`, which has `this` = obj.

**Q23: How does 'this' work in classes?**
In class methods, `this` refers to the instance. Methods need binding if passed as callbacks.

```javascript
class MyClass {
    constructor() {
        this.value = 42;
        // Bind in constructor for callbacks
        this.method = this.method.bind(this);
    }
}
```

**Q24: Why avoid arrow functions as object methods?**
Arrow functions inherit `this` from outer scope, not the object, making them unsuitable for object methods.

```javascript
let obj = {
    name: 'Test',
    greet: () => {
        console.log(this.name);  // undefined (this = global)
    }
};
```

**Q25: Explain 'this' in nested functions.**
```javascript
let obj = {
    name: 'Test',
    outer: function() {
        function inner() {
            console.log(this);  // Window/global (not obj!)
        }
        inner();
    }
};
```
Inner function has default binding (global), not implicit binding. Fix: arrow function or save `this` reference.

---

## Quick Reference

### Execution Context
```
Global EC → Only one
Function EC → One per function call
Creation Phase → Memory allocation
Execution Phase → Code runs
Call Stack → LIFO (Last In, First Out)
```

### Scope
```
Global → Accessible everywhere
Function → Accessible within function
Block → Accessible within {} (let, const)
Lexical → Determined by code structure
Closure → Function + its lexical environment
```

### Hoisting
```
var → Hoisted, initialized undefined
let/const → Hoisted, TDZ until initialization
function declaration → Fully hoisted
function expression → Variable hoisted only
```

### This Binding Priority
```
1. new binding
2. Explicit binding (call/apply/bind)
3. Implicit binding (obj.method)
4. Default binding (global/undefined)

Arrow functions → Inherit 'this'
```

---

**Ready for File 3: Browser APIs & Data Handling!** 🌐

*This will cover LocalStorage, SessionStorage, Cookies, JSON, and AJAX/Fetch - everything you need for working with data in the browser!*