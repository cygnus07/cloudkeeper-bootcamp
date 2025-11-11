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
        // arr.push(num)
        arr[arr.length] = num
    }

    // console.log(arr)
    // addNumAtEnd(arr,10)
    // console.log(arr)


    // assignment 4
    function removeFirstNum(arr){
        // arr.shift()
        for(var i = 0; i < arr.length - 1; i++){
            arr[i] = arr[i + 1]
        }
        arr.length = arr.length - 1
    }

    // console.log(arr)
    // removeFirstNum(arr)
    // console.log(arr)


    // assignment 5
    function insertNumAtIndex(arr,index,num){
        // arr.splice(index,0,num)
        for(var i = arr.length; i > index; i--){
            arr[i] = arr[i - 1]
        }
        arr[index] = num
    }

    // console.log(arr)
    // insertNumAtIndex(arr,2,10)
    // console.log(arr)


    // assignment 6
    function replaceNumber(arr,a,b){
    // return arr.map(num => (num === a? b: num))
    var res = []
    for(var i = 0; i < arr.length; i++){
        res[i] = arr[i] === a ? b : arr[i]
    }
    return res
    }

    // console.log(arr)
    // var newArr = replaceNumber(arr, 2, 100)
    // console.log(newArr)

    // assignment 7
    function merge(arr, left, mid, right){
        var n1 = mid - left + 1
        var n2 = right - mid
        var leftArr = []
        var rightArr = []

        for(var i = 0; i < n1; i++){
            leftArr[i] = arr[left + i]
        }
        for(var j = 0; j < n2; j++){
            rightArr[j] = arr[mid + 1 + j]
        }

        var i = 0, j = 0, k = left
        while(i < n1 && j < n2){
            if(leftArr[i] <= rightArr[j]){
                arr[k] = leftArr[i]
                i++
            } else {
                arr[k] = rightArr[j]
                j++
            }
            k++
        }

        while(i < n1){
            arr[k] = leftArr[i]
            i++
            k++
        }

        while(j < n2){
            arr[k] = rightArr[j]
            j++
            k++
        }
    }

    function sortArray(arr, left, right) {
        if(left === undefined) left = 0
        if(right === undefined) right = arr.length - 1

        if(left < right){
            var mid = Math.floor((left + right) / 2)
            sortArray(arr, left, mid)
            sortArray(arr, mid + 1, right)
            merge(arr, left, mid, right)
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
        // return arr.indexOf(num)
        for(var i = 0; i < arr.length; i++){
            if(arr[i] === num){
                return i
            }
        }
        return -1
    }

    // console.log(arr)
    // console.log(findIndex(arr, 4))

