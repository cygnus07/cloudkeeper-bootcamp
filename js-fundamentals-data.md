# 🚀 JavaScript Fundamentals & Data Structures

> **Master JS basics, Arrays, and Objects - Bootcamp & Interview Ready**

## 📑 Table of Contents

1. [Variables](#variables)
2. [Operators](#operators)
3. [Conditionals](#conditionals)
4. [Loops](#loops)
5. [Arrays](#arrays)
6. [Objects](#objects)
7. [Interview Questions](#interview-questions)

---

## Variables

### Declaration Types

```javascript
// var - function scoped, hoisted, can redeclare
var name = 'John';
var name = 'Jane'; // ✅ works

// let - block scoped, hoisted (temporal dead zone), cannot redeclare
let age = 25;
// let age = 30; // ❌ Error: Cannot redeclare

// const - block scoped, must initialize, cannot reassign
const PI = 3.14159;
// PI = 3.14; // ❌ Error: Cannot reassign

// const with objects/arrays - can modify contents
const user = { name: 'John' };
user.name = 'Jane'; // ✅ works (modifying property)
// user = {}; // ❌ Error: Cannot reassign variable
```

### Key Differences

| Feature | var | let | const |
|---------|-----|-----|-------|
| **Scope** | Function | Block | Block |
| **Hoisting** | Yes (undefined) | Yes (TDZ) | Yes (TDZ) |
| **Redeclare** | Yes | No | No |
| **Reassign** | Yes | Yes | No |
| **Must Initialize** | No | No | Yes |

### Data Types

```javascript
// Primitive types
let str = "Hello";           // String
let num = 42;                // Number
let bool = true;             // Boolean
let nothing = null;          // Null
let notDefined = undefined;  // Undefined
let sym = Symbol('id');      // Symbol (ES6)
let bigNum = 123n;           // BigInt (ES11)

// Reference type
let obj = { name: 'John' };  // Object
let arr = [1, 2, 3];         // Array (special object)
let func = function() {};    // Function (special object)

// Type checking
typeof str;        // "string"
typeof num;        // "number"
typeof bool;       // "boolean"
typeof nothing;    // "object" (JavaScript quirk!)
typeof notDefined; // "undefined"
typeof sym;        // "symbol"
typeof obj;        // "object"
typeof arr;        // "object" (not "array"!)
typeof func;       // "function"

// Better array check
Array.isArray(arr); // true
```

---

## Operators

### Arithmetic Operators

```javascript
let a = 10, b = 3;

a + b;  // 13 - Addition
a - b;  // 7  - Subtraction
a * b;  // 30 - Multiplication
a / b;  // 3.333... - Division
a % b;  // 1  - Modulus (remainder)
a ** b; // 1000 - Exponentiation (ES7)

// Increment/Decrement
let x = 5;
x++;    // 6 - Post-increment (returns then increments)
++x;    // 7 - Pre-increment (increments then returns)
x--;    // 6 - Post-decrement
--x;    // 5 - Pre-decrement
```

### Assignment Operators

```javascript
let x = 10;

x += 5;   // x = x + 5  → 15
x -= 3;   // x = x - 3  → 12
x *= 2;   // x = x * 2  → 24
x /= 4;   // x = x / 4  → 6
x %= 4;   // x = x % 4  → 2
x **= 3;  // x = x ** 3 → 8
```

### Comparison Operators

```javascript
// Loose equality (type coercion)
5 == '5';      // true
0 == false;    // true
null == undefined; // true

// Strict equality (no coercion) - USE THIS!
5 === '5';     // false
0 === false;   // false
null === undefined; // false

// Inequality
5 != '5';      // false (loose)
5 !== '5';     // true (strict) - USE THIS!

// Relational
5 > 3;         // true
5 < 3;         // false
5 >= 5;        // true
5 <= 4;        // false
```

### Logical Operators

```javascript
// AND - both must be true
true && true;   // true
true && false;  // false

// OR - at least one must be true
true || false;  // true
false || false; // false

// NOT - inverts boolean
!true;          // false
!false;         // true

// Short-circuit evaluation
let x = null;
let y = x || 'default';  // 'default' (x is falsy)
let z = x && 'value';    // null (x is falsy, returns x)

// Nullish coalescing (ES11) - only null/undefined
let a = 0;
let b = a || 10;   // 10 (0 is falsy)
let c = a ?? 10;   // 0 (0 is not null/undefined)
```

### Ternary Operator

```javascript
// condition ? valueIfTrue : valueIfFalse
let age = 18;
let status = age >= 18 ? 'Adult' : 'Minor';  // 'Adult'

// Nested ternary (avoid if complex)
let score = 85;
let grade = score >= 90 ? 'A' : score >= 80 ? 'B' : score >= 70 ? 'C' : 'F';
```

### Other Operators

```javascript
// String concatenation
'Hello' + ' ' + 'World';  // 'Hello World'

// Template literals (ES6) - RECOMMENDED
let name = 'John';
let greeting = `Hello ${name}!`;  // 'Hello John!'

// typeof
typeof 42;         // "number"

// instanceof
let arr = [1, 2];
arr instanceof Array;  // true

// in
let obj = { name: 'John' };
'name' in obj;     // true

// delete
delete obj.name;   // true (property deleted)

// Spread operator (ES6)
let arr1 = [1, 2, 3];
let arr2 = [...arr1, 4, 5];  // [1, 2, 3, 4, 5]

// Optional chaining (ES11)
let user = { address: { city: 'NYC' } };
user?.address?.city;   // 'NYC'
user?.contact?.phone;  // undefined (no error)
```

---

## Conditionals

### if / else if / else

```javascript
let score = 85;

if (score >= 90) {
    console.log('Grade: A');
} else if (score >= 80) {
    console.log('Grade: B');  // This executes
} else if (score >= 70) {
    console.log('Grade: C');
} else {
    console.log('Grade: F');
}

// Single line (no braces needed)
if (score >= 90) console.log('Excellent!');

// Multiple conditions
let age = 25, hasLicense = true;
if (age >= 18 && hasLicense) {
    console.log('Can drive');
}
```

### switch

```javascript
let day = 'Monday';

switch (day) {
    case 'Monday':
        console.log('Start of work week');
        break;  // Important! Without break, falls through
    case 'Friday':
        console.log('End of work week');
        break;
    case 'Saturday':
    case 'Sunday':  // Multiple cases
        console.log('Weekend!');
        break;
    default:
        console.log('Midweek');
}

// Switch with expressions
let grade = 'B';
switch (true) {
    case grade === 'A':
        console.log('Excellent');
        break;
    case grade === 'B':
        console.log('Good');
        break;
    default:
        console.log('Keep trying');
}
```

---

## Loops

### for Loop

```javascript
// Standard for loop
for (let i = 0; i < 5; i++) {
    console.log(i);  // 0, 1, 2, 3, 4
}

// Multiple variables
for (let i = 0, j = 10; i < 5; i++, j--) {
    console.log(i, j);  // 0 10, 1 9, 2 8, 3 7, 4 6
}

// Infinite loop (careful!)
for (;;) {
    // break; required to exit
}
```

### while Loop

```javascript
let i = 0;
while (i < 5) {
    console.log(i);
    i++;
}

// Infinite loop example
while (true) {
    let input = prompt('Enter "quit" to exit');
    if (input === 'quit') break;
}
```

### do...while Loop

```javascript
// Executes at least once (condition checked after)
let i = 0;
do {
    console.log(i);
    i++;
} while (i < 5);

// Useful for menu systems
let choice;
do {
    choice = prompt('Enter 1-3, or 0 to quit');
} while (choice !== '0');
```

### for...in Loop (Objects)

```javascript
let person = { name: 'John', age: 30, city: 'NYC' };

for (let key in person) {
    console.log(key + ': ' + person[key]);
    // name: John
    // age: 30
    // city: NYC
}

// Works with arrays but not recommended (use for...of)
let arr = ['a', 'b', 'c'];
for (let index in arr) {
    console.log(index);  // 0, 1, 2 (indices, not values!)
}
```

### for...of Loop (Arrays, Iterables)

```javascript
let arr = ['a', 'b', 'c'];

for (let value of arr) {
    console.log(value);  // a, b, c
}

// Works with strings
for (let char of 'Hello') {
    console.log(char);  // H, e, l, l, o
}

// With index using entries()
for (let [index, value] of arr.entries()) {
    console.log(index, value);  // 0 a, 1 b, 2 c
}
```

### Loop Control

```javascript
// break - exit loop
for (let i = 0; i < 10; i++) {
    if (i === 5) break;  // stops at 5
    console.log(i);  // 0, 1, 2, 3, 4
}

// continue - skip to next iteration
for (let i = 0; i < 5; i++) {
    if (i === 2) continue;  // skip 2
    console.log(i);  // 0, 1, 3, 4
}

// Labeled statements (rare)
outer: for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
        if (i === 1 && j === 1) break outer;  // breaks outer loop
        console.log(i, j);
    }
}
```

---

## Arrays

Arrays are ordered collections of values.

### Creating Arrays

```javascript
// Array literal (recommended)
let arr = [1, 2, 3, 4, 5];

// Array constructor
let arr2 = new Array(5);        // [empty × 5] (5 empty slots)
let arr3 = new Array(1, 2, 3);  // [1, 2, 3]

// Mixed types (valid but avoid)
let mixed = [1, 'two', true, null, { name: 'John' }];

// Array.of (ES6)
let arr4 = Array.of(5);         // [5] (not empty array)

// Array.from (ES6)
let arr5 = Array.from('hello'); // ['h', 'e', 'l', 'l', 'o']
let arr6 = Array.from({ length: 3 }, (v, i) => i); // [0, 1, 2]
```

### Accessing Elements

```javascript
let fruits = ['apple', 'banana', 'orange'];

// By index (zero-based)
fruits[0];    // 'apple'
fruits[1];    // 'banana'
fruits[2];    // 'orange'
fruits[3];    // undefined

// Negative indices (not native, use at())
fruits.at(-1);  // 'orange' (ES2022)
fruits.at(-2);  // 'banana'

// First and last
fruits[0];                   // first
fruits[fruits.length - 1];   // last
```

### Array Properties

```javascript
let arr = [1, 2, 3, 4, 5];

arr.length;        // 5
arr.length = 3;    // truncate to [1, 2, 3]
arr.length = 5;    // extend to [1, 2, 3, empty × 2]
```

### Adding/Removing Elements

```javascript
let arr = [1, 2, 3];

// Add to end
arr.push(4);           // [1, 2, 3, 4] returns 4 (new length)
arr.push(5, 6);        // [1, 2, 3, 4, 5, 6]

// Remove from end
arr.pop();             // [1, 2, 3, 4, 5] returns 6 (removed element)

// Add to beginning
arr.unshift(0);        // [0, 1, 2, 3, 4, 5] returns 6 (new length)

// Remove from beginning
arr.shift();           // [1, 2, 3, 4, 5] returns 0 (removed element)

// Add/remove at position (splice)
let arr2 = [1, 2, 5, 6];
arr2.splice(2, 0, 3, 4);  // [1, 2, 3, 4, 5, 6]
// splice(index, deleteCount, ...itemsToAdd)

arr2.splice(1, 2);        // [1, 4, 5, 6] (removed [2, 3])
```

### Array Methods (Non-Mutating)

```javascript
let arr = [1, 2, 3, 4, 5];

// slice - extract portion (doesn't modify original)
arr.slice(1, 3);       // [2, 3] (from index 1 to 3, excluding 3)
arr.slice(2);          // [3, 4, 5] (from index 2 to end)
arr.slice(-2);         // [4, 5] (last 2 elements)

// concat - merge arrays
let arr2 = [6, 7];
arr.concat(arr2);      // [1, 2, 3, 4, 5, 6, 7]
arr.concat(6, 7);      // [1, 2, 3, 4, 5, 6, 7]

// join - array to string
arr.join();            // "1,2,3,4,5"
arr.join(' - ');       // "1 - 2 - 3 - 4 - 5"

// indexOf / lastIndexOf
arr.indexOf(3);        // 2 (first occurrence)
arr.indexOf(10);       // -1 (not found)
arr.lastIndexOf(3);    // 2 (last occurrence)

// includes (ES7)
arr.includes(3);       // true
arr.includes(10);      // false

// toString
arr.toString();        // "1,2,3,4,5"
```

### Array Iteration Methods (IMPORTANT!)

```javascript
let arr = [1, 2, 3, 4, 5];

// forEach - iterate (no return value)
arr.forEach((value, index, array) => {
    console.log(value, index);
});

// map - transform each element (returns new array)
let doubled = arr.map(x => x * 2);  // [2, 4, 6, 8, 10]

// filter - select elements (returns new array)
let evens = arr.filter(x => x % 2 === 0);  // [2, 4]

// find - first element that matches (ES6)
let found = arr.find(x => x > 3);  // 4

// findIndex - index of first match (ES6)
let index = arr.findIndex(x => x > 3);  // 3

// some - test if ANY element passes
let hasEven = arr.some(x => x % 2 === 0);  // true

// every - test if ALL elements pass
let allPositive = arr.every(x => x > 0);  // true

// reduce - accumulate to single value
let sum = arr.reduce((acc, curr) => acc + curr, 0);  // 15
let product = arr.reduce((acc, curr) => acc * curr, 1);  // 120

// reduceRight - reduce from right to left
let result = [1, 2, 3].reduceRight((acc, curr) => acc - curr);  // 0
```

### Sorting & Reversing

```javascript
let arr = [3, 1, 4, 1, 5, 9];

// sort - mutates original!
arr.sort();              // [1, 1, 3, 4, 5, 9]

// Custom sort
arr.sort((a, b) => a - b);  // ascending
arr.sort((a, b) => b - a);  // descending

// Sort strings
let words = ['banana', 'apple', 'cherry'];
words.sort();            // ['apple', 'banana', 'cherry']

// reverse - mutates original!
arr.reverse();           // [9, 5, 4, 3, 1, 1]

// Non-mutating versions (ES2023)
arr.toSorted();          // new sorted array
arr.toReversed();        // new reversed array
```

### Array Searching

```javascript
let users = [
    { id: 1, name: 'John' },
    { id: 2, name: 'Jane' },
    { id: 3, name: 'Bob' }
];

// find
let user = users.find(u => u.id === 2);  // { id: 2, name: 'Jane' }

// findIndex
let index = users.findIndex(u => u.name === 'Bob');  // 2

// filter (multiple results)
let results = users.filter(u => u.name.startsWith('J'));
// [{ id: 1, name: 'John' }, { id: 2, name: 'Jane' }]
```

### Flattening Arrays

```javascript
let nested = [1, [2, 3], [4, [5, 6]]];

// flat (ES10) - flatten by depth
nested.flat();           // [1, 2, 3, 4, [5, 6]] (depth 1)
nested.flat(2);          // [1, 2, 3, 4, 5, 6] (depth 2)
nested.flat(Infinity);   // [1, 2, 3, 4, 5, 6] (all levels)

// flatMap - map then flatten (depth 1)
let arr = [1, 2, 3];
arr.flatMap(x => [x, x * 2]);  // [1, 2, 2, 4, 3, 6]
```

### Spread Operator with Arrays

```javascript
let arr1 = [1, 2, 3];
let arr2 = [4, 5, 6];

// Copy array
let copy = [...arr1];  // [1, 2, 3]

// Merge arrays
let merged = [...arr1, ...arr2];  // [1, 2, 3, 4, 5, 6]

// Add elements
let newArr = [...arr1, 4, 5];  // [1, 2, 3, 4, 5]

// Function arguments
Math.max(...arr1);  // 3

// Convert to array
let str = 'hello';
let chars = [...str];  // ['h', 'e', 'l', 'l', 'o']
```

### Destructuring Arrays

```javascript
let arr = [1, 2, 3, 4, 5];

// Basic destructuring
let [first, second] = arr;  // first = 1, second = 2

// Skip elements
let [a, , c] = arr;  // a = 1, c = 3

// Rest operator
let [x, ...rest] = arr;  // x = 1, rest = [2, 3, 4, 5]

// Default values
let [p, q, r, s, t, u = 10] = arr;  // u = 10 (default)

// Swapping variables
let [one, two] = [1, 2];
[one, two] = [two, one];  // one = 2, two = 1
```

### Array Tricks & Patterns

```javascript
// Remove duplicates
let arr = [1, 2, 2, 3, 3, 4];
let unique = [...new Set(arr)];  // [1, 2, 3, 4]

// Create range
let range = Array.from({ length: 5 }, (_, i) => i + 1);  // [1, 2, 3, 4, 5]

// Fill array
let zeros = new Array(5).fill(0);  // [0, 0, 0, 0, 0]

// Random element
let randomItem = arr[Math.floor(Math.random() * arr.length)];

// Shuffle array (Fisher-Yates)
function shuffle(array) {
    let arr = [...array];
    for (let i = arr.length - 1; i > 0; i--) {
        let j = Math.floor(Math.random() * (i + 1));
        [arr[i], arr[j]] = [arr[j], arr[i]];
    }
    return arr;
}

// Chunk array
function chunk(arr, size) {
    return Array.from({ length: Math.ceil(arr.length / size) }, 
        (_, i) => arr.slice(i * size, i * size + size));
}
chunk([1, 2, 3, 4, 5], 2);  // [[1, 2], [3, 4], [5]]
```

---

## Objects

Objects are collections of key-value pairs.

### Creating Objects

```javascript
// Object literal (most common)
let person = {
    name: 'John',
    age: 30,
    city: 'NYC'
};

// Object constructor
let obj = new Object();
obj.name = 'John';

// Object.create()
let proto = { greet() { console.log('Hi'); } };
let obj2 = Object.create(proto);

// Empty object
let empty = {};
```

### Accessing Properties

```javascript
let person = { 
    name: 'John', 
    age: 30,
    'full name': 'John Doe'  // space in key
};

// Dot notation (recommended for valid identifiers)
person.name;        // 'John'
person.age;         // 30

// Bracket notation (required for special keys)
person['name'];     // 'John'
person['full name']; // 'John Doe'

// Dynamic keys
let key = 'age';
person[key];        // 30

// Optional chaining (ES2020)
let user = { address: { city: 'NYC' } };
user?.address?.city;      // 'NYC'
user?.contact?.phone;     // undefined (no error)
```

### Adding/Modifying Properties

```javascript
let person = { name: 'John' };

// Add new property
person.age = 30;
person['city'] = 'NYC';

// Modify existing
person.name = 'Jane';

// Computed property names (ES6)
let key = 'email';
let obj = {
    [key]: 'john@email.com',
    ['is' + 'Active']: true  // isActive: true
};

// Property shorthand (ES6)
let name = 'John', age = 30;
let person2 = { name, age };  // { name: 'John', age: 30 }
```

### Deleting Properties

```javascript
let person = { name: 'John', age: 30 };

delete person.age;  // true
person.age;         // undefined

// Check if deleted
'age' in person;    // false
```

### Checking Properties

```javascript
let person = { name: 'John', age: 30 };

// in operator
'name' in person;        // true
'salary' in person;      // false

// hasOwnProperty
person.hasOwnProperty('name');  // true

// undefined check (not reliable!)
person.name !== undefined;  // true
```

### Object Methods

```javascript
let person = {
    name: 'John',
    age: 30,
    
    // Method (ES5)
    greet: function() {
        console.log('Hello');
    },
    
    // Method shorthand (ES6)
    sayHi() {
        console.log(`Hi, I'm ${this.name}`);
    },
    
    // Arrow function (no 'this' binding)
    printAge: () => {
        console.log(this.age);  // undefined (or global)
    }
};

person.greet();      // 'Hello'
person.sayHi();      // "Hi, I'm John"
```

### Iterating Objects

```javascript
let person = { name: 'John', age: 30, city: 'NYC' };

// for...in loop
for (let key in person) {
    console.log(key + ': ' + person[key]);
}

// Object.keys() - returns array of keys
Object.keys(person);  // ['name', 'age', 'city']

Object.keys(person).forEach(key => {
    console.log(key, person[key]);
});

// Object.values() - returns array of values (ES8)
Object.values(person);  // ['John', 30, 'NYC']

// Object.entries() - returns array of [key, value] pairs (ES8)
Object.entries(person);  // [['name', 'John'], ['age', 30], ['city', 'NYC']]

Object.entries(person).forEach(([key, value]) => {
    console.log(key, value);
});
```

### Object Methods (Static)

```javascript
let obj1 = { a: 1, b: 2 };
let obj2 = { c: 3, d: 4 };

// Object.assign() - copy properties
let merged = Object.assign({}, obj1, obj2);  // { a: 1, b: 2, c: 3, d: 4 }

// Spread operator (ES9) - cleaner
let merged2 = { ...obj1, ...obj2 };  // { a: 1, b: 2, c: 3, d: 4 }

// Object.freeze() - prevent modifications
let frozen = Object.freeze({ name: 'John' });
frozen.name = 'Jane';  // fails silently (strict mode: error)
frozen.age = 30;       // fails silently

// Object.seal() - prevent adding/removing (can modify existing)
let sealed = Object.seal({ name: 'John' });
sealed.name = 'Jane';  // ✅ works
sealed.age = 30;       // ❌ fails silently
delete sealed.name;    // ❌ fails silently

// Object.keys/values/entries
Object.keys(obj1);     // ['a', 'b']
Object.values(obj1);   // [1, 2]
Object.entries(obj1);  // [['a', 1], ['b', 2]]

// Object.fromEntries() - opposite of entries (ES10)
let entries = [['a', 1], ['b', 2]];
Object.fromEntries(entries);  // { a: 1, b: 2 }

// Object.is() - comparison (like ===, but handles special cases)
Object.is(NaN, NaN);   // true (NaN === NaN is false)
Object.is(+0, -0);     // false (+0 === -0 is true)
```

### Destructuring Objects

```javascript
let person = { name: 'John', age: 30, city: 'NYC' };

// Basic destructuring
let { name, age } = person;  // name = 'John', age = 30

// Rename variables
let { name: fullName, age: years } = person;  
// fullName = 'John', years = 30

// Default values
let { name, country = 'USA' } = person;  // country = 'USA'

// Nested destructuring
let user = {
    name: 'John',
    address: { city: 'NYC', zip: 10001 }
};
let { name, address: { city, zip } } = user;
// name = 'John', city = 'NYC', zip = 10001

// Rest operator
let { name, ...rest } = person;
// name = 'John', rest = { age: 30, city: 'NYC' }

// Function parameters
function greet({ name, age }) {
    console.log(`Hi ${name}, you're ${age}`);
}
greet(person);  // "Hi John, you're 30"
```

### Computed Properties

```javascript
// Dynamic keys
let key = 'name';
let obj = { [key]: 'John' };  // { name: 'John' }

// Expressions as keys
let obj2 = {
    ['prop_' + 1]: 'value1',
    ['prop_' + 2]: 'value2'
};
// { prop_1: 'value1', prop_2: 'value2' }

// Method names
let methodName = 'sayHi';
let obj3 = {
    [methodName]() {
        console.log('Hi');
    }
};
obj3.sayHi();  // 'Hi'
```

### Object Cloning

```javascript
let original = { 
    name: 'John', 
    age: 30,
    address: { city: 'NYC' }
};

// Shallow copy (1 level deep)
let copy1 = { ...original };
let copy2 = Object.assign({}, original);

copy1.name = 'Jane';          // doesn't affect original
copy1.address.city = 'LA';    // DOES affect original! (nested reference)

// Deep copy (all levels)
// Method 1: JSON (limitations: no functions, dates, undefined)
let deepCopy1 = JSON.parse(JSON.stringify(original));

// Method 2: structuredClone (modern browsers)
let deepCopy2 = structuredClone(original);

// Method 3: Manual recursive function
function deepClone(obj) {
    if (obj === null || typeof obj !== 'object') return obj;
    if (obj instanceof Date) return new Date(obj);
    if (obj instanceof Array) return obj.map(item => deepClone(item));
    
    let cloned = {};
    for (let key in obj) {
        if (obj.hasOwnProperty(key)) {
            cloned[key] = deepClone(obj[key]);
        }
    }
    return cloned;
}
```

### Object Comparison

```javascript
let obj1 = { a: 1 };
let obj2 = { a: 1 };
let obj3 = obj1;

// Reference comparison
obj1 === obj2;  // false (different objects)
obj1 === obj3;  // true (same reference)

// Deep equality (manual)
function deepEqual(obj1, obj2) {
    if (obj1 === obj2) return true;
    if (obj1 == null || obj2 == null) return false;
    if (typeof obj1 !== 'object' || typeof obj2 !== 'object') return false;
    
    let keys1 = Object.keys(obj1);
    let keys2 = Object.keys(obj2);
    
    if (keys1.length !== keys2.length) return false;
    
    for (let key of keys1) {
        if (!keys2.includes(key)) return false;
        if (!deepEqual(obj1[key], obj2[key])) return false;
    }
    
    return true;
}

deepEqual(obj1, obj2);  // true
```

### Object Patterns & Tricks

```javascript
// Merge with override
let defaults = { theme: 'light', lang: 'en' };
let userSettings = { theme: 'dark' };
let settings = { ...defaults, ...userSettings };
// { theme: 'dark', lang: 'en' }

// Remove property (immutable)
let { age, ...withoutAge } = person;

// Transform object
let prices = { apple: 1, banana: 2, orange: 3 };
let doubled = Object.fromEntries(
    Object.entries(prices).map(([key, val]) => [key, val * 2])
);
// { apple: 2, banana: 4, orange: 6 }

// Group by
let users = [
    { name: 'John', age: 30 },
    { name: 'Jane', age: 25 },
    { name: 'Bob', age: 30 }
];

let byAge = users.reduce((acc, user) => {
    (acc[user.age] = acc[user.age] || []).push(user);
    return acc;
}, {});
// { 25: [{name: 'Jane', age: 25}], 30: [{name: 'John', age: 30}, {name: 'Bob', age: 30}] }

// Pick properties
function pick(obj, keys) {
    return keys.reduce((acc, key) => {
        if (key in obj) acc[key] = obj[key];
        return acc;
    }, {});
}

let person = { name: 'John', age: 30, city: 'NYC' };
pick(person, ['name', 'city']);  // { name: 'John', city: 'NYC' }

// Omit properties
function omit(obj, keys) {
    return Object.fromEntries(
        Object.entries(obj).filter(([key]) => !keys.includes(key))
    );
}

omit(person, ['age']);  // { name: 'John', city: 'NYC' }
```

---

## Interview Questions

### Variables

**Q1: What's the difference between var, let, and const?**
- `var`: Function scoped, hoisted (initialized as undefined), can redeclare
- `let`: Block scoped, hoisted (TDZ), cannot redeclare, can reassign
- `const`: Block scoped, hoisted (TDZ), cannot redeclare or reassign (but object contents can change)

**Q2: What is Temporal Dead Zone (TDZ)?**
Time between entering scope and variable initialization where accessing let/const throws error.

```javascript
console.log(x);  // ReferenceError: Cannot access 'x' before initialization
let x = 5;
```

**Q3: Can you change a const object?**
Yes, you can modify properties but cannot reassign the variable.

```javascript
const obj = { a: 1 };
obj.a = 2;     // ✅ works
obj = { b: 2 }; // ❌ error
```

### Arrays

**Q4: What's the difference between map and forEach?**
- `map`: Returns new array with transformed elements
- `forEach`: No return value, just iterates

**Q5: How to remove duplicates from an array?**
```javascript
let unique = [...new Set(array)];
```

**Q6: What's the difference between slice and splice?**
- `slice`: Non-mutating, returns portion
- `splice`: Mutating, adds/removes elements

**Q7: How does reduce work?**
Reduces array to single value by accumulating results.

```javascript
let sum = [1, 2, 3].reduce((acc, curr) => acc + curr, 0);  // 6
```

**Q8: Difference between find and filter?**
- `find`: Returns first match (single element or undefined)
- `filter`: Returns all matches (array)

### Objects

**Q9: How to check if property exists?**
```javascript
'key' in obj;              // true/false
obj.hasOwnProperty('key'); // true/false (own properties only)
```

**Q10: How to clone an object?**
- Shallow: `{...obj}` or `Object.assign({}, obj)`
- Deep: `structuredClone(obj)` or `JSON.parse(JSON.stringify(obj))`

**Q11: What's the difference between for...in and for...of?**
- `for...in`: Iterates over object keys (and array indices)
- `for...of`: Iterates over iterable values (arrays, strings, etc.)

**Q12: How to merge two objects?**
```javascript
let merged = { ...obj1, ...obj2 };
// or
let merged = Object.assign({}, obj1, obj2);
```

### General

**Q13: What are falsy values in JavaScript?**
`false`, `0`, `''`, `null`, `undefined`, `NaN`, `0n`

**Q14: What's the difference between == and ===?**
- `==`: Loose equality (type coercion)
- `===`: Strict equality (no coercion) - always use this!

**Q15: What is short-circuit evaluation?**
Logical operators stop evaluating once result is determined.

```javascript
let x = null || 'default';  // 'default' (stops at first truthy)
let y = true && 'value';    // 'value' (continues if left is truthy)
```

**Q16: Explain array destructuring with rest operator.**
```javascript
let [first, ...rest] = [1, 2, 3, 4];
// first = 1, rest = [2, 3, 4]
```

**Q17: What is the spread operator?**
Expands iterables into individual elements.

```javascript
let arr = [1, 2, 3];
let copy = [...arr];
let merged = [...arr1, ...arr2];
```

**Q18: How to convert array-like objects to arrays?**
```javascript
Array.from(arrayLike);
[...arrayLike];
```

**Q19: What's the difference between null and undefined?**
- `null`: Intentional absence of value (assigned)
- `undefined`: Variable declared but not assigned

**Q20: How to check if variable is an array?**
```javascript
Array.isArray(variable);  // best way
```

---

## Quick Reference

### Must-Know Array Methods
```
forEach, map, filter, find, reduce, some, every
push, pop, shift, unshift, splice, slice
includes, indexOf, join, concat
```

### Must-Know Object Methods
```
Object.keys(), Object.values(), Object.entries()
Object.assign(), spread operator
Object.freeze(), Object.seal()
Object.fromEntries()
```

### Common Patterns
```javascript
// Remove duplicates
[...new Set(arr)]

// Flatten array
arr.flat()

// Group by
arr.reduce((acc, item) => {...}, {})

// Clone object (shallow)
{...obj}

// Clone object (deep)
structuredClone(obj)
```

---

**Ready for File 2: Core JavaScript Concepts!** 🚀

*This covers Global Execution Context, Scope, Hoisting, and "this" keyword - the most important interview topics!*