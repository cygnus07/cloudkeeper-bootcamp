function Product(name, price, category) {
    this.name = name;
    this.price = price;
    this.category = category;
}

Product.prototype.getDiscountedPrice = function(percent) {
    var newPrice = this.price - (this.price * percent / 100);
    return newPrice;
};

(function() {
    var products = [
        new Product("Iphone 17", 399, "Smartphones"),
        new Product("Mars", 39, "Chocolate"),
        new Product("Sapiens", 49, "Books"),
        new Product("The Great Gatsby", 59, "Books")
    ];

    var container = document.getElementById("product-container");

    for (var i = 0; i < products.length; i++) {
        var card = document.createElement('div');
        card.className = 'product-card';

        var name = document.createElement('h3');
        name.textContent = "Product Name: " + products[i].name;

        var price = document.createElement('h3');
        price.textContent = "Product Price: " + products[i].price;

        var category = document.createElement('h3');
        category.textContent = "Product Category: " + products[i].category;

        card.appendChild(name);
        card.appendChild(price);
        card.appendChild(category);

        container.appendChild(card);
    }
})();
