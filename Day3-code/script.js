var products = [
    new Product("Iphone 17", "$399", "Smartphones"),
    new Product("Mars", "$39", "Chocolate"),
    new Product("Sapiens", "$39", "Books"),
    new Product("The Great Gatsby", "$59", "Books")
]

// console.log(Products)

// products.forEach((product,index) => console.log(product))
// products.forEach((p) => console.log(p))
// console.log(products[0])

// console.log(typeof(products))

function Product(name, price, category) {
    this.name = name
    this.price = price
    this.category = category
}


Product.prototype.getDiscountedPrice = function(percent) {
    var newPrice = this.price - (this.price * percent / 100)
    return newPrice
}

