var arr = [1,2,3,4,5]

// assignment 1
var form = document.getElementById('myForm');
if (form) {
    form.addEventListener('submit', function(event) {
        var name = document.getElementById('name').value;
        var age = document.getElementById('age').value;

        if (name.trim() === '' || age < 18) {
            event.preventDefault();
            alert('Please enter a valid name and age must be 18 or above');
        }
    });
}

// assignment 2
var colorDiv = document.getElementById('colorDiv');
if (colorDiv) {
    colorDiv.addEventListener('mouseover', function() {
        colorDiv.style.backgroundColor = 'red';
    });

    colorDiv.addEventListener('mouseout', function() {
        colorDiv.style.backgroundColor = 'green';
    });
}

// assignment #3
function addNumAtEnd(arr,num){
     arr.push(num)
}

// console.log(arr)
// addNumAtEnd(arr,10)
// console.log(arr)


// assignment 4
function removeFirstNum(arr){
     arr.shift()
}

// console.log(arr)
// removeFirstNum(arr)
// console.log(arr)


// assignment 5
function insertNumAtIndex(arr,index,num){
    arr.splice(index,0,num)
}

// console.log(arr)
// insertNumAtIndex(arr,2,10)
// console.log(arr)


// assignment 6
function replaceNumber(arr,a,b){
  return arr.map(num => (num === a? b: num))
}

// console.log(arr)
// var newArr = replaceNumber(arr, 2, 100)
// console.log(newArr)

// assignment 7
function sortArray(arr) {
    for (var i = 0; i < arr.length - 1; i++) {
        for (var j = 0; j < arr.length - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                var temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

// console.log(arr)
// sortArray(arr)
// console.log(arr)


// assignment 8
function reverseArray(arr){
    var i=0, j=arr.length-1
    while(i<j){
        [arr[i], arr[j]] = [arr[j], arr[i]]
        i++
        j--
    }
}

// console.log(arr)
// reverseArray(arr)
// console.log(arr)

// assingment 9
function findIndex(arr, num){
    return arr.indexOf(num)
}

// console.log(arr)
// console.log(findIndex(arr, 4))





