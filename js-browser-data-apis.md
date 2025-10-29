# 🌐 JavaScript Browser APIs & Data Handling

> **Master Storage APIs, JSON, and AJAX - Production Ready**

## 📑 Table of Contents

1. [Web Storage API](#web-storage-api)
2. [LocalStorage](#localstorage)
3. [SessionStorage](#sessionstorage)
4. [Cookies](#cookies)
5. [Storage Comparison](#storage-comparison)
6. [JSON](#json)
7. [AJAX & Fetch API](#ajax--fetch-api)
8. [Interview Questions](#interview-questions)

---

## Web Storage API

The **Web Storage API** provides mechanisms for storing key-value pairs in the browser.

### Two Types

```javascript
// 1. localStorage - persists until manually cleared
localStorage.setItem('key', 'value');

// 2. sessionStorage - persists until tab/window closed
sessionStorage.setItem('key', 'value');
```

### Key Characteristics

| Feature | Value |
|---------|-------|
| **Storage Limit** | ~5-10MB per origin |
| **Data Type** | Strings only |
| **Synchronous** | Yes (blocking) |
| **Scope** | Same origin (protocol + domain + port) |
| **Browser Support** | All modern browsers |

---

## LocalStorage

**LocalStorage** stores data with **no expiration time** - persists even after browser restart.

### Basic Operations

```javascript
// SET - store data
localStorage.setItem('username', 'John');
localStorage.setItem('age', '30');

// GET - retrieve data
let username = localStorage.getItem('username');  // "John"
let age = localStorage.getItem('age');            // "30"

// REMOVE - delete single item
localStorage.removeItem('age');

// CLEAR - delete all items
localStorage.clear();

// CHECK - if key exists
if (localStorage.getItem('username') !== null) {
    console.log('Username exists');
}

// LENGTH - number of items
console.log(localStorage.length);  // number of stored items

// KEY - get key by index
let firstKey = localStorage.key(0);  // first key name
```

### Storing Different Data Types

```javascript
// ❌ PROBLEM: Only stores strings!
localStorage.setItem('number', 123);
let num = localStorage.getItem('number');
console.log(typeof num);  // "string" (not number!)

localStorage.setItem('bool', true);
let bool = localStorage.getItem('bool');
console.log(typeof bool);  // "string" (not boolean!)

// ✅ SOLUTION: Use JSON for complex data
let user = {
    name: 'John',
    age: 30,
    email: 'john@example.com'
};

// Store object
localStorage.setItem('user', JSON.stringify(user));

// Retrieve object
let retrievedUser = JSON.parse(localStorage.getItem('user'));
console.log(retrievedUser.name);  // "John"
console.log(typeof retrievedUser);  // "object"

// Store array
let todos = ['Buy milk', 'Walk dog', 'Code'];
localStorage.setItem('todos', JSON.stringify(todos));

// Retrieve array
let retrievedTodos = JSON.parse(localStorage.getItem('todos'));
console.log(Array.isArray(retrievedTodos));  // true
```

### Iterating Over LocalStorage

```javascript
// Method 1: For loop with key()
for (let i = 0; i < localStorage.length; i++) {
    let key = localStorage.key(i);
    let value = localStorage.getItem(key);
    console.log(key, value);
}

// Method 2: For...in loop
for (let key in localStorage) {
    if (localStorage.hasOwnProperty(key)) {
        console.log(key, localStorage.getItem(key));
    }
}

// Method 3: Object.keys()
Object.keys(localStorage).forEach(key => {
    console.log(key, localStorage.getItem(key));
});
```

### Storage Events

Listen for changes made in **other tabs/windows** (same origin).

```javascript
// Listen for storage changes
window.addEventListener('storage', (e) => {
    console.log('Key changed:', e.key);
    console.log('Old value:', e.oldValue);
    console.log('New value:', e.newValue);
    console.log('Storage area:', e.storageArea);
    console.log('URL:', e.url);
});

// Note: Event fires in OTHER tabs, not the one making the change!
```

### Practical Examples

```javascript
// Example 1: User Preferences
function saveTheme(theme) {
    localStorage.setItem('theme', theme);
    applyTheme(theme);
}

function loadTheme() {
    let theme = localStorage.getItem('theme') || 'light';
    applyTheme(theme);
}

function applyTheme(theme) {
    document.body.className = theme;
}

// Load theme on page load
window.addEventListener('DOMContentLoaded', loadTheme);

// Example 2: Shopping Cart
function addToCart(item) {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.push(item);
    localStorage.setItem('cart', JSON.stringify(cart));
}

function getCart() {
    return JSON.parse(localStorage.getItem('cart')) || [];
}

function clearCart() {
    localStorage.removeItem('cart');
}

// Example 3: Form Data (auto-save)
let formData = {
    name: '',
    email: '',
    message: ''
};

// Save on input
document.querySelectorAll('input, textarea').forEach(field => {
    field.addEventListener('input', (e) => {
        formData[e.target.name] = e.target.value;
        localStorage.setItem('formData', JSON.stringify(formData));
    });
});

// Restore on page load
window.addEventListener('DOMContentLoaded', () => {
    let saved = localStorage.getItem('formData');
    if (saved) {
        formData = JSON.parse(saved);
        Object.keys(formData).forEach(key => {
            let field = document.querySelector(`[name="${key}"]`);
            if (field) field.value = formData[key];
        });
    }
});

// Clear after submission
function submitForm() {
    // ... submit logic
    localStorage.removeItem('formData');
}
```

### Error Handling

```javascript
// Storage can fail (quota exceeded, private browsing, etc.)
function safeSetItem(key, value) {
    try {
        localStorage.setItem(key, value);
        return true;
    } catch (e) {
        if (e.name === 'QuotaExceededError') {
            console.error('Storage quota exceeded');
            // Handle: clear old data, notify user, etc.
        } else {
            console.error('Storage error:', e);
        }
        return false;
    }
}

// Check if localStorage is available
function isLocalStorageAvailable() {
    try {
        let test = '__storage_test__';
        localStorage.setItem(test, test);
        localStorage.removeItem(test);
        return true;
    } catch (e) {
        return false;
    }
}

if (isLocalStorageAvailable()) {
    // Use localStorage
} else {
    // Fallback to cookies or in-memory storage
}
```

---

## SessionStorage

**SessionStorage** is similar to localStorage but **data is cleared when the page session ends** (tab/window closed).

### Basic Operations (Same as localStorage)

```javascript
// SET
sessionStorage.setItem('tempData', 'temporary');

// GET
let data = sessionStorage.getItem('tempData');

// REMOVE
sessionStorage.removeItem('tempData');

// CLEAR
sessionStorage.clear();

// LENGTH
console.log(sessionStorage.length);

// KEY
let key = sessionStorage.key(0);
```

### When to Use SessionStorage

```javascript
// ✅ Good use cases:
// 1. Temporary data during session
sessionStorage.setItem('searchQuery', 'JavaScript tutorials');

// 2. Form data for multi-step forms
sessionStorage.setItem('step1Data', JSON.stringify(formData));

// 3. Authentication tokens (temporary)
sessionStorage.setItem('tempToken', token);

// 4. Wizard/flow state
sessionStorage.setItem('wizardStep', '3');

// 5. One-time notifications
if (!sessionStorage.getItem('welcomeShown')) {
    showWelcomeMessage();
    sessionStorage.setItem('welcomeShown', 'true');
}
```

### SessionStorage vs LocalStorage

```javascript
// localStorage - persists forever
localStorage.setItem('remember', 'me');
// Close browser, reopen → data still there

// sessionStorage - cleared on tab close
sessionStorage.setItem('temporary', 'data');
// Close tab, reopen → data gone

// Each tab has separate sessionStorage
// Tab A: sessionStorage.setItem('key', 'A');
// Tab B: sessionStorage.setItem('key', 'B');
// Both tabs have different values!
```

---

## Cookies

**Cookies** are small pieces of data stored in the browser and **sent with every HTTP request**.

### Setting Cookies

```javascript
// Basic syntax: document.cookie = "key=value";
document.cookie = "username=John";

// Set with expiration (days)
function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        let date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

setCookie('username', 'John', 7);  // Expires in 7 days

// Set with all options
document.cookie = "user=John; expires=Fri, 31 Dec 2025 23:59:59 GMT; path=/; domain=example.com; secure; SameSite=Strict";
```

### Cookie Attributes

| Attribute | Description | Example |
|-----------|-------------|---------|
| **expires** | Expiration date | `expires=Fri, 31 Dec 2025 23:59:59 GMT` |
| **max-age** | Lifetime in seconds | `max-age=3600` (1 hour) |
| **path** | URL path | `path=/` (entire site) |
| **domain** | Domain | `domain=example.com` |
| **secure** | HTTPS only | `secure` |
| **SameSite** | CSRF protection | `SameSite=Strict` or `Lax` or `None` |
| **HttpOnly** | JS cannot access (server-only) | `HttpOnly` (set by server) |

### Getting Cookies

```javascript
// Get all cookies (returns string)
console.log(document.cookie);  // "username=John; theme=dark; lang=en"

// Get specific cookie
function getCookie(name) {
    let nameEQ = name + "=";
    let cookies = document.cookie.split(';');
    
    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim();
        if (cookie.indexOf(nameEQ) === 0) {
            return cookie.substring(nameEQ.length);
        }
    }
    return null;
}

let username = getCookie('username');  // "John"

// Modern approach (if available)
if ('cookieStore' in window) {
    // Cookie Store API (newer browsers)
    let cookie = await cookieStore.get('username');
    console.log(cookie.value);
}
```

### Deleting Cookies

```javascript
// Set expiration to past date
function deleteCookie(name) {
    document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

deleteCookie('username');

// Or use max-age=0
document.cookie = "username=; max-age=0; path=/";
```

### Practical Cookie Examples

```javascript
// Example 1: Remember Me functionality
function rememberUser(username, remember) {
    if (remember) {
        setCookie('username', username, 30);  // 30 days
    } else {
        sessionStorage.setItem('username', username);  // Session only
    }
}

// Example 2: Cookie consent
if (!getCookie('cookieConsent')) {
    showCookieBanner();
}

function acceptCookies() {
    setCookie('cookieConsent', 'true', 365);  // 1 year
    hideCookieBanner();
}

// Example 3: Language preference
function setLanguage(lang) {
    setCookie('lang', lang, 365);
    location.reload();
}

function getLanguage() {
    return getCookie('lang') || 'en';  // Default to English
}

// Example 4: Shopping cart (better in localStorage!)
// Note: Cookies sent with every request (overhead)
// Use localStorage for large data
```

### Cookie Security

```javascript
// ✅ SECURE: HTTPS only
document.cookie = "session=abc123; secure";

// ✅ SECURE: Prevent CSRF attacks
document.cookie = "session=abc123; SameSite=Strict";

// ✅ SECURE: HttpOnly (set by server, not JS)
// Set-Cookie: session=abc123; HttpOnly; Secure; SameSite=Strict

// ❌ INSECURE: Sensitive data in cookies
document.cookie = "password=12345";  // Never do this!

// ❌ INSECURE: No secure flag on sensitive cookies
document.cookie = "sessionId=abc123";  // Use secure flag!
```

---

## Storage Comparison

### Feature Comparison

| Feature | localStorage | sessionStorage | Cookies |
|---------|-------------|----------------|---------|
| **Capacity** | ~5-10MB | ~5-10MB | ~4KB |
| **Lifespan** | Until cleared | Until tab closed | Until expired |
| **Scope** | Same origin | Same origin + tab | Same origin |
| **Sent to Server** | ❌ No | ❌ No | ✅ Yes (every request) |
| **Accessible from** | JavaScript only | JavaScript only | JS + Server |
| **Synchronous** | ✅ Yes | ✅ Yes | ✅ Yes |
| **Browser Support** | All modern | All modern | All browsers |

### When to Use What

```javascript
// ✅ USE LOCALSTORAGE FOR:
// - User preferences (theme, language)
// - Shopping cart
// - Draft content (auto-save)
// - Cached data
// - User settings
localStorage.setItem('theme', 'dark');

// ✅ USE SESSIONSTORAGE FOR:
// - Temporary form data
// - One-time messages
// - Wizard/flow progress
// - Temporary authentication
sessionStorage.setItem('wizardStep', '2');

// ✅ USE COOKIES FOR:
// - Authentication tokens (with HttpOnly, Secure)
// - Server-side session IDs
// - Tracking user activity
// - A/B testing variations
// - Data that server needs
document.cookie = "sessionId=abc123; secure; HttpOnly";

// ❌ DON'T USE COOKIES FOR:
// - Large data (use localStorage)
// - Data server doesn't need (wastes bandwidth)
// - Sensitive data without security flags
```

### Size Limits

```javascript
// Check storage size (approximate)
function getStorageSize(storage) {
    let total = 0;
    for (let key in storage) {
        if (storage.hasOwnProperty(key)) {
            total += key.length + storage[key].length;
        }
    }
    return total;
}

let localSize = getStorageSize(localStorage);
let sessionSize = getStorageSize(sessionStorage);

console.log('localStorage size:', localSize, 'bytes');
console.log('sessionStorage size:', sessionSize, 'bytes');

// Test storage limit
function testStorageLimit() {
    try {
        let i = 0;
        while (true) {
            localStorage.setItem('test' + i++, 'x'.repeat(1000));
        }
    } catch (e) {
        console.log('Storage limit reached at:', i, 'items');
        console.log('Approximate limit:', (i * 1000) / 1024 / 1024, 'MB');
        localStorage.clear();
    }
}
```

---

## JSON

**JSON (JavaScript Object Notation)** is a lightweight data-interchange format.

### JSON Syntax Rules

```javascript
// ✅ VALID JSON
{
    "name": "John",           // Keys must be quoted
    "age": 30,                // Numbers
    "active": true,           // Booleans
    "address": null,          // Null
    "hobbies": ["reading", "coding"],  // Arrays
    "contact": {              // Nested objects
        "email": "john@example.com"
    }
}

// ❌ INVALID JSON
{
    name: "John",             // Keys must be quoted
    age: 30,                  // Trailing comma not allowed
    active: true,
    func: function() {},      // Functions not allowed
    date: new Date(),         // Dates not allowed
    undef: undefined          // undefined not allowed
}
```

### JSON.stringify()

Converts JavaScript value to JSON string.

```javascript
// Basic usage
let obj = { name: 'John', age: 30 };
let json = JSON.stringify(obj);
console.log(json);  // '{"name":"John","age":30}'

// Arrays
let arr = [1, 2, 3];
JSON.stringify(arr);  // '[1,2,3]'

// Primitives
JSON.stringify(42);        // '42'
JSON.stringify('hello');   // '"hello"'
JSON.stringify(true);      // 'true'
JSON.stringify(null);      // 'null'

// undefined is omitted
JSON.stringify({ a: undefined, b: 2 });  // '{"b":2}'

// Functions are omitted
JSON.stringify({ a: function() {}, b: 2 });  // '{"b":2}'

// Dates converted to strings
JSON.stringify({ date: new Date() });  
// '{"date":"2025-01-15T10:30:00.000Z"}'

// NaN and Infinity become null
JSON.stringify({ a: NaN, b: Infinity });  // '{"a":null,"b":null}'
```

### JSON.stringify() with Parameters

```javascript
let obj = {
    name: 'John',
    age: 30,
    password: 'secret123',
    hobbies: ['reading', 'coding']
};

// Replacer function (filter/transform properties)
let json = JSON.stringify(obj, (key, value) => {
    if (key === 'password') return undefined;  // Exclude password
    if (typeof value === 'string') return value.toUpperCase();
    return value;
});
// {"name":"JOHN","age":30,"hobbies":["READING","CODING"]}

// Replacer array (include only specific keys)
let json2 = JSON.stringify(obj, ['name', 'age']);
// {"name":"John","age":30}

// Space parameter (pretty print)
let json3 = JSON.stringify(obj, null, 2);  // 2 spaces indentation
/* 
{
  "name": "John",
  "age": 30,
  "password": "secret123",
  "hobbies": [
    "reading",
    "coding"
  ]
}
*/

let json4 = JSON.stringify(obj, null, '\t');  // Tab indentation
```

### JSON.parse()

Converts JSON string to JavaScript value.

```javascript
// Basic usage
let json = '{"name":"John","age":30}';
let obj = JSON.parse(json);
console.log(obj.name);  // "John"
console.log(obj.age);   // 30

// Arrays
let arrJson = '[1,2,3]';
let arr = JSON.parse(arrJson);
console.log(arr[0]);  // 1

// Primitives
JSON.parse('42');        // 42
JSON.parse('"hello"');   // "hello"
JSON.parse('true');      // true
JSON.parse('null');      // null

// ❌ Invalid JSON throws error
try {
    JSON.parse('{name: "John"}');  // Error: Unexpected token
} catch (e) {
    console.error('Invalid JSON:', e.message);
}

// ❌ Trailing commas not allowed
try {
    JSON.parse('{"a":1,}');  // Error
} catch (e) {
    console.error(e.message);
}
```

### JSON.parse() with Reviver

```javascript
let json = '{"name":"John","birthDate":"2000-01-15","age":30}';

// Reviver function (transform values)
let obj = JSON.parse(json, (key, value) => {
    // Convert date strings to Date objects
    if (key === 'birthDate') {
        return new Date(value);
    }
    return value;
});

console.log(obj.birthDate instanceof Date);  // true
console.log(obj.birthDate.getFullYear());    // 2000

// Remove unwanted properties
let obj2 = JSON.parse(json, (key, value) => {
    if (key === 'age') return undefined;  // Exclude age
    return value;
});
console.log(obj2);  // {name: "John", birthDate: "2000-01-15"}
```

### Deep Clone with JSON

```javascript
// Quick deep clone (has limitations!)
let original = {
    name: 'John',
    address: { city: 'NYC' },
    hobbies: ['reading']
};

let clone = JSON.parse(JSON.stringify(original));

clone.address.city = 'LA';
console.log(original.address.city);  // "NYC" (not affected)

// ⚠️ LIMITATIONS:
let obj = {
    func: function() {},     // Lost
    date: new Date(),        // Becomes string
    regex: /test/,           // Becomes {}
    undef: undefined,        // Lost
    nan: NaN,                // Becomes null
    infinity: Infinity       // Becomes null
};

let cloned = JSON.parse(JSON.stringify(obj));
console.log(cloned);
// {date: "2025-01-15...", regex: {}, nan: null, infinity: null}
```

### Handling Circular References

```javascript
// Circular references throw error
let obj = { name: 'John' };
obj.self = obj;  // Circular reference

try {
    JSON.stringify(obj);
} catch (e) {
    console.error(e.message);  // Converting circular structure to JSON
}

// Solution: Use replacer to handle cycles
function stringifyWithCycles(obj) {
    const seen = new WeakSet();
    
    return JSON.stringify(obj, (key, value) => {
        if (typeof value === 'object' && value !== null) {
            if (seen.has(value)) {
                return '[Circular]';
            }
            seen.add(value);
        }
        return value;
    });
}

console.log(stringifyWithCycles(obj));
// {"name":"John","self":"[Circular]"}
```

### toJSON() Method

```javascript
// Custom JSON serialization
let person = {
    name: 'John',
    age: 30,
    password: 'secret',
    
    toJSON() {
        return {
            name: this.name,
            age: this.age
            // password excluded
        };
    }
};

JSON.stringify(person);  // '{"name":"John","age":30}'

// Date has built-in toJSON
let date = new Date();
JSON.stringify({ date });  
// {"date":"2025-01-15T10:30:00.000Z"}
```

---

## AJAX & Fetch API

**AJAX (Asynchronous JavaScript and XML)** allows making HTTP requests without page reload.

### XMLHttpRequest (Old Way)

```javascript
// XMLHttpRequest (legacy, but still works)
let xhr = new XMLHttpRequest();

xhr.open('GET', 'https://api.example.com/data', true);

xhr.onload = function() {
    if (xhr.status === 200) {
        let data = JSON.parse(xhr.responseText);
        console.log(data);
    } else {
        console.error('Error:', xhr.status);
    }
};

xhr.onerror = function() {
    console.error('Request failed');
};

xhr.send();
```

### Fetch API (Modern Way)

```javascript
// Basic GET request
fetch('https://api.example.com/data')
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));

// With async/await (RECOMMENDED)
async function fetchData() {
    try {
        let response = await fetch('https://api.example.com/data');
        let data = await response.json();
        console.log(data);
    } catch (error) {
        console.error('Error:', error);
    }
}

fetchData();
```

### HTTP Methods

```javascript
// GET - retrieve data
fetch('https://api.example.com/users')
    .then(res => res.json())
    .then(data => console.log(data));

// POST - create data
fetch('https://api.example.com/users', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        name: 'John',
        email: 'john@example.com'
    })
})
    .then(res => res.json())
    .then(data => console.log('Created:', data));

// PUT - update entire resource
fetch('https://api.example.com/users/1', {
    method: 'PUT',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        name: 'John Updated',
        email: 'john.new@example.com'
    })
})
    .then(res => res.json())
    .then(data => console.log('Updated:', data));

// PATCH - partial update
fetch('https://api.example.com/users/1', {
    method: 'PATCH',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        email: 'john.new@example.com'
    })
})
    .then(res => res.json())
    .then(data => console.log('Patched:', data));

// DELETE - remove resource
fetch('https://api.example.com/users/1', {
    method: 'DELETE'
})
    .then(res => {
        if (res.ok) {
            console.log('Deleted successfully');
        }
    });
```

### Fetch Options

```javascript
fetch('https://api.example.com/data', {
    method: 'POST',               // HTTP method
    headers: {                    // Request headers
        'Content-Type': 'application/json',
        'Authorization': 'Bearer token123'
    },
    body: JSON.stringify(data),   // Request body
    mode: 'cors',                 // cors, no-cors, same-origin
    credentials: 'include',       // include, same-origin, omit
    cache: 'no-cache',            // default, no-cache, reload, force-cache
    redirect: 'follow',           // follow, error, manual
    referrerPolicy: 'no-referrer' // no-referrer, origin, etc.
});
```

### Response Object

```javascript
async function fetchData() {
    let response = await fetch('https://api.example.com/data');
    
    // Status
    console.log(response.status);      // 200
    console.log(response.statusText);  // "OK"
    console.log(response.ok);          // true (status 200-299)
    
    // Headers
    console.log(response.headers.get('Content-Type'));
    
    // Body methods
    let json = await response.json();        // Parse as JSON
    let text = await response.text();        // Get as text
    let blob = await response.blob();        // Get as Blob
    let buffer = await response.arrayBuffer(); // Get as ArrayBuffer
    let formData = await response.formData(); // Get as FormData
    
    // Response can only be read once!
    // let json = await response.json();
    // let text = await response.text();  // Error: body already read
}
```

### Error Handling

```javascript
// Fetch only rejects on network errors, not HTTP errors!
async function fetchWithErrorHandling(url) {
    try {
        let response = await fetch(url);
        
        // Check if response is OK (status 200-299)
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        
        let data = await response.json();
        return data;
        
    } catch (error) {
        // Network error or HTTP error
        console.error('Fetch error:', error.message);
        throw error;
    }
}

// Usage
fetchWithErrorHandling('https://api.example.com/data')
    .then(data => console.log(data))
    .catch(error => console.error('Failed:', error));

// Better error handling
async function betterFetch(url) {
    try {
        let response = await fetch(url);
        
        if (!response.ok) {
            // Try to get error message from response
            let errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `HTTP ${response.status}`);
        }
        
        return await response.json();
        
    } catch (error) {
        if (error.name === 'TypeError') {
            // Network error
            throw new Error('Network error - check your connection');
        }
        throw error;
    }
}
```

### Timeout Handling

```javascript
// Fetch doesn't have built-in timeout
// Use AbortController
async function fetchWithTimeout(url, timeout = 5000) {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), timeout);
    
    try {
        let response = await fetch(url, {
            signal: controller.signal
        });
        clearTimeout(timeoutId);
        return await response.json();
    } catch (error) {
        if (error.name === 'AbortError') {
            throw new Error('Request timeout');
        }
        throw error;
    }
}

// Usage
fetchWithTimeout('https://api.example.com/slow-endpoint', 3000)
    .then(data => console.log(data))
    .catch(error => console.error(error.message));
```

### Cancel Requests

```javascript
// Using AbortController
let controller = new AbortController();

fetch('https://api.example.com/data', {
    signal: controller.signal
})
    .then(res => res.json())
    .then(data => console.log(data))
    .catch(error => {
        if (error.name === 'AbortError') {
            console.log('Request cancelled');
        }
    });

// Cancel after 2 seconds
setTimeout(() => controller.abort(), 2000);

// Practical example: Search with debounce
let searchController;

function search(query) {
    // Cancel previous request
    if (searchController) {
        searchController.abort();
    }
    
    // New request
    searchController = new AbortController();
    
    fetch(`https://api.example.com/search?q=${query}`, {
        signal: searchController.signal
    })
        .then(res => res.json())
        .then(data => displayResults(data))
        .catch(error => {
            if (error.name !== 'AbortError') {
                console.error(error);
            }
        });
}
```

### Promises & Async/Await

```javascript
// Promise chain
fetch('https://api.example.com/users/1')
    .then(response => {
        if (!response.ok) throw new Error('Not found');
        return response.json();
    })
    .then(user => {
        console.log('User:', user);
        return fetch(`https://api.example.com/users/${user.id}/posts`);
    })
    .then(response => response.json())
    .then(posts => {
        console.log('Posts:', posts);
    })
    .catch(error => {
        console.error('Error:', error);
    })
    .finally(() => {
        console.log('Request completed');
    });

// Async/await (cleaner)
async function getUserAndPosts(userId) {
    try {
        // Get user
        let userResponse = await fetch(`https://api.example.com/users/${userId}`);
        if (!userResponse.ok) throw new Error('User not found');
        let user = await userResponse.json();
        console.log('User:', user);
        
        // Get posts
        let postsResponse = await fetch(`https://api.example.com/users/${userId}/posts`);
        let posts = await postsResponse.json();
        console.log('Posts:', posts);
        
        return { user, posts };
    } catch (error) {
        console.error('Error:', error);
    } finally {
        console.log('Request completed');
    }
}

getUserAndPosts(1);
```

### Parallel Requests

```javascript
// Sequential (slow)
async function sequential() {
    let user = await fetch('https://api.example.com/users/1').then(r => r.json());
    let posts = await fetch('https://api.example.com/posts/1').then(r => r.json());
    let comments = await fetch('https://api.example.com/comments/1').then(r => r.json());
    
    return { user, posts, comments };
}

// Parallel (fast) - using Promise.all
async function parallel() {
    let [user, posts, comments] = await Promise.all([
        fetch('https://api.example.com/users/1').then(r => r.json()),
        fetch('https://api.example.com/posts/1').then(r => r.json()),
        fetch('https://api.example.com/comments/1').then(r => r.json())
    ]);
    
    return { user, posts, comments };
}

// Promise.allSettled - get all results even if some fail
async function allSettled() {
    let results = await Promise.allSettled([
        fetch('https://api.example.com/users/1'),
        fetch('https://api.example.com/invalid'),  // might fail
        fetch('https://api.example.com/posts/1')
    ]);
    
    results.forEach((result, index) => {
        if (result.status === 'fulfilled') {
            console.log(`Request ${index}: Success`);
        } else {
            console.log(`Request ${index}: Failed -`, result.reason);
        }
    });
}

// Promise.race - first to complete wins
async function race() {
    let fastest = await Promise.race([
        fetch('https://api.slow.com/data'),
        fetch('https://api.fast.com/data')
    ]);
    
    return await fastest.json();
}
```

### Real-World Example: CRUD App

```javascript
const API_URL = 'https://jsonplaceholder.typicode.com';

// GET all users
async function getUsers() {
    try {
        let response = await fetch(`${API_URL}/users`);
        if (!response.ok) throw new Error('Failed to fetch users');
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        return [];
    }
}

// GET single user
async function getUser(id) {
    try {
        let response = await fetch(`${API_URL}/users/${id}`);
        if (!response.ok) throw new Error('User not found');
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        return null;
    }
}

// CREATE user
async function createUser(userData) {
    try {
        let response = await fetch(`${API_URL}/users`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });
        
        if (!response.ok) throw new Error('Failed to create user');
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        return null;
    }
}

// UPDATE user
async function updateUser(id, userData) {
    try {
        let response = await fetch(`${API_URL}/users/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });
        
        if (!response.ok) throw new Error('Failed to update user');
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        return null;
    }
}

// DELETE user
async function deleteUser(id) {
    try {
        let response = await fetch(`${API_URL}/users/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Failed to delete user');
        return true;
    } catch (error) {
        console.error('Error:', error);
        return false;
    }
}

// Usage
async function demo() {
    // Get all users
    let users = await getUsers();
    console.log('Users:', users);
    
    // Create new user
    let newUser = await createUser({
        name: 'John Doe',
        email: 'john@example.com'
    });
    console.log('Created:', newUser);
    
    // Update user
    let updated = await updateUser(1, {
        name: 'John Updated'
    });
    console.log('Updated:', updated);
    
    // Delete user
    let deleted = await deleteUser(1);
    console.log('Deleted:', deleted);
}
```

---

## Interview Questions

### Storage APIs

**Q1: What's the difference between localStorage and sessionStorage?**
- **localStorage**: Persists until manually cleared, shared across all tabs/windows
- **sessionStorage**: Cleared when tab/window closed, separate for each tab

**Q2: What is the storage limit for localStorage?**
Approximately 5-10MB per origin (varies by browser).

**Q3: Can you store objects in localStorage?**
No, only strings. Convert objects using `JSON.stringify()` to store and `JSON.parse()` to retrieve.

```javascript
localStorage.setItem('user', JSON.stringify(user));
let user = JSON.parse(localStorage.getItem('user'));
```

**Q4: How to check if localStorage is available?**
```javascript
function isLocalStorageAvailable() {
    try {
        localStorage.setItem('test', 'test');
        localStorage.removeItem('test');
        return true;
    } catch (e) {
        return false;
    }
}
```

**Q5: What are cookies and when to use them?**
Small data pieces sent with every HTTP request. Use for authentication tokens, session IDs, and data the server needs. Don't use for large data.

**Q6: What are the main differences between cookies and localStorage?**
- **Cookies**: ~4KB, sent with requests, can be HttpOnly, have expiration
- **localStorage**: ~5-10MB, client-side only, no expiration

**Q7: What is the storage event?**
Fires when storage changes in OTHER tabs/windows (same origin). Useful for syncing data across tabs.

```javascript
window.addEventListener('storage', (e) => {
    console.log('Changed:', e.key, e.newValue);
});
```

### JSON

**Q8: What is JSON?**
JavaScript Object Notation - a lightweight data-interchange format. Text-based, language-independent, easy to read/write.

**Q9: What are the rules for valid JSON?**
- Keys must be quoted strings
- Values: string, number, boolean, null, array, object
- No trailing commas
- No comments
- No undefined, functions, or dates

**Q10: What's the difference between JSON.stringify() and JSON.parse()?**
- `JSON.stringify()`: Converts JS value → JSON string
- `JSON.parse()`: Converts JSON string → JS value

**Q11: How to deep clone an object with JSON?**
```javascript
let clone = JSON.parse(JSON.stringify(original));
```
**Limitations**: Loses functions, dates become strings, undefined removed, NaN/Infinity become null.

**Q12: What happens to functions in JSON.stringify()?**
They're omitted from the output.

```javascript
JSON.stringify({ a: 1, b: function() {} });  // '{"a":1}'
```

### AJAX & Fetch

**Q13: What is AJAX?**
Asynchronous JavaScript and XML - technique for making HTTP requests without page reload. Allows updating parts of page dynamically.

**Q14: What's the difference between XMLHttpRequest and Fetch?**
- **Fetch**: Modern, promise-based, cleaner syntax, doesn't reject on HTTP errors
- **XMLHttpRequest**: Older, callback-based, more complex

**Q15: Does fetch reject on HTTP errors (404, 500)?**
No! Fetch only rejects on network errors. HTTP errors return resolved promise. Must check `response.ok`.

```javascript
let response = await fetch(url);
if (!response.ok) throw new Error('HTTP error');
```

**Q16: How to make a POST request with fetch?**
```javascript
fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
});
```

**Q17: How to handle fetch timeout?**
Use AbortController with setTimeout.

```javascript
const controller = new AbortController();
setTimeout(() => controller.abort(), 5000);

fetch(url, { signal: controller.signal });
```

**Q18: How to cancel a fetch request?**
```javascript
const controller = new AbortController();
fetch(url, { signal: controller.signal });
controller.abort();  // Cancel request
```

**Q19: What is the difference between Promise.all() and Promise.allSettled()?**
- **Promise.all()**: Rejects if any promise rejects
- **Promise.allSettled()**: Waits for all, returns results regardless of success/failure

**Q20: How to make parallel requests?**
```javascript
let [users, posts] = await Promise.all([
    fetch('/users').then(r => r.json()),
    fetch('/posts').then(r => r.json())
]);
```

**Q21: What is CORS?**
Cross-Origin Resource Sharing - security mechanism that restricts requests from different origins. Server must send proper CORS headers.

**Q22: How to send credentials with fetch?**
```javascript
fetch(url, { credentials: 'include' });
```

**Q23: Can you read fetch response multiple times?**
No! Response body can only be read once. Clone if needed.

```javascript
let response = await fetch(url);
let clone = response.clone();
let json = await response.json();
let text = await clone.text();
```

**Q24: What's the difference between GET and POST?**
- **GET**: Retrieve data, parameters in URL, cacheable, no body
- **POST**: Send data, parameters in body, not cached, has body

**Q25: How to handle errors in async/await?**
```javascript
async function fetchData() {
    try {
        let response = await fetch(url);
        if (!response.ok) throw new Error('HTTP error');
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}
```

---

## Quick Reference

### Storage APIs
```javascript
// localStorage
localStorage.setItem('key', value);
localStorage.getItem('key');
localStorage.removeItem('key');
localStorage.clear();

// sessionStorage (same API)
sessionStorage.setItem('key', value);

// Cookies
document.cookie = "key=value; expires=...; path=/";
```

### JSON
```javascript
// Stringify
JSON.stringify(obj);
JSON.stringify(obj, replacer, 2);  // pretty print

// Parse
JSON.parse(jsonString);
JSON.parse(jsonString, reviver);

// Deep clone
JSON.parse(JSON.stringify(obj));
```

### Fetch
```javascript
// GET
fetch(url)
    .then(r => r.json())
    .then(data => console.log(data));

// POST
fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
});

// Async/await
async function getData() {
    let response = await fetch(url);
    if (!response.ok) throw new Error('Error');
    return await response.json();
}

// Parallel
Promise.all([fetch(url1), fetch(url2)]);
```

---

**🎉 Complete! All 3 JavaScript Files Done!** 

You now have comprehensive guides for:
1. ✅ **JS Fundamentals & Data Structures**
2. ✅ **Core Concepts (Execution, Scope, Hoisting, This)**
3. ✅ **Browser APIs & Data Handling**

Ready to build amazing web applications! 🚀