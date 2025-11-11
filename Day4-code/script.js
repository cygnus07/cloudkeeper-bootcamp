// ES6 Assignment Solutions

// convert to Arrow Function
const add = (a, b) => {
    return a + b;
};
// console.log(add(5, 3))
// console.log(add(10, 20))


// Default Parameters
const greet = (name, message = "Welcome!") => {
    return `${message} ${name}`
};
// console.log(greet("John"))
// console.log(greet("Sarah", "Hello!"))


// Template Literals
const formatString = (name, age) => {
    return `Hello, my name is ${name} and I am ${age} years old.`
};
// console.log(formatString("Alice", 25))
// console.log(formatString("Bob", 30))


// Object Destructuring
const person = {
    name: 'Ghost',
    age: 25,
    address: {
        city: 'Winterfell',
        country: 'Seven Kingdoms'
    }
};

const extractPersonInfo = (person) => {
    const { name, address: { city } } = person
    console.log(`${name} lives in ${city}.`)
    console.log(person.address)
}
extractPersonInfo(person)


//  Rest Operator
const sumAll = (...numbers) => {
    let total = 0
    for (let num of numbers) {
        total += num
    }
    return total
};
// console.log(sumAll(1, 2, 3))
// console.log(sumAll(10, 20, 30, 40))


// filter Even Numbers
const filterEvens = (arr) => {
    return arr.filter((num) => num % 2 === 0)
}
// console.log(filterEvens([1, 2, 3, 4, 5, 6]))
// console.log(filterEvens([10, 15, 20, 25, 30]))


// array Mapping
const doubleValues = (arr) => {
    return arr.map((num) => num * 2)
}
// console.log(doubleValues([1, 2, 3]))
// console.log(doubleValues([5, 10, 15]))


// find the Maximum
const findMax = (arr) => {
    return Math.max(...arr)
};
// console.log(findMax([3, 5, 7, 2, 8]))
// console.log(findMax([100, 200, 50, 75]))


// Object and Array Destructuring
const data = [
    {
        name: "jon",
        age: 24
    },
    {
        name: "tyrion",
        age: 21
    }
];

const extractInfo = (data) => {
    const [{ age, name }] = data
    return `${name}'s age is ${age}.`
}
// console.log(extractInfo(data))


// data Manipulation using Array functions
const products = [{
    "id": 1,
    "title": "Backpack",
    "price": 109.95,
    "description": "Your perfect pack for everyday use",
    "category": "men's clothing",
    "rating": {
        "rate": 3.9,
        "count": 120
    }
}]

const filterKeys = (products) => {
    return products.map((product) => {
        return {
            id: product.id,
            title: product.title,
            rate: product.rating.rate,
            count: product.rating.count
        }
    })
}
// console.log(filterKeys(products))


//  default Parameter
const fun = (name = "abc") => {
    if (name) {
        console.log(name)
    } else {
        console.log(name)
    }
};

// console.log("Output:")
// fun("");




// flatten nested arrays
const flattenArray = (arr) => {
    // return arr.flat(Infinity)
    let result = []
    for(let i = 0; i < arr.length; i++){
        if(Array.isArray(arr[i])){
            let flattened = flattenArray(arr[i])
            for(let j = 0; j < flattened.length; j++){
                result[result.length] = flattened[j]
            }
        } else {
            result[result.length] = arr[i]
        }
    }
    return result
}
// console.log(flattenArray([1, [2, [3, [4, 5]]]]))
// console.log(flattenArray([1, [2, 3], [4, [5, 6]]]))
