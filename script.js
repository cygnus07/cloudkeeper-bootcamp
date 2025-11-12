const merge = (arr, left, mid, right) => {
    let leftArr = arr.slice(left, mid+1)
    let rightArr = arr.slice(mid+1, right+1)

    let i=0, j=0, k =left
    while(i<leftArr.length && j<rightArr.length){
        if(leftArr[i] < rightArr[j]) arr[k++] = leftArr[i++]
        else arr[k++] = rightArr[j++]
    }

    while(i<leftArr.length) arr[k++] = leftArr[i++]
    while(j<rightArr.length) arr[k++] = rightArr[j++]
}

const sortArray = (arr, left=0, right=arr.length-1) => {
    if(left < right){
        let mid = Math.floor((left+right)/2)
        sortArray(arr, left, mid)
        sortArray(arr, mid+1, right)
        merge(arr, left, mid, right)
    }   
}

let arr = [1,7,2,0,4,3]
console.log(arr)
sortArray(arr)
console.log(arr)